package com.wayne.project.accountInformation;

import java.sql.SQLException;

public interface IAccountClosure {

    public boolean closeAccount(String choice) throws SQLException;
}
