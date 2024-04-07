import simulation.ApplicationManager;
import singleton.ApplicationManagerSingleton;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ApplicationManager applicationManager = ApplicationManagerSingleton.getApplicationManagerSingleAccess();
        applicationManager.run();
    }
}
