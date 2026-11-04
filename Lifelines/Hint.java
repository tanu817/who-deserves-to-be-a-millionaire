package Lifelines;

import WHM.GUI;
import java.awt.*;
import javax.swing.*;

/**
 * Implements the Hint lifeline which displays a helpful tip for the current question.
 * Shows a popup window containing the hint text in a styled format.
 * This lifeline can only be used once per game.
 */
public class Hint implements Lifelines {

    private Boolean used;
    JFrame hintFrame;
    GUI gui;

    /**
     * Constructs a new Hint lifeline.
     * @param a Reference to the main GUI instance
     */
    public Hint(GUI a) {
        used = false;
        gui = a;
    }

    /**
     * Executes the Hint lifeline action.
     * Creates and displays a popup window containing the hint text
     * for the current question with styled formatting.
     */
    @Override
    public void use() {
        hintFrame = new JFrame("Your Hint");
        hintFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hintFrame.setSize(400, 200);
        String hintText = gui.getQuestionManager().getHint();
        JLabel hintLabel = new JLabel("<html><body style='width: 250px'>" 
            + hintText + "</body></html>");
        hintLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        hintLabel.setForeground(new Color(238, 231, 229));
        hintLabel.setOpaque(true);
        hintLabel.setBackground(new Color(45, 27, 20));
        hintLabel.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 3, true));
        hintLabel.setBounds(394, 413, 193, 56);
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hintLabel.setVerticalAlignment(SwingConstants.CENTER);
        hintFrame.add(hintLabel);
        hintFrame.setVisible(true);
        used = true;
    }

    /**
     * Checks if this lifeline has been used.
     * @return true if already used, false if still available
     */
    @Override
    public boolean isUsed() {
        return used;
    }
}
