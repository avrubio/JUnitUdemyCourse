package com.invadethecode.service;

import com.invadethecode.data.UsersRepository;
import com.invadethecode.data.UsersRepositoryImpl;
import com.invadethecode.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
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
        boolean isUserCreated = usersRepository.save(user);

        if (!isUserCreated) throw new UserServiceException("Could not create user");

        return user;
    }

}
