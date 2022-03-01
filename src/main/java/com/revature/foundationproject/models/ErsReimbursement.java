package com.revature.foundationproject.models;

import java.sql.Timestamp;
import java.util.Objects;

public class ErsReimbursement {
    private String reimb_id;
    private double amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private byte[] receipt;
    private String payment_id;
    private ErsUser author;
    private ErsUser resolver;
    private ErsReimbStatus ersReimbStatus;
    private ErsReimbType ersReimbType;


    public ErsReimbursement() {
        super();
    }

    public ErsReimbursement(String reimb_id, double amount, Timestamp submitted, Timestamp resolved, String description, byte[] receipt, String payment_id, ErsUser author, ErsUser resolver, ErsReimbStatus ersReimbStatus, ErsReimbType ersReimbType) {
        this.reimb_id = reimb_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.author = author;
        this.resolver = resolver;
        this.ersReimbStatus = ersReimbStatus;
        this.ersReimbType = ersReimbType;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
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

    public ErsUser getAuthor() {
        return author;
    }

    public void setAuthor(ErsUser author) {
        this.author = author;
    }

    public ErsUser getResolver() {
        return resolver;
    }

    public void setResolver(ErsUser resolver) {
        this.resolver = resolver;
    }

    public ErsReimbStatus getErsReimbStatus() {
        return ersReimbStatus;
    }

    public void setErsReimbStatus(ErsReimbStatus ersReimbStatus) {
        this.ersReimbStatus = ersReimbStatus;
    }

    public ErsReimbType getErsReimbType() {
        return ersReimbType;
    }

    public void setErsReimbType(ErsReimbType ersReimbType) {
        this.ersReimbType = ersReimbType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErsReimbursement that = (ErsReimbursement) o;
        return Double.compare(that.amount, amount) == 0 && receipt == that.receipt && Objects.equals(reimb_id, that.reimb_id) && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved) && Objects.equals(description, that.description) && Objects.equals(payment_id, that.payment_id) && Objects.equals(author, that.author) && Objects.equals(resolver, that.resolver) && Objects.equals(ersReimbStatus, that.ersReimbStatus) && Objects.equals(ersReimbType, that.ersReimbType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimb_id, amount, submitted, resolved, description, receipt, payment_id, author, resolver, ersReimbStatus, ersReimbType);
    }

    @Override
    public String toString() {
        return "ErsReimbursement{" +
                "reimb_id='" + reimb_id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", payment_id='" + payment_id + '\'' +
                ", author=" + author +
                ", resolver=" + resolver +
                ", ersReimbStatus=" + ersReimbStatus +
                ", ersReimbType=" + ersReimbType +
                '}';
    }
}
