package com.wayne.project.accountInformation;

import java.sql.SQLException;

public interface IRequestChequeBook {

    public String requestChequeBook(String accountType, String choice) throws SQLException;
}
