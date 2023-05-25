package com.sms.blackmagic.util;

public class AuthenticationRequest {
    private String accountName;
    private String password;

    // standard getters and setters
    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
