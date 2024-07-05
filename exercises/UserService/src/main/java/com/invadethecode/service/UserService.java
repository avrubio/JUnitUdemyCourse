package com.invadethecode.service;

import com.invadethecode.model.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);
}
