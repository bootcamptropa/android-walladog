package com.walladog.walladog.models;

/**
 * Created by hadock on 5/02/16.
 *
 */
public class Transaction {
    private int id;
    private String seller;
    private String product;
    private String buyer;
    private String date_transaction;

    public Transaction(int id, String seller, String product, String buyer, String date_transaction) {
        this.id = id;
        this.seller = seller;
        this.product = product;
        this.buyer = buyer;
        this.date_transaction = date_transaction;
    }

    public Transaction() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getDate_transaction() {
        return date_transaction;
    }

    public void setDate_transaction(String date_transaction) {
        this.date_transaction = date_transaction;
    }
}
