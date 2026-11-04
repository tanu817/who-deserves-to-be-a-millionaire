package Questions;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Manages the game's question database and progression.
 * Handles loading questions from CSV, selecting random questions for each level,
 * and tracking game progression.
 */

public class QuestionManager {

    ArrayList<String[]> allQuestions = new ArrayList<String[]>();
    ArrayList<String[]> selectedQuestions = new ArrayList<String[]>();

    int currentLevel = 1;

    int[] moneyLevels = {
        100, 200, 300, 500, 1000, 2000, 4000, 8000, 
        16000, 32000, 64000, 125000, 250000, 500000, 1000000
    };

    /**
    * Reads questions from the CSV file into memory.
    * Expected CSV format: question,level,correct,wrong1,wrong2,wrong3,hint
    */

    public void readQuestions() {
        File file = new File("Questions/Questions.csv");
        try {
            Scanner scanner = new Scanner(file, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(",");
                allQuestions.add(parts);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("File not found.");
        }
    }

    /**
     * Selects random questions for each game level.
     * Creates a set of 15 questions plus one extra for the switch lifeline.
     */    
    public void setSelector() {
        Random rand = new Random();

        for (int level = 1; level <= 15; level++) {
            ArrayList<String[]> levelQuestions = new ArrayList<>();

            for (String[] q: allQuestions) {
                int questionLevel = Integer.parseInt(q[1].trim());
                if (questionLevel == level) {
                    levelQuestions.add(q);
                }
            }

            if (levelQuestions.size() > 0) {
                int randomIndex = rand.nextInt(levelQuestions.size());
                String[] selectedQuestion = levelQuestions.get(randomIndex);
                selectedQuestions.add(selectedQuestion);
            }
        }
    
        if (allQuestions.size() > 0) {
            int randomIndex = rand.nextInt(allQuestions.size());
            String[] switchQuestion = allQuestions.get(randomIndex);
            selectedQuestions.add(switchQuestion);  
        }
    }   

    /**
     * Gets the index of the switch question in selectedQuestions.
     * @return index of switch question
     */
    public int getSwitchQuestionIndex() {
        return selectedQuestions.size();
    }

    /**
     * Replaces current question with switch question when lifeline is used.
     */
    public void useSwitchQuestion() {
        String[] switchQ = selectedQuestions.get(getSwitchQuestionIndex() - 1);
        selectedQuestions.set(currentLevel - 1, switchQ);
    }

    /**
     * Gets wrong options for current question.
     * @return array of 3 wrong answer strings
     */
    public String[] getWO() {
        String[] questionParts = getLevel();
        if (questionParts == null) {
            return null;
        }

        String[] wrongAnswers = new String[3];
        wrongAnswers[0] = questionParts[3];
        wrongAnswers[1] = questionParts[4];
        wrongAnswers[2] = questionParts[5];

        return wrongAnswers;
    }

    /**
     * Gets current level's question data.
     * @return array containing question parts or null if invalid level
     */
    public String[] getLevel() {
        if (currentLevel >= 1 && currentLevel <= 15) {
            return selectedQuestions.get(currentLevel - 1);
        } else {
            return null; // Invalid level
        }
    }

    /**
     * Gets question data for specific level.
     * @param level Level number (1-15)
     * @return array containing question parts
     */
    public String[] getLevel(int level) {
        return selectedQuestions.get(level - 1);
    }

    /**
     * Gets current game level.
     * @return Current level number (1-15)
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    /**
     * Gets money amount for current question.
     * @return Prize amount for current level
     */

    public int getMoney() {
        if (currentLevel >= 1 && currentLevel <= 15) {
            return moneyLevels[currentLevel - 1];
        } else if (currentLevel > 15) {
            // player finished: return the top prize
            return moneyLevels[moneyLevels.length - 1];
        } else {
            return 0; // Invalid level
        }
    }

    /**
     * Gets money won at current level.
     * @return Amount won so far
     */
    public int getMoneyWon() {
        if (currentLevel > 1 && currentLevel <= 15) {
            return moneyLevels[currentLevel - 2];
        } else if (currentLevel > 15) {
            // finished: they won the top prize
            return moneyLevels[moneyLevels.length - 1];
        } else {
            return 0;
        }
    }

    /**
     * Gets hint for current question.
     * @return Hint string or null if no hint available
     */
    public String getHint() {
        String[] questionParts = getLevel();
        if (questionParts != null) {
            return questionParts[6];
        } else {
            return null;
        }
    }

    /**
     * Advances to next level when correct answer given.
     */
    public void nextLevel() {
        // advance to the next numeric level. If this moves currentLevel to 16,
        // getLevel() will return null and GUI will display the win screen.
        currentLevel++;
    }

    /**
     * Gets array of money values for each level.
     * @return Array of prize amounts
     */
    public int[] getMoneyLevels() {
        return moneyLevels;
    }
}