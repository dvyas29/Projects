package com.wayne.project.fundsTransfer;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IBeneficiaryDatabase;
import java.sql.SQLException;

public class Beneficiary implements IBeneficiary {

    private String customerId;
    String beneficiaries;
    private IBeneficiaryDatabase beneficiaryDatabase;
    private DatabaseFactory databaseFactory;

    public Beneficiary(String customerId) {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        beneficiaryDatabase = databaseFactory.createBeneficiaryDatabaseObject();
    }

    public boolean addBeneficiary(String customerId,String BeneficiaryName,String BankName,String BeneficiaryAccountNumber,String BeneficiaryIFSCode,int TransferLimit) throws SQLException {

        beneficiaryDatabase.createBeneficiary(customerId,BeneficiaryName,BankName,BeneficiaryAccountNumber,BeneficiaryIFSCode,TransferLimit);
        return true;
    }

    public String beneficiaryList(String customerId) throws SQLException {

        this.customerId = customerId;
        beneficiaries = beneficiaryDatabase.listBeneficiaries(customerId);
        return beneficiaries;
    }

    public boolean removeBeneficiary(String customerId, String BeneficiaryName) throws SQLException {

        this.customerId = customerId;
        beneficiaryDatabase.deleteBeneficiary(customerId,BeneficiaryName);
        return true;
    }
}
