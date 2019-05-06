package com.sumrid_k.pos.Report.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductQuantity {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private double price;
    private int quantity;
    private long productId;

    public ProductQuantity() {
    }
    public ProductQuantity(String name, int quantity, double price, long productId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setId(long id) {
        this.id = id;
    }




}