package com.revature.foundationproject.services;

import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.dtos.requests.LoginRequest;
import com.revature.foundationproject.dtos.requests.NewUserRequest;
import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.models.ErsUserRole;
import com.revature.foundationproject.util.exceptions.AuthenticationException;
import com.revature.foundationproject.util.exceptions.InvalidRequestException;
import com.revature.foundationproject.util.exceptions.ResourceConflictException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class UserService {

    private UserDAO userDAO;

    // Constructor injection
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ErsUser register(NewUserRequest newUserRequest) throws NoSuchAlgorithmException {
        ErsUser newErsUser = newUserRequest.extractUser();
        String role_name = newUserRequest.getRole_name();

        if (!isUserValid(newErsUser) || !isRoleNameValid(role_name)) {
            throw new InvalidRequestException("Bad registration details given.");
        }

        boolean usernameAvailable = isUsernameAvailable(newErsUser.getUsername());
        boolean emailAvailable = isEmailAvailable(newErsUser.getEmail());

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users: ";
            if (!usernameAvailable) msg += "username ";
            if (!emailAvailable) msg += "email";
            throw new ResourceConflictException(msg);
        }
        //Hash password
        String password = newErsUser.getPassword();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        password = no.toString(16);
        while (password.length() < 32) {
            password = "0" + password;
        }

        newErsUser.setPassword(password);

        ErsUserRole userRole = userDAO.findUserRoleByRoleName(role_name);
        newErsUser.setUser_id(UUID.randomUUID().toString());
        newErsUser.setIs_active(false);
        newErsUser.setRole(userRole);

        userDAO.save(newErsUser);

        return newErsUser;
    }

    public ErsUser login(LoginRequest loginRequest) throws NoSuchAlgorithmException {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!isUsernameValid(username) || !isPasswordValid(password)) {
            throw new InvalidRequestException("Invalid credentials provided!");
        }
        //Hash password
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        password = no.toString(16);
        while (password.length() < 32) {
            password = "0" + password;
        }

        ErsUser authUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authUser == null || !authUser.getIs_active()) {
            throw new AuthenticationException();
        }

        return authUser;

    }

    public void reactivateUserAccountByAdmin(ErsUser ersUser){
        userDAO.reactivateUserAccount(ersUser);
    }

    public void deactivateUserAccountByAdmin(ErsUser ersUser){
        userDAO.deactivateUserAccount(ersUser);
    }

    private boolean isUserValid(ErsUser ersUser) {
        // given name and surname are not just empty strings or filled with whitespace
        if (ersUser.getGiven_name().trim().equals("") || ersUser.getSurname().trim().equals("")) {
            return false;
        }

        // Usernames must be a minimum of 8 and a max of 25 characters in length, and only contain alphanumeric characters.
        if (!isUsernameValid(ersUser.getUsername())) {
            return false;
        }

        // Passwords require a minimum eight characters, at least one uppercase letter, one lowercase
        // letter, one number and one special character
        if (!isPasswordValid(ersUser.getPassword())) {
            return false;
        }

        // Basic email validation
        return isEmailValid(ersUser.getEmail());
    }

    public boolean isRoleNameValid (String role_name){
        return role_name.equals("FINANCE_MANAGER") || role_name.equals("EMPLOYEE");
    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
    }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9]{8,25}");
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    public boolean isUsernameAvailable(String username) {
        return userDAO.findUserByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        return userDAO.findUserByEmail(email) == null;
    }
}
