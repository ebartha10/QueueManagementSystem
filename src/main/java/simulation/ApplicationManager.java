package simulation;
import server.impl.ServerImpl;
import ui.SimulationGUI;

import javax.swing.*;

import static java.lang.Thread.sleep;

public class ApplicationManager implements Runnable{
    private SimulationManager simulationManager;
    volatile SimulationGUI simulationGUI;
    final Thread simulationInterfaceThread;

    public ApplicationManager() {
        this.simulationGUI = new SimulationGUI();
        simulationInterfaceThread = new Thread(simulationGUI);

        JFrame frame = new JFrame("Simulation");
        frame.setContentPane(simulationGUI.$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        while(true) {
            synchronized (simulationGUI.getStartSimulationButton()) {
                System.out.println("WAITING");
                try {
                    simulationGUI.getStartSimulationButton().wait();
                }
                catch (InterruptedException e) {
                    System.out.println("Unexpected exit!");
                    break;
                }

                simulationManager = parseSimulationParametersFromGUI(simulationGUI);
                simulationGUI.initSimulation(simulationManager.getNumberOfServers());

                Thread simulationManagerThread = new Thread(simulationManager);
                simulationManagerThread.start();
                simulationGUI.getResetButton().setVisible(true);

                while (simulationManagerThread.isAlive()) {
                    simulationGUI.updatePanels(simulationManager.getScheduler().getServerList());

                    if (simulationGUI.isResetSim()) {
                        for (ServerImpl server : simulationManager.getServerList()) {
                            server.stopServer();
                        }
                        simulationManagerThread.interrupt();
                        simulationGUI.resetSimulation();
                        break;
                    }
                    try {
                        sleep(500);
                    }
                    catch (InterruptedException e) {
                        System.out.println("Unexpected Exit!");
                        return;
                    }
                }
                simulationGUI.updatePanels(simulationManager.getScheduler().getServerList());
            }
        }
    }

    //TODO ADD CHECKS FOR NULL ETC
    private SimulationManager parseSimulationParametersFromGUI(SimulationGUI simulationGUI) {
        int clients = Integer.parseInt(simulationGUI.getTextFieldClients().getText());
        int queues = Integer.parseInt(simulationGUI.getTextFieldQueues().getText());
        int simInterval = Integer.parseInt(simulationGUI.getTextFieldInterval().getText());
        int minArrival = Integer.parseInt(simulationGUI.getTextFieldMinArrive().getText());
        int maxArrival = Integer.parseInt(simulationGUI.getTextFieldMaxArrive().getText());
        int minService = Integer.parseInt(simulationGUI.getTextMinService().getText());
        int maxService = Integer.parseInt(simulationGUI.getTextMaxService().getText());
        return new SimulationManager(queues, simInterval, minService, maxService, minArrival, maxArrival, clients);
    }
}
