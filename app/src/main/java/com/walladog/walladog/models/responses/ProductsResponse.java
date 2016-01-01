package com.walladog.walladog.models.responses;

import com.walladog.walladog.models.Product;

import java.util.List;


public class ProductsResponse extends BaseResponse {

    List<Product> data;
    private int totalCount;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public ProductsResponse() {

    }

    public int getTotalCount() {
        return data.size();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
