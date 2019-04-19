package com.sumrid_k.pos.Report.model;

import java.util.Date;
import java.util.List;

public class Bill {

    private long id;
    private Date date;
    private List<ProductQuantity> productQuantityList;
    private double totalPrice;
    private String companyName;
    private String userName;

    public Bill() {
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

    public List<ProductQuantity> getProductQuantityList() {
        return productQuantityList;
    }

    public void setProductQuantityList(List<ProductQuantity> productQuantityList) {
        this.productQuantityList = productQuantityList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
