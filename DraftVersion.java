import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// BULLS AND COWS: BASIC ANALOG VERSION.
public class DraftVersion
{
    public static int digitsInCorrectNumber = 4;
    public static int correctNumber;

    public static void main(String[] args)
    {
        Scanner userInput = new Scanner(System.in);

        // Get the correct number.
        correctNumber = generateRandomNumber(digitsInCorrectNumber);
        System.out.println(correctNumber);
        System.out.println(intToArray(correctNumber));

        // User guesses
        int userGuess = getGuess(userInput);
        System.out.println(userGuess);
        System.out.println(intToArray(userGuess));

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

    // GET COWS: Takes the input and correctNumber, returns bulls (right number, wrong place)
    public static int getCows(ArrayList<Integer> guessedArray)
    {
        ArrayList<Integer> correctArray = intToArray(correctNumber); // TODO optimize this to a class variable.

        int cows = 0;

        // check cows for each digit
        for (int digitGuessed : guessedArray)
        {
            // TODO work on this next...
        }

        return cows;
    }

    // GET BULLS: Takes the input and correctNumber, returns bulls (right number and place)


    // setting: use buttons or a keyboard.
}

