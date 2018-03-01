package com.epam.preprod.service;

import com.epam.preprod.entity.User;

import java.util.Optional;

public interface UserService {
    int addUser(User user);

    Optional<User> getUserByEmail(String email);
}
