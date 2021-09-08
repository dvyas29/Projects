package com.wayne.project.loans;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class LoansFactoryNormal extends LoansFactory {

    public ILoans createLoansObject(String customerId) throws SQLException, FileNotFoundException {
        return new Loans(customerId);
    }
}
