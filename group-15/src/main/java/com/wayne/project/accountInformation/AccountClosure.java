package com.wayne.project.accountInformation;

import com.wayne.project.adminOperations.AdminOperationsFactory;
import com.wayne.project.adminOperations.AdminOperationsFactoryNormal;
import com.wayne.project.adminOperations.IAdminOperations;


import java.sql.SQLException;

public class AccountClosure implements IAccountClosure{

    private IAdminOperations adminOperations;
    private AdminOperationsFactory adminOperationsFactory;

    String customerId;

    public AccountClosure(String customerId) throws SQLException {
        this.customerId = customerId;
        adminOperationsFactory = new AdminOperationsFactoryNormal();
        adminOperations= adminOperationsFactory.createAdminOperationsObject();
    }

    @Override
    public boolean closeAccount(String choice) throws SQLException {

        if(choice.equalsIgnoreCase("y")){
            return adminOperations.closeAccount(customerId);
        }
        return false;
    }
}
