package com.epam.preprod.manager;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class);
    private DataBaseManager dataBaseManager;

    public TransactionManager(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public <T> T doInTransaction(TransactionOperation<T> operation) {
        T result = null;
        Connection connection = null;
        try {
            connection = dataBaseManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            result = operation.operation(connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error(e1);
            }
            logger.error(e);
        } finally {
            dataBaseManager.closeConnection(connection);
        }
        return result;
    }
}
