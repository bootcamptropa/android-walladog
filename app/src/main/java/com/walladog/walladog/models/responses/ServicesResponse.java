package com.walladog.walladog.models.responses;

import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.WDServices;

import java.util.List;


public class ServicesResponse extends BaseResponse {

    List<WDServices> data;
    private int totalCount;

    public List<WDServices> getData() {
        return data;
    }

    public void setData(List<WDServices> data) {
        this.data = data;
    }

    public ServicesResponse() {

    }

    public int getTotalCount() {
        return data.size();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
