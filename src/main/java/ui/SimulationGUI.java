package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import misc.SharedCounter;
import server.impl.ServerImpl;
import singleton.SharedCounterSingleton;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimulationGUI implements Runnable {
    private JPanel panel1;
    private JTextField textFieldClients;
    private JTextField textFieldQueues;
    private JTextField textFieldInterval;
    private JTextField textFieldMinArrive;
    private JTextField textFieldMaxArrive;
    private JTextField textMinService;
    private JTextField textMaxService;
    private JPanel panelQ;
    private JButton startSimulationButton;
    private JLabel textTime;
    private JButton resetButton;
    private JScrollPane panelScroll;
    private JProgressBar totalTasksBar;
    private List<QueueGUI> queueGUIList;
    private boolean resetSim;
    private final SharedCounter sharedCounter;

    public void updatePanels() {
        panelQ.revalidate();
        panelQ.repaint();
    }

    public void initSimulation(int queueNumber) {
        for (Component t : panelQ.getComponents()) {
            if (t instanceof QueueGUI) {
                panelQ.remove(t);
            }
        }
        panelQ.setLayout(new GridLayoutManager(queueNumber + 1, 1, new Insets(5, 5, 0, 5), -1, -1));
        queueGUIList = new ArrayList<>();
        for (int i = 0; i < queueNumber; i++) {
            QueueGUI queue = new QueueGUI(i + 1);
            panelQ.add(queue, new GridConstraints(i, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK, null, null, null, 0, false));
            queue.setVisible(true);
            queueGUIList.add(queue);
        }
        resetSim = false;
        totalTasksBar.setString("Done: " + sharedCounter.getFinishedTasks());
    }

    public void resetSimulation() {
        textFieldClients.setText("");
        textFieldInterval.setText("");
        textTime.setText("0");
        textFieldQueues.setText("");
        textMaxService.setText("");
        textMinService.setText("");
        textFieldMinArrive.setText("");
        textFieldMaxArrive.setText("");
        for (Component t : panelQ.getComponents()) {
            if (t instanceof QueueGUI) {
                panelQ.remove(t);
            }
        }
        resetSim = false;
        panelQ.revalidate();
        panelQ.repaint();
    }

    @Override
    public void run() {
    }

    public SimulationGUI() {
        sharedCounter = SharedCounterSingleton.getSharedCounter();
        startSimulationButton.addActionListener(e -> {
            synchronized (startSimulationButton) {
                startSimulationButton.notify();
            }
        });
        resetButton.addActionListener(e -> {
            resetSim = true;
            System.out.println("RESETTING SIM");
        });
    }

    public JButton getStartSimulationButton() {
        return this.startSimulationButton;
    }

    public boolean isResetSim() {
        return resetSim;
    }

    public void setResetSim(boolean resetSim) {
        this.resetSim = resetSim;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public void updatePanels(List<ServerImpl> serverList) {

        textTime.setText("" + sharedCounter.getSimulationTime());
        for (int i = 0; i < serverList.size(); i++) {
            int taskRunning = serverList.get(i).getTaskIDRunning();
            double percentDone = serverList.get(i).getPercentDone();
            if (percentDone == 100) {
                queueGUIList.get(i).getProgressBar().setString("Waiting");
                queueGUIList.get(i).getProgressBar().setValue(100);
            } else {
                if (taskRunning != -1) {
                    queueGUIList.get(i).getProgressBar().setValue((int) percentDone);
                    queueGUIList.get(i).getProgressBar().setString("Task #" + taskRunning);
                } else {
                    queueGUIList.get(i).getProgressBar().setValue(0);
                    queueGUIList.get(i).getProgressBar().setString("Waiting");
                }
            }
            queueGUIList.get(i).repaint();
            queueGUIList.get(i).revalidate();
        }
        totalTasksBar.setString("Done: " + sharedCounter.getFinishedTasks());
        panelQ.revalidate();
        panelQ.repaint();
    }

    public JTextField getTextFieldClients() {
        return textFieldClients;
    }

    public JTextField getTextFieldQueues() {
        return textFieldQueues;
    }

    public JTextField getTextFieldInterval() {
        return textFieldInterval;
    }

    public JTextField getTextFieldMinArrive() {
        return textFieldMinArrive;
    }

    public JTextField getTextFieldMaxArrive() {
        return textFieldMaxArrive;
    }

    public JTextField getTextMinService() {
        return textMinService;
    }

    public JTextField getTextMaxService() {
        return textMaxService;
    }

    public JLabel getTextTime() {
        return textTime;
    }

    public JProgressBar getTotalTasks() {
        return totalTasksBar;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setMinimumSize(new Dimension(640, 480));
        panel1.setPreferredSize(new Dimension(640, 480));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(5, 5, new Insets(5, 5, 0, 5), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Number of clients");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldClients = new JTextField();
        panel2.add(textFieldClients, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldInterval = new JTextField();
        panel2.add(textFieldInterval, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Simulation interval");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Minimum arrival");
        panel2.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldMinArrive = new JTextField();
        panel2.add(textFieldMinArrive, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Maximum arrival");
        panel2.add(label4, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldMaxArrive = new JTextField();
        panel2.add(textFieldMaxArrive, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Number of queues");
        panel2.add(label5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldQueues = new JTextField();
        panel2.add(textFieldQueues, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Minimum service");
        panel2.add(label6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textMinService = new JTextField();
        panel2.add(textMinService, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Maximum service");
        panel2.add(label7, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textMaxService = new JTextField();
        panel2.add(textMaxService, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        startSimulationButton = new JButton();
        startSimulationButton.setText("Start Simulation");
        panel2.add(startSimulationButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Time:");
        panel2.add(label8, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textTime = new JLabel();
        Font textTimeFont = this.$$$getFont$$$("Verdana", Font.BOLD, -1, textTime.getFont());
        if (textTimeFont != null) textTime.setFont(textTimeFont);
        textTime.setOpaque(true);
        textTime.setText("1");
        panel2.add(textTime, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 5, false));
        resetButton = new JButton();
        resetButton.setText("Reset Simulation");
        resetButton.setVisible(false);
        panel2.add(resetButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalTasksBar = new JProgressBar();
        totalTasksBar.setBackground(new Color(-16741596));
        totalTasksBar.setBorderPainted(false);
        totalTasksBar.setForeground(new Color(-16741596));
        totalTasksBar.setIndeterminate(true);
        totalTasksBar.setInheritsPopupMenu(true);
        totalTasksBar.setName("TotalTasks");
        totalTasksBar.setString("Tasks Finished");
        totalTasksBar.setStringPainted(true);
        totalTasksBar.setValue(50);
        panel2.add(totalTasksBar, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelScroll = new JScrollPane();
        panel1.add(panelScroll, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelQ = new JPanel();
        panelQ.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelScroll.setViewportView(panelQ);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }


}
