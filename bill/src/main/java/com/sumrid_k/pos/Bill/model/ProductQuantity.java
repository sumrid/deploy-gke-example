package com.sumrid_k.pos.Bill.model;

import javax.persistence.*;

@Entity
public class ProductQuantity {
    @Id
    @GeneratedValue
    private long id;

//    @OneToOne(cascade= CascadeType.ALL)
    private String productJson;
    private int quantity;

    public ProductQuantity() {
    }

    public ProductQuantity(String productJson, int quantity) {
        this.productJson = productJson;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductJson() {
        return productJson;
    }

    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
