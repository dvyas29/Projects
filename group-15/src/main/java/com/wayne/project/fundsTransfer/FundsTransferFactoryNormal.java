package com.wayne.project.fundsTransfer;

import java.sql.SQLException;

public class FundsTransferFactoryNormal extends FundsTransferFactory{

    public IBeneficiary createBeneficiaryObject(String customerId) throws SQLException {

        return new Beneficiary(customerId);
    }

    public IBeneficiaryTransfer createBeneficiaryTransferObject(String customerId) throws SQLException {

        return new BeneficiaryTransfer(customerId);
    }

}
