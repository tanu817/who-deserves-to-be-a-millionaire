# Who Deserves to Be a Millionaire

A Java Swing quiz game inspired by the TV show *Who Wants to Be a Millionaire*. You answer multiple-choice questions against a 60-second countdown, climbing a money ladder up to $1,000,000. A wrong answer or running out of time ends the game, and you can walk away at any point to keep what you've won.

This was a uni project, built by **Tanusri Madhalam** and **Bhavini Kopparam Ajay** for a course at Eindhoven University of Technology (TU/e) in 2025.

## Features

- **Money ladder:** questions get harder as you go, and a roadmap panel shows your progress.
- **Countdown timer:** 60 seconds per question, with a ticking sound while it runs.
- **Four lifelines**, each usable once:
  - 50/50 removes two wrong answers
  - Add time gives you extra seconds
  - Hint shows a clue in a new window
  - Switch swaps the question for a different one at the same level
- **Random event cards:** unexpected boosts or setbacks that pop up during the game.
- **Exit and keep your winnings:** leave at any time and take the amount you've earned.
- **Win and lose screens** showing the amount won.
- **Question bank:** around 150 questions in `Questions/Questions.csv`, each with a hint.

## Running it

You need a JDK. From the repository root:

```bash
javac -d out GameController.java WHM/*.java Lifelines/*.java Questions/*.java
java -cp out:. GameController
```

On Windows, use `out;.` instead of `out:.` in the second command.

Run it from the repository root, because the game reads `Questions/Questions.csv` and the font and icons from the working directory. A start screen appears, and clicking Start loads the first question.

## Images and sounds are not included

The original game used the show's logo on its background images and sound effects ripped from the TV show. Those belong to their owners, so they aren't in this repository.

- **Without them:** the game still runs, with plain dark backgrounds and no sound.
- **To add your own:** put the files in the repository root, next to `GameController.java`.
  - `1.png` to `5.png` are 1920x1080 backgrounds for the start, question, lose, win, and roadmap screens
  - `StartSound.wav`, `TickingSound.wav`, `WinMusic.wav`, and `LoseMusic.wav` are the sound effects

## How it's built

- **`GameController`:** the entry point.
- **`WHM`:** the GUI, the listener that handles clicks, the countdown timer, sound manager, font loader, random events, and custom widgets.
- **`Lifelines`:** one class per lifeline, behind a common interface so the game treats them all the same way.
- **`Questions`:** `QuestionManager` reads the CSV and hands out questions by level without repeating them.

Object-oriented ideas used in the project:

- **Separation of concerns:** `GUI` builds the screens and `Listener` handles what happens when you click.
- **Composition:** screens are assembled from many small, independent objects.
- **Inheritance:** `LifelineButton` and `BackgroundPanel` extend Swing's `JButton` and `JPanel`.
- **Polymorphism:** `Listener` implements `ActionListener` and `MouseListener`, and the lifelines share an interface.
- **Encapsulation:** classes like `CountdownTimer` and `FontLoader` keep their internals private.

## Credits

- **Authors:** Tanusri Madhalam and Bhavini Kopparam Ajay.
- **Font:** Grenze SemiBold, an open-source font (SIL Open Font License).
- **Questions:** based on the question bank at [Quiz Questions UK](https://quiz-questions.uk/who-wants-to-be-a-millionaire/).
- **Learning resources:**
  - [Refactoring.Guru](https://refactoring.guru/design-patterns) for design patterns (the lifeline structure)
  - [The Java Tutorials](https://docs.oracle.com/javase/tutorial/uiswing/events/eventsandcomponents.html) from Oracle for Swing
  - [RyiShow's countdown timer tutorial](https://www.youtube.com/watch?v=zWw72j-EbqI) on YouTube
  - [CodeSpeedy](https://www.codespeedy.com/how-to-add-audio-on-jswing-in-java/) for playing audio in Swing
  - [GeeksforGeeks](https://www.geeksforgeeks.org/java/java/) for Java tutorials
