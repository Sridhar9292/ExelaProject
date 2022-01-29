package com.exelatech.ecx.backend.dao;

import javax.persistence.*;

@Entity
@Table(name = "dps2.job_status")
@NamedQueries({@NamedQuery(name = "DpsJobStatus.getJobsStatus", query = "SELECT c FROM DpsJobStatus c")})
public class DpsJobStatus {

    @Id
    @Column(name = "id")
    private String Id;

    @Column(name = "name")
    private String Name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public DpsJobStatus() {
    }
    
}