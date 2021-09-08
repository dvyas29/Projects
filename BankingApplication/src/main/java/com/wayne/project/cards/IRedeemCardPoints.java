package com.wayne.project.cards;

import java.sql.SQLException;

public interface IRedeemCardPoints {

    public int fetchRedeemCardPoints(String typeOfCard) throws SQLException;

    public String redeemCardPoints(String typeOfCard, int redeemCardPoints) throws SQLException;
}
