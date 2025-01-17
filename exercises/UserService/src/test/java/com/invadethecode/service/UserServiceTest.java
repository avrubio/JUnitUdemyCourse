package com.invadethecode.service;

import com.invadethecode.data.UsersRepository;
import com.invadethecode.model.User;
import com.invadethecode.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UsersRepository usersRepository;
    @Mock
    EmailVerificationServiceImpl emailVerificationService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    //Arrange for each test instance
    @BeforeEach
    void init() {
//        userService = new UserServiceImpl(usersRepository);
        firstName = "ari";
        lastName = "vanegas";
        email = "test@aol.com";
        password = "1234567";
        repeatPassword = "1234567";
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_ReturnUserObject() {
    //Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);
        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        //Assert
        assertNotNull(user, "The createUser() should not have returned null");
        assertEquals(firstName, user.getFirstName(), "users first name does not match");
        assertEquals(lastName, user.getLastName(), "users last name does not match");
        assertEquals(email, user.getEmail(), "users email does not match");
        assertNotNull(user.getId(), "user id is missing");
        verify(usersRepository, times(1))
                .save(any(User.class));
    }

    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
 firstName = "";
        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have cause and Illegal Argument Exception");
        //Assert
        assertEquals("User's first name is empty", thrown.getMessage(),
                "Exception error message is not correct");
    }
    @DisplayName("Empty  last name causes correct exception")
    @Test
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        lastName = "";
        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty last name should have cause and Illegal Argument Exception");
        //Assert
        assertEquals("User's last name is empty", thrown.getMessage(),
                "Exception error message is not correct");
    }
    @DisplayName("Empty email causes correct exception")
    @Test
    void testCreateUser_whenEmailIsEmpty_throwsIllegalArgumentException() {
        email = "";
        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty email should have cause and Illegal Argument Exception");
        //Assert
        assertEquals("User's email is empty", thrown.getMessage(),
                "Exception error message is not correct");
    }
    @DisplayName("Non matching passwords causes correct exception")
    @Test
    void testCreateUser_whenPasswordsDoNotMatch_throwsIllegalArgumentException() {
        password = "hello";
        repeatPassword= "h3llo";
        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Passwords not matching should have cause and Illegal Argument Exception");
        //Assert
        assertEquals("passwords do not match", thrown.getMessage(),
                "Exception error message is not correct");
    }

    @DisplayName("If save() method causes RuntimeException, a UserServiceException is thrown")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException(){
        //arrange
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        //act & assert
        assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName,lastName,email,password,repeatPassword);
        }, "should have thrown UserserviceException instead");

        //assert
    }

    @DisplayName("EmailNotification is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException(){

        when(usersRepository.save(any(User.class))).thenReturn(true);

        doThrow(EmailNotificationServiceException.class)
                .when(emailVerificationService)
                        .scheduleEmailConfirmation(any(User.class));

        assertThrows(UserServiceException.class, ()-> {
            userService
                    .createUser(firstName,lastName,email,password,repeatPassword);
        }, "Should have thrown UserServiceException instead");

        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    @Test
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation(){
    //Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        doCallRealMethod().when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        //act
        userService
                .createUser(firstName,lastName,email,password,repeatPassword);
        //assert
        verify(emailVerificationService, times(1))
                .scheduleEmailConfirmation(any(User.class));
    }


}
