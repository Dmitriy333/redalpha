package com.redalpha.test.model.call;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redalpha.test.util.JsonDateSerializer;

/**
 * Call entity.
 */
public class Call {
    private String phone;
    private String firstname;
    private String lastname;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date time;

    public Call() {
    }

    public Call(String phone, String firstname, String lastname, Date time) {
        super();
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
