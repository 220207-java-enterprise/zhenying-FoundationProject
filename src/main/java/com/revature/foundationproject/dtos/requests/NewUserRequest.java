package com.revature.foundationproject.dtos.requests;

import com.revature.foundationproject.models.ErsUser;

public class NewUserRequest {
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private String role_name;

    public NewUserRequest() {
        super();
    }

    public NewUserRequest(String username, String email, String password, String given_name, String surname, String role_name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.role_name = role_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole_name(){
        return role_name;
    }

    public void setRole_name(String role_name){
        this.role_name = role_name;
    }

    public ErsUser extractUser(){
        return new ErsUser(username, email, password, given_name, surname);
    }
    @Override
    public String toString() {
        return "NewUserRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
