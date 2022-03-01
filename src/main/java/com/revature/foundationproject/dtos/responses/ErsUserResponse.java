package com.revature.foundationproject.dtos.responses;

import com.revature.foundationproject.models.ErsUser;

public class ErsUserResponse {
    private String user_id;
    private String username;
    private String given_name;
    private String role;

    public ErsUserResponse(ErsUser user) {
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.given_name = user.getGiven_name();
        this.role = user.getRole().getRole_name();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
