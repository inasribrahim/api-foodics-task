package com.foodics.api.credential;

public class CredentialsPayload {

    private String email;
    private String password;
    private String token;

    public CredentialsPayload(String username, String password,String token) {
        this.email = username;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
