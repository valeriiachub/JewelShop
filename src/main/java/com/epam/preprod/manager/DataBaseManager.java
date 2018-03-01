package com.epam.preprod.manager;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseManager {
    private static final Logger logger = Logger.getLogger(DataBaseManager.class);

    public Connection getConnection() {
        Connection connection = null;
        try {
            logger.debug("Start");
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/webshop");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException ex) {
            logger.error("Cannot obtain a connection from the pool", ex);
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            logger.debug("Try close connection.");
            if (connection != null) {
                logger.debug("Connection is closed");
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Can not close connection." + e.getMessage());
        }
    }

}
