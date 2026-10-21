/**
 * CS312 Assignment 5 - Hangman
 *
 *  Name: Van Trook, vst379
 *
 */
import java.util.Scanner;

public class Hangman {
	
	public static final int MAX_NUM_GUESSES = 5;

    public static void main(String[] args) {

        // Load phrases from a file into the phrases variable
        PhraseBank phrases = buildPhraseBank(args);
        // Create a scanner for input. (Do not create another scanner object.)
        Scanner scanner = new Scanner(System.in);
       
        // Print the intro to the program.
        intro(); 
        
        boolean done = false;
        while (!done) {
            playRound(scanner, phrases);
            done = isGameEnd(scanner);
        }

        scanner.close();
    }

    public static void playRound(Scanner scanner, PhraseBank phrases) {
        String phrase = phrases.getNextPhrase();
        String masked = createMask(phrase);
        String notGuessed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int wrongGuesses = 0;

        System.out.println();
        System.out.println("I am thinking of a " + phrases.getTopic() + " ...");
        System.out.println();
        while (!isSolved(masked) && wrongGuesses < MAX_NUM_GUESSES) {
            showPhrase(masked);
            char guess = getGuess(scanner, notGuessed);
            boolean present = phrase.indexOf(guess) != -1;
            if (present) {
                masked = revealLetter(phrase, masked, guess);
            } else {
                wrongGuesses++;
            }
            notGuessed = removeLetter(notGuessed, guess);
            showGuessResult(guess, present, wrongGuesses);
        }
        showResult(phrase, isSolved(masked));
    }

    public static String createMask(String phrase) {
        String masked = "";
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == '_') {
                masked += '_';
            } else {
                masked += '*';
            }
        }
        return masked;
    }

    public static boolean isSolved(String masked) {
        return masked.indexOf('*') == -1;
    }

    public static String revealLetter(String phrase, String masked, char guess) {
        String result = "";
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess) {
                result += guess;
            } else {
                result += masked.charAt(i);
            }
        }
        return result;
    }

    public static String removeLetter(String letters, char letter) {
        int index = letters.indexOf(letter);
        return letters.substring(0, index) + letters.substring(index + 1);
    }

    public static String addHyphens(String letters) {
        String result = "";
        for (int i = 0; i < letters.length(); i++) {
            if (i > 0) {
                result += "--";
            }
            result += letters.charAt(i);
        }
        return result;
    }

    public static void showPhrase(String masked) {
        System.out.println("The current phrase is " + masked);
        System.out.println();
    }

    public static char getGuess(Scanner scanner, String notGuessed) {
        String input = promptForGuess(scanner, notGuessed);
        char guess = input.toUpperCase().charAt(0);
        while (notGuessed.indexOf(guess) == -1) {
            System.out.println(input + " is not a valid guess.");
            input = promptForGuess(scanner, notGuessed);
            guess = input.toUpperCase().charAt(0);
        }
        return guess;
    }

    public static String promptForGuess(Scanner scanner, String notGuessed) {
        System.out.println("The letters you have not guessed yet are: ");
        System.out.println(addHyphens(notGuessed));
        System.out.println();
        System.out.print("Enter your next guess: ");
        String input = scanner.nextLine().trim();
        System.out.println();
        return input;
    }

    public static void showGuessResult(char guess, boolean present, int wrongGuesses) {
        System.out.println("You guessed " + guess + ".");
        System.out.println();
        if (present) {
            System.out.println("That is present in the secret phrase.");
        } else {
            System.out.println("That is not present in the secret phrase.");
        }
        System.out.println();
        System.out.println("Number of wrong guesses so far: " + wrongGuesses);
    }

    public static void showResult(String phrase, boolean won) {
        if (won) {
            System.out.println("The phrase is " + phrase + ".");
            System.out.println("You win!!");
        } else {
            System.out.println("You lose. The secret phrase was " + phrase);
        }
    }

    public static boolean isGameEnd(Scanner scanner) {
        System.out.println("Do you want to play again?");
        System.out.print("Enter 'Y' or 'y' to play again: ");
        String answer = scanner.nextLine().trim().toUpperCase();
        return answer.length() == 0 || answer.charAt(0) != 'Y';
    }


    // Build the PhraseBank.
    // Creates a set of secret phrases read from a file using the PhraseBank.java class.
    // ** Do not change this method. **
    public static PhraseBank buildPhraseBank(String[] args) {
        PhraseBank result;
        // If no command line arguments 
        if (args == null || args.length == 0
                        || args[0] == null || args[0].length() == 0) {
        	String[] filenames = {"HangmanMovies.txt"};
        	result =  new PhraseBank(filenames[0], true); // Creates a PhraseBank with the default file, HangmanMovies.txt
        // Yes, command line arguments. The grading script uses this code so that multiple files can be tested.              
        } else {
            result = new PhraseBank(args[0]);
        }
        return result;
    }


    // Print the intro to the program.
    // ** Do not change this method **
    public static void intro() {
        System.out.println("This program plays the game of hangman.");
        System.out.println();
        System.out.println("The computer will pick a random phrase.");
        System.out.println("Input letters for your guess.");
        System.out.println("After 5 wrong guesses you lose.");
    }
}