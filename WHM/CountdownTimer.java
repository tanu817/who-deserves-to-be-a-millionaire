package WHM;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Implements a countdown timer for the game's question screen.
 * Provides visual display of remaining time and handles timeout events.
 * Features include start/stop control, time adjustments, and timeout callbacks.
 */
public class CountdownTimer {
    public JLabel timerLabel = new JLabel("", SwingConstants.CENTER);
    public Timer timer;
    public int second;
    private Runnable onTimeout;

    /**
     * Constructs a new CountdownTimer starting at 60 seconds.
     * Initializes the timer display with styling and creates the countdown timer.
     */
    public CountdownTimer() {
        second = 60;
        timerLabel.setOpaque(true);
        timerLabel.setBackground(new Color(68, 41, 31));
        timerLabel.setForeground(new Color(238, 231, 229));
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        updateLabel();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
    }

    /**
     * Updates the timer state each second.
     * Decrements the counter and handles timeout when zero is reached.
     */
    private void tick() {
        second--;
        if (second <= 0) {
            stop();
            updateLabel();
            if (onTimeout != null) {
                // run on EDT just in case
                SwingUtilities.invokeLater(onTimeout);
            }
        } else {
            updateLabel();
        }
    }

    /**
     * Updates the visual display of the timer.
     * Shows remaining seconds in the format "XXs".
     */
    private void updateLabel() {
        timerLabel.setText(second + "s");
    }

    /**
     * Starts the countdown if timer exists and isn't running.
     */
    public void start() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Stops the countdown if timer exists and is running.
     */
    public void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Resets the timer to specified number of seconds.
     * Stops current countdown before reset.
     * 
     * @param seconds New time value in seconds
     */
    public void reset(int seconds) {
        stop();
        this.second = seconds;
        updateLabel();
    }

    /**
     * Sets callback function to execute when timer reaches zero.
     * 
     * @param r Runnable to execute on timeout
     */
    public void setTimeoutCallback(Runnable r) {
        this.onTimeout = r;
    }

    /**
     * Sets the timer to a specific number of seconds.
     * 
     * @param seconds New time value in seconds
     */
    public void setSeconds(int seconds) {
        this.second = seconds;
        updateLabel();
    }

    /**
     * Adds seconds to current time, capped at 60 seconds maximum.
     * Resets and restarts timer after addition.
     * 
     * @param secondsToAdd Number of seconds to add
     */
    public void addSeconds(int secondsToAdd) {
        second += secondsToAdd;
        if (second > 60) {
            second = 60;
        }
        reset(second);
        start();
        // int minutes = second / 60;
        // int seconds = second % 60;
        // timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Subtracts seconds from current time, minimum 0 seconds.
     * Resets and restarts timer after subtraction.
     * 
     * @param secondsToMinus Number of seconds to subtract
     */
    public void minusSeconds(int secondsToMinus) {
        second -= secondsToMinus;
        if (second < 0) {
            second = 0;
        }
        reset(second);
        start();
        // int minutes = second / 60;
        // int seconds = second % 60;
        // timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
