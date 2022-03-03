package com.revature.foundationproject.dtos.requests;

import com.revature.foundationproject.models.ErsReimbursement;
import com.revature.foundationproject.models.ErsUser;

import java.util.Arrays;

public class ReimbRequest {
    private double amount;
    private String description;
    private byte[] receipt;
    private String payment_id;
    private String author_id;
    private String reimbType;

    public ReimbRequest() {
        super();
    }

    public ReimbRequest(double amount, String description, byte[] receipt, String payment_id, String author_id, String reimbType) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.author_id = author_id;
        this.reimbType = reimbType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getReimbType() {
        return reimbType;
    }

    public void setReimbType(String reimbType) {
        this.reimbType = reimbType;
    }

    @Override
    public String toString() {
        return "ReimbRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", payment_id='" + payment_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", reimbType='" + reimbType + '\'' +
                '}';
    }
}
