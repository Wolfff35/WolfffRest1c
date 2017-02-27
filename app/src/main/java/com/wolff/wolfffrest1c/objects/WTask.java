package com.wolff.wolfffrest1c.objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolff on 07.02.2017.
 */

public class WTask extends WCatalog implements Serializable{
    private static final long serialVersionUID = 2163051469151804396L;
    private WUsers author;
    private WUsers programmer;

    private String text;
    private String ps;

    private boolean isClosed;
    private Date dateClosed;

    private Date dateInWork;
    private boolean isInWork;

    private Date dateCreate;

    public WTask(){
    }
    public WTask(String guid, String id, String name,WUsers author,WUsers programmer,String text,String ps,boolean isClosed,Date dateClosed, boolean isInWork,Date dateInWork,Date dateCreate){
        this.setGuid(guid);
        this.setId(id);
        this.setName(name);

        this.author=author;
        this.programmer=programmer;

        this.text = text;
        this.ps = ps;

        this.isClosed=isClosed;
        this.dateClosed=dateClosed;

        this.isInWork = isInWork;
        this.dateInWork = dateInWork;

        this.dateCreate = dateCreate;

    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public Date getDateInWork() {
        return dateInWork;
    }

    public void setDateInWork(Date dateInWork) {
        this.dateInWork = dateInWork;
    }

    public boolean isInWork() {
        return isInWork;
    }

    public void setInWork(boolean inWork) {
        isInWork = inWork;
    }

    public WUsers getAuthor() {
        return author;
    }

    public void setAuthor(WUsers author) {
        this.author = author;
    }

    public WUsers getProgrammer() {
        return programmer;
    }

    public void setProgrammer(WUsers programmer) {
        this.programmer = programmer;
    }

    public String getText() {
        return text;
    }

    public void setText(String description) {
        this.text = description;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
