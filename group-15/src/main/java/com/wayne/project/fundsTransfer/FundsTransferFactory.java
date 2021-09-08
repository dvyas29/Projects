package com.wayne.project.fundsTransfer;

import java.sql.SQLException;

public abstract class FundsTransferFactory {

    public abstract IBeneficiary createBeneficiaryObject(String customerId) throws SQLException;
    public abstract IBeneficiaryTransfer createBeneficiaryTransferObject(String customerId) throws SQLException;
}
