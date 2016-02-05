package com.walladog.walladog.models;

/**
 * Created by hadock on 5/02/16.
 *
 */
public class UserSignIn {


    /**
     *
     * {"username":"ramonete","password":"1234","password2":"1234","first_name":"Ramon","last_name":"ALBERTI DANES",
     * "email":"notengo@email.com","longitude":0,"latitude":0,"token_facebook":0,"avatar_url":"http://walladog.com/assets/logos/walladogt.png"}
     *
     */

    private String username;
    private String password;
    private String password2;
    private String email;

    public UserSignIn(String username, String password, String password2, String email) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
        this.email = email;
    }

    public UserSignIn() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
