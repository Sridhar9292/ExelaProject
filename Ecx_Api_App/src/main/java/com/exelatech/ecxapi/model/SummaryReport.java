package com.exelatech.ecxapi.model;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SummaryReport implements Serializable {
    private static final long serialVersionUID = -2160412830967522355L;
    public String fileName;
    public String filePath;
    public Date lastModified;
}
