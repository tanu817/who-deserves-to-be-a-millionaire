package WHM;

import Lifelines.AddTime;
import Lifelines.Fifty;
import Lifelines.Hint;
import Lifelines.LifelineSwitch;
import Questions.QuestionManager;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * Main GUI class for the "Who Deserves to Be a Millionaire" application.
 * Responsible for creating and managing all screens (start, question, roadmap,
 * win and lost), lifeline buttons and interaction with QuestionManager and
 * CountdownTimer.
 */
public class GUI {

    Color offWhite = new Color(238, 231, 229);
    JLabel questionLabel;
    JButton[] optionButtons = new JButton[4];
    JLabel amountLabel;
    JLabel winAmountLabel;
    QuestionManager questionManager;
    QuestionLabel[] roadMapLabels;

    CountdownTimer countdownTimer;

    JButton startButton;
    JButton roadmapButton;
    JButton exitButton;
    JButton closeButton;
    JButton optionButton;
    LifelineButton switchButton;
    LifelineButton timeButton;
    LifelineButton fiftyFiftyButton;
    LifelineButton hintButton;
    public JFrame startFrame;
    JFrame roadmapFrame;
    JFrame winFrame;
    JFrame lostFrame;
    JFrame questionFrame;
    Listener listener;
    private RandomEvents randomEvents;
    

    AddTime t;
    Fifty f;
    Hint hint;
    LifelineSwitch switchLifeline;

    /**
     * Constructs a new GUI instance and initializes core components such as the
     * Listener, CountdownTimer, lifeline helpers and roadmap labels array.
     */
    public GUI() {
        listener = new Listener(this);
        countdownTimer = new CountdownTimer();
        t = new AddTime(countdownTimer);
        f = new Fifty(this);
        hint = new Hint(this);
        switchLifeline = new LifelineSwitch(this);
        roadMapLabels = new QuestionLabel[15];
    }

    /**
     * Returns the QuestionManager used by this GUI.
     *
     * @return the QuestionManager instance or null if not initialized
     */
    public QuestionManager getQuestionManager() {
        return questionManager;
    }

    /**
     * Returns the CountdownTimer used by this GUI.
     *
     * @return the CountdownTimer instance
     */
    public CountdownTimer getCountdownTimer() {
        return countdownTimer;
    }

    /**
     * Returns the RandomEvents instance used by this GUI.
     *
     * @return the RandomEvents instance or null if not initialized
     */
    public RandomEvents getRandomEvents() {
        return randomEvents;
    }

    /**
     * Initializes all screens and reads questions from storage. This method must
     * be called before showing the frames to ensure question data and UI are
     * prepared.
     *
     * @throws IOException if question reading or resource loading fails
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public void initialiseScreens() throws IOException, FontFormatException {
        questionManager = new QuestionManager();
        questionManager.readQuestions();
        questionManager.setSelector();

        startScreen();
        questionScreen();
        roadmapScreen();
        winScreen();
        lostScreen();
        randomEvents = new RandomEvents(this);
    }

    /**
     * Builds and configures the start screen frame with the START button.
     *
     * @throws IOException if resources cannot be loaded
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public void startScreen() throws IOException, FontFormatException {
        startFrame = new JFrame("Who Deserves to be a Millionaire");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(1000, 550);
        startFrame.setLocationRelativeTo(null);
        startFrame.setLayout(new BorderLayout());

        BackgroundPanel bp = new BackgroundPanel("/1.png");
        bp.setBounds(0, 0, 1000, 550);
        startFrame.add(bp);

        FontLoader grenze = new FontLoader(25);

        startButton = new JButton("START");
        startButton.setFont(grenze.getFont());
        startButton.setForeground(offWhite);
        startButton.setBackground(new Color(45, 27, 20));
        startButton.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 2, true));
        startButton.setBounds(394, 413, 193, 56);
        startButton.setFocusPainted(false);
        startButton.setFocusable(false);
        // Listener is now in Listener.java
        startButton.addActionListener(listener);
        bp.add(startButton);
        startFrame.setVisible(false);

    }

    /**
     * Builds and configures the win screen frame which shows the winning amount.
     *
     * @throws IOException if resources cannot be loaded
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public void winScreen() throws IOException, FontFormatException {
        winFrame = new JFrame("You Won!");
        winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winFrame.setSize(1000, 550);
        winFrame.setLocationRelativeTo(null);
        winFrame.setLayout(new BorderLayout());

        BackgroundPanel bp = new BackgroundPanel("/2.png");
        bp.setBounds(0, 0, 1000, 550);
        winFrame.add(bp);

        FontLoader grenze = new FontLoader(25);

        winAmountLabel = new JLabel("$0"); 
        winAmountLabel.setForeground(offWhite);
        winAmountLabel.setFont(grenze.getFont());
        winAmountLabel.setOpaque(true);
        winAmountLabel.setBackground(new Color(45, 27, 20));
        winAmountLabel.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 3, true));
        winAmountLabel.setBounds(394, 413, 193, 56);
        winAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winAmountLabel.setVerticalAlignment(SwingConstants.CENTER);
        bp.add(winAmountLabel);

        winFrame.setVisible(false);
    }

    /**
     * Builds and configures the lost screen frame that is shown when the player
     * loses a game (wrong answer or timer expiry).
     *
     * @throws IOException if resources cannot be loaded
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public void lostScreen() throws IOException, FontFormatException {
        lostFrame = new JFrame("You Lost!");
        lostFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lostFrame.setSize(1000, 550);
        lostFrame.setLocationRelativeTo(null);
        lostFrame.setLayout(new BorderLayout());

        BackgroundPanel bp = new BackgroundPanel("/3.png");
        bp.setBounds(0, 0, 1000, 550);
        lostFrame.add(bp);

        FontLoader grenze = new FontLoader(20);

        JLabel blntLabel = new JLabel("Better luck next time!");
        blntLabel.setForeground(offWhite);
        blntLabel.setFont(grenze.getFont());
        blntLabel.setOpaque(true);
        blntLabel.setBackground(new Color(45, 27, 20));
        blntLabel.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 3, true));
        blntLabel.setBounds(397, 408, 193, 56);
        blntLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blntLabel.setVerticalAlignment(SwingConstants.CENTER);
        bp.add(blntLabel);

        lostFrame.setVisible(false);
    }

    /**
     * Loads and displays the question for the specified level. The method reads
     * the question text, constructs shuffled options (including the correct answer)
     * and updates relevant UI elements (labels, option buttons and timer).
     *
     * @param level the question level to load (1-based index expected by QuestionManager)
     */
    public void loadQuestion(int level) {
        if (questionManager == null) {
            return;
        }
        String[] q = questionManager.getLevel(level);
        if (q == null) {
            return;
        }

        String questionText = q.length > 0 ? q[0] : "No question";
        String correct = q.length > 2 ? q[2] : "";

        List<String> opts = new ArrayList<>();
        opts.add(correct);
        for (int i = 3; i < q.length && opts.size() < 4; i++) {
            if (q[i] != null && !q[i].isEmpty()) {
                opts.add(q[i]);
            }
        }
        while (opts.size() < 4) {
            opts.add("");
        }
        Collections.shuffle(opts, new Random());

        questionLabel.setText("<html><div style='text-align:center;'>" 
            + questionText + "</div></html>");
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(opts.get(i));
        }
        amountLabel.setText(String.valueOf(questionManager.getMoney()));

