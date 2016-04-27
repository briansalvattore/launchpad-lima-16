package com.horses.launchpad.lima16;

import java.util.Date;

/**
 * @author Brian Salvattore
 */
public class MessageEntity {

    private String message;
    private Date date;
    private PersonEntity entity;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonEntity getEntity() {
        return entity;
    }

    public void setEntity(PersonEntity entity) {
        this.entity = entity;
    }
}
