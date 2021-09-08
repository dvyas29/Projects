package com.wayne.project.database;

import java.sql.SQLException;

public interface IBeneficiaryDatabase {

    public boolean createBeneficiary(String customerId,String BeneficiaryName, String BankName, String BeneficiaryAccountNumber, String BeneficiaryIFSCode, int TransferLimit) throws SQLException;
    public String listBeneficiaries(String customer_id) throws SQLException;
    public boolean deleteBeneficiary(String customer_id, String BeneficiaryName) throws SQLException;
    public String getBankName(String BeneficiaryName) throws SQLException;
    public int getTransferLimit(String BeneficiaryName) throws SQLException;
}
