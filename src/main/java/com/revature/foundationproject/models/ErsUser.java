package com.revature.foundationproject.models;

import java.util.Objects;

public class ErsUser {
    private String user_id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private boolean is_active;
    private ErsUserRole user_role;

    public ErsUser(){
        super();
    }

    public ErsUser(String user_id, String username, String email, String password, String given_name, String surname, boolean is_active, ErsUserRole user_role) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
        this.user_role = user_role;
    }

    public ErsUser(String username, String email, String password, String given_name, String surname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.given_name = given_name;
        this.surname = surname;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public void setRole(ErsUserRole user_role){ this.user_role = user_role; }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public ErsUserRole getRole(){ return  user_role; }

    @Override
    public int hashCode() {
        return Objects.hash(user_id,username,email,password,given_name,surname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return  true;
        if (obj == null || getClass() != obj.getClass())return false;
        ErsUser ersUser = (ErsUser) obj;

        return Objects.equals(user_id, ersUser.user_id)&&
                Objects.equals(username, ersUser.username)&&
                Objects.equals(email, ersUser.email)&&
                Objects.equals(password, ersUser.password)&&
                Objects.equals(given_name, ersUser.given_name)&&
                Objects.equals(surname, ersUser.surname)&&
                Objects.equals(is_active, ersUser.is_active)&&
                Objects.equals(user_role, ersUser.user_role);
    }

    @Override
    public String toString() {
        return "ErsUser{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", is_active=" + is_active +
                ", user_role='" + user_role + '\'' +
                '}';
    }
}
