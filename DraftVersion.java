import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// BULLS AND COWS: BASIC ANALOG VERSION.
public class DraftVersion
{
    public static int digitsInCorrectNumber = 4;
    public static int correctNumber;

    // Array Indexing - for better organization.
    final public static int BULLS = 0;
    final public static int COWS = 1;

    public static void main(String[] args)
    {
        Scanner userInput = new Scanner(System.in);

        // Get the correct number.
        correctNumber = generateRandomNumber(digitsInCorrectNumber);
        System.out.println(correctNumber);
        System.out.println(intToArray(correctNumber) + "\n");

        // User guesses
        int userGuess = getGuess(userInput);
        System.out.println(userGuess);
        System.out.println(intToArray(userGuess));

        // Bulls and Cows algorithm
        System.out.println("\nprinting bulls and cows for " + userGuess);
        int[] userResults = getGuessFeedback(userGuess);
        System.out.println("Bulls: " + userResults[BULLS] + "\nCows: " + userResults[COWS]);

        // recursive funcion to get all of the digits

        // Compare numbers

        // Print results

    }

    // 2 player mode?
    // number of digits? --> 3 4 or 5

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
                    System.out.println("\nThe correct number has " + digitsInCorrectNumber + " digits. Try again.");
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

