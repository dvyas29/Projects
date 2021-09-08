package com.wayne.project.contactInformation;

import java.sql.SQLException;

public interface IContactInformation {

    public String getContactInformation() throws SQLException;
    public boolean sendQueryInformation(String customerName, String customerId, String email, String query) throws SQLException;
}
