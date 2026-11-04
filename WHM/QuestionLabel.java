package WHM;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 * When object of this class made, a question label is made with the required
 * aesthetics.
 */
public class QuestionLabel {

    JLabel questionLabel;
    Timer timer;

    /**
     * Constructor for when a Question Label object made.
     */

    public QuestionLabel(int a, int b, int x, int y) throws IOException, FontFormatException {
        FontLoader grenze = new FontLoader(20);

        questionLabel = new JLabel("QUESTION" + a + " - " + b);
        questionLabel.setForeground(new Color(238, 231, 229));
        questionLabel.setFont(grenze.getFont());
        questionLabel.setOpaque(true);
        questionLabel.setBackground(new Color(45, 27, 20));
        questionLabel.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 3, true));
        questionLabel.setBounds(x, y, 220, 44);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
    }

    /**
     * Animates the question label by changing its background color periodically.
     */
    public void animate() {
        timer = new Timer(500, e -> {
            Color current = questionLabel.getBackground();
            if (current.equals(new Color(211, 182, 114))) {
                questionLabel.setBackground(new Color(153, 123, 79));
            } else {
                questionLabel.setBackground(new Color(211, 182, 114));
            }
        });
        timer.start();
    }

    /**
     * Stops the animation of the question label.
     */
    public void stopAnimation() {
        timer.stop();
        questionLabel.setBackground(new Color(45, 27, 20));
    }

    public JLabel getLabel() {
        return questionLabel;
    }

    public void setColor(int x, int y, int z) {
        questionLabel.setBackground(new Color(x, y, z));
    }
}
