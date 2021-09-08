package com.wayne.project.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class RequestChequeBookDatabase extends ConnectToDatabase implements IRequestChequeBookDatabase{

    private Connection connectionForChequeBook;
    private Statement statementForChequeBook;

    @Override
    public boolean updateChequeBookStatus(String customerId, String accountType) throws SQLException {
        try{
            connectionForChequeBook = createDatabaseConnection();
            statementForChequeBook = connectionForChequeBook.createStatement();
            String updateRequestChequeBookQuery="Update account_information SET chequebook = 'YES' WHERE customer_id='"
                                                +customerId+"';";
            boolean isChequeBookStatusUpdated = statementForChequeBook.execute(updateRequestChequeBookQuery);

            return isChequeBookStatusUpdated;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForChequeBook.close();
            this.closeDatabaseConnection();
        }
        return false;
    }
}
