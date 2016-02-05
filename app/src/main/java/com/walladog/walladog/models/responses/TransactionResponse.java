package com.walladog.walladog.models.responses;

import com.walladog.walladog.models.Transaction;
import java.io.Serializable;
import java.util.List;


public class TransactionResponse extends BaseResponse implements Serializable {

    List<Transaction> data;
    private int totalCount;

    public TransactionResponse(List<Transaction> data) {
        this.data = data;
    }


    public List<Transaction> getData() {
        return data;
    }

    public void setData(List<Transaction> data) {
        this.data = data;
    }


    public int getTotalCount() {
        return data.size();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


}
