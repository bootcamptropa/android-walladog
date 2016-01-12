package com.walladog.walladog.models.responses;

import java.util.ArrayList;

/**
 * Created by hadock on 1/01/16.
 *
 */

public class BaseResponse {
    private boolean apisuccess=false;
    private ArrayList<ErrorCode> errorCodes=null;

    public boolean isApisuccess() {
        return apisuccess;
    }

    public void setApisuccess(boolean apisuccess) {
        this.apisuccess = apisuccess;
    }

    public ArrayList<ErrorCode> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(ArrayList<ErrorCode> errorCodes) {
        this.errorCodes = errorCodes;
    }


}
