package com.revature.foundationproject.screens;

import com.revature.foundationproject.dtos.requests.NewUserRequest;
import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.models.ErsUserRole;
import com.revature.foundationproject.services.UserService;

import java.io.BufferedReader;
import java.io.IOException;

public class RegisterScreen extends Screen{
    private final UserService userService;

    public RegisterScreen(BufferedReader consoleReader, UserService userService) {
        super("/register", consoleReader);
        this.userService = userService;
    }

    @Override
    public void render() throws IOException {
        NewUserRequest newUserRequest = new NewUserRequest();
        System.out.println("Register:");

        System.out.print("Username: ");
        newUserRequest.setUsername(consoleReader.readLine());

        System.out.print("Email: ");
        newUserRequest.setEmail(consoleReader.readLine());

        System.out.print("Password: ");
        newUserRequest.setPassword(consoleReader.readLine());

        System.out.print("Given name: ");
        newUserRequest.setGiven_name(consoleReader.readLine());

        System.out.print("Surname: ");
        newUserRequest.setSurname(consoleReader.readLine());

        System.out.print("Role Name: FINANCE_MANAGER or EMPLOYEE: ");
        newUserRequest.setRole_name(consoleReader.readLine());

        ErsUser registerUser = userService.register(newUserRequest);
        System.out.println(registerUser);
    }
}
