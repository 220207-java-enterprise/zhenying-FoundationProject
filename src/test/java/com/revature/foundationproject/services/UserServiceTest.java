package com.revature.foundationproject.services;

import com.revature.foundationproject.daos.UserDAO;
import org.junit.*;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService sut;
    private UserDAO mockUserDao = mock(UserDAO.class);

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

        String username = "";

        boolean result = sut.isUsernameValid(username);

        Assert.assertFalse(result);

    }

    @Test
    public void test_isRoleNameValid_returnsTrue_givenRightString(){
        String role_name = "EMPLOYEE";
        boolean result = sut.isRoleNameValid(role_name);
        Assert.assertTrue(result);
    }
}
