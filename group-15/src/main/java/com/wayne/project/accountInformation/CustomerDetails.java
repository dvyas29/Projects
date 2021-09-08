package com.wayne.project.accountInformation;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.ICustomerDetailsDatabase;

import java.sql.SQLException;

public class CustomerDetails implements  ICustomerDetails{

    ICustomerDetailsDatabase customerDetailsDatabase;
    private String customerId;
    private DatabaseFactory databaseFactory;

    public CustomerDetails(String customerId) {
        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        customerDetailsDatabase = databaseFactory.createCustomerDetailsDatabase();
    }

    @Override
    public boolean updateAddress(String address) throws SQLException {
        boolean isAddressUpdated = customerDetailsDatabase.updateAddress(customerId, address);
        return isAddressUpdated;
    }

    @Override
    public boolean updateSSN(double SSN) throws SQLException {
        boolean isSSNUpdated = customerDetailsDatabase.updateSSN(customerId, SSN);
        return isSSNUpdated;
    }

    @Override
    public boolean updateEmail(String email) throws SQLException {
        boolean isEmailUpdated = customerDetailsDatabase.updateEmail(customerId , email);
        return isEmailUpdated;
    }
}
