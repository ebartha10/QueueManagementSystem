package ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class QueueGUI extends JPanel {

    private JProgressBar progressBar;
    private JLabel label;

    public QueueGUI(int queueNo) {
        this.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 5, 0, 5), -1, -1));
        progressBar = new JProgressBar();
        progressBar.setString("Waiting");
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setBackground(Color.GREEN);
        this.add(progressBar, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW , com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label = new JLabel();
        label.setText("Queue #" + queueNo);
        this.add(label, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
}
