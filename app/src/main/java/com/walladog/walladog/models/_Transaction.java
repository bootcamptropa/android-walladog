package com.walladog.walladog.models;

import org.json.JSONObject;


public class _Transaction {

    private double id;
    private double sellerId;
    private double productId;
    private double buyerId;
    private String transactionDate;


    public _Transaction() {

    }

    public _Transaction(JSONObject json) {

        this.id = json.optDouble("id");
        this.sellerId = json.optDouble("sellerId");
        this.productId = json.optDouble("productId");
        this.buyerId = json.optDouble("buyerId");
        this.transactionDate = json.optString("transaction_date");

    }

    public double getId() {
        return this.id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(double sellerId) {
        this.sellerId = sellerId;
    }

    public double getProductId() {
        return this.productId;
    }

    public void setProductId(double productId) {
        this.productId = productId;
    }

    public double getBuyerId() {
        return this.buyerId;
    }

    public void setBuyerId(double buyerId) {
        this.buyerId = buyerId;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "{id = "+id+", productId = "+productId+", transactionDate = "+transactionDate+", buyerId = "+buyerId+", sellerId = "+sellerId+"}";
    }
}
