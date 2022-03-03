package com.revature.foundationproject.services;

import com.revature.foundationproject.daos.ReimbursementDAO;
import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.dtos.requests.ReimbRequest;
import com.revature.foundationproject.models.ErsReimbStatus;
import com.revature.foundationproject.models.ErsReimbType;
import com.revature.foundationproject.models.ErsReimbursement;
import com.revature.foundationproject.models.ErsUser;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ReimbursementService {
    private ReimbursementDAO reimbursementDAO;

    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }


    public void HandleSubmitNewReimbursementRequest(ReimbRequest reimbRequest){
        ErsUser currentUser = new ErsUser();
        currentUser.setUser_id(reimbRequest.getAuthor_id());

        ErsReimbType ersReimbType = reimbursementDAO.findReimbTypeByTpeName(reimbRequest.getReimbType());
        ErsReimbStatus ersReimbStatus = reimbursementDAO.findReimbStatusByStatusName("PENDING");
        Timestamp currentTime = Timestamp.from(Instant.now());
        String ErsReimbId = UUID.randomUUID().toString();

        ErsReimbursement ersReimbursement = new ErsReimbursement(ErsReimbId, reimbRequest.getAmount(),
                currentTime, null, reimbRequest.getDescription(),reimbRequest.getReceipt(), reimbRequest.getPayment_id(),
                currentUser, null, ersReimbStatus, ersReimbType );

        reimbursementDAO.submitNewReimbursementRequestByErsUser(ersReimbursement);
    }


    //For Employee
    public List<ErsReimbursement> findAllPendingReimbursementsByEmployee(String employee_id){
        ErsUser ersUser = reimbursementDAO.findErsUserByUserId(employee_id);
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllPendingReimbursementsByErsUser(ersUser);

        return  ersReimbursements;
    }

    public List<ErsReimbursement> findAllReimbursementsByEmployee(String employee_id){
        ErsUser ersUser = reimbursementDAO.findErsUserByUserId(employee_id);
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllReimbursementsByErsUser(ersUser);

        return  ersReimbursements;
    }


    //For Finance Manager
    public List<ErsReimbursement> findAllPendingReimbursementsByFM(){
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllPendingReimbursementsByFM();

        return  ersReimbursements;
    }

    public List<ErsReimbursement> findAllSolvedReimbursementsByFM(String FM_id){
        ErsUser ersUser = reimbursementDAO.findErsUserByUserId(FM_id);
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllSolvedReimbursementsByFM(ersUser);

        return  ersReimbursements;
    }



}
