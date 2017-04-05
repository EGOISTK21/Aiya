package xyz.egoistk21.aiya.bean;

/**
 * Created by EGOISTK on 2017/3/26.
 */

public class User {

    private String username;
    private String usersig;
    private String loginToken;
    private String accesstoken;

    public User(String username) {
        this.username = username;
    }

    public void setUsersig(String usersig) {
        this.usersig = usersig;
    }

    public void updateUsersig(String usersig) {
        this.usersig = usersig;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getUsername() {
        return username;
    }

    public String getUsersig() {
        return usersig;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

}
