package com.walladog.walladog.models.apiservices;

import android.util.Log;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created by hadock on 12/01/16.
 * Mediante esta clase "GLORIOSA" conseguimos que al recivir un 401
 * nuestro servicio se re-autentique magicamente
 *
 */

public class TokenAuthenticator  implements Authenticator {

    private static final String TAG = TokenAuthenticator.class.getName();

    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {
        // Refresh your access_token using a synchronous api request
        AccessToken newAccessToken = ServiceGeneratorOAuth.createService(WDOAuth.class,AccessToken.clientID,AccessToken.clientSecret)
                .getAccessToken(AccessToken.grantType,AccessToken.cusername,AccessToken.cpassword).execute().body();

        Log.v(TAG, "Nos han denegado token, pedimos otro SINCRONAMENTE " + newAccessToken.getAccessToken());

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header("Authorization", "Bearer " + newAccessToken.getAccessToken())
                .build();
    }

    @Override
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        //Null para no autenticar
        return null;
    }


}