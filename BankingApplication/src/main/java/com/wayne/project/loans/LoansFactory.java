package com.wayne.project.loans;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public abstract class LoansFactory {

    public abstract ILoans createLoansObject(String customerId) throws SQLException, FileNotFoundException;
}
