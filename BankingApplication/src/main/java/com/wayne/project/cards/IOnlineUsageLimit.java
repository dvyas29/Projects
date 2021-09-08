package com.wayne.project.cards;

import java.sql.SQLException;

public interface IOnlineUsageLimit {

    public boolean setOnlineUsageLimit(int onlineUsageLimit,String typeOfCard) throws SQLException;

}
