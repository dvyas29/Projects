package com.wayne.project.cards;

import java.sql.SQLException;

public abstract class CardsFactory {

    public abstract ICards createCardsObject(String customerId) throws SQLException;

    public abstract IAutoPay createAutoPayObject(String customerId) throws SQLException;

    public abstract IPaymentReminders createPaymentReminderObject(String customerId);

    public abstract IRedeemCardPoints createRedeemCardPointsObject(String customerId) throws SQLException;

    public abstract IOnlineUsageLimit createOnlineUsageLimitObject(String customerId) throws SQLException;
}
