package com.epam.preprod.dao.impl.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.epam.preprod.dao.UserDAO;
import com.epam.preprod.entity.User;
import com.epam.preprod.entity.enums.Role;

public class MySqlUserDaoImpl implements UserDAO {

    private static final Logger logger = Logger.getLogger(MySqlUserDaoImpl.class);
    private static final String SQL_ADD_USER = "INSERT INTO user (role_id, firstName, lastName,email, password, sendNews, imgName) VALUES (?,?,?,?,?,?)";
    private static final String SQL_GET_USER_BY_EMAIL = "SELECT id, role_id, firstName,lastName,email,password,sendNews, imgName FROM user WHERE email = ?";

    @Override
    public Optional<User> getUserByEmail(String email, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                User user = extractUserFromResultSet(resultSet);
                if (Objects.nonNull(user)) {
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Can`t get user by this email", e);
        }
        return Optional.empty();
    }

    @Override
    public int addUser(User user, Connection connection) {
        return addUser(connection, user);
    }

    private int addUser(Connection con, User user) {
        int id = 0;
        try (PreparedStatement statement = con.prepareStatement(SQL_ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            fillPrepareStatementForAddUser(user, statement);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    user.setId(id);
                }
            }
        } catch (SQLException e) {
            logger.error("Can`t add user", e);
        }
        return id;
    }

    private User extractUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));

                Role role = Role.values()[resultSet.getInt("role_id")];
                user.setRole(role);

                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setSendNews(resultSet.getBoolean("sendNews"));
                user.setImageName(resultSet.getString("imgName"));
            }
        } catch (SQLException e) {
            logger.error("Can`t extract user from result set", e);
        }
        return user;
    }

    private void fillPrepareStatementForAddUser(User user, PreparedStatement statement) {
        int k = 0;
        try {
            statement.setInt(++k, user.getRole().getNumericValue());
            statement.setString(++k, user.getFirstName());
            statement.setString(++k, user.getLastName());
            statement.setString(++k, user.getEmail());
            statement.setString(++k, user.getPassword());
            statement.setBoolean(++k, user.isSendNews());
            statement.setString(++k, user.getImageName());
        } catch (SQLException e) {
            logger.error("Can`t fill prepared statement", e);
        }
    }
}
