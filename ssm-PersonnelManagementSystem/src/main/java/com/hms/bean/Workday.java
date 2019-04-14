package com.hms.bean;

public class Workday {
    private Integer id;

    private String year;

    private String month;

    private Integer workdays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public Integer getWorkdays() {
        return workdays;
    }

    public void setWorkdays(Integer workdays) {
        this.workdays = workdays;
    }
}