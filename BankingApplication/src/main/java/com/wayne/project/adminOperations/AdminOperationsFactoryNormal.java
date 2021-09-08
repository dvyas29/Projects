package com.wayne.project.adminOperations;

public class AdminOperationsFactoryNormal extends AdminOperationsFactory {

    public IAdminOperations createAdminOperationsObject() {

        return new AdminOperations();
    }
}
