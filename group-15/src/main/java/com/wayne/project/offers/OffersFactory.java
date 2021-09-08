package com.wayne.project.offers;

public abstract class OffersFactory {

    public abstract IAutomotiveLoanOffers createAutomotiveLoanOffersObject();
    public abstract ICreditCardOffers createCreditCardOffersObject();
    public abstract ILifeInsuranceOffers createLifeInsuranceOffersObject();
    public abstract ILoanOffers createLoanOffersObject();
    public abstract IMutualFundsOffers createMutualFundsOffersObject();
    public abstract ITouristLoanOffers createTouristLoanOffersObject();
}
