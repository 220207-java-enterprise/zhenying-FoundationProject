package com.revature.foundationproject.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundationproject.dtos.requests.LoginRequest;
import com.revature.foundationproject.dtos.responses.Principal;
import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.services.TokenService;
import com.revature.foundationproject.services.UserService;
import com.revature.foundationproject.util.exceptions.AuthenticationException;
import com.revature.foundationproject.util.exceptions.InvalidRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(UserServlet.class);

    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthServlet(TokenService tokenService, UserService userService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getServletContext().getInitParameter("programmaticParam"));
    }

    // Login endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {

            LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
            Principal principal = new Principal(userService.login(loginRequest));
            String payload = mapper.writeValueAsString(principal);


            String token = tokenService.generateToken(principal);
            resp.setHeader("Authorization", token);
            resp.setContentType("application/json");
            writer.write(payload);


        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    //Admin reactivate or deactivate a user account
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            logger.warn("Unauthenticated request made to UserServlet#doGet");
            resp.setStatus(401);
            return;
        }
        if (!requester.getRole().equals("ADMIN")) {
            logger.warn("Unauthorized request made by user: " + requester.getUsername());
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        try {
            ErsUser ersUser = mapper.readValue(req.getInputStream(), ErsUser.class);

            String[] reqFrags = req.getRequestURI().split("/");
            //[    ,  "foundation-project", "auth", ""]
            if (reqFrags.length == 4 && reqFrags[3].equals("reactivate")) {
                userService.reactivateUserAccountByAdmin(ersUser);
                return;
            }else if (reqFrags.length == 4 && reqFrags[3].equals("deactivate")) {
                userService.deactivateUserAccountByAdmin(ersUser);
                return;
            }

        }catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }

        //ResourcePersistenceException
        //DataSourceException


    }
}
