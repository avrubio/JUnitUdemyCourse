package com.invadethecode.service;

import com.invadethecode.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
