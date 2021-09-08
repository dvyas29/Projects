package com.wayne.project.database;

import java.sql.SQLException;

public interface IOnlineUsageLimitDatabase {

    public boolean setUsageLimit(String accountNumber, int onlineUsageLimit, String typeOfCard) throws SQLException;
}
