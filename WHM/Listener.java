package WHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * Handles all user interaction events for the game.
 * Implements both MouseListener and ActionListener to process
 * button clicks, lifeline usage, and game progression events.
 */
public class Listener implements MouseListener, ActionListener {

    GUI b;

    /**
     * Constructs a new Listener with reference to main GUI.
     * @param a The main GUI instance to control
     */
    public Listener(GUI a) {
        b = a;
    }

    /**
     * Handles button click events for game start and answer selection.
     * Routes events to appropriate handler methods based on source.
     * @param e The ActionEvent containing source button information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b.startButton) {
            handleStart();
        } else {
            for (JButton optionButton : b.optionButtons) {
                if (e.getSource() == optionButton) {
                    handleOption(optionButton);
                    return; 
                }
            }
        }
    }

    /**
     * Handles game start sequence.
     * Shows question screen, starts timer and random events,
     * initializes sounds and game state.
     */
    private void handleStart() {
        if (b.startFrame != null) {
            b.startFrame.setVisible(false);
        }

        if (b.questionFrame != null) {
            b.loadQuestion(b.questionManager.getCurrentLevel());
            if (b.countdownTimer != null) {
                b.countdownTimer.reset(60);
                b.countdownTimer.start();
            }

            SoundManager.playOnce("/StartSound.wav");
            SoundManager.loop("/TickingSound.wav");

            b.questionFrame.setVisible(true);
            if (b.getRandomEvents() != null) {
                b.getRandomEvents().startRandomEvents();
            }
        }
    }

    /**
     * Handles answer selection and game progression.
     * Validates answer, updates score, manages screen transitions
     * and sound effects based on correct/incorrect answer.
     * @param src The button containing selected answer
     */
    private void handleOption(JButton src) {
        if (b.countdownTimer != null) {
            b.countdownTimer.stop();
        }
        SoundManager.stop("/TickingSound.wav");

        String selected = src.getText();
        String[] q = b.questionManager.getLevel();
        if (q == null) {
            return;
        }
        String correct = q.length > 2 ? q[2] : "";

        if (selected.equals(correct)) {
            b.questionManager.nextLevel();
            String[] next = b.questionManager.getLevel();
            if (next == null) {
                if (b.winAmountLabel != null) {
                    b.winAmountLabel.setText("$" + b.questionManager.getMoneyWon());
                }
                SoundManager.stopAll();
                SoundManager.playOnce("/WinMusic.wav");
                if (b.questionFrame != null) {
                    b.questionFrame.setVisible(false);
                }
                if (b.winFrame != null) {
                    b.winFrame.setVisible(true);
                }
            } else {
                b.loadQuestion(b.questionManager.getCurrentLevel());
                if (b.countdownTimer != null) {
                    b.countdownTimer.reset(60);
                    b.countdownTimer.start();
                }
                SoundManager.loop("/TickingSound.wav");
            }
        } else {
            if (b.countdownTimer != null) {
                b.countdownTimer.stop(); 
            }
            SoundManager.stopAll();
            SoundManager.playOnce("/LoseMusic.wav");
            if (b.questionFrame != null) {
                b.questionFrame.setVisible(false);
            }
            if (b.lostFrame != null) {
                b.lostFrame.setVisible(true);
            }
        }
    }

    /**
     * Handles player exit from game.
     * Stops timer and sounds, calculates final score,
     * transitions to win screen with earnings display.
     */
    private void handleExit() {
        if (b.countdownTimer != null) {
            b.countdownTimer.stop();
        }
        SoundManager.stop("/TickingSound.wav");
        SoundManager.stopAll(); 
        SoundManager.playOnce("/WinMusic.wav");
        if (b.winAmountLabel != null && b.questionManager != null) {
            b.winAmountLabel.setText("$" + b.questionManager.getMoneyWon());
        }
        
        if (b.questionFrame != null) {
            b.questionFrame.setVisible(false);
        }
        if (b.winFrame != null) {
            b.winFrame.setVisible(true);
        }

    }

    /**
     * Handles mouse click events for lifelines and UI controls.
     * Processes roadmap display, lifeline usage, and game exit.
     * Updates UI visibility based on lifeline usage.
     * @param e The MouseEvent containing click information
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == b.roadmapButton) {
            b.getRoadMapLabels()[b.getQuestionManager().getCurrentLevel() - 1].animate();
            b.roadmapFrame.setVisible(true);
        } else if (e.getSource() == b.closeButton) {
            b.getRoadMapLabels()[b.getQuestionManager().getCurrentLevel() - 1].stopAnimation();
            b.roadmapFrame.setVisible(false);
        } else if (e.getSource() == b.exitButton) {
            handleExit();
        } else if (e.getSource() == b.timeButton) {
            b.t.use();
            b.timeButton.setVisible(!b.t.isUsed());
        } else if (e.getSource() == b.fiftyFiftyButton) {
            b.f.use();
            b.fiftyFiftyButton.setVisible(!b.f.isUsed());
        } else if (e.getSource() == b.hintButton) {
            b.hint.use();
            b.hintButton.setVisible(!b.hint.isUsed());
        } else if (e.getSource() == b.switchButton) {
            b.switchLifeline.use();
            b.switchButton.setVisible(!b.switchLifeline.isUsed());
        }
    }

    // Required MouseListener interface methods
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}