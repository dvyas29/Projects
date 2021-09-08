package com.wayne.project.database;

import java.sql.*;

public class BeneficiaryDatabase extends ConnectToDatabase implements IBeneficiaryDatabase {

    private Connection connectionForBeneficiary;
    private Statement statementForBeneficiary;
    private ResultSet resultSetForBeneficiary;

    public boolean createBeneficiary(String customerId,String BeneficiaryName, String BankName, String BeneficiaryAccountNumber, String BeneficiaryIFSCcode, int TransferLimit) throws SQLException {

        String sqlQueryToCreateBeneficiary = "INSERT INTO beneficiaries" + "(customer_id,beneficiary_name, bank_name, beneficiary_account_number, beneficiary_ifsc,transfer_limit)" +
                "VALUES ('"+customerId+"','"+BeneficiaryName+"', '"+BankName+"', '"+BeneficiaryAccountNumber+"', '"+BeneficiaryIFSCcode+"', '"+TransferLimit+"');";
        connectionForBeneficiary = this.createDatabaseConnection();
        statementForBeneficiary = connectionForBeneficiary.createStatement();
        try{
            boolean added = statementForBeneficiary.execute(sqlQueryToCreateBeneficiary);
            return added;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForBeneficiary.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    public String listBeneficiaries(String customerId) throws SQLException {

        String beneficiaries = "";
        connectionForBeneficiary = this.createDatabaseConnection();
        String sqlQueryToListBeneficiaries = "Select * from beneficiaries where customer_id = '"+customerId+"';";
        statementForBeneficiary = connectionForBeneficiary.createStatement();
        try{
            resultSetForBeneficiary = statementForBeneficiary.executeQuery(sqlQueryToListBeneficiaries);
            while (resultSetForBeneficiary.next()){
                int beneficiary_id = resultSetForBeneficiary.getInt("beneficiary_id");
                String customer_id = resultSetForBeneficiary.getString("customer_id");
                String beneficiary_name = resultSetForBeneficiary.getString("beneficiary_name");
                String bankName = resultSetForBeneficiary.getString("bank_name");
                String beneficiary_accountNumber = resultSetForBeneficiary.getString("beneficiary_account_number");
                String beneficiary_IFSC = resultSetForBeneficiary.getString("beneficiary_ifsc");
                int transfer_limit = resultSetForBeneficiary.getInt("transfer_limit");
                beneficiaries = beneficiaries + beneficiary_id + " " + customer_id+" "+beneficiary_name + " " + bankName + " " + beneficiary_accountNumber + " " + beneficiary_IFSC + " " + transfer_limit + "\n";
            }
            return beneficiaries;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForBeneficiary.close();
            statementForBeneficiary.close();
            this.closeDatabaseConnection();
        }
        return beneficiaries;
    }

    public boolean deleteBeneficiary(String customer_id, String BeneficiaryName) throws SQLException {

        connectionForBeneficiary = this.createDatabaseConnection();
        statementForBeneficiary = connectionForBeneficiary.createStatement();
        String SqlQueryToDeleteBeneficiary = "Delete from beneficiaries where beneficiary_name = '"+BeneficiaryName+"' and customer_id = '"+customer_id+"'";
        try{
            int isDeleted = statementForBeneficiary.executeUpdate(SqlQueryToDeleteBeneficiary);
            if (isDeleted > 0) {
                return true;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForBeneficiary.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    public String getBankName(String BeneficiaryName) throws SQLException {
        
        String fetchedBankName = null;
        connectionForBeneficiary = this.createDatabaseConnection();
        statementForBeneficiary = connectionForBeneficiary.createStatement();
        String SqlQueryToGetBankName = "select bank_name from beneficiaries where beneficiary_name = '"+BeneficiaryName+"';";
        try{
            resultSetForBeneficiary = statementForBeneficiary.executeQuery(SqlQueryToGetBankName);
            while(resultSetForBeneficiary.next()) {
                fetchedBankName = resultSetForBeneficiary.getString("bank_name");
            }
            return fetchedBankName;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForBeneficiary.close();
            statementForBeneficiary.close();
            this.closeDatabaseConnection();
        }
        return fetchedBankName;
    }

    public int getTransferLimit(String BeneficiaryName) throws SQLException {

        int transferLimit = 0;
        connectionForBeneficiary = this.createDatabaseConnection();
        statementForBeneficiary = connectionForBeneficiary.createStatement();
        String sqlQueryToGetTransferLimit = "Select transfer_limit from beneficiaries where beneficiary_name = '"+BeneficiaryName+"'";
        try {
            resultSetForBeneficiary = statementForBeneficiary.executeQuery(sqlQueryToGetTransferLimit);
            while (resultSetForBeneficiary.next()) {
                transferLimit = resultSetForBeneficiary.getInt("transfer_limit");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForBeneficiary.close();
            statementForBeneficiary.close();
            this.closeDatabaseConnection();
        }
        return transferLimit;
    }

}
