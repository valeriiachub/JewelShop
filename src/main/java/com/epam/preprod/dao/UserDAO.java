package com.epam.preprod.dao;

import com.epam.preprod.entity.User;

import java.sql.Connection;
import java.util.Optional;

public interface UserDAO {

    Optional<User> getUserByEmail(String email, Connection connection);

    int addUser(User user, Connection connection);
}
