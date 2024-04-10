package singleton;

import simulation.ApplicationManager;

public class ApplicationManagerSingleton {
    private final static ApplicationManager applicationManagerSingleAccess =  new ApplicationManager();
    public static ApplicationManager getApplicationManagerSingleAccess() {
        return applicationManagerSingleAccess;
    }
}
