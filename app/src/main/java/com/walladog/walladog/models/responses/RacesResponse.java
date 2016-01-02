package com.walladog.walladog.models.responses;

import com.walladog.walladog.models.Product;
import com.walladog.walladog.models.Race;

import java.util.List;


public class RacesResponse extends BaseResponse {

    List<Race> data;
    private int totalCount;

    public List<Race> getData() {
        return data;
    }

    public void setData(List<Race> data) {
        this.data = data;
    }

    public RacesResponse() {

    }

    public int getTotalCount() {
        return data.size();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
