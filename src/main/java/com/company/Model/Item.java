package com.company.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by tyuly on 29.01.2017.
 */
@Entity
public class Item extends BaseEntity {
    @Column
    private String name;
    @Column
    private String description;
    @Column
    boolean isBuy;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public boolean getBuy() {
        return isBuy;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getId() + " " + name + " " + description;
    }

}
