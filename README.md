# Java Hangman

A terminal hangman game written in Java. The program picks a random movie
title from a list, and the player guesses letters until they solve the
phrase or use up their wrong guesses.

## Features

- Random phrase selection from a file of movie titles
- Displays the topic and a masked phrase, with letters revealed as they're guessed
- Tracks guessed letters and rejects invalid or repeated guesses
- Limits the player to 5 wrong guesses per round
- Offers to play again after each round

## How to Run

Requires Java 8 or newer.

```bash
javac *.java
java Hangman
```

To use a different phrase list, pass the file name as a command-line argument:

```bash
java Hangman MyPhrases.txt
```

The first line of the file is the topic, and each following line is one phrase.

## Example
I am thinking of a Movie ...

The current phrase is ***** ***** ***
...


## Project Structure

- `Hangman.java`: game loop, guess handling, and display
- `PhraseBank.java`: loads and shuffles phrases from a file
- `HangmanMovies.txt`: default list of movie titles

## Credits

The `PhraseBank` class and the overall project structure were provided by
the CS312 course staff. I implemented the game logic in `Hangman.java`,
including `playRound`, `getGuess`, `revealLetter', etc.
