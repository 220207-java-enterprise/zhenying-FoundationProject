package com.revature.foundationproject.services;

import com.revature.foundationproject.daos.ReimbursementDAO;
import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.dtos.requests.ReimbRequest;
import com.revature.foundationproject.dtos.responses.ReimbursementResponse;
import com.revature.foundationproject.models.ErsReimbStatus;
import com.revature.foundationproject.models.ErsReimbType;
import com.revature.foundationproject.models.ErsReimbursement;
import com.revature.foundationproject.models.ErsUser;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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
    public List<ReimbursementResponse> findAllPendingReimbursementsByEmployee(String employee_id){
        ErsUser ersUser = reimbursementDAO.findErsUserByUserId(employee_id);
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllPendingReimbursementsByErsUser(ersUser);

        List<ReimbursementResponse> reimbursementResponses = convertErsReimbursementListToResponse(ersReimbursements);
        return  reimbursementResponses;
    }

    public List<ReimbursementResponse> findAllReimbursementsByEmployee(String employee_id){
        ErsUser ersUser = reimbursementDAO.findErsUserByUserId(employee_id);
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllReimbursementsByErsUser(ersUser);

        List<ReimbursementResponse> reimbursementResponses = convertErsReimbursementListToResponse(ersReimbursements);
        return  reimbursementResponses;
    }


    //For Finance Manager
    public List<ReimbursementResponse> findAllPendingReimbursementsByFM(){
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllPendingReimbursementsByFM();

        List<ReimbursementResponse> reimbursementResponses = convertErsReimbursementListToResponse(ersReimbursements);
        return  reimbursementResponses;
    }

    public List<ReimbursementResponse> findAllSolvedReimbursementsByFM(String FM_id){
        ErsUser ersUser = reimbursementDAO.findErsUserByUserId(FM_id);
        List <ErsReimbursement>  ersReimbursements =
                reimbursementDAO.findAllSolvedReimbursementsByFM(ersUser);

        List<ReimbursementResponse> reimbursementResponses = convertErsReimbursementListToResponse(ersReimbursements);
        return  reimbursementResponses;
    }

    //Helper function
    List<ReimbursementResponse> convertErsReimbursementListToResponse(List<ErsReimbursement> ersReimbursementList){
        List<ReimbursementResponse> reimbursementResponseArrayList = new ArrayList<>();

        for(ErsReimbursement ersReimbursement: ersReimbursementList){
            /*ReimbursementResponse reimbursementResponse = new ReimbursementResponse(
                    ersReimbursement.getReimb_id(), ersReimbursement.getAmount(),
                    ersReimbursement.getSubmitted(),ersReimbursement.getResolved(),
                    ersReimbursement.getDescription(),ersReimbursement.getReceipt(),
                    ersReimbursement.getPayment_id(),ersReimbursement.getAuthor().getUsername(),
                    ersReimbursement.getResolver().getUsername(),ersReimbursement.getErsReimbStatus().getStatus(),
                    ersReimbursement.getErsReimbType().getType()
            );*/
            ReimbursementResponse reimbursementResponse = new ReimbursementResponse();
            reimbursementResponse.setReimb_id(ersReimbursement.getReimb_id());
            reimbursementResponse.setAmount(ersReimbursement.getAmount());
            reimbursementResponse.setSubmitted(ersReimbursement.getSubmitted().toString());
            //reimbursementResponse.setResolved(ersReimbursement.getResolved().toString());
            if(ersReimbursement.getResolved() == null){
                reimbursementResponse.setResolved(null);
            }else{
                reimbursementResponse.setResolved(ersReimbursement.getResolved().toString());
            }

            reimbursementResponse.setDescription(ersReimbursement.getDescription());
            reimbursementResponse.setReceipt(ersReimbursement.getReceipt());
            reimbursementResponse.setPayment_id(ersReimbursement.getPayment_id());

            reimbursementResponse.setAuthor(ersReimbursement.getAuthor().getGiven_name());
            //reimbursementResponse.setResolver(ersReimbursement.getResolver().getGiven_name());
            if(ersReimbursement.getResolver() == null){
                reimbursementResponse.setResolver(null);
            }else{
                reimbursementResponse.setResolver(ersReimbursement.getResolver().getGiven_name());
            }

            reimbursementResponse.setStatus(ersReimbursement.getErsReimbStatus().getStatus());
            reimbursementResponse.setType(ersReimbursement.getErsReimbType().getType());
            reimbursementResponseArrayList.add(reimbursementResponse);
        }

        return reimbursementResponseArrayList;
    }

}
