package com.wayne.project.fundsTransfer;

import java.sql.SQLException;

public interface IBeneficiary {

    public boolean addBeneficiary(String customerId,String BeneficiaryName,String BankName,String BeneficiaryAccountNumber,String BeneficiaryIFSCcode,int TransferLimit) throws SQLException;
    public String beneficiaryList(String customerId) throws SQLException;
    public boolean removeBeneficiary(String customer_id, String BeneficiaryName) throws SQLException;
}
