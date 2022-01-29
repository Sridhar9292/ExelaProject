package com.exelatech.ecx.backend.monitor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exelatech.ecx.backend.model.ECXEvent;


/**
 * Monitors a remit datafeed workflow to help determine if all the billers that were imported 
 * (including empty batches) have corresponding events in another step (eg. export or send), 
 * within a timeframe.  If not, an error event is generated and sent to the error event queue.
 * 
 * 
 * The scatter/gather pattern is employed, but it is unknown how many items (billers) are in play.
 * 
 * The events come in: scatter event (billerID) and then hopefully the corresponding (billerID) gather event.
 * 
 * An initial timeout window is started at the appearance of the first scatter event to let all 
 * the scattered events come in, after which they are considered 'late'.  
 * 
 * Gather events are paired up with their corresponding scatter events based on billerID. 
 * Each pair is also given its own time frame to reconcile - a pair-timeout.  
 * 
 * If at the end of the initial window, there are any unpaired scatter events who's pair timeout would
 * extend the initial window, then the initial window is extended by that difference.  
 * As long as scatter events appear before any extended timeout, the new arrival can similarly extend the timeout again.
 * (After that, perhaps even a little grace period, as if to say, "are you sure you're all done? Are there any more?")
 * 
 * Once the initial (extended and graced) time period expires, if there are unpaired scatter events, 
 * issue error event which contain a snapshot of the paired and unpaired events.
 * 
 * Even after the main time window finally expires, if gather events eventually come in, 
 * then they can still be paired up.  If this happens, and they all pair up, then a success event is sent.  
 * Note that after the main time window finally expires,  the status can
 * flicker back and forth if new scatter events pair-timeout, and their partner then appears.
 * 
 * Not all billers deliver to a file - PNG delivers to webquery.  Must pair up 'delivered' events.
 *   
 * Two monitors would be used for the dashboard:  1 for the export step and 1 for the send step.
 * That way, the send step can start showing sending for those files that are sent, 
 * even if export didn't finish yet or had an error.  If only some of the export files ever get
 * exported, Send would eventually error out also since it would timeout not having matched 
 * all the biller events with sent files either. Likewise, if the missing/late files appear, 
 * and are sent, the Send step will go to success after the export step does.
 * 
 * @author WilliamMcDonald
 *
 */
public class RemitBillerFeedMonitor implements EventMonitor { //implements Action {
	private static final Logger logger = LogManager.getLogger();

	private long timeout=900000L; // default = 900 seconds; or 15mins;
		public EventMonitor setTimeout(long timeout) { this.timeout=timeout; return this; }
		public long getTimeout() { return this.timeout; }

	private long eventTimeout=30000L; // default = 30 seconds;
		public EventMonitor setPairingTimeout(long timeout) { this.eventTimeout=timeout; return this; }
		public long getPairingTimeout() { return this.eventTimeout; }

	private long gracetime=5000L; // default = 5 seconds;
	private long endTime = 0L;	// actual window timeout time. (extendable)
		private synchronized long getEndTime() { return endTime; }
		private synchronized long setEndTime(long newTime) { long oldEndTime=endTime; endTime=newTime; return oldEndTime; }

	// records the count, start and pair events by client-clientLOB.
	private Map<String, Map<Pair<String,String>, ECXEvent>> records = new HashMap<String, Map<Pair<String,String>, ECXEvent>>();
	
	private String countType, startType, pairType;
	private ECXEvent successEvent;
	private ECXEvent errorEvent;
	private ECXEvent warningEvent;

	public RemitBillerFeedMonitor(ECXEvent count, ECXEvent start, ECXEvent pair, ECXEvent success, ECXEvent error, ECXEvent warning) {
		countType=count.get(ECXEvent.NAME);
		startType=start.get(ECXEvent.NAME);
		pairType=pair.get(ECXEvent.NAME);
		this.successEvent=success;
		this.errorEvent=error;
		this.warningEvent=warning;		
		records.put(countType, new LinkedHashMap<Pair<String,String>, ECXEvent>());
		records.put(startType, new LinkedHashMap<Pair<String,String>, ECXEvent>());
		records.put(pairType,  new LinkedHashMap<Pair<String,String>, ECXEvent>());
	}
	
	EventMonitorListener listener = null;
	private String feedDocID = null;

	@Override
	public EventMonitor setListener(EventMonitorListener listener) { this.listener=listener; return this; }

