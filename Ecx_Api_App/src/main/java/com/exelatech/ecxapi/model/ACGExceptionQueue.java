package com.exelatech.ecxapi.model;

import java.io.Serializable;

public class ACGExceptionQueue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String msgId;
	private String corelationId;
	private int priority;
	private boolean redelivered;
	private long timestamp;
	private String type;
	private String msg;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getCorelationId() {
		return corelationId;
	}

	public void setCorelationId(String corelationId) {
		this.corelationId = corelationId;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isRedelivered() {
		return redelivered;
	}

	public void setRedelivered(boolean redelivered) {
		this.redelivered = redelivered;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ACGExceptionQueue [msgId=" + msgId + ", corelationId=" + corelationId + ", priority=" + priority
				+ ", redelivered=" + redelivered + ", timestamp=" + timestamp + ", type=" + type + "]";
	}

}
