package com.revature.foundationproject.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundationproject.dtos.requests.ReimbRequest;
import com.revature.foundationproject.dtos.responses.Principal;
import com.revature.foundationproject.models.ErsReimbursement;
import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.services.ReimbursementService;
import com.revature.foundationproject.services.TokenService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ErsReimbursementServlet extends HttpServlet {

    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;

    public ErsReimbursementServlet(TokenService tokenService, ReimbursementService reimbursementService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            resp.setStatus(401);
            return;
        }
        System.out.println(requester.getRole());
        if (!requester.getRole().equals("EMPLOYEE") && !requester.getRole().equals("FINANCE_MANAGER")) {
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        try{
            String[] reqFrags = req.getRequestURI().split("/");
            //[    ,  "foundation-project", "reimbursements", "find-all-pending-reimbs-"]
            //[    ,  "foundation-project", "reimbursements", "find-all-reimbs-"]

            String cases = reqFrags[3]+requester.getRole();
            List<ErsReimbursement> ersReimbursementList = null;

            switch (cases){
                case "find-all-pending-reimbs-EMPLOYEE":
                    ersReimbursementList = reimbursementService.findAllPendingReimbursementsByEmployee(requester.getUser_id());
                    break;
                case "find-all-pending-reimbs-FINANCE_MANAGER":
                    ersReimbursementList = reimbursementService.findAllPendingReimbursementsByFM();
                    break;


            }

            if(ersReimbursementList != null){
                String payload = mapper.writeValueAsString(ersReimbursementList);
                resp.setContentType("application/json");
                resp.getWriter().write(payload);
            }

        }catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        ReimbRequest reimbRequest = mapper.readValue(req.getInputStream(), ReimbRequest.class);

        if (requester == null) {
            resp.setStatus(401);
            return;
        }
        if (!requester.getRole().equals("EMPLOYEE") || !requester.getUser_id().equals(reimbRequest.getAuthor_id())) {
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        if(reimbRequest.getAmount()<0 || reimbRequest.getAmount()>10000 ||
                reimbRequest.getDescription() == null || reimbRequest.getAuthor_id() == null ||
                (!reimbRequest.getReimbType().equals("LODGING") && !reimbRequest.getReimbType().equals("TRAVEL") &&
                        !reimbRequest.getReimbType().equals("FOOD") && !reimbRequest.getReimbType().equals("OTHER"))
        ){
            resp.setStatus(400);
            return;
        }

        try {
            String[] reqFrags = req.getRequestURI().split("/");
            //[    ,  "foundation-project", "reimbursements", "submit"]
            if (reqFrags.length == 4 && reqFrags[3].equals("submit")) {

                reimbursementService.HandleSubmitNewReimbursementRequest(reimbRequest);
                return;
            }else {
                resp.setStatus(404);
            }

        }catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }


    }

}
