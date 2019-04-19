package com.sumrid_k.pos.Report.model;

public class Stock {

    private int id;
    private String name;
    private String date_in;
    private String date_out;
    private int quantity;
    private double price;
    private String status;
    private int productId;

    public Stock(){}

    public Stock(int id, String name, String date_in, int quantity, double price, String status, String date_out, int productId) {
        this.id = id;
        this.name = name;
        this.date_in = date_in;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.date_out = date_out;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDate_out() {
        return date_out;
    }

    public void setDate_out(String date_out) {
        this.date_out = date_out;
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

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
