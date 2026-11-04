package Lifelines;

/**
 * Interface defining the contract for all game lifelines.
 * Each lifeline provides help to the player during gameplay.
 */

public interface Lifelines {
    /**
     * Executes the lifeline's specific action.
     */
    public void use();

    /**
     * Checks if this lifeline has been used.
     * @return true if already used, false if still available
     */
    public boolean isUsed();
}
