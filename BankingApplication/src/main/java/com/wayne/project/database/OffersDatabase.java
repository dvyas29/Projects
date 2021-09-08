package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OffersDatabase extends ConnectToDatabase implements IOffersDatabase {

    Connection connectionForOffers;
    Statement statementForOffers;
    ResultSet resultSetForOffers;
    private String offerName;
    private String offerStatus;
    private String offerDescription;
    private String allOffers = "";

    @Override
    public String getCreditCardOffers() throws SQLException {

        try{
            connectionForOffers = this.createDatabaseConnection();
            statementForOffers = connectionForOffers.createStatement();
            String getCreditCardOffersQuery = "Select * from offers WHERE offer_name = 'CreditCard';";
            resultSetForOffers = statementForOffers.executeQuery(getCreditCardOffersQuery);
            while(resultSetForOffers.next()){

                offerName = resultSetForOffers.getString("offer_name");
                offerDescription = resultSetForOffers.getString("offer_description");
                offerStatus = resultSetForOffers.getString("offer_status");
                allOffers +="\n"+offerName+":- "+offerDescription+" Status :- "+offerStatus;
            }
            return allOffers;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForOffers.close();
            resultSetForOffers.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String getLoanOffers() throws SQLException {

        try{
            connectionForOffers = this.createDatabaseConnection();
            statementForOffers = connectionForOffers.createStatement();
            String getLoanOffersQuery = "Select * from offers WHERE offer_name = 'Loan';";
            resultSetForOffers = statementForOffers.executeQuery(getLoanOffersQuery);
            while(resultSetForOffers.next()){
                offerName = resultSetForOffers.getString("offer_name");
                offerDescription = resultSetForOffers.getString("offer_description");
                offerStatus = resultSetForOffers.getString("offer_status");
                allOffers +="\n"+offerName+":- "+offerDescription+" Status :- "+offerStatus;
            }
            return allOffers;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForOffers.close();
            resultSetForOffers.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String getMutualFundsOffers() throws SQLException {
            try{
                connectionForOffers = this.createDatabaseConnection();
                statementForOffers = connectionForOffers.createStatement();
                String getMutualFundsQuery = "Select * from offers WHERE offer_name = 'MutualFund';";
                resultSetForOffers = statementForOffers.executeQuery(getMutualFundsQuery);
                while(resultSetForOffers.next()){
                    offerName = resultSetForOffers.getString("offer_name");
                    offerStatus = resultSetForOffers.getString("offer_status");
                    allOffers +="\n"+offerName+":- "+offerDescription+" Status :- "+offerStatus;
                }
                return allOffers;
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            finally {
                statementForOffers.close();
                resultSetForOffers.close();
                this.closeDatabaseConnection();
            }
            return null;
        }

    @Override
    public String geLifeInsuranceOffers() throws SQLException {

        try{
            connectionForOffers = this.createDatabaseConnection();
            statementForOffers = connectionForOffers.createStatement();
            String getLifeInsuranceOffersQuery = "Select * from offers WHERE offer_name = 'LifeInsurance';";
            resultSetForOffers = statementForOffers.executeQuery(getLifeInsuranceOffersQuery);
            while(resultSetForOffers.next()){

                offerName = resultSetForOffers.getString("offer_name");
                offerDescription = resultSetForOffers.getString("offer_description");
                offerStatus = resultSetForOffers.getString("offer_status");
                allOffers +="\n"+offerName+":- "+offerDescription+" Status :- "+offerStatus;
            }
            return allOffers;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForOffers.close();
            resultSetForOffers.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String getAutomotiveLoanOffers() throws SQLException {

        try{
            connectionForOffers = this.createDatabaseConnection();
            statementForOffers = connectionForOffers.createStatement();
            String getAutomotiveLoanOffersQuery = "Select * from offers WHERE offer_name = 'Automotive';";
            resultSetForOffers = statementForOffers.executeQuery(getAutomotiveLoanOffersQuery);
            while(resultSetForOffers.next()){

                offerName = resultSetForOffers.getString("offer_name");
                offerDescription = resultSetForOffers.getString("offer_description");
                offerStatus = resultSetForOffers.getString("offer_status");
                allOffers +="\n"+offerName+":- "+offerDescription+" Status :- "+offerStatus;
            }
            return allOffers;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForOffers.close();
            resultSetForOffers.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String getTouristLoanOffers() throws SQLException {

        try{
            connectionForOffers = createDatabaseConnection();
            statementForOffers = connectionForOffers.createStatement();
            String getTouristLoanOffersQuery = "Select * from offers WHERE offer_name = 'Tourist';";
            resultSetForOffers = statementForOffers.executeQuery(getTouristLoanOffersQuery);
            while(resultSetForOffers.next()){

                offerName = resultSetForOffers.getString("offer_name");
                offerDescription = resultSetForOffers.getString("offer_description");
                offerStatus = resultSetForOffers.getString("offer_status");
                allOffers +="\n"+offerName+":- "+offerDescription+" Status :- "+offerStatus;
            }
            return allOffers;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForOffers.close();
            resultSetForOffers.close();
            this.closeDatabaseConnection();
        }
        return null;
    }
}
