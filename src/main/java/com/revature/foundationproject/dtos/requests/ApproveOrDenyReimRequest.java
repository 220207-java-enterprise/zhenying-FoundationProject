package com.revature.foundationproject.dtos.requests;

public class ApproveOrDenyReimRequest {
    private String reimb_id;
    private boolean approve;


    public ApproveOrDenyReimRequest() {
        super();
    }

    public ApproveOrDenyReimRequest(String reimb_id, boolean approve) {
        this.reimb_id = reimb_id;
        this.approve = approve;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

}
