package WHM;

import java.awt.*;
import javax.swing.*;

/**
 * Makes it less time consuming to create a lifeline button in the GUI class. 
 */
public class LifelineButton extends JButton {

    private Image backgroundImage;

    /**
     * Constructor for object. Adds background to the button. 
     */
    public LifelineButton(String imagePath) {
        super("");
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
