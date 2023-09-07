package com.foodics.api.login.credential;

public class CredentialsBuilder {
    private String email;
    private String password;
    private String token;

    public CredentialsBuilder setEmail(String email) {
        this.email = email;
        return this;
    }
    public CredentialsBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
    public CredentialsBuilder setToken(String token) {
        this.token = token;
        return this;
    }
    public CredentialsPayload perform(){
        return  new CredentialsPayload(email,password,token);
    }
    public static CredentialsBuilder builder(){
        return new CredentialsBuilder();
    }
}
