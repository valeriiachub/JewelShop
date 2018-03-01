package com.epam.preprod.service.impl;

import com.epam.preprod.dao.UserDAO;
import com.epam.preprod.entity.User;
import com.epam.preprod.manager.TransactionManager;
import com.epam.preprod.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private TransactionManager transactionManager;
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO, TransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.transactionManager = transactionManager;
    }

    @Override
    public int addUser(User user) {
        return transactionManager.doInTransaction((connection) ->
                userDAO.addUser(user, connection));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return transactionManager.doInTransaction((connection) ->
                userDAO.getUserByEmail(email, connection));
    }
}
