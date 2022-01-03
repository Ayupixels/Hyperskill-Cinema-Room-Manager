package cinema;

import java.util.Scanner;

public class Cinema {

    final static int SMALL_SCREEN_ROOM = 60;
    final static int REGULAR_TICKET_PRICE = 10;
    final static int BACK_ROW_TICKET_PRICE = 8;
    static int chosenRow = 0;
    static int chosenSeatInRow = 0;
    private static int noOfPurchasedTickets = 0;
    private static int totalNoOfSeats = 0;
    private static int currentIncome = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Write your code here

        System.out.println("Enter the number of rows:");
        int screenRoomRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int screenRoomSeatsPerRow = scanner.nextInt();
        totalNoOfSeats = screenRoomRows * screenRoomSeatsPerRow;

        String[][] cinema = new String[screenRoomRows][screenRoomSeatsPerRow];

        emptyTheatre(screenRoomRows,screenRoomSeatsPerRow, cinema);

        while (true) {
            System.out.println();
            displayMenu();
            int userChoice = scanner.nextInt();
            if (selectedChoice(scanner, screenRoomRows, screenRoomSeatsPerRow, cinema, userChoice)) return;
        }

    }

    private static boolean selectedChoice(Scanner scanner, int screenRoomRows, int screenRoomSeatsPerRow, String[][] cinema, int userChoice) {

        if(userChoice == 1) {
            displaySitting(screenRoomRows, screenRoomSeatsPerRow, cinema);
        } else if (userChoice == 2) {
            noOfPurchasedTickets = buyATicket(scanner, screenRoomRows, screenRoomSeatsPerRow, cinema);
        } else if (userChoice == 3) {
            float percentage = (float)noOfPurchasedTickets / (float)totalNoOfSeats * 100;

            System.out.println("Number of Purchased tickets: " + noOfPurchasedTickets);
            System.out.printf("Percentage: % .2f%% \n" , percentage);
            System.out.println("Current income: $" + currentIncome);
            System.out.println("Total income: $" + calculateTotalProfit(screenRoomRows, screenRoomSeatsPerRow));

            } else if (userChoice == 0) {
            return true;
        } else {
                System.out.println("Please select available options.");
        }
        return false;
    }

    private static void displayMenu() {
        System.out.println("1. Show the seats\n" +
                           "2. Buy a ticket\n" +
                           "3. Statistics\n" +
                           "0. Exit");
    }

    private static int buyATicket(Scanner scanner, int screenRoomRows, int screenRoomSeatsPerRow, String[][] cinema) {
        int purchased = 0;

        while (purchased == 0) {

            System.out.println("Enter a row number:");
            chosenRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            chosenSeatInRow = scanner.nextInt();

            if (chosenRow <= screenRoomRows && chosenSeatInRow <= screenRoomSeatsPerRow) {
                if ("B".equals(cinema[chosenRow - 1][chosenSeatInRow - 1])) {
                    System.out.println("That ticket has already been purchased!");
                }
                if ("S".equals(cinema[chosenRow - 1][chosenSeatInRow - 1])) {
                    reserveSitting(chosenRow, chosenSeatInRow, cinema);
                    System.out.print("Ticket price: ");
                    System.out.println("$" + calculateTicketPrice(chosenRow, screenRoomRows));
                    noOfPurchasedTickets++;
                    purchased = 1;
                }
            } else {
                System.out.println("Wrong input!");
            }
        }

        return noOfPurchasedTickets;
    }

    public static void displaySitting(int rows,int columns, String[][] cinema){
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= columns; i++) {
            System.out.print(i + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < rows; i++) {
            System.out.print(i+1 + " ");
            for (int j = 0; j < columns; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void emptyTheatre(int rows, int columns, String[][] cinema) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cinema[i][j] = "S";
            }
        }
    }


    public static int calculateTotalProfit(int rows, int seats) {
        int frontRowSeats = rows / 2 * seats;
        int backRowSeats = (rows / 2 + rows % 2) * seats;

        int profit;
        if (totalNoOfSeats <= SMALL_SCREEN_ROOM) {
            profit = REGULAR_TICKET_PRICE * totalNoOfSeats;
        } else {
            profit = (REGULAR_TICKET_PRICE * frontRowSeats) +
                     (BACK_ROW_TICKET_PRICE * backRowSeats);
        }
        return profit;
    }

    public static int calculateTicketPrice(int row, int rows) {

        int ticketPrice = REGULAR_TICKET_PRICE;
        if (totalNoOfSeats > SMALL_SCREEN_ROOM) {
            if (row > rows / 2) {
                ticketPrice = BACK_ROW_TICKET_PRICE;
            }
        }
        currentIncome += ticketPrice;
        return ticketPrice;
    }
    public static void reserveSitting(int row, int seat, String[][] cinema){
                cinema[row-1][seat-1] = "B";
    }
}