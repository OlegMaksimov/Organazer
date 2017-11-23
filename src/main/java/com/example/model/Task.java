package com.example.model;

import java.util.Date;

/**
 * Created by iMac on 23.11.2017.
 */
public class Task {
    public String name;
    public String discription;
    public Long category;
    public Long priority;
    public Boolean important;
    public Boolean quickly;
    public Date dateBegin;
    public Date dateEnd;

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public Boolean getQuickly() {
        return quickly;
    }

    public void setQuickly(Boolean quickly) {
        this.quickly = quickly;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", discription='" + discription + '\'' +
                ", category=" + category +
                ", priority=" + priority +
                ", important=" + important +
                ", quickly=" + quickly +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
