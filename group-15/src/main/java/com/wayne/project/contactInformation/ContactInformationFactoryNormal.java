package com.wayne.project.contactInformation;

public class ContactInformationFactoryNormal extends ContactInformationFactory{

    public IContactInformation createContactInformationObject() {

        return new ContactInformation();
    }
}
