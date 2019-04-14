package com.hms.bean;

import java.sql.Time;
import java.util.Date;

public class Attendance {
    private Integer id;

    private Time intime;

    private Time outtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Time intime) {
        this.intime = intime;
    }

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Time outtime) {
        this.outtime = outtime;
    }
}