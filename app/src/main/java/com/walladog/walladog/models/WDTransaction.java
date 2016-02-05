package com.walladog.walladog.models;

/**
 * Created by hadock on 5/02/16.
 *
 */
public class WDTransaction {

    private int product;

    public WDTransaction(int product) {
        this.product = product;
    }

    public WDTransaction() {
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
