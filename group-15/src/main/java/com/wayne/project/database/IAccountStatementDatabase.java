package com.wayne.project.database;

import java.sql.SQLException;
import java.util.List;

public interface IAccountStatementDatabase {

    public List<String[]> getCurrentMonthStatement(String customerId, int currentMonth, int currentYear) throws SQLException;
    public List<String[]> getQuarterlyStatement(String customerId, int currentYear) throws SQLException;
    public List<String[]> getYearlyStatement(String customerId, int currentYear) throws SQLException;
    public List<String[]> getCustomStatement(String customerId, String startDate, String finalDate) throws SQLException;
}
