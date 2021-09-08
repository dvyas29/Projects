package com.wayne.project.database;

import java.sql.SQLException;

public interface IRedeemCardPointsDatabase {

    public int fetchRedeemCardPoints(String accountNumber, String typeOfCard) throws SQLException;

    public boolean setRedeemCardPointsToZero(String accountNumber, String typeOfCard) throws SQLException;
}
