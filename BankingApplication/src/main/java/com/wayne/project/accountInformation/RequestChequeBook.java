package com.wayne.project.accountInformation;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IRequestChequeBookDatabase;

import java.sql.SQLException;

public class RequestChequeBook implements IRequestChequeBook{
    IRequestChequeBookDatabase requestChequeBookDatabase;
    private String customerId;
    DatabaseFactory databaseFactory;

    public RequestChequeBook(String customerId) {
        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        requestChequeBookDatabase = databaseFactory.createRequestChequeBookDatabase();
    }

    @Override
    public String requestChequeBook(String accountType, String choice) throws SQLException {
        if(choice.equalsIgnoreCase("y")){
                boolean isStatusUpdated = requestChequeBookDatabase.updateChequeBookStatus(customerId, accountType);
                if(isStatusUpdated == true){
                    return "ChequeBookStatus Updated to Yes";
                }
        }
        return "ChequeBookStatus not Updated";
    }
}
