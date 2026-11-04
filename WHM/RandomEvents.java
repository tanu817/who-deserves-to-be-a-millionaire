package WHM;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Manages random events during gameplay that affect the timer.
 * Implements two events: adding and subtracting time from the countdown timer.
 * Each event occurs only once per game session.
 */
public class RandomEvents {
    private Timer eventTimer;
    private Random random;
    private GUI gui;
    private boolean isActive;
    int numberOfOcc;

    /**
     * Constructs a RandomEvents manager.
     * 
     * @param gui The main GUI instance to access game components
     */
    public RandomEvents(GUI gui) {
        this.gui = gui;
        this.random = new Random();
        this.eventTimer = new Timer();
        this.isActive = false;
        numberOfOcc = 0;
    }

    /**
     * Initiates the random events system.
     * Only starts if events are not already active.
     */
    public void startRandomEvents() {
        if (isActive) {
            return;
        }
        isActive = true;
        scheduleNextEvent();
    }

    /**
     * Schedules the next random event with a random delay between 5-300 seconds.
     * Events continue until stopEvents() is called or all events have occurred.
     */
    private void scheduleNextEvent() {
        if (!isActive) {
            return;
        }

        int delay = 5000 + random.nextInt(180000);

        eventTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isActive) {
                    triggerRandomEvent();
                    scheduleNextEvent();
                }
            }
        }, delay);
    }

    /**
     * Triggers a random event based on the number of previous occurrences.
     * First occurrence: adds 10 seconds to timer
     * Second occurrence: subtracts 10 seconds from timer
     * After both events occur, stops the event system
     */
    private void triggerRandomEvent() {
        UIManager.put("OptionPane.background", new Color(153, 128, 79)); // light blue
        UIManager.put("Panel.background", new Color(153, 128, 79)); // inner panel

        UIManager.put("OptionPane.messageForeground", new Color(238, 231, 229)); // off-white
        SwingUtilities.invokeLater(() -> {
            if (gui.getCountdownTimer() != null && numberOfOcc == 0) {
                gui.getCountdownTimer().addSeconds(10);
                numberOfOcc += 1;
                JOptionPane.showMessageDialog(gui.questionFrame,
                        "Seems the producers like your style — heres 10 extra seconds!",
                        "Suprise!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (gui.getCountdownTimer() != null && numberOfOcc == 1) {
                gui.getCountdownTimer().minusSeconds(10);
                numberOfOcc += 1;
                JOptionPane.showMessageDialog(gui.questionFrame,
                        "The intern accidentally pressed fast-forward… minus 10s!",
                        "Oh no!",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                stopEvents();
            }
        });
    }

    /**
     * Stops all random events and cancels any pending events.
     * Creates a new Timer instance for future use.
     */
    public void stopEvents() {
        isActive = false;
        if (eventTimer != null) {
            eventTimer.cancel();
            eventTimer = new Timer();
        }
    }

    /**
     * Resets the random events system to initial state.
     * Stops all events and clears the active flag.
     */
    public void reset() {
        stopEvents();
        isActive = false;
    }
}
