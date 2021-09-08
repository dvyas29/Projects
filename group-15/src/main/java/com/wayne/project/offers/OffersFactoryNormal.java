package com.wayne.project.offers;

public class OffersFactoryNormal extends OffersFactory{
    @Override
    public IAutomotiveLoanOffers createAutomotiveLoanOffersObject() {
        return new AutomotiveLoanOffers();
    }

    @Override
    public ICreditCardOffers createCreditCardOffersObject() {
        return new CreditCardOffers();
    }

    @Override
    public ILifeInsuranceOffers createLifeInsuranceOffersObject() {
        return new LifeInsuranceOffers();
    }

    @Override
    public ILoanOffers createLoanOffersObject() {
        return new LoanOffers();
    }

    @Override
    public IMutualFundsOffers createMutualFundsOffersObject() {
        return new MutualFundsOffers();
    }

    @Override
    public ITouristLoanOffers createTouristLoanOffersObject() {
        return new TouristLoanOffers();
    }
}
