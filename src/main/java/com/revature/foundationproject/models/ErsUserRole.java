package com.revature.foundationproject.models;

import java.util.Objects;

public class ErsUserRole {
    private String role_id;
    private String role_name;

    public ErsUserRole() {
        super();
    }

    public ErsUserRole(String role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErsUserRole that = (ErsUserRole) o;
        return Objects.equals(role_id, that.role_id) && Objects.equals(role_name, that.role_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_id, role_name);
    }

    @Override
    public String toString() {
        return "ErsUserRole{" +
                "role_id='" + role_id + '\'' +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
