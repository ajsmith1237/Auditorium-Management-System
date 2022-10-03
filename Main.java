/*
    Code written by Ashton Smith
    ajs190019
    Last date worked on: 10/3/2022
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner fileScanner = null;
        Scanner userInput = new Scanner(System.in);

        //create hashmap
        HashMap<String, user> users = new HashMap<>();
        //build hashmap
        users = fillHashmap(users);

        try {
            fileScanner = new Scanner(new File("A1.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("A1 failed");
        }

        ArrayList<String> contentArray1 = new ArrayList<>();
        int numOfRows1 = 0;
        int numOfSeats1 = 0;

        //taking 1st file's contents into first content array
        for (int x = 0; fileScanner.hasNextLine(); x++, numOfRows1++)
        {
            String fileContents = fileScanner.nextLine();
            numOfSeats1 = fileContents.length();
            contentArray1.add(fileContents);
        }

        try {
            fileScanner = new Scanner(new File("A2.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("A2 failed");
        }

        ArrayList<String> contentArray2 = new ArrayList<>();
        int numOfRows2 = 0;
        int numOfSeats2 = 0;

        //taking 2nd file's contents into second content array
        for (int x = 0; fileScanner.hasNextLine(); x++, numOfRows2++)
        {
            String fileContents = fileScanner.nextLine();
            numOfSeats2 = fileContents.length();
            contentArray2.add(fileContents);
        }

        try {
            fileScanner = new Scanner(new File("A3.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("A3 failed");
        }

        ArrayList<String> contentArray3 = new ArrayList<>();
        int numOfRows3 = 0;
        int numOfSeats3 = 0;

        //taking 3rd file's contents into third content array
        for (int x = 0; fileScanner.hasNextLine(); x++, numOfRows3++)
        {
            String fileContents = fileScanner.nextLine();
            numOfSeats3 = fileContents.length();
            contentArray3.add(fileContents);
        }

        // creates first linked list auditorium and fills from content array
        Auditorium<Seat> A = new Auditorium<>(numOfSeats1, numOfRows1);
        fillAuditorium(A, contentArray1, numOfRows1, numOfSeats1);

        // creates second linked list auditorium and fills from content array
        Auditorium<Seat> B = new Auditorium<>(numOfSeats2, numOfRows2);
        fillAuditorium(B, contentArray2, numOfRows2, numOfSeats2);

        // creates third linked list auditorium and fills from content array
        Auditorium<Seat> C = new Auditorium<>(numOfSeats3, numOfRows3);
        fillAuditorium(C, contentArray3, numOfRows3, numOfSeats3);

        // Auditoriums created, display login
        boolean startingPoint = true;
        do
        {
            int incorrectAttempts = 0;

            //prompt and check for username
            System.out.println("Please enter a username to sign in:");
            String username = userInput.nextLine();
            boolean userExists = users.containsKey(username);

            if (userExists)
            {

                user curUser = users.get(username);

                //prompt for password
                System.out.println("Please enter your password:");
                String password = userInput.nextLine();

                //check and repeat prompt for password 3 times
                while (incorrectAttempts <= 2 && curUser.getPassword().compareTo(password) != 0)
                {
                    incorrectAttempts++;
                    System.out.println("Invalid password! Please try again:");
                    password = userInput.nextLine();
                }

                //display main menu after signed in
                if (curUser.getPassword().compareTo(password) == 0)
                {

                    //admin/customer distinction
                    if (username.compareTo("admin") == 0)
                    {
                        //admin control menu
                        boolean adminMenu = true;
                        do {

                            boolean validInput;
                            int menuChoice = 0;

                            do
                            {
                                validInput = true;

                                //amin menu prompt
                                try {
                                    System.out.println("What would you like to do?");
                                    System.out.println("1. Print Report");
                                    System.out.println("2. Logout");
                                    System.out.println("3. Exit");

                                    String menuString = userInput.nextLine();
                                    menuChoice = Integer.parseInt(menuString);

                                    if (menuChoice < 1 || menuChoice > 3) { throw new Exception(); }

                                } catch (Exception e) {
                                    validInput = false;
                                    System.out.println("Invalid Input.");
                                }
                            } while (!validInput);

                            if (menuChoice == 1) { printReport(A, B, C); }    //outputting results
                            else if (menuChoice == 2) { adminMenu = false; }    //logout
                            else    //exit entire program
                            {
                                adminMenu = false;
                                startingPoint = false;

                                //saving each auditorium to file
                                saveToFile(A, "A1Final.txt");
                                saveToFile(B, "A2Final.txt");
                                saveToFile(C, "A3Final.txt");
                            }

                        }   while (adminMenu);
                    }
                    else
                    {
                        //regular user menu
                        boolean customerMenu = true;
                        do
                        {
                            boolean validInput;
                            int menuChoice = 0;

                            // Main menu
                            do
                            {
                                validInput = true;

                                try {
                                    System.out.println("What would you like to do?");
                                    System.out.println("1. Reserve Seats");
                                    System.out.println("2. View Orders");
                                    System.out.println("3. Update Order");
                                    System.out.println("4. Display Receipt");
                                    System.out.println("5. Log Out");

                                    String menuString = userInput.nextLine();
                                    menuChoice = Integer.parseInt(menuString);

                                    if (menuChoice < 1 || menuChoice > 5) { throw new Exception(); }

                                } catch (Exception e) {
                                    validInput = false;
                                    System.out.println("Invalid Input.");
                                }

                            } while (!validInput);

                            if (menuChoice == 1) {

                                // auditorium input validation
                                int auditoriumChoice = 0;
                                do {

                                    try {
                                        System.out.print("\n1. Auditorium 1\n2. Auditorium 2\n3. Auditorium 3\n");
                                        String i = userInput.nextLine();
                                        auditoriumChoice = Integer.parseInt(i);

                                        if (auditoriumChoice < 1 || auditoriumChoice > 3) { throw new Exception(); }
                                    }
                                    catch (Exception e)
                                    {
                                        validInput = false;
                                        System.out.println("Invalid Input.");
                                    }

                                } while (!validInput);

                                // sets current auditorium rows and seats
                                int numOfRows = 0, numOfSeats = 0;
                                switch (auditoriumChoice)
                                {
                                    case 1:
                                    {
                                        numOfRows = numOfRows1;
                                        numOfSeats = numOfSeats1;
                                        displayAuditorium(numOfRows, numOfSeats, A);
                                        break;
                                    }
                                    case 2:
                                    {
                                        numOfRows = numOfRows2;
                                        numOfSeats = numOfSeats2;
                                        displayAuditorium(numOfRows, numOfSeats, B);
                                        break;
                                    }
                                    case 3:
                                    {
                                        numOfRows = numOfRows3;
                                        numOfSeats = numOfSeats3;
                                        displayAuditorium(numOfRows, numOfSeats, C);
                                        break;
                                    }
                                }

                                // User prompted for seats details

                                int rowSelected = 0;
                                int seatSelected = 0;
                                int adultTickets = 0;
                                int childTickets = 0;
                                int seniorTickets = 0;

                                // Row selection
                                do
                                {
                                    validInput = true;

                                    try {
                                        System.out.println("What row would you like?");
                                        String rowString = userInput.nextLine();
                                        rowSelected = (Integer.parseInt(rowString)) - 1;

                                        if (rowSelected < 0 || rowSelected > numOfRows - 1) { throw new NumberFormatException(); }
                                    } catch (NumberFormatException e) {
                                        validInput = false;
                                        System.out.println("Invalid Input.");
                                    }

                                } while (!validInput);

                                // Seat Selection
                                do
                                {
                                    validInput = true;

                                    try {
                                        System.out.println("What starting seat would you like?");
                                        String seatString = userInput.nextLine();
                                        seatString = seatString.toUpperCase(Locale.ROOT);
                                        seatSelected = (seatString.charAt(0) - 65);

                                        if (seatSelected < 0 || seatSelected > numOfSeats - 1) { throw new Exception(); }
                                    } catch (Exception e) {
                                        validInput = false;
                                        System.out.println("Invalid Input.");
                                    }

                                } while (!validInput);

                                // Adult ticket Input
                                do
                                {
                                    validInput = true;

                                    try {
                                        System.out.println("How many adult tickets would you like?");
                                        String adultString = userInput.nextLine();
                                        adultTickets = (Integer.parseInt(adultString));

                                        if (adultTickets < 0) { throw new Exception(); }

                                    } catch (Exception e) {
                                        validInput = false;
                                        System.out.println("Invalid Input.");
                                    }

                                } while (!validInput);

                                // Child ticket Input
                                do
                                {
                                    validInput = true;

                                    try {
                                        System.out.println("How many child tickets would you like?");
                                        String childString = userInput.nextLine();
                                        childTickets = (Integer.parseInt(childString));

                                        if (childTickets < 0) { throw new Exception(); }

                                    } catch (Exception e) {
                                        validInput = false;
                                        System.out.println("Invalid Input.");
                                    }

                                } while (!validInput);

                                // Senior ticket Input
                                do
                                {
                                    validInput = true;

                                    try {
                                        System.out.println("How many senior tickets would you like?");
                                        String seniorString = userInput.nextLine();
                                        seniorTickets = (Integer.parseInt(seniorString));

                                        if (seniorTickets < 0) { throw new Exception(); }

                                    } catch (Exception e) {
                                        validInput = false;
                                        System.out.println("Invalid Input.");
                                    }

                                } while (!validInput);

                                //switch and reservation returns 1 for reserved or 0 if not
                                boolean reserved = true;
                                switch (auditoriumChoice)
                                {
                                    case 1:
                                    {
                                        reserved = reserveSeats(A, rowSelected, seatSelected, adultTickets, childTickets,
                                                seniorTickets, curUser, 1, null);
                                        break;
                                    }
                                    case 2:
                                    {
                                        reserved = reserveSeats(B, rowSelected, seatSelected, adultTickets, childTickets,
                                                seniorTickets, curUser, 2,null);
                                        break;
                                    }
                                    case 3:
                                    {
                                        reserved = reserveSeats(C, rowSelected, seatSelected, adultTickets, childTickets,
                                                seniorTickets, curUser, 3, null);
                                        break;
                                    }
                                }

                                // If seat is reserved, call bestAvailable for specific auditorium
                                if (!reserved) {
                                    System.out.println("The seats you requested were already taken");

                                    switch (auditoriumChoice)
                                    {
                                        case 1:
                                        {
                                            bestAvailable(A, adultTickets, childTickets, seniorTickets,numOfRows,
                                                    numOfSeats, userInput, curUser, 1);
                                            break;
                                        }
                                        case 2:
                                        {
                                            bestAvailable(B, adultTickets, childTickets, seniorTickets,numOfRows,
                                                    numOfSeats, userInput, curUser, 2);
                                            break;
                                        }
                                        case 3:
                                        {
                                            bestAvailable(C, adultTickets, childTickets, seniorTickets,numOfRows,
                                                    numOfSeats, userInput, curUser, 3);
                                            break;
                                        }
                                    }
                                }
                            }
                            else if (menuChoice == 2)       //view orders
                            {
                                viewOrders(curUser, false);
                            }
                            else if (menuChoice == 3)       //update orders
                            {

                                boolean updateOrders = true;

                                // order selection
                                System.out.println("List of orders to be selected: ");
                                viewOrders(curUser, false);
                                System.out.print("Please enter which order you'd like to update: ");
                                int i = Integer.parseInt(userInput.nextLine()) - 1;
                                order chosenOrder = null;

                                try {
                                    chosenOrder = curUser.getOrders().get(i);
                                }
                                catch (Exception e)
                                {
                                    System.out.println("Invalid order choice.");
                                    updateOrders = false;
                                }

                                // while for returning to this menu
                                while (updateOrders)
                                {

                                    System.out.println("1. Add tickets to order\n2. Delete tickets from order\n3. Cancel Order");

                                    int userMenu = Integer.parseInt(userInput.nextLine());

                                    if (userMenu == 1)         //add tickets
                                    {
                                        int auditoriumChoice;
                                        int rowSelected = 0, seatSelected = 0, numOfRows = 0, numOfSeats = 0,
                                                adultTickets = 0, childTickets = 0, seniorTickets =0;
                                        char typeSelected = 0;
                                        auditoriumChoice = chosenOrder.getAuditorium();

                                        switch (auditoriumChoice)
                                        {
                                            case 1:
                                            {
                                                displayAuditorium(numOfRows1, numOfSeats1, A);
                                                numOfRows = numOfRows1;
                                                numOfSeats = numOfSeats1;
                                                break;
                                            }
                                            case 2:
                                            {
                                                displayAuditorium(numOfRows2, numOfSeats2, B);
                                                numOfRows = numOfRows2;
                                                numOfSeats = numOfSeats2;
                                                break;
                                            }
                                            case 3:
                                            {
                                                displayAuditorium(numOfRows3, numOfSeats3, C);
                                                numOfRows = numOfRows3;
                                                numOfSeats = numOfSeats3;
                                                break;
                                            }
                                        }

                                        // Row selection
                                        do
                                        {
                                            validInput = true;

                                            try {
                                                System.out.println("What row would you like?");
                                                String rowString = userInput.nextLine();
                                                rowSelected = (Integer.parseInt(rowString)) - 1;

                                                if (rowSelected < 0 || rowSelected > numOfRows - 1) { throw new NumberFormatException(); }
                                            } catch (NumberFormatException e) {
                                                validInput = false;
                                                System.out.println("Invalid Input.");
                                            }

                                        } while (!validInput);

                                        // Seat Selection
                                        do
                                        {
                                            validInput = true;

                                            try {
                                                System.out.println("What starting seat would you like?");
                                                String seatString = userInput.nextLine();
                                                seatString = seatString.toUpperCase(Locale.ROOT);
                                                seatSelected = (seatString.charAt(0) - 65);

                                                if (seatSelected < 0 || seatSelected > numOfSeats - 1) { throw new Exception(); }
                                            } catch (Exception e) {
                                                validInput = false;
                                                System.out.println("Invalid Input.");
                                            }

                                        } while (!validInput);

                                        // Type Selection
                                        do
                                        {
                                            validInput = true;

                                            try {
                                                System.out.println("What kind of ticket will this be?\nA- Adult C- Child S- Senior");
                                                String typeString = userInput.nextLine();
                                                typeString = typeString.toUpperCase(Locale.ROOT);
                                                typeSelected =(typeString.charAt(0));

                                                if (typeSelected != 'A' && typeSelected != 'C' && typeSelected != 'S')
                                                { throw new Exception(); }
                                            } catch (Exception e) {
                                                validInput = false;
                                                System.out.println("Invalid Input.");
                                            }

                                        } while (!validInput);

                                        switch (typeSelected)
                                        {
                                            case 'A':
                                            {
                                                adultTickets = 1;
                                                break;
                                            }
                                            case 'C':
                                            {
                                                childTickets = 1;
                                                break;
                                            }
                                            case 'S':
                                            {
                                                seniorTickets = 1;
                                                break;
                                            }

                                        }

                                        //reserving
                                        boolean reserved = true;
                                        switch (auditoriumChoice)
                                        {
                                            case 1:
                                            {
                                                reserved = reserveSeats(A, rowSelected, seatSelected, adultTickets, childTickets,
                                                        seniorTickets, curUser, 1, chosenOrder);
                                                break;
                                            }
                                            case 2:
                                            {
                                                reserved = reserveSeats(B, rowSelected, seatSelected, adultTickets, childTickets,
                                                        seniorTickets, curUser, 2, chosenOrder);
                                                break;
                                            }
                                            case 3:
                                            {
                                                reserved = reserveSeats(C, rowSelected, seatSelected, adultTickets, childTickets,
                                                        seniorTickets, curUser, 3, chosenOrder);
                                                break;
                                            }
                                        }

                                        if (!reserved)
                                        {
                                            System.out.println("Those seats were taken");
                                        }

                                        updateOrders = false;

                                    } else if (userMenu == 2)     //delete
                                    {
                                        curUser.removeSeat(userInput,chosenOrder);
                                        updateOrders = false;

                                    } else if (userMenu == 3)     //cancel
                                    {
                                        for (int j = 0; j < chosenOrder.getSeats().size(); j++)
                                        {
                                            chosenOrder.getSeats().get(j).setType('.');
                                        }
                                        curUser.getOrders().remove(i);
                                        System.out.println("Order Cancelled!");
                                        updateOrders = false;
                                    }
                                }
                            }
                            else if (menuChoice == 4)       //display receipt
                            {
                                viewOrders(curUser, true);
                            }
                            else
                            {
                                // logout
                                customerMenu = false;
                            }

                        }   while (customerMenu);
                    }
                }
            }
            else
            {
                //incorrect username entered
                System.out.println("That user does not exist.");
            }

        } while (startingPoint);

        fileScanner.close();
        userInput.close();
    }

    //takes file input to insert into linked lists
    public static void fillAuditorium(Auditorium<Seat> A, ArrayList<String> contentArray, int numOfRows, int numOfSeats)
    {

        Node cur = A.getHead();
        Node leftSeat = cur;

        // fills auditorium with contents that were in the file
        for (int y = 0; y < numOfRows; y++)
        {
            String currentLine = contentArray.get(y);
            for (int x = 0; x < numOfSeats; x++)
            {
                cur.setData(new Seat(y, (char)(x + 65), currentLine.charAt(x)));
                cur =  cur.getNext();
            }

            cur =  leftSeat.getDown();
            leftSeat = cur;

        }

    }

    // Prints auditorium for user
    public static void displayAuditorium(int numOfRows, int numOfSeats, Auditorium<Seat> A)
    {
        Node cur = A.getHead();
        Node leftSeat = cur;

        //cur = A.getHead();

        System.out.print("  ");
        for (int x = 0; x < numOfSeats; x++)
        {
            System.out.print((char)(65 + x));
        }

        System.out.println();

        // Usual printing by y and x for loops to iterate through auditorium
        for (int y = 0; y < numOfRows; y++)
        {
            System.out.print((y + 1) + " ");

            for (int x = 0; x < numOfSeats; x++)
            {
                char currSeatStatus = ((Seat) cur.getData()).getType();

                if (currSeatStatus == '.')
                {
                    System.out.print(".");
                }
                else
                {
                    System.out.print("#");
                }

                cur = cur.getNext();
            }

            System.out.println();

            cur = leftSeat.getDown();
            leftSeat = cur;
        }

    }

    // reserves for user the selected seats
    public static boolean reserveSeats(Auditorium A, int rowSelected, int seatSelected, int adultTickets,
                                       int childTickets, int seniorTickets, user curUser, int auditorium, order existingOrder)
    {
        int totalTickets = adultTickets + childTickets + seniorTickets;

        //determine if seats are reservable
        boolean seatsAvailable = checkSeatAvailability(A, rowSelected, seatSelected, totalTickets);

        if (seatsAvailable)
        {

            Node cur = A.getHead();

            //Navigating the linked lists with cur pointer
            for (int y = 0; y < rowSelected; y++) {cur = cur.getDown();}

            for (int x = 0; x < seatSelected; x++) {cur = cur.getNext();}

            if (existingOrder == null) {
                curUser.getOrders().add(new order(auditorium));

                //reserving seats ACS order
                for (int x = 0; x < adultTickets; x++) {
                    ((Seat) cur.getData()).setType('A');
                    curUser.getOrders().get(curUser.getOrders().size() - 1).addSeat('A', (Seat) cur.getData());
                    cur = cur.getNext();
                }

                for (int x = 0; x < childTickets; x++) {
                    ((Seat) cur.getData()).setType('C');
                    curUser.getOrders().get(curUser.getOrders().size() - 1).addSeat('C', (Seat) cur.getData());
                    cur = cur.getNext();
                }

                for (int x = 0; x < seniorTickets; x++) {
                    ((Seat) cur.getData()).setType('S');
                    curUser.getOrders().get(curUser.getOrders().size() - 1).addSeat('S', (Seat) cur.getData());
                    cur = cur.getNext();
                }
            }
            else
            {
                if (adultTickets ==1)
                {
                    ((Seat) cur.getData()).setType('A');
                    existingOrder.addSeat('A',((Seat) cur.getData()));
                }
                else if (childTickets == 1)
                {
                    ((Seat) cur.getData()).setType('C');
                    existingOrder.addSeat('C',((Seat) cur.getData()));
                }
                else if (seniorTickets == 1)
                {
                    ((Seat) cur.getData()).setType('S');
                    existingOrder.addSeat('S',((Seat) cur.getData()));
                }
            }
        }
        return seatsAvailable;
    }

    // Checks every seat in order from leftmost seat
    public static boolean checkSeatAvailability(Auditorium<Seat> A, int rowSelected, int seatSelected, int totalTickets)
    {
        boolean available = true;
        Node cur = A.getHead();

        try
        {
            //Navigating the linked lists with cur pointer
            for (int y = 0; y < rowSelected; y++) {cur = cur.getDown();}

            for (int y = 0; y < seatSelected ; y++) {cur = cur.getNext();}

            //checking type held in seat
            for (int x = 0; x < totalTickets; x++) {
                if (((Seat) cur.getData()).getType() != '.') {
                    available = false;
                }

                if (x < totalTickets - 1)
                {

                    cur = cur.getNext();
                }
            }
        }
        catch (NullPointerException e) //catches if null accessed
        {
            available = false;
        }

        return available;
    }

    // Calculates best seats to center of auditorium
    public static void bestAvailable(Auditorium<Seat> A, int adultTickets, int childTickets, int seniorTickets,
                                     int numOfRows, int numOfSeats, Scanner userInput, user curUser, int auditorium)
    {
        double currentBest = -1, newDistance;
        int bestRow = 0, bestSeat = 0;
        int totalTickets = adultTickets + childTickets + seniorTickets;

        Node<Seat> cur = A.getHead();
        Node<Seat> leftSeat = cur;

        //navigating down
        for (int y = 0; y < numOfRows; y++)
        {

            //navigating right
            for (int x = 0; x < numOfSeats; x++)
            {
                //check availability
                boolean seatsAvailable = checkSeatAvailability(A, y, x, totalTickets);

                if (seatsAvailable)
                {

                    //finding distance between seats and center
                    newDistance = getDistance(A, y, x, totalTickets, numOfSeats, numOfRows);

                    //If better, replace best
                    if (newDistance < currentBest || currentBest == -1)
                    {
                        currentBest = newDistance;
                        bestRow = y;
                        bestSeat = x;

                    }
                    else if (newDistance == currentBest) //else if for tie in distance
                    {
                        double newDistMiddleRow = Math.abs(((numOfRows + 1)/2.0) - y - 1);
                        double bestDistMiddleRow = Math.abs(((numOfRows + 1)/2.0) - bestRow - 1);

                        if (newDistMiddleRow < bestDistMiddleRow)
                        {
                            currentBest = newDistance;
                            bestRow = y;
                            bestSeat = x;

                        }

                    }

                }

                cur = cur.getNext();
            }

            cur = leftSeat.getDown();
            leftSeat = cur;
        }

        // checks to see if it was replaced from original value
        if (currentBest != -1)
        {

            //offer best available
            System.out.println("Would you rather have the seats " + (bestRow + 1) + (char)(bestSeat + 65) + " - "
                    + (bestRow + 1) + (char)(bestSeat + 65 + totalTickets - 1) + "?");
            System.out.println("Enter Y or N to choose.");
            char bestTaken = userInput.nextLine().charAt(0);

            //User has chosen best seats in house to reserve
            if (bestTaken == 'Y' || bestTaken == 'y') {
                reserveSeats(A, bestRow, bestSeat, adultTickets, childTickets, seniorTickets, curUser, auditorium, null);
            }
        }
        else
        {
            // Best seat denied
            System.out.println("no seats available");
        }

    }

    // Calculates distance from center
    public static double getDistance(Auditorium<Seat> A, int rowSelected, int seatSelected, int tickets, int numOfSeats, int numOfRows)
    {

        Node cur = A.getHead();

        // Navigating to first seat
        for (int y = 0; y < rowSelected; y++) {cur = cur.getDown();}

        for (int x = 0; x < seatSelected; x++) {cur = cur.getNext();}


        // Hypotenuse length calculation
        double x = ((numOfSeats + 1)/2.0) - ((((Seat)cur.getData()).getSeat() - 64) + ((tickets - 1)/2.0));

        double y = ((numOfRows + 1)/2.0) - (((Seat)cur.getData()).getRow() + 1);

        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));

    }

    // Takes care of writing to file
    public static void saveToFile(Auditorium<Seat> A, String fileName)
    {
        char currSeat;
        PrintWriter fileOutput = null;

        // Tries to create file
        try {
            fileOutput = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Pointers for navigating
        Node cur = A.getHead();
        Node leftSeat = cur;

        while (leftSeat != null)
        {
            // finding current seat type
            while (cur != null)
            {
                if (((Seat)cur.getData()).getType() == 'A')     // Adult Ticket
                {
                    currSeat = 'A';
                }
                else if (((Seat)cur.getData()).getType() == 'C')        //Child Ticket
                {
                    currSeat = 'C';
                }
                else if (((Seat)cur.getData()).getType() == 'S')        //Senior Ticket
                {
                    currSeat = 'S';
                }else
                {
                    currSeat = '.';
                }

                // File output per seat
                if (fileOutput != null) {
                    fileOutput.print(currSeat);
                }

                cur = cur.getNext();
            }

            if (fileOutput != null) {
                fileOutput.println();
            }

            // Moving to next row
            cur = leftSeat.getDown();
            leftSeat = cur;
        }


        if (fileOutput != null) {
            fileOutput.close();
        }
    }

    //prints off the report for tickets and sales
    public static void printReport(Auditorium<Seat> A, Auditorium<Seat> B, Auditorium<Seat> C)
    {
        int adultTickets = 0, childTickets = 0, seniorTickets = 0, totalSeats = 0, totalTickets = 0;
        int allOpenSeats = 0, allReserved = 0, allAdult = 0, allChild = 0, allSenior = 0;
        float curSales, totalSales = 0;

        // Pointers for navigating
        Node cur = A.getHead();
        Node leftSeat = cur;

        //tallying auditorium up
        while (leftSeat != null)
        {

            // Tallying sales
            while (cur != null)
            {
                totalSeats++;

                if (((Seat)cur.getData()).getType() == 'A')     // Adult Ticket
                {
                    adultTickets++;
                    totalTickets++;
                }
                else if (((Seat)cur.getData()).getType() == 'C')        //Child Ticket
                {
                    childTickets++;
                    totalTickets++;
                }
                else if (((Seat)cur.getData()).getType() == 'S')        //Senior Ticket
                {
                    seniorTickets++;
                    totalTickets++;
                }

                cur = cur.getNext();
            }

            // Moving to next row
            cur = leftSeat.getDown();
            leftSeat = cur;
        }

        //Calculating Money
        curSales = (float) (adultTickets * 10.0 + childTickets * 5.0 + seniorTickets * 7.50);

        //TODO mess with formatting
        // Sales to User
        System.out.print("Auditorium 1\t" + (totalSeats - totalTickets) + "\t" + totalTickets + "\t" + adultTickets
                + "\t" + childTickets + "\t" + seniorTickets);
        System.out.printf("\t$%.2f\n", curSales);

        //adding up total
        allOpenSeats += (totalSeats - totalTickets);
        allReserved += totalTickets;
        allAdult += adultTickets;
        allChild += childTickets;
        allSenior += seniorTickets;
        totalSales += curSales;

        //setting all back to 0 for next auditorium
        adultTickets = 0;
        childTickets = 0;
        seniorTickets = 0;
        totalSeats = 0;
        totalTickets = 0;


        // Pointers for navigating
        cur = B.getHead();
        leftSeat = cur;

        //tallying auditorium up
        while (leftSeat != null)
        {

            // Tallying sales
            while (cur != null)
            {
                totalSeats++;

                if (((Seat)cur.getData()).getType() == 'A')     // Adult Ticket
                {
                    adultTickets++;
                    totalTickets++;
                }
                else if (((Seat)cur.getData()).getType() == 'C')        //Child Ticket
                {
                    childTickets++;
                    totalTickets++;
                }
                else if (((Seat)cur.getData()).getType() == 'S')        //Senior Ticket
                {
                    seniorTickets++;
                    totalTickets++;
                }

                cur = cur.getNext();
            }

            // Moving to next row
            cur = leftSeat.getDown();
            leftSeat = cur;
        }

        //Calculating Money
        curSales = (float) (adultTickets * 10.0 + childTickets * 5.0 + seniorTickets * 7.50);

        // Sales to User
        System.out.print("Auditorium 2\t" + (totalSeats - totalTickets) + "\t" + totalTickets + "\t" + adultTickets
                + "\t" + childTickets + "\t" + seniorTickets);
        System.out.printf("\t$%.2f\n", curSales);

        //adding up total
        allOpenSeats += (totalSeats - totalTickets);
        allReserved += totalTickets;
        allAdult += adultTickets;
        allChild += childTickets;
        allSenior += seniorTickets;
        totalSales += curSales;

        // setting all back to 0 for next
        adultTickets = 0;
        childTickets = 0;
        seniorTickets = 0;
        totalSeats = 0;
        totalTickets = 0;


        // Pointers for navigating
        cur = C.getHead();
        leftSeat = cur;

        //tallying auditorium up
        while (leftSeat != null)
        {

            // Tallying sales
            while (cur != null)
            {
                totalSeats++;

                if (((Seat)cur.getData()).getType() == 'A')     // Adult Ticket
                {
                    adultTickets++;
                    totalTickets++;
                }
                else if (((Seat)cur.getData()).getType() == 'C')        //Child Ticket
                {
                    childTickets++;
                    totalTickets++;
                }
                else if (((Seat)cur.getData()).getType() == 'S')        //Senior Ticket
                {
                    seniorTickets++;
                    totalTickets++;
                }

                cur = cur.getNext();
            }

            // Moving to next row
            cur = leftSeat.getDown();
            leftSeat = cur;
        }

        //Calculating Money
        curSales = (float) (adultTickets * 10.0 + childTickets * 5.0 + seniorTickets * 7.50);

        // Sales to User
        System.out.print("Auditorium 3\t" + (totalSeats - totalTickets) + "\t" + totalTickets + "\t" + adultTickets
                + "\t" + childTickets + "\t" + seniorTickets);
        System.out.printf("\t$%.2f\n", curSales);

        //adding up total
        allOpenSeats += (totalSeats - totalTickets);
        allReserved += totalTickets;
        allAdult += adultTickets;
        allChild += childTickets;
        allSenior += seniorTickets;
        totalSales += curSales;


        //Printing overall summary
        System.out.println("Total\t" + allOpenSeats + "\t" + allReserved + "\t" + allAdult
                + "\t" + allChild + "\t" + allSenior);
        System.out.printf("Total Sales between all auditoriums:    $%.2f\n", totalSales);

    }

    // fills the hashmap from file
    public static HashMap<String, user> fillHashmap(HashMap<String, user> users)
    {
        BufferedReader brr = null;
        try
        {
            //reading from file
            File userFile = new File("userdb.dat");
            FileReader reader = new FileReader(userFile);
            brr = new BufferedReader(reader);

            String line;

            while ((line = brr.readLine()) != null)
            {
                String[] userData = line.split("\\s+");
                user newUser = new user(userData[0], userData[1]);
                users.put(newUser.getUsername(), newUser);
            }

            brr.close();
            reader.close();
        }
        catch (Exception E)
        {
            System.out.println(E);
        }


        return users;
    }

    public static void viewOrders(user curUser, boolean displayMoney)
    {
        float orderCost = 0, totalCost = 0;

        if (curUser.getOrders() == null)
        {
            System.out.println("No orders\n");
        }
        else
        {
            for (int i = 0; i < curUser.getOrders().size(); i++)        //each order
            {
                System.out.println("Order " + (i+1) + ": ");
                order curOrder = curUser.getOrders().get(i);        //making it easier to read
                System.out.print("Auditorium " + curOrder.getAuditorium() + ", ");

                for (int j = 0; j < curOrder.getSeats().size(); j++)      //seats per order
                {
                    Seat curSeat = curOrder.getSeats().get(j);        //making it easier to read

                    System.out.print(curSeat.getRow() + 1 + "" + (char)(curSeat.getSeat()));
                    if (j != curOrder.getSeats().size() - 1)
                    {
                        System.out.print(", ");
                    }

                }

                // output tickets
                System.out.print("\n" + curOrder.getAdultTickets() + " adult, " + curOrder.getChildTickets()
                        + " child, " + curOrder.getSeniorTickets() + " senior\n");

                // displays for admin when boolean is true
                if (displayMoney)
                {
                    orderCost = curOrder.getAdultTickets()*10f + curOrder.getChildTickets()*5f + curOrder.getSeniorTickets()*7.5f;
                    totalCost += orderCost;

                    System.out.print("Order Total: $");
                    System.out.printf("%.2f\n", orderCost);
                }

            }

            if (curUser.getOrders().size() == 0)
            {
                System.out.println("No orders to display.");
            }

            // displays for admin when boolean is true
            if (displayMoney)
            {

                System.out.print("\nCustomer Total: $");
                System.out.printf("%.2f\n", totalCost);
            }
        }

    }

}

// stores data for each user account, stored in hashmap
class user
{
    private String username;
    private String password;
    private ArrayList<order> orders;

    public user(String username, String password) {
        this.username = username;
        this.password = password;
        orders = new ArrayList<>(1);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<order> getOrders() {
        return orders;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOrders(ArrayList<order> orders) {
        this.orders = orders;
    }

    // Removes specifically selected seat from users selected order
    public void removeSeat(Scanner userInput, order chosenOrder)
    {
        int l, menuChoice = 0;
        boolean validInput;
        System.out.print("Please enter the number of which seat you want to remove.\nInput 0 to exit.\n");

        //seat prompt
        for (l = 0; l < chosenOrder.getSeats().size(); l++)
        {

            System.out.print((l+1) + ".");
            System.out.print(chosenOrder.getSeats().get(l).getRow()+1);
            System.out.print(chosenOrder.getSeats().get(l).getSeat());
            System.out.print("  ");
        }

        do {
            validInput = true;

            try {
                String menuString = userInput.nextLine();
                menuChoice = Integer.parseInt(menuString);

                if (menuChoice < 0 || menuChoice > l) {
                    throw new Exception();
                }

            } catch (Exception e) {
                validInput = false;
                System.out.println("Invalid Input.");
            }
        } while (!validInput);

        // remove order selected
        if (menuChoice > 0)
        {
            char n = chosenOrder.getSeats().get((menuChoice -1)).getType();
            switch (n)
            {
                case 'A': {
                    chosenOrder.setAdultTickets((chosenOrder.getAdultTickets() - 1));
                    break;
                }
                case 'C': {
                    chosenOrder.setChildTickets((chosenOrder.getChildTickets() - 1));
                    break;
                }
                case 'S': {
                    chosenOrder.setSeniorTickets((chosenOrder.getSeniorTickets() - 1));
                    break;
                }
            }
            chosenOrder.getSeats().get((menuChoice -1)).setType('.');
            chosenOrder.getSeats().remove((menuChoice -1));
        }
    }
}

//stores data on each order, stored in user object
class order
{
    private ArrayList<Seat> seats;
    private int adultTickets;
    private int childTickets;
    private int seniorTickets;
    private int auditorium;

    public order(int auditorium) {
        seats = new ArrayList<>(1);
        this.adultTickets = 0;
        this.childTickets = 0;
        this.seniorTickets = 0;
        this.auditorium = auditorium;
    }

    public order(ArrayList<Seat> seats, int adultTickets, int childTickets, int seniorTickets) {
        this.seats = seats;
        this.adultTickets = adultTickets;
        this.childTickets = childTickets;
        this.seniorTickets = seniorTickets;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public int getAdultTickets() {
        return adultTickets;
    }

    public int getChildTickets() {
        return childTickets;
    }

    public int getSeniorTickets() {
        return seniorTickets;
    }

    public int getAuditorium() {
        return auditorium;
    }


    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public void setAdultTickets(int adultTickets) {
        this.adultTickets = adultTickets;
    }

    public void setChildTickets(int childTickets) {
        this.childTickets = childTickets;
    }

    public void setSeniorTickets(int seniorTickets) {
        this.seniorTickets = seniorTickets;
    }

    public void addSeat(char type, Seat curSeat)
    {
        switch (type)
        {
            case 'A':
            {
                adultTickets++;
                break;
            }
            case 'C':
            {
                childTickets++;
                break;
            }
            case 'S':
            {
                seniorTickets++;
                break;
            }
        }

        seats.add(curSeat);
    }


}
