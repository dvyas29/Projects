package com.wayne.project.database;

import java.sql.SQLException;

public interface IRequestChequeBookDatabase {
    public boolean updateChequeBookStatus(String customerId, String accountType) throws SQLException;
}
