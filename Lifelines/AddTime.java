package Lifelines;

import WHM.CountdownTimer;

/**
 * Implements the Add Time lifeline which resets the countdown timer.
 * When used, resets the timer back to 60 seconds and starts it.
 * This lifeline can only be used once per game.
 */
public class AddTime implements Lifelines {
    
    CountdownTimer timer;
    Boolean used;

    /**
     * Constructs a new Add Time lifeline.
     * @param t Reference to the game's countdown timer
     */
    public AddTime(CountdownTimer t) {
        timer = t;
        used = false;
    }

    /**
     * Executes the Add Time lifeline action.
     * Resets the timer to 60 seconds and starts it.
     */
    @Override
    public void use() {
        timer.reset(60);
        timer.start();
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

