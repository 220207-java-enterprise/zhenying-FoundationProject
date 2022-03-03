package com.revature.foundationproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundationproject.daos.ReimbursementDAO;
import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.services.ReimbursementService;
import com.revature.foundationproject.services.TokenService;
import com.revature.foundationproject.services.UserService;
import com.revature.foundationproject.servlets.AuthServlet;
import com.revature.foundationproject.servlets.ErsReimbursementServlet;
import com.revature.foundationproject.servlets.UserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //System.out.println("Initializing Foundation Project web application");
        logger.debug("Initializing Foundation Project");

        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserServlet userServlet = new UserServlet(tokenService,userService,mapper);
        AuthServlet authServlet = new AuthServlet(tokenService, userService, mapper);

        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        ReimbursementService reimbursementService = new ReimbursementService(reimbursementDAO);
        ErsReimbursementServlet ersReimbursementServlet = new ErsReimbursementServlet(tokenService, reimbursementService, mapper);

        // Programmatic Servlet Registration
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/registration");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth/*");
        context.addServlet("ErsReimbursementServlet", ersReimbursementServlet).addMapping("/reimbursements/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //System.out.println("Shutting down Foundation Project web application");
        logger.debug("Shutting down Foundation Project");
    }
}
