package MidtermProject;

import java.util.Scanner;

public class BandOfTheHour {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to the Band of the Hour");
        System.out.println("-----------------------------");

        // Initializing variables for stadium setup
        int numRows = promptNumRows(scanner);
        int[] numPositions = new int[numRows];
        double[][] positionsWeight = new double[numRows][];

        // Prompting for number of positions in each row
        for (int i = 0; i < numRows; i++) {
            numPositions[i] = promptNumPositions(scanner, (char) ('A' + i));
            positionsWeight[i] = new double[numPositions[i]];
        }

        // Main menu loop
        boolean exit = false;
        while (!exit) {
            displayMenu();
            char choice = Character.toUpperCase(scanner.next().charAt(0));

            switch (choice) {
                case 'A': // Adding a musician
                    addMusician(numRows, numPositions, positionsWeight, scanner);
                    break;
                case 'R': // Removing a musician
                    removeMusician(numRows, numPositions, positionsWeight, scanner);
                    break;
                case 'P': // Printing current assignment
                    printCurrentAssignment(numRows, numPositions, positionsWeight);
                    break;
                case 'X': // Exiting the program
                    exit = true;
                    break;
                default:
                    System.out.println("ERROR: Invalid option, try again.");
            }
        }

        // Exiting program
        System.out.println("Exiting the program now!");
    }

    // Method to prompt the user for the number of rows
    public static int promptNumRows(Scanner scanner) {
        int numRows;
        do {
            System.out.print("Please enter number of rows (1 to 10): ");
            numRows = scanner.nextInt();
            if (numRows < 1 || numRows > 10)
                System.out.println("ERROR: Out of range, try again.");
        } while (numRows < 1 || numRows > 10);
        return numRows;
    }

    // Method to prompt the user for the number of positions in a row
    public static int promptNumPositions(Scanner scanner, char row) {
        int numPos;
        do {
            System.out.print("Please enter number of positions in row " + row + " (1 to 8): ");
            numPos = scanner.nextInt();
            if (numPos < 1 || numPos > 8)
                System.out.println("ERROR: Out of range, try again.");
        } while (numPos < 1 || numPos > 8);
        return numPos;
    }

    // Method to display the menu options
    public static void displayMenu() {
        System.out.print("\n(A)dd, (R)emove, (P)rint, e(X)it: ");
    }

    // Method to add a musician
    public static void addMusician(int numRows, int[] numPositions, double[][] positionsWeight, Scanner scanner) {
        boolean invalidInputRow = true;
        while (invalidInputRow) {
            System.out.print("Please enter row letter: ");
            char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
            int row = rowLetter - 'A';

            // Validate row input
            if (row < 0 || row >= numRows) {
                System.out.println("ERROR: Out of range, try again.");
                continue;
            }

            int numPos = numPositions[row];
            boolean invalidInputPosition = true;

            // Calculate total weight of musicians in the row
            double totalWeightInRow = 0;
            for (int i = 0; i < numPos; i++) {
                totalWeightInRow += positionsWeight[row][i];
            }

            while (invalidInputPosition) {
                System.out.print("Please enter position number (1 to " + numPos + "): ");
                int position = scanner.nextInt();
                if (position < 1 || position > numPos) {
                    System.out.println("ERROR: Out of range, try again.");
                    continue;
                }
                position--;

                // Check if position is occupied
                if (positionsWeight[row][position] != 0) {
                    System.out.println("ERROR: There is already a musician there. Returning to the menu...");
                    return;
                }

                // Prompt user for musician weight
                boolean invalidInputWeight = true;
                while (invalidInputWeight) {
                    System.out.print("Please enter weight (45.0 to 200.0): ");
                    double weight = scanner.nextDouble();
                    if (weight < 45.0 || weight > 200.0) {
                        System.out.println("ERROR: Out of range, try again.");
                        continue;
                    }

                    // Check if adding this musician would exceed the weight limit for the row
                    if ((totalWeightInRow + weight) > (100 * numPos)) {
                        System.out.println("ERROR: That would exceed the average weight limit. Returning to the menu...");
                        return;
                    }

                    positionsWeight[row][position] = weight;
                    System.out.println("****** Musician added.");
                    invalidInputWeight = false;
                }

                invalidInputPosition = false;
            }

            invalidInputRow = false;
        }
    }

    // Method to remove a musician
    public static void removeMusician(int numRows, int[] numPositions, double[][] positionsWeight, Scanner scanner) {
        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("Please enter row letter: ");
            char rowLetter = Character.toUpperCase(scanner.next().charAt(0));
            int row = rowLetter - 'A';

            // Validate row input
            if (row < 0 || row >= numRows) {
                System.out.println("ERROR: Out of range, try again.");
                continue;
            }

            int numPos = numPositions[row];

            System.out.print("Please enter position number (1 to " + numPos + "): ");
            int position = scanner.nextInt();
            if (position < 1 || position > numPos) {
                System.out.println("ERROR: Out of range, try again.");
                continue;
            }
            position--;

            // Check if position is vacant
            if (positionsWeight[row][position] == 0) {
                System.out.println("ERROR: That position is vacant.");
                return;
            }

            positionsWeight[row][position] = 0;
            System.out.println("****** Musician removed.");
            invalidInput = false;
        }
    }

    // Method to print the current assignment
    public static void printCurrentAssignment(int numRows, int[] numPositions, double[][] positionsWeight) {
        System.out.println("Current Assignment:");

        // Iterate over each row
        for (int i = 0; i < numRows; i++) {
            char row = (char) ('A' + i);
            System.out.print(row + ": ");

            // Iterate over each position in the current row
            for (int j = 0; j < numPositions[i]; j++) {
                System.out.printf("%5.1f   ", positionsWeight[i][j]);
            }
            System.out.println();
        }
    }
}


