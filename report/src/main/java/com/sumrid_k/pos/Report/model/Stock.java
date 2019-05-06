package com.sumrid_k.pos.Report.model;

import java.util.Date;

public class Stock {

    private int id;
    private String name;
    private Date date;
    private int quantity;
    private double price;
    private int productId;

    public Stock(){}

    public Stock(int id, String name, Date date, int quantity, double price, int productId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
