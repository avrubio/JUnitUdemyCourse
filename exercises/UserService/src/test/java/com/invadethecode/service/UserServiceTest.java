package com.invadethecode.service;

import com.invadethecode.model.User;
import com.invadethecode.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {

    @Test
    void testCreateUser_whenUserDetailsProvided_ReturnUserObject(){
        //Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "ari";
        String lastName = "vanegas";
        String email = "test@aol.com";
        String password = "1234567";
        String repeatPassword = "1234567";
        //Act
        User user = userService.createUser(firstName,lastName,email,password,repeatPassword);
        //Assert
        assertNotNull(user, "The createUser() should not have returned null");
    }

    @Test
    void testCreateUser_whenUserIsCreated_returnedUserObjectContainsSameFirstName(){
        //Arrange
        UserService userService = new UserServiceImpl();
        String firstName = "ari";
        String lastName = "vanegas";
        String email = "test@aol.com";
        String password = "1234567";
        String repeatPassword = "1234567";
        //Act
        User user = userService.createUser(firstName,lastName,email,password,repeatPassword);

        //Assert
        assertEquals(firstName, user.getFirstName());
    }
}
