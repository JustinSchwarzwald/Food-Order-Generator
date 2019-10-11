/*
 * Author:      Justin Schwarzwald
 * Date:        5/23/2018
 * Assignment:  Assignment 1
 * Description: Program takes in either a file (which contains the order items wanting to be purchased) or inputs of the items wanting to be ordered, and outputs an invoice to a file called invoice.txt
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Schwarzwald_Justin_assignment1
{
    // Prices of items sold initialized as global constants
    public static final double PRICE_HOTDOG = 2.00;
    public static final double PRICE_BRATWURST = 2.75;
    public static final double PRICE_HAMBURGER = 3.00;
    public static final double PRICE_CHEESEBURGER = 3.75;
    public static final double PRICE_POPCORN = 1.00;
    public static final double PRICE_SODA = 1.25;
    public static final double PRICE_CANDY = 0.75;
    
    public static final double TAX = .05; // In percent form
    
    
    public static void main(String[] args)
    {
        // Initializing variables to be used in main
        int userChoice = 0; // 1 for creating order or 2 for loading order
        int menuSelection = 0; // Selection of which item the user wants
        boolean fileFound = false; // Condition on finding input file

        

        double billSubtotal = 0;
        double billTax = 0;
        double billTotal = 0;

        ArrayList<String> currentOrder = new ArrayList<String>(); // List of all items that user selected

        Scanner userInput = new Scanner(System.in);

        Scanner input = null;
        File inputFile = null;
        PrintWriter outFile = null;

        // Setting condition to make sure only values 1 and 2 are entered, will continue looping otherwise
        while (userChoice != 1 && userChoice != 2)
        {
            // Shows the user menu
            userMenu();

            // Checks if user entered a number
            if (userInput.hasNextInt())
            {
                // Sets variable userChoice to number input in a number was given
                userChoice = userInput.nextInt();
                // Checks if number was a 1 or 2
                if (userChoice != 1 && userChoice != 2)
                {
                    // Tells user to input either a 1 or 2 if they didn't
                    System.out.println("\nYou need to enter the value 1 or 2 to continue\n");
                }
            }
            // If a number wasn't entered the following runs
            else
            {
                // Reset the scanner
                userInput.next();
                // Tells user to input either a 1 or 2 if they didn't
                System.out.println("\nYou need to enter the value 1 or 2 to continue\n");
            }
        } // End of while loop once 1 or 2 is entered

        // Manually processing order
        if (userChoice == 1)
        {

            // Continually takes order until -1 is entered
            while (menuSelection != -1)
            {
                do
                {
                    // Shows the menu and prompts user for there selection, added one for reference to next item to be ordered
                    orderMenu(currentOrder.size() + 1);

                    // Checks if user entered a number
                    if (userInput.hasNextInt())
                    {
                        // Sets variable menuSelection to the number that was given by user
                        menuSelection = userInput.nextInt();

                        // Checks if number is an item on the menu or -1 to finish
                        if (menuSelection < -1 || menuSelection > 7 || menuSelection == 0)
                        {
                            // If number entered is not 1 through 7 displays error msg
                            System.out.println("\nYou need to enter the number corresponding to the following items, or a -1 to finish your order");
                        }
                        // If selection is 1 through 7 adds selection to the current order(arraylist)
                        else
                        {
                            // Calls addItem, which adds the item to the array
                            addItem(menuSelection, currentOrder);
                        }
                    }
                    // If a number wasn't entered the following runs
                    else
                    {
                        // Reset the scanner
                        userInput.next();
                        // If number entered is not 1 through 7 displays error msg
                        System.out.println("\nYou need to enter the number corresponding to the following items, or a -1 to finish your order");
                    }

                } while (menuSelection < -1 || menuSelection > 7 || menuSelection == 0); // Ends loop when the user enters a valid entry

            } // End of while loop -1 was entered and order is complete

        }
        // Reading an order from file
        else
        {
            userInput.nextLine(); // Clearing scanner
            System.out.println("\nProcessing order from file."); 

            // Loops until a valid file is loaded
            while (!fileFound)
            {
                // Prompting user for the file name (file attached is "order.txt")
                System.out.print("Please enter the filename >> "); 
                // Opening the file and scanner
                try
                {
                    inputFile = new File(userInput.nextLine()); // "order.txt"
                    input = new Scanner(inputFile);
                    fileFound = true; // Changes it to true once file is found, if error it skips and goes to catch
                }
                catch (FileNotFoundException e)
                {
                    System.out.println("\nThat file was not found, please try again\n");
                }
            }
            
            System.out.println(""); // For formatting
            
            // Loop until file reads last line
            while (input.hasNextLine())
            {
                if (!input.hasNextInt()) // Checks if its just a number
                {
                    // Adds each line to currentOrder(arraylist) and prints out to the console the line read
                    currentOrder.add(input.nextLine());
                    System.out.println("read " + currentOrder.get(currentOrder.size() - 1));
                }
                else // Skips line if line is a number
                {
                    input.nextLine();
                }
            }
        } // End of reading file

        // Creating the invoice
        try
        {
            // Setting the outFile to "invoice.txt"
            outFile = new PrintWriter("invoice.txt");
        }
        catch (FileNotFoundException e)
        {
            // Prints if error is made
            e.printStackTrace();
        }

        // Writes to file
        outFile.println("FoodStand");
        outFile.println("---------------------------------");
        // Writes each item of the arraylist to file with formatting
        for (int i = 0; i < currentOrder.size(); i++)
        {
            if (currentOrder.get(i).equals("hotdog"))
                outFile.printf("%-12s - %18.2f%n", "HOTDOG", PRICE_HOTDOG);
            if (currentOrder.get(i).equals("bratwurst"))
                outFile.printf("%-12s - %18.2f%n", "BRATWURST", PRICE_BRATWURST);
            if (currentOrder.get(i).equals("hamburger"))
                outFile.printf("%-12s - %18.2f%n", "HAMBURGER", PRICE_HAMBURGER);
            if (currentOrder.get(i).equals("cheeseburger"))
                outFile.printf("%-12s - %18.2f%n", "CHEESEBURGER", PRICE_CHEESEBURGER);
            if (currentOrder.get(i).equals("popcorn"))
                outFile.printf("%-12s - %18.2f%n", "POPCORN", PRICE_POPCORN);
            if (currentOrder.get(i).equals("soda"))
                outFile.printf("%-12s - %18.2f%n", "SODA", PRICE_SODA);
            if (currentOrder.get(i).equals("candy"))
                outFile.printf("%-12s - %18.2f%n", "CANDY", PRICE_CANDY);
        }
        outFile.println("---------------------------------");

        // Calculates the subtotal, tax, and total of bill
        billSubtotal = getSubtotal(currentOrder);
        billTax = billSubtotal * TAX;
        billTotal = billSubtotal * (1 + TAX);

        // Writes subtotal, tax, and total of bill formatted to the file
        outFile.printf("%20s %-6s %5.2f", "Subtotal", "-", billSubtotal);
        outFile.printf("%n%20s %-6s %5.2f", "Tax", "-", billTax);
        outFile.printf("%n%20s %-6s %5.2f", "Total", "-", billTotal);

        // Closing scanners and the file
        userInput.close();
        outFile.close();

        // Print confirmation the Invoice has been completed
        System.out.println("\nInvoice has been saved as invoice.txt");
    }
    
    // Prints the menu and the order number which the user is on
    private static void orderMenu(int numInArray)
    {
        System.out.println("\nMenu:");
        System.out.println("  1. Hotdog - $2.00");
        System.out.println("  2. Bratwurst - $2.75");
        System.out.println("  3. Hamburger - $3.00");
        System.out.println("  4. Cheeseburger - $3.75");
        System.out.println("  5. Popcorn - $1.00");
        System.out.println("  6. Soda - $1.25");
        System.out.println("  7. Candy - $0.75");
        System.out.print("Please choose item #" + numInArray + " or -1 to complete the order >> ");
    }

    // Prints the users menu to choose to either 1 make an order, or 2 read an order
    private static void userMenu()
    {
        System.out.println("Welcome to FoodStand!");
        System.out.println("Would you like to:");
        System.out.println("1) Manually process an order");
        System.out.println("2) Read an order from a file");
        System.out.print("\nPlease enter choice >> ");
    }

    // Calculates the Subtotal of order and returns it
    private static double getSubtotal(ArrayList<String> itemsOrdered)
    {
        double subtotal = 0;
        
        // Goes through each item in arraylist and adds the price for that item to variable subtotal, then returns the value once all items are added up
        for (int i = 0; i < itemsOrdered.size(); i++)
        {
            if (itemsOrdered.get(i).equals("hotdog"))
                subtotal += PRICE_HOTDOG;
            if (itemsOrdered.get(i).equals("bratwurst"))
                subtotal += PRICE_BRATWURST;
            if (itemsOrdered.get(i).equals("hamburger"))
                subtotal += PRICE_HAMBURGER;
            if (itemsOrdered.get(i).equals("cheeseburger"))
                subtotal += PRICE_CHEESEBURGER;
            if (itemsOrdered.get(i).equals("popcorn"))
                subtotal += PRICE_POPCORN;
            if (itemsOrdered.get(i).equals("soda"))
                subtotal += PRICE_SODA;
            if (itemsOrdered.get(i).equals("candy"))
                subtotal += PRICE_CANDY;
        }
        return subtotal;

    }
    
    // Takes in the number selected and adds the string corresponding to the item selected to the array list
    private static void addItem(int menuSelection, ArrayList<String> currentOrder)
    {
        if (menuSelection == 1)
            currentOrder.add("hotdog");
        if (menuSelection == 2)
            currentOrder.add("bratwurst");
        if (menuSelection == 3)
            currentOrder.add("hamburger");
        if (menuSelection == 4)
            currentOrder.add("cheeseburger");
        if (menuSelection == 5)
            currentOrder.add("popcorn");
        if (menuSelection == 6)
            currentOrder.add("soda");
        if (menuSelection == 7)
            currentOrder.add("candy");
    }

}
