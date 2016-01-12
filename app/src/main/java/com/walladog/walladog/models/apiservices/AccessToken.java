package com.walladog.walladog.models.apiservices;

/**
 * Created by hadock on 11/01/16.
 *
 */

public class AccessToken {

    public static final String grantType = "password";
    public static final String clientID = "uEAoxVEYOVmpg4Z8IAyCCEItlXO8Cf4G7RSu647d";
    public static final String clientSecret = "Zc0JGu5pVUt7zNxrhQaCvit6ydr4WxMVI4nXluF9GBWgJV0rbUcR5y2uBZl3TgmLYryashJREp9AiIkbfRUVv5Cdd3n6ZX4Va3fI2cmvMwcgWRFYnrp7K8ZtwkopXrhV";
    public static final String redirectUri = "";
    public static final String cusername = "admin";
    public static final String cpassword = "Keepcoding123";
    public static final String OAUTH2_TOKEN = "OAuth2Token";


    private String access_token;
    private String token_type;
    private String expires_in;
    private String refresh_token;
    private String scope;

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if ( ! Character.isUpperCase(token_type.charAt(0))) {
            token_type =
                    Character
                            .toString(token_type.charAt(0))
                            .toUpperCase() + token_type.substring(1);
        }

        return token_type;
    }

    public String getAccessToken() {
        return access_token;
    }
}