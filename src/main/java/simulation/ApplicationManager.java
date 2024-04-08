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
        //frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void run() {
        while(true) {
            synchronized (simulationGUI.getStartSimulationButton()) {
                System.out.println("WAITING FOR SIMULATION");
                try {
                    simulationGUI.getStartSimulationButton().wait();
                }
                catch (InterruptedException e) {
                    System.out.println("Unexpected exit!");
                    break;
                }

                simulationManager = parseSimulationParametersFromGUI(simulationGUI);
                if(simulationManager == null){
                    continue;
                }
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
        String clientsText = simulationGUI.getTextFieldClients().getText();
        String queuesText = simulationGUI.getTextFieldQueues().getText();
        String simIntervalText = simulationGUI.getTextFieldInterval().getText();
        String minArrivalText = simulationGUI.getTextFieldMinArrive().getText();
        String maxArrivalText = simulationGUI.getTextFieldMaxArrive().getText();
        String minServiceText = simulationGUI.getTextMinService().getText();
        String maxServiceText = simulationGUI.getTextMaxService().getText();
        if (clientsText == null || clientsText.isEmpty()
                || queuesText == null || queuesText.isEmpty()
                || simIntervalText == null || simIntervalText.isEmpty()
                || minArrivalText == null || minArrivalText.isEmpty()
                || maxArrivalText == null || maxArrivalText.isEmpty()
                || minServiceText == null || minServiceText.isEmpty()
                || maxServiceText == null || maxServiceText.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter numbers!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int clients = Integer.parseInt(simulationGUI.getTextFieldClients().getText());
        int queues = Integer.parseInt(simulationGUI.getTextFieldQueues().getText());
        int simInterval = Integer.parseInt(simulationGUI.getTextFieldInterval().getText());
        int minArrival = Integer.parseInt(simulationGUI.getTextFieldMinArrive().getText());
        int maxArrival = Integer.parseInt(simulationGUI.getTextFieldMaxArrive().getText());
        int minService = Integer.parseInt(simulationGUI.getTextMinService().getText());
        int maxService = Integer.parseInt(simulationGUI.getTextMaxService().getText());

        if(clients < 0 ){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid client number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(queues < 0 ){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid queue number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(simInterval < 0 ){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid simulation interval number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(minArrival < 0 || minArrival > simInterval){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid minimum arrival number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(maxArrival < 0 || maxArrival > simInterval || maxArrival < minArrival ){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid maximum arrival number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(minService < 0 || minService > simInterval){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid minimum service number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if(maxService < 0 || maxService > simInterval || maxService < minArrival ){
            JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid maximum service number!", "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new SimulationManager(queues, simInterval, minService, maxService, minArrival, maxArrival, clients);
    }
}
