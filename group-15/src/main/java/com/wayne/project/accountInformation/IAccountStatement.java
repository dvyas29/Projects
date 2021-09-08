package com.wayne.project.accountInformation;

import java.sql.Date;

public interface IAccountStatement {

    public void showCurrentMonthStatement();
    public void showQuarterlyStatement();
    public void showYearlyStatement();
    public void showCustomStatement(String startDate, String endDate);
}
