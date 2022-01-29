package com.exelatech.ecx.backend.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.dao.Dao;
import com.exelatech.ecx.backend.dao.Prisma;
import com.exelatech.ecx.backend.domain.Setting;
import com.exelatech.ecx.backend.service.IRequest;

public class PrismaController implements IRequest {
    // SAME THAT IREQUEST
    static Logger log = Logger.getLogger(PrismaController.class.getName());

    private ArrayList<Prisma> data;
    Setting _setting;

    @Override
    public void Setting(com.exelatech.ecx.backend.domain.Setting setting) {
        _setting = setting;
    }

    @Override
    public void RequestData() throws Exception {
        LocalDate localdate = LocalDate.now().minusDays(_setting.getJobActivityInterval());
        Date date = java.sql.Date.valueOf(localdate);
        Dao<Prisma> dao = new Dao<Prisma>("job_activity");
        log.info("Getting job activity from Prisma/job activity");
        this.data = new ArrayList<Prisma>(dao.selectMultiple("jobactivity.orders", "begin_date", date));
        dao.Close();

    }

    public ArrayList<Prisma> getData() {
        return data;
    }

    public void setData(ArrayList<Prisma> data) {
        this.data = data;
    }

}