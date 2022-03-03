package com.revature.foundationproject.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundationproject.dtos.requests.LoginRequest;
import com.revature.foundationproject.dtos.requests.NewUserRequest;
import com.revature.foundationproject.dtos.responses.Principal;
import com.revature.foundationproject.dtos.responses.ResourceCreationResponse;
import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.services.TokenService;
import com.revature.foundationproject.services.UserService;
import com.revature.foundationproject.util.exceptions.InvalidRequestException;
import com.revature.foundationproject.util.exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper mapper;

    public UserServlet(TokenService tokenService, UserService userService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mapper = mapper;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("<h1>/users works!111</h1>");
    }

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.getWriter().write("<h1>/post works!111</h1>");
    }*/

    // registration endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            if(newUserRequest.getRole_name().equals("EMPLOYEE") || newUserRequest.getRole_name().equals("FINANCE_MANAGER")){
                System.out.println("Here");
                ErsUser newUser = userService.register(newUserRequest);
                resp.setStatus(201); // CREATED
                resp.setContentType("application/json");
                String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUser_id()));
                respWriter.write(payload);
            }else {
                resp.setStatus(400);
            }
        } catch (InvalidRequestException | DatabindException e) {
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            resp.setStatus(500);
        }

    }
    protected void checkAvailability(HttpServletRequest req, HttpServletResponse resp) {
        String usernameValue = req.getParameter("username");
        String emailValue = req.getParameter("email");
        if (usernameValue != null) {
            if (userService.isUsernameAvailable(usernameValue)) {
                resp.setStatus(204); // NO CONTENT
            } else {
                resp.setStatus(409);
            }
        } else if (emailValue != null) {
            if (userService.isEmailAvailable(emailValue)) {
                resp.setStatus(204);
            } else {
                resp.setStatus(409);
            }
        }
    }
}
