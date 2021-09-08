package com.wayne.project.database;

import java.sql.SQLException;

public interface IContactInformationDatabase {

    public String loadContactInformation() throws SQLException;
    public boolean saveContactQuery(String customerName, String customerId, String email, String query) throws SQLException;
    public String fetchContactQueries() throws SQLException;
}
