package com.revature.foundationproject.models;

import java.util.Objects;

public class ErsReimbStatus {
    private String status_id;
    private String status;

    public ErsReimbStatus() {
        super();
    }

    public ErsReimbStatus(String status_id, String status) {
        this.status_id = status_id;
        this.status = status;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErsReimbStatus that = (ErsReimbStatus) o;
        return Objects.equals(status_id, that.status_id) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_id, status);
    }

    @Override
    public String toString() {
        return "ErsReimbStatus{" +
                "status_id='" + status_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
