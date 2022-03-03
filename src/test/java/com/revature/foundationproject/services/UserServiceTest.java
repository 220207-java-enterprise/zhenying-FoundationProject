package com.revature.foundationproject.services;

import com.revature.foundationproject.daos.UserDAO;
import com.revature.foundationproject.dtos.requests.LoginRequest;
import com.revature.foundationproject.util.exceptions.InvalidRequestException;
import org.checkerframework.checker.units.qual.A;
import org.junit.*;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService sut;
    private final UserDAO mockUserDao = mock(UserDAO.class);

    @Before
    public void setup() {
        sut = new UserService(mockUserDao);
    }

    @After
    public void cleanUp() {
        reset(mockUserDao);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenEmptyString() {
        Assert.assertFalse(sut.isUsernameValid(""));
    }

    @Test
    public void test_isRoleNameValid_returnsTrue_givenRightString(){
        Assert.assertTrue(sut.isRoleNameValid("EMPLOYEE"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenShortUsername() {
        Assert.assertFalse(sut.isUsernameValid("short"));
    }

    @Test
    public void test_isEmailValid_returnFalse_givenInvalidEmail(){
        Assert.assertFalse(sut.isEmailValid("invalid@@Email"));
    }

    @Test
    public void test_isPasswordValid_returnFalse_givenEmptyString(){
        Assert.assertFalse(sut.isPasswordValid(""));
    }






}
