package singleton;

import simulation.ApplicationManager;

public class ApplicationManagerSingleton {
    private static ApplicationManager applicationManagerSingleAccess;
    static{
        applicationManagerSingleAccess = new ApplicationManager();
    }

    public static ApplicationManager getApplicationManagerSingleAccess() {
        return applicationManagerSingleAccess;
    }
}