        if (countdownTimer != null) {
            countdownTimer.reset(60);
        }
    }

    /**
     * Builds and configures the main question screen including lifelines,
     * question label, option buttons and the countdown timer. Also wires up the
     * timeout callback to handle timer expiry.
     *
     * @throws IOException if resources cannot be loaded
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public void questionScreen() throws IOException, FontFormatException {
        questionFrame = new JFrame("Here we go!");
        questionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        questionFrame.setSize(1000, 550);
        questionFrame.setLocationRelativeTo(null);
        questionFrame.setLayout(new BorderLayout());

        BackgroundPanel bp = new BackgroundPanel("/5.png");
        bp.setBounds(0, 0, 1000, 550);
        questionFrame.getContentPane().add(bp);

        FontLoader grenze = new FontLoader(20);

        roadmapButton = new JButton("Road Map");
        roadmapButton.setFont(grenze.getFont());
        roadmapButton.setForeground(offWhite);
        roadmapButton.setBackground(new Color(45, 27, 20));
        roadmapButton.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 2, true));
        roadmapButton.setBounds(20, 14, 120, 44);
        roadmapButton.setFocusPainted(false);
        roadmapButton.setFocusable(false);
        bp.add(roadmapButton);
        roadmapButton.addMouseListener(listener);

        exitButton = new JButton("Exit");
        exitButton.setFont(grenze.getFont());
        exitButton.setForeground(offWhite);
        exitButton.setBackground(new Color(45, 27, 20));
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 2, true));
        exitButton.setBounds(153, 14, 120, 44);
        exitButton.setFocusPainted(false);
        exitButton.setFocusable(false);
        exitButton.addMouseListener(listener);
        bp.add(exitButton);

        String currentAmount = "$" + Integer.toString(questionManager.getMoneyWon());

        amountLabel = new JLabel(currentAmount);
        amountLabel.setForeground(offWhite);
        amountLabel.setFont(grenze.getFont());
        amountLabel.setOpaque(true);
        amountLabel.setBackground(new Color(45, 27, 20));
        amountLabel.setBounds(751, 14, 221, 48);
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setVerticalAlignment(SwingConstants.CENTER);
        bp.add(amountLabel);

        switchButton = new LifelineButton("/6.png");
        switchButton.setBounds(347, 14, 53, 44);
        switchButton.addMouseListener(listener);
        bp.add(switchButton);

        timeButton = new LifelineButton("/7.png");
        timeButton.setBounds(433, 14, 53, 44);
        timeButton.addMouseListener(listener);
        bp.add(timeButton);
        timeButton.setVisible(!t.isUsed());

        fiftyFiftyButton = new LifelineButton("/8.png");
        fiftyFiftyButton.setBounds(527, 14, 53, 44);
        fiftyFiftyButton.addMouseListener(listener);
        bp.add(fiftyFiftyButton);

        hintButton = new LifelineButton("/9.png");
        hintButton.setBounds(620, 14, 53, 44);
        hintButton.addMouseListener(listener);
        bp.add(hintButton);

        FontLoader h = new FontLoader(20);

        questionLabel = new JLabel("Question", SwingConstants.CENTER);
        questionLabel.setForeground(offWhite);
        questionLabel.setFont(h.getFont());
        questionLabel.setOpaque(true);
        questionLabel.setBackground(new Color(68, 41, 31));
        questionLabel.setBounds(125, 215, 750, 48);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
        bp.add(questionLabel);

        int x = 170;
        int y = 318;

        FontLoader g = new FontLoader(20);

        for (int i = 0; i < 4; i++) {
            optionButton = new JButton("Option");
            optionButtons[i] = optionButton;
            optionButton.setForeground(offWhite);
            optionButton.setFont(g.getFont());
            optionButton.setOpaque(true);
            optionButton.setBackground(new Color(94, 58, 45));
            optionButton.setBounds(x, y, 221, 48);
            optionButton.setHorizontalAlignment(SwingConstants.LEFT);
            optionButton.setVerticalAlignment(SwingConstants.CENTER);
            optionButton.setBorder(BorderFactory.createLineBorder(new Color(94, 58, 45), 2, true));
            optionButton.setFocusPainted(false);
            optionButton.setFocusable(false);

            optionButton.addActionListener(listener);
            bp.add(optionButton);
            y += 101;
            if (i == 1) {
                x = x + 460;
                y = 318;
            }
        }

        countdownTimer.timerLabel.setBounds(427, 113, 133, 63);
        bp.add(countdownTimer.timerLabel);
        countdownTimer.setTimeoutCallback(() -> {
            SwingUtilities.invokeLater(() -> {
                if (countdownTimer != null) {
                    countdownTimer.stop();
                }
                SoundManager.stop("/TickingSound.wav");
                SoundManager.playOnce("/LoseMusic.wav");
                questionFrame.setVisible(false);
                lostFrame.setVisible(true);
            });
        });
        loadQuestion(questionManager.getCurrentLevel());

        questionFrame.setVisible(false);
    }

    /**
     * Clears (empties) the first option button that matches the given text.
     * Typically used by lifelines to remove wrong options.
     *
     * @param wo the option text to clear
     */
    public void clearOptions(String wo) {
        String s;
        for (int j = 0; j < optionButtons.length; j++) {
            s = optionButtons[j].getText();

            if (s.equals(wo)) {
                optionButtons[j].setText("");
                break;
            }
        }

    }

    /**
     * Builds and configures the roadmap window which shows the current level and
     * money levels for all questions.
     *
     * @throws IOException if resources cannot be loaded
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public void roadmapScreen() throws IOException, FontFormatException {
        roadmapFrame = new JFrame("Roadmap");
        roadmapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        roadmapFrame.setSize(1000, 550);
        roadmapFrame.setLocationRelativeTo(null);
        roadmapFrame.setLayout(new BorderLayout());

        BackgroundPanel bp = new BackgroundPanel("/4.png");
        bp.setBounds(0, 0, 1000, 550);
        roadmapFrame.add(bp);

        int x = 113;
        int y = 131;
        int[] moneyLevels = questionManager.getMoneyLevels();
        QuestionLabel one;
        for (int i = 0; i < 15; i++) {
            one = new QuestionLabel(i + 1, moneyLevels[i], x, y);
            bp.add(one.getLabel());
            y += 72;

            if (i == 4 || i == 9) {
                one.setColor(113, 112, 110);
                x += 280;
                y = 131;
            } else if (i == 14) {
                one.setColor(121, 93, 57);
            }
            roadMapLabels[i] = one;
        }

        FontLoader grenze = new FontLoader(20);

        closeButton = new JButton("Close");
        closeButton.setFont(grenze.getFont());
        closeButton.setForeground(offWhite);
        closeButton.setBackground(new Color(45, 27, 20));
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(153, 128, 79), 2, true));
        closeButton.setBounds(907, 475, 67, 31);
        closeButton.setFocusPainted(false);
        closeButton.setFocusable(false);
        closeButton.addMouseListener(listener);
        bp.add(closeButton);

        roadmapFrame.setVisible(false);
    }

    /**
     * Returns the array of QuestionLabel instances used by the roadmap screen.
     *
     * @return the roadmap labels array (length 15)
     */
    public QuestionLabel[] getRoadMapLabels() {
        return roadMapLabels;
    }

    /**
     * Entry point of the application. Initializes the GUI and shows the start frame.
     *
     * @param args command line arguments (ignored)
     * @throws IOException if initialization resources cannot be loaded
     * @throws FontFormatException if a custom font cannot be loaded
     */
    public static void main(String[] args) throws IOException, FontFormatException {
        GUI a = new GUI();
        a.initialiseScreens();
        a.startFrame.setVisible(true);
    }

}