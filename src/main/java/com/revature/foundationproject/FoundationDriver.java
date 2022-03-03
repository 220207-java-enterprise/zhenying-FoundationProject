package com.revature.foundationproject;

import com.revature.foundationproject.daos.ReimbursementDAO;
import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.models.ErsReimbStatus;
import com.revature.foundationproject.models.ErsReimbType;
import com.revature.foundationproject.models.ErsReimbursement;
import com.revature.foundationproject.models.ErsUser;

import java.sql.*;

public class FoundationDriver {


    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();

        ErsUser ersUser_employee = new ErsUser("user2","user2@gmail.com","123456","user2","user2");
        ersUser_employee.setUser_id("7c3521f5-ff75-4e8a-9913-01d15ee4dc9user2");

        ErsUser ersUser_FM = new ErsUser("finance1", "finance1@gmail.com", "123456", "finance1", "finance1");
        ersUser_FM.setUser_id("7c3521f5-ff75-4e8a-9913-01d15ee4dc9finance1");

        ErsReimbStatus ersReimbStatus = new ErsReimbStatus("7c3521f5-ff75-4e8a-9913-01d15ee4dc9h", "PENDING");
        ErsReimbType ersReimbType = new ErsReimbType("7c3521f5-ff75-4e8a-9913-01d15ee4dc9f", "FOOD");

        ErsReimbursement ersReimbursement = new ErsReimbursement("7c3521f5-ff75-4e8a-9913-01d15ee4dc9user2reimb4",
                39.99,  Timestamp.valueOf("2022-02-28 00:00:00"), null, "food expenses",
                null, null,ersUser_employee,
                null,ersReimbStatus, ersReimbType);

        reimbursementDAO.HandleReimbursementRequestByFM(ersUser_FM, ersReimbursement, true);
        //reimbursementDAO.submitNewReimbursementRequestByErsUser(ersReimbursement);

        //List<ErsReimbursement> ersReimbursementList = reimbursementDAO.findAllReimbursementsByErsUser(ersUser);

        //List<ErsReimbursement> ersReimbursementList = reimbursementDAO.findAllPendingReimbursementsByFM();

        /*List<ErsReimbursement> ersReimbursementList = reimbursementDAO.findAllSolvedReimbursementsByFM(ersUser_FM);
        System.out.println(ersReimbursementList.size());
        for(ErsReimbursement ersReimbursement_:ersReimbursementList){
            System.out.println(ersReimbursement_);
        }*/
        //userDAO.updateUserPassword(ersUser_employee, "123456");

        /*ErsUser ersUser2 = userDAO.findUserByUsername("user2");
        userDAO.reactivateUserAccount(ersUser2);
        System.out.println(ersUser2);*/
    }
}
