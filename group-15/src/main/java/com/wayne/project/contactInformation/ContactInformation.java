package com.wayne.project.contactInformation;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IContactInformationDatabase;
import java.sql.SQLException;

public class ContactInformation implements IContactInformation {

    private IContactInformationDatabase contactInformationDatabase;
    private DatabaseFactory databaseFactory;

    public ContactInformation() {

        databaseFactory = new DatabaseFactoryNormal();
        contactInformationDatabase = databaseFactory.createContactInformationDatabaseObject();
    }

    public String getContactInformation() throws SQLException {

        String contactDetails = "";
        contactDetails = contactInformationDatabase.loadContactInformation();
        return contactDetails;
    }

    public boolean sendQueryInformation(String customerName, String customerId, String email, String query) throws SQLException {

        contactInformationDatabase.saveContactQuery(customerName,customerId,email,query);
        return true;
    }

}
