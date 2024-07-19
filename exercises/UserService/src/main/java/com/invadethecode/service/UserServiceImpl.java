package com.invadethecode.service;

import com.invadethecode.data.UsersRepository;
import com.invadethecode.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    UsersRepository usersRepository;
    EmailVerificationService emailVerificationService;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("User's email is empty");
        }

        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("passwords do not match");
        }

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());
//dependency injection_ it depends on the userRepository
        boolean isUserCreated = false;
        try {
            isUserCreated = usersRepository.save(user);
        } catch(RuntimeException ex) {
            throw new UserServiceException(ex.getMessage());
        }

        if (!isUserCreated) throw new UserServiceException("Could not create user");

        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        }catch(RuntimeException ex){
            throw new UserServiceException(ex.getMessage());
        }
        return user;
    }

    public void demoMethod(){
        System.out.println("Demo method");
    }
}
