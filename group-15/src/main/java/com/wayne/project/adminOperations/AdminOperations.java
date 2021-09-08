package com.wayne.project.adminOperations;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAdminOperationsDatabase;
import com.wayne.project.database.ILoansDatabase;
import java.sql.SQLException;

public class AdminOperations implements IAdminOperations {

    private IAdminOperationsDatabase adminOperationsDatabase;
    private DatabaseFactory databaseFactory;
    private ILoansDatabase loansDatabase;

    public AdminOperations() {

        databaseFactory = new DatabaseFactoryNormal();
        adminOperationsDatabase = databaseFactory.createAdminOperationsDatabaseObject();
        loansDatabase = databaseFactory.createLoansDatabaseObject();
    }

    @Override
    public void createCustomer(String customerUserName, String customerName, String customerPassword, String email, String contact, String address,String ssn) throws SQLException {

        adminOperationsDatabase.persistCustomer(customerUserName,customerName,customerPassword,email,contact,address,ssn);
    }

    @Override
    public boolean closeAccount(String customerId) throws SQLException {

        adminOperationsDatabase.deleteAccount(customerId);
        return true;
    }

    @Override
    public boolean updateCustomerQuery(String customerId) throws SQLException {

        adminOperationsDatabase.updateCustomerQueryStatus(customerId);
        return true;
    }

    @Override
    public boolean approveLoanRequest(String customerId, String customerName, String email, double amount, double loanEstimate, int timePeriod, double annualIncome) throws SQLException {

        String loanType = "personal";
        double loanEMI = loanEstimate/timePeriod;
        if (loanEMI < annualIncome) {
            String status = "Approved";
            double loanAvailable = 0.05 * loanEstimate;
            loansDatabase.saveLoansApplication(customerId,customerName,email,loanAvailable,timePeriod,loanType,status);
            return true;
        }
        else {
            return false;
        }
    }
}
