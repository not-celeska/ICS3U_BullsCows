/*
MISHA BUSHKOV
ICS3U SUMMATIVE: Bulls and Cows [GAMEPLAY VERS.]
DATE: 25 / 12 / 2023
 */

// == IMPORTS =============
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
TODO: add option to choose number of digits (3, 4, 5, 6). Two player mode.
 */

// BULLS AND COWS: BASIC ANALOG VERSION.
public class DraftVersion
{
    // == INSTANCE VARIABLES ======================

    public static int digitsInCorrectNumber = 4;
    public static int correctNumber;

    // Array Indexing - for better organization.
    public static int[] guessFeedback = new int[2];
    final public static int BULLS = 0;
    final public static int COWS = 1;

    // User stats.
    public static int secondsSpentGuessing;
    public static int timesGuessed = 0;

    // == MAIN ===========================

    public static void main(String[] args)
    {
        Scanner userInput = new Scanner(System.in);

        // Get the correct number.
        correctNumber = generateRandomNumber(digitsInCorrectNumber);

        // For bug fixing purposes.
        System.out.print("Have the correct number shown [y / anything else]? ");
        if (userInput.nextLine().equalsIgnoreCase("y"))
        {
            System.out.println("SYSTEM | The correct number is: [" + correctNumber + "].");
        }

        // Log the start time.
        long startTime = System.currentTimeMillis();

        // User guesses and recieves feedback until user guesses the number.
        while (guessFeedback[BULLS] != 4)
        {
            int userGuess = getGuess(userInput);

            // THIS IS ONLY HERE FOR THE UI TO LOOK GOOD [DOES NOT NEED TO BE HERE].
            if (userGuess == correctNumber)
            {
                timesGuessed++;
                break;
            }

            // Get feedback.
            guessFeedback = getGuessFeedback(userGuess);

            // Print feedback.
            System.out.println("\nSYSTEM | Printing bulls and cows for [" + userGuess + "].");
            System.out.print("BULLS: " + guessFeedback[BULLS] + " | COWS: " + guessFeedback[COWS] + " | ");

            // Add times guessed.
            timesGuessed++;
        }

        // Log the end time.
        long endTime = System.currentTimeMillis();

        // Convert to minutes and seconds.
        int seconds = Math.round((endTime - startTime) / 1000);
        int minutes = 0;

        // If took more than 60 seconds - convert to minutes.
        if (seconds >= 60)
        {
            minutes = seconds / 60;
            seconds %= 60;
        }

        // Take time and format it.
        String timeTook;
        if (minutes > 0)
        {
            timeTook = minutes + "m " + seconds + "s";
        }
        else
        {
            timeTook = seconds + "s";
        }

        // Print a buffer + results.
        System.out.println("\n<>< YOU WON ><><><><><><><><><><><><><><><><><><><><><><>");
        System.out.println("GUESSES TOOK | " + timesGuessed);
        System.out.println("TIME SPENT   | " + timeTook);
        // write all guesses?
    }

    // == OTHER METHODS ==================

    // GENERATE RANDOM NUMBER: Just generates a random number with the length of numDigits.
    public static int generateRandomNumber(int numDigits)
    {
        Random numberGenerator = new Random();
        String randomNumber = "";

        // 1. We will generate [numDigits] random numbers from 0-9.
        for (int i = 0; i < numDigits; i++)
        {
            int randomDigit;

            // a) check i: 0123 cannot happen <-- conversion from string to integer will cause it to become 123.
            if (i == 0)
            {
                randomDigit = numberGenerator.nextInt(1, 10);
            }
            else
            {
                randomDigit = numberGenerator.nextInt(0, 10);
            }

            // b) Make sure each number in the integer is unique; helps us out later on when checking Bulls and Cows.
            if (randomNumber.contains(String.valueOf(randomDigit)))
            {
                numDigits++; // This has the for loop go on one more time.
            }
            else
            {
                randomNumber += randomDigit; // Adds it to the random number.
            }
        }

        return Integer.parseInt(randomNumber);
    }

    // GET GUESS: Gets a error-checked guess.
    public static int getGuess(Scanner userInput)
    {
        int userGuess;

        while (true)
        {

            System.out.print("Enter your guess [" + digitsInCorrectNumber + "#]: ");

            try
            { // Try: checks that use inputted an integer.
                userGuess = userInput.nextInt();

                if (String.valueOf(userGuess).length() != digitsInCorrectNumber)
                { // If: checks that the user inputted the right number of digits.
                    System.out.println("\nThe correct number has " + digitsInCorrectNumber + " digits [0 does not register as the first one]. Try again.");
                }
                else if (!allDigitsAreUnique(userGuess))
                {
                    System.out.println("\nEnsure that each digit in your guess is unique. Try again.");
                }
                else
                {
                    return userGuess;
                }
            }
            catch (Exception e)
            {
                System.out.println("\nYou did not enter an integer. Try again.");
                userInput.next();
            }
        }
    }

    // ALL DIGITS ARE UNIQUE: Checks that all the digits in a number are unique - this will help not confuse the player in the feedback.
    public static boolean allDigitsAreUnique(int number)
    {
        // We will be using this string reference to get independant numeric values; could have used a ArrayList style.
        String stringNumber = String.valueOf(number);

        // Go through the whole list to compare each number individually.
        for (int digitIndex = 0; digitIndex < stringNumber.length(); digitIndex++)
        {
            // Get the digit itself and set how many times it was found to 0.
            int currentDigit = Character.getNumericValue(stringNumber.charAt(digitIndex));
            int timesDigitFound = 0;

            // Go through the list again to determine how many times this digit appears in the number.
            for (int allDigits = 0; allDigits < stringNumber.length(); allDigits++)
            {
                // If the digit appeared in the number.
                if (currentDigit == Character.getNumericValue(stringNumber.charAt(allDigits)))
                {
                    timesDigitFound ++;
                }
            }

            // If the digit ended up showing more than once.
            if (timesDigitFound > 1)
            {
                return false;
            }

        }

        return true;
    }


    // INT TO ARRAYLIST: Takes an int then coverts it into an array with the integer digits.
    public static ArrayList<Integer> intToArray(int integer)
    {

        ArrayList<Integer> intArray = new ArrayList<>();
        String integerString = String.valueOf(integer);

        for (int i = 0; i < integerString.length(); i++)
        {
            char digitChar = integerString.charAt(i);
            intArray.add(Character.getNumericValue(digitChar));
        }

        return intArray;
    }

    // GET GUESS FEEDBACK: Takes the input and correctNumber, returns bulls (right number and place) and cows (right number wrong place)
    public static int[] getGuessFeedback(int guess)
    {
        int[] guessFeedback = {0, 0}; // Check "Array Indexing" class variables.
        ArrayList<Integer> guessArray = intToArray(guess);
        ArrayList<Integer> correctArray = intToArray(correctNumber);

        // Go through the guessed array and compare it to the correct array.
        for (int digitIndex = 0; digitIndex < String.valueOf(guess).length(); digitIndex++)
        {
            // Is the guessed digit at the current index even in the correct number?
            if (correctArray.contains(guessArray.get(digitIndex)))
            {
                // Is the digit in the same spot as the digit in the correct number?
                if (guessArray.get(digitIndex) == correctArray.get(digitIndex))
                {
                    guessFeedback[BULLS]++;
                }
                else
                {
                    guessFeedback[COWS]++;
                }
            }
        }

        return guessFeedback;
    }

    // setting: use buttons or a keyboard.
}