	public EventMonitor notify(ECXEvent event) throws InterruptedException 
	{
		if (records.keySet().contains(event.get(ECXEvent.NAME))) // event of interest
		{ 
			// record the event indexed by client-clientLOB
			Map<Pair<String,String>, ECXEvent> record = records.get(event.get(ECXEvent.NAME));
			String clientCode = event.get(ECXEvent.CLIENT_CODE);
			String clientLobCode = event.get(ECXEvent.CLIENT_LOB_CODE);

			/*
			// if necessary, determine the CCL by matching the output file names.
			if ((null == clientCode || null == clientLobCode) && event.get(ECXEvent.NAME).equals(startType) ) { 
				String outputFilename = event.get("outFilename");
				for (ECXEvent startEvent : records.get(startType).values())
					if (outputFilename.equalsIgnoreCase(startEvent.get("outFileName"))) {
						clientCode = startEvent.get(ECXEvent.CLIENT_CODE);
						clientLobCode = startEvent.get(ECXEvent.CLIENT_LOB_CODE);
						break;
					}
			}
			*/
			
			// store the event
			Pair<String,String> ccl = new ImmutablePair<String,String>(clientCode, clientLobCode);
			record.put(ccl, event);
			logger.debug("Recording {} event for client={} event={}", 
				event.get(ECXEvent.NAME).equalsIgnoreCase(countType)?"COUNT":event.get(ECXEvent.NAME).equalsIgnoreCase(startType)?"START":"PAIR",
				ccl, event);
			
			// determine any action to take.
			long now = System.currentTimeMillis();
			logger.debug("now={} and timeout={}", now, timeout);
			
			// TODO - Should the timeout window start at the first count event, the first start event or some other event like RemitDataImported?  Or a combination?
			if (getEndTime() <= 0)  
				setAlarm(now+timeout); // start alarm
			if (null == feedDocID)
				feedDocID=event.get(ECXEvent.FEED_DOC_ID);
			if (startType.equalsIgnoreCase(event.get(ECXEvent.NAME))) 
				setAlarm(now+getPairingTimeout());	// extend alarm if necessary
			if (pairType.equalsIgnoreCase(event.get(ECXEvent.NAME)) && now >= getEndTime()) 
				listener.send(completenessEvent());
		}
		return this;
	}
	
	private void setAlarm(long time) {
		logger.debug("Try to set alarm to {}", time);
		if (time > getEndTime()) setEndTime(time);
		logger.debug("Alarm set to {}", getEndTime());
		startAlarm();
	}
	
	Thread alarm = null;
	private void startAlarm() {
		if (null==alarm) {
			alarm=new Thread() {
				public void run() {
					try {
						logger.debug("Initial endtime={} now={}", getEndTime(), System.currentTimeMillis());
						for (long now = System.currentTimeMillis(); getEndTime() > now; now=System.currentTimeMillis())
						{
							Thread.sleep(getEndTime() - now);
							logger.debug("endtime={} now={}", getEndTime(), System.currentTimeMillis());
						}
						listener.send(completenessEvent());
					} catch (InterruptedException e) {
						logger.catching(e); e.printStackTrace();
					} catch (Exception ex) {
						logger.catching(ex); ex.printStackTrace();
					}
					logger.debug("finishing alarm...");
				}
			}; 
		}
		if (!alarm.isAlive())// && !alarm.isInterrupted())
		{
			logger.debug("Starting alarm thread.");
			alarm.start();
		}
		else logger.debug("Thread is still alive.");
	}

	private ECXEvent completenessEvent() {
		ECXEvent event = null;
		long now = System.currentTimeMillis();
		logger.debug("now={}", now);
		if ( (records.get(startType).keySet().equals(records.get(pairType).keySet())) && 
	 	 	 (records.get(countType).keySet().equals(records.get(startType).keySet()))) { // all expected paired up
			successEvent.put("eventTimestamp", Long.toString(now));
			successEvent.put("feedDocID", feedDocID);
			successEvent.putAll(getSnapshot());
			event=successEvent;
			// stop timer ?
		} else if (warningEvent.get(ECXEvent.NAME).equals("BillerRemitDataExportWarning")) {
			warningEvent.put("eventTimestamp", Long.toString(now));
			warningEvent.put("feedDocID", feedDocID);
			warningEvent.put(ECXEvent.ERROR_MESSAGE,"Unable to export within "+timeout/60/1000 +" min");
			warningEvent.putAll(getSnapshot());
			event =warningEvent;
		}else {
			errorEvent.put("eventTimestamp", Long.toString(now));
			errorEvent.put("feedDocID", feedDocID);
			errorEvent.put(ECXEvent.ERROR_MESSAGE,"Unable to export within "+timeout/60/1000 +" min");
			errorEvent.putAll(getSnapshot()); // TODO - should the snapshot objects be part of the body?
			event=errorEvent;
		}
		logger.debug("event={}", event);
		event.setId(UUID.randomUUID().toString());
		return event;
	}
	
	private Map<String, String> getSnapshot() 
	{
		Map<String, String> eventList = new HashMap<String, String>();
		eventList.put("expectedEvents",	getEventIDs(countType));
		eventList.put("startedEvents", 	getEventIDs(startType));
		eventList.put("pairedEvents", 	getEventIDs(pairType));
		return eventList;
	}
	
	private String getEventIDs(String type) {
		StringBuffer ids = new StringBuffer();
		for (ECXEvent event : records.get(type).values()) 
			ids.append(event.get(ECXEvent.ID)).append(",");
		return (ids.length() > 0) ? ids.substring(0, ids.lastIndexOf(",")) : "";
	}
}
