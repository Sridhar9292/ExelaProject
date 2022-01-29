package com.exelatech.ecx.backend.domain;

import com.exelatech.ecx.backend.service.IKey;

public class MoveItc implements IKey {
    
    private String FileName;
    private String Time;
    private String TaskId;
    private String TaskName;
    private String id;
    private String key;
    
    // Alternate for _Type search
    private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String GetPK() {
        this.id= getKey();
        return getKey();
    }

    public MoveItc() {
    }
}