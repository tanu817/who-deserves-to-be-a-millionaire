package Lifelines;

import WHM.GUI;
import java.util.Random;

/**
 * Implements the 50:50 lifeline which removes two wrong answers from the options.
 * Randomly selects and hides two incorrect answers, leaving the correct answer
 * and one wrong answer visible. Can only be used once per game.
 */
public class Fifty implements Lifelines {

    private Boolean used;
    GUI gui;
    Random random;

    /**
     * Constructs a new Fifty lifeline without GUI reference.
     * Initializes random number generator.
     */
    public Fifty() {
        used = false;
        random = new Random();
    }

    /**
     * Constructs a new Fifty lifeline with GUI reference.
     *
     * @param a Reference to the main GUI instance
     */
    public Fifty(GUI a) {
        this();
        gui = a;
    }

    /**
     * Executes the 50:50 lifeline action.
     * Randomly selects and removes two wrong answers from the display,
     * leaving the correct answer and one random wrong answer visible.
     */
    @Override
    public void use() {
        String[] wrongAns = gui.getQuestionManager().getWO();
        int w1 = 0;
        int w2 = 0;
        do {
            w1 = random.nextInt(3);
            w2 = random.nextInt(3);
        } while (w1 == w2);
        gui.clearOptions(wrongAns[w1]);
        gui.clearOptions(wrongAns[w2]);
        used = true;
    }

    /**
     * Checks if this lifeline has been used.
     *
     * @return true if already used, false if still available
     */
    @Override
    public boolean isUsed() {
        return used;
    }
}
