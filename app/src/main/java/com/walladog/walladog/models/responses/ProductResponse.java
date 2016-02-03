package com.walladog.walladog.models.responses;

import com.walladog.walladog.models.Product;

import java.io.Serializable;
import java.util.List;


public class ProductResponse extends BaseResponse implements Serializable {

    private int count;
    private String next;
    private String previous;
    private List<Product> results;

    public ProductResponse() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }
}
