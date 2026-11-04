package WHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Sets the background for a panel. 
 **/
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    /**
     * Contructor for the class. So when an object of BackgroundPanel created the location must 
     * be given. 
     * @param imagePath
     **/
    public BackgroundPanel(String imagePath) {

        java.net.URL imageURL = getClass().getResource(imagePath);
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            backgroundImage = icon.getImage().getScaledInstance(1000, 520, Image.SCALE_SMOOTH);
        } else {
            // Background images are not included in the repository, so fall back to a plain panel.
            setBackground(new Color(0x2B1A12));
        }
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }
}
