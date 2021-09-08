package com.wayne.project.cards;

import java.sql.SQLException;

public class CardsFactoryNormal extends CardsFactory {

    public ICards createCardsObject(String customerId) throws SQLException {
        return new Cards(customerId);
    }

    public IAutoPay createAutoPayObject(String customerId) throws SQLException {
        return new AutoPay(customerId);
    }

    public IPaymentReminders createPaymentReminderObject(String customerId) {
        return new PaymentReminder(customerId);
    }

    public IRedeemCardPoints createRedeemCardPointsObject(String customerId) throws SQLException {
        return new RedeemCardPoints(customerId);
    }

    public IOnlineUsageLimit createOnlineUsageLimitObject(String customerId) throws SQLException {
        return new OnlineUsageLimit(customerId);
    }
}
