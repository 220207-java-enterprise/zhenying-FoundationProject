package com.revature.foundationproject.services;

import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.util.exceptions.AuthenticationException;

public class UserService {

    private UserDAO userDAO;

    // Constructor injection
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ErsUser login(String username, String password) {

        ErsUser authUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authUser == null) {
            throw new AuthenticationException();
        }

        return authUser;

    }
}
