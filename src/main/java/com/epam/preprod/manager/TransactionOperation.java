package com.epam.preprod.manager;

import java.sql.Connection;

public interface TransactionOperation<T> {

    T operation(Connection connection);
}
