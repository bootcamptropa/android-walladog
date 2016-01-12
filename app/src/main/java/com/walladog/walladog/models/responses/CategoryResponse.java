package com.walladog.walladog.models.responses;

import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;

import java.util.List;


public class CategoryResponse extends BaseResponseWD {

    List<Category> data;
    private int totalCount;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    public CategoryResponse() {

    }

    public int getTotalCount() {
        return data.size();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
