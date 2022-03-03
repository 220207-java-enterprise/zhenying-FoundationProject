package com.revature.foundationproject.dtos.requests;

public class ResetPasswordRequest {
    private String user_id;
    private String new_password;

    public ResetPasswordRequest() {
        super();
    }

    public ResetPasswordRequest(String user_id, String new_password) {
        this.user_id = user_id;
        this.new_password = new_password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
