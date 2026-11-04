import WHM.GUI;
import WHM.SoundManager;
import javax.swing.SwingUtilities;

/**
 * Connects all of the methods together. Main method will also be here. 
 **/
public class GameController {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // preload sounds (optional but avoids first-play delay)
                SoundManager.preload("/StartMusic.wav");
                SoundManager.preload("/TickingSound.wav");
                SoundManager.preload("/WinMusic.wav");
                SoundManager.preload("/LoseMusic.wav");

                // to play start music immediately when program runs
                SoundManager.playOnce("/StartMusic.wav");

                GUI a = new GUI();
                a.initialiseScreens();
                a.startFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
