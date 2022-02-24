package com.revature.foundationproject.screens;


import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.services.UserService;

import java.io.BufferedReader;
import java.io.IOException;

public class LoginScreen extends Screen {

    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, UserService userService) {
        super("/login", consoleReader);
        this.userService = userService;
    }

    @Override
    public void render() throws IOException {

        System.out.println("Please provide your account credentials to login:");

        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();

        ErsUser authenticatedUser = userService.login(username, password);
        System.out.println(authenticatedUser); // TODO go somewhere from here?

    }
}
