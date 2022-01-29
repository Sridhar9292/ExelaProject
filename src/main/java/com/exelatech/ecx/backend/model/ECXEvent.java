package com.exelatech.ecx.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

public class ECXEvent extends TreeMap<String, String> implements Serializable, ElasticSearchID {
    private static final long serialVersionUID = 1L;

    // Common Members
    public static final String ID = "eventID";
    public static final String FEED_DOC_ID = "feedDocID";
    public static final String CLIENT_CODE = "clientCode";
    public static final String CLIENT_LOB_CODE = "clientLobCode";
    public static final String TIMESTAMP = "eventTimestamp";
    public static final String FEED_DOC_AUTHOR = "feedDocAuthor";
    public static final String NAME = "eventName";
    public static final String FEED_DOC_TYPE = "feedDocType";

    // Other Members
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_DESC = "errorDesc";
    public static final String ERROR_CAUSE = "errorCause";
    public static final String ERROR_RESOLUTION = "errorResolution";
    public static final String ERROR_MESSAGE = "errorMessage";

    // Print Members
    public static final String JOB_NUMBER = "JobNumber";
    public static final String PP_JOBID = "PPJobID";
    public static final String PP_SUBJOBID = "PPSubJobID";
    public static final String RECORD_TYPE = "errorMessage";
    public static final String APP_CODE = "AppID";
    public static final String SUBEVENT_NAME = "subEvent";
    public static final String FILE_NAME = "Filename";

	public static final String MAJOR_WO = "MajorWO";
	public static final String MINOR_WO = "MinorWO";

	public List<String> tags = new ArrayList<String>();
	public void setTags(List<String> tags) { this.tags = tags; }
	public List<String> getTags() { return this.tags; } 

    public ECXEvent() {
        super.put(ECXEvent.ID, UUID.randomUUID().toString());
    }

    public ECXEvent(String name) {
        super.put(ECXEvent.ID, UUID.randomUUID().toString());
        super.put(NAME, name);
    }

    public ECXEvent(ECXEvent event) {
        super(event);
    }

    // Accessors
    public String getId() {
        return get(ECXEvent.ID);
    }

    public void setId(String id) {
        put(ECXEvent.ID, id);
    }

    public String getEventName(){
        return get(ECXEvent.NAME);
    }

    public void setEventName(String eventName){
        super.put(ECXEvent.NAME, eventName);
    }

    /**
     * Provides a builder pattern (ie flow)
     */
    public ECXEvent set(String key, String value) {
        this.put(key, value);
        return this;
    }

    // Evaluators
    @Override
    /** equals - Two events are equal if they have the value of their NAME properties are the same (case insensative). */
    public boolean equals(Object e) {
        return ((ECXEvent) e).get(NAME).equalsIgnoreCase(this.get(NAME));
    }

};