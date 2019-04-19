package com.sumrid_k.pos.Report.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date date;
    private long bestseller;
    private long lowInventory;
    private double income;
    private double profit;

    public Report() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getBestseller() {
        return bestseller;
    }

    public void setBestseller(long bestseller) {
        this.bestseller = bestseller;
    }

    public long getLowInventory() {
        return lowInventory;
    }

    public void setLowInventory(long lowInventory) {
        this.lowInventory = lowInventory;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
