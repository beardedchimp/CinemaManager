package cinema;

import java.util.Scanner;

public class Cinema {
    static Scanner scanner = new Scanner(System.in);
    final static int NORMAL_PRICE_PER_TICKET = 10;
    final static int DISCOUNTED_PRICE_PER_TICKET = 8;

    public static void main(String[] args) {

        System.out.printf("Enter the number of rows:%n");
        int totalCinemaRows = scanner.nextInt();
        System.out.printf("Enter the number of seats in each row:%n");
        int totalCinemaColumns = scanner.nextInt();
        char[][] cinemaSeats = setupCinema(totalCinemaRows, totalCinemaColumns);

        showMenu(cinemaSeats);

    }

    public static void showMenu(char[][] cinemaSeats2DArray) {
        boolean isTerminalActive = true;

        double numberOfTotalSeats = (cinemaSeats2DArray.length - 1) * (cinemaSeats2DArray[0].length - 1);

        int numberOfPurchasedTickets = 0;
        int currentTotalIncome = 0;
        int totalPossibleIncome = totalPossibleIncome(cinemaSeats2DArray);

        while(isTerminalActive) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int userSelection = scanner.nextInt();

            switch (userSelection) {
                case 1:
                    printCinema(cinemaSeats2DArray);
                    break;
                case 2:
                    currentTotalIncome += buyATicket(cinemaSeats2DArray);
                    numberOfPurchasedTickets++;
                    break;
                case 3:
                    showStatistics(numberOfPurchasedTickets, numberOfTotalSeats,
                            currentTotalIncome, totalPossibleIncome);
                    break;
                case 0:
                    isTerminalActive = false;
            }
        }
    }

    public static void showStatistics(int numberOfPurchasedTickets, double numberOfTotalSeats,
                                      int currentTotalIncome, int totalPossibleIncome) {
        double percentageOfPurchasedTickets;

        if (numberOfTotalSeats != 0) {
            percentageOfPurchasedTickets = numberOfPurchasedTickets / numberOfTotalSeats * 100;
        } else {
            percentageOfPurchasedTickets = 0.00;
        }

        System.out.printf("Number of purchased tickets: %d%n", numberOfPurchasedTickets);
        System.out.printf("Percentage: %.2f%%%n", percentageOfPurchasedTickets);
        System.out.printf("Current income: $%d%n", currentTotalIncome);
        System.out.printf("Total income: $%d%n%n", totalPossibleIncome);
    }

    public static int buyATicket(char[][]cinemaSeats) {
        boolean isWrongInput = true;
        int selectedRow = 0;
        int selectedColumn = 0;
        int[] selectedSeat = new int[2];
        int ticketPrice;

        while(isWrongInput) {
            System.out.println("Enter a row number:");
            selectedRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            selectedColumn = scanner.nextInt();
            if (selectedRow >= cinemaSeats.length || selectedRow <= 0 ||
                    selectedColumn >= cinemaSeats[selectedRow].length || selectedColumn <= 0) {
                System.out.println("Wrong input!");
            } else if (cinemaSeats[selectedRow][selectedColumn] == 'B') {
                System.out.println("That ticket has already been purchased!");
            } else {
                isWrongInput = false;
            }
        }
        selectedSeat[0] = selectedRow;
        selectedSeat[1] = selectedColumn;

        updateCinemaSeats(cinemaSeats, selectedSeat);

        ticketPrice = ticketPrice(cinemaSeats, selectedRow);

        System.out.println("Ticket price: $" + ticketPrice);
        return ticketPrice;
    }

    public static void updateCinemaSeats(char[][] cinemaSeats, int[] selectedSeat) {
            cinemaSeats[selectedSeat[0]][selectedSeat[1]] = 'B';
    }

    public static char[][] setupCinema(int rows, int columns) {
        char[][] cinemaSeats = new char[rows + 1][columns + 1];

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= columns; j++) {
                if (i == 0 && j == 0) {
                    cinemaSeats[i][j] = ' ';
                } else if (i == 0) {
                    cinemaSeats[i][j] = Character.forDigit(j, 10);
                } else if (j == 0) {
                    cinemaSeats[i][j] = Character.forDigit(i, 10);
                } else {
                    cinemaSeats[i][j] = 'S';
                }
            }
        }
        return cinemaSeats;
    }

    public static void printCinema(char[][] cinemaSeats) {
        System.out.printf("Cinema:%n");
        for (int i = 0; i < cinemaSeats.length; i++) {
            for (int j = 0; j < cinemaSeats[i].length; j++) {
                System.out.printf("%c ", cinemaSeats[i][j]);
            }
            System.out.printf("%n");
        }
    }

    public static int ticketPrice(char[][] cinemaSeats, int selectedRow) {
        int rows = cinemaSeats.length - 1;
        int columns = cinemaSeats[0].length - 1;

        int numberOfSeats = rows * columns;

        if (numberOfSeats <= 60) {
            return NORMAL_PRICE_PER_TICKET;
        } else {
            int halfRows = rows / 2;
            if (selectedRow > halfRows) {
                return DISCOUNTED_PRICE_PER_TICKET;
            } else {
                return NORMAL_PRICE_PER_TICKET;
            }
        }
    }

    public static int totalPossibleIncome(char[][] cinemaSeats) {
        int possibleIncome;
        int rows = cinemaSeats.length - 1;
        int columns = cinemaSeats[0].length - 1;
        int numberOfSeats = rows * columns;

        if (numberOfSeats <= 60) {
            possibleIncome = numberOfSeats * NORMAL_PRICE_PER_TICKET;
        } else {
            int halfRows = rows / 2;
            int backHalfRows = rows - halfRows;
            possibleIncome = (halfRows * NORMAL_PRICE_PER_TICKET * columns) +
                    (backHalfRows * DISCOUNTED_PRICE_PER_TICKET * columns);
        }
        return possibleIncome;
    }
}