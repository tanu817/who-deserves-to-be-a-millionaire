package Lifelines;

import WHM.GUI;

/**
 * Implements the Switch lifeline which replaces the current question with an alternate one.
 * This lifeline can only be used once per game.
 */
public class LifelineSwitch implements Lifelines {

    private Boolean used;
    GUI gui;

    /**
     * Constructs a new Switch lifeline.
     * @param a Reference to the main GUI instance
     */
    public LifelineSwitch(GUI a) {
        used = false;
        gui = a;
    }

    /**
     * Executes the Switch lifeline action.
     * Replaces current question with alternate question,
     * updates display, and restarts timer.
     */
    @Override
    public void use() {
        gui.getQuestionManager().useSwitchQuestion();
        gui.loadQuestion(gui.getQuestionManager().getCurrentLevel());
        gui.getCountdownTimer().start();
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
