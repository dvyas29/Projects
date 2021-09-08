package com.wayne.project.accountInformation;

import java.sql.SQLException;

public interface IAccountSummary {

    public String getAccountSummary() throws SQLException;
    public boolean updateAccountBalance(String accountType, float amount) throws SQLException;

}
