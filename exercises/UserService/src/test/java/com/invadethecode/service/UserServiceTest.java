package com.invadethecode.service;

import com.invadethecode.model.User;
import com.invadethecode.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    UserService userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;
    //Arrange for each test instance
    @BeforeEach
    void init(){
        UserService userService = new UserServiceImpl();
        String firstName = "ari";
        String lastName = "vanegas";
        String email = "test@aol.com";
        String password = "1234567";
        String repeatPassword = "1234567";
    }

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_ReturnUserObject(){

        //Act
        User user = userService.createUser(firstName,lastName,email,password,repeatPassword);
        //Assert
        assertNotNull(user, "The createUser() should not have returned null");
        assertEquals(firstName, user.getFirstName(), "users first name does not match");
        assertEquals(lastName, user.getLastName(), "users last name does not match");
        assertEquals(email, user.getEmail(), "users email does not match");
        assertNotNull(user.getId(), "user id is missing");
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException(){

        //Act & Assert
       IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, ()-> {
            userService.createUser(firstName,lastName,email,password,repeatPassword);
        }, "Empty first name should have cause and Illegal Argument Exception");
        //Assert
        assertEquals("User's first name is empty", thrown.getMessage(),
                "Exception error messsage is not correct");
    }
}
