/**
    Derek Ma & Kevin Zhan
    January 11, 2022
    Ms. Basaraba
    This is a program written to function as a Jeopardy game. This game was written for our ICS3UP ISP.
    To create this program we used 4 additional custom classes. In this program, we applied many of the 
    concepts that we learned in the course such as variables, arrays, animations, graphics, user input, 
    file manipulation, and threads. We also used many different structures such as for loops, while loops, 
    try-catch blocks, switch blocks, and if statements.
**/

import java.awt.*;
import hsa.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class JeopardyGame {
    private static Console c; //the main console of the game
    static int databaseSize; //the size of the total category database
    static Category catDatabase; //an object of the Category class that stores all categories
    static Category gameCategory; //an object of the Category class that stores categories used for each game
    static int categoryNum[]; //an array that stores the randomized categories
    static JeopardyBoard mainBoard; //an object of the JeopardyBoard class that is responsible for displaying the board
    static Leaderboard highScores; //an object of the Leaderboard class that stores high scores
    static int whoseTurn; //an int variable that tracks which player's turn it is
    static Color backgroundColor;
    static String playerNames[]; //an array that tracks the players' names
    static int playerPoints[]; //an array that tracks the players' points
    static Clock timer; //an object of the Clock class that functions as a countdown timer
    static int questionRow; //an int variable for the row of the question the player wants to answer
    static int questionColumn; //an int variable for the column of the question the player wants to answer
    static int answerTime[]; //an array that stores the time each player answers their third round question in

    public JeopardyGame(int size) {
        //initializes console
        c = new Console(33, 100, "Jeopardy!");
        databaseSize = size;
        //initializes an object of the Category class with the size of the database
        catDatabase = new Category(databaseSize);
        //initializes an object of the Category class with a size of 12
        gameCategory = new Category(12);
        categoryNum = new int[databaseSize];
        //initializes the object of the JeopardyBoard class
        mainBoard = new JeopardyBoard(c);
        //initializes the object of the Leaderboard class
        highScores = new Leaderboard(c);
        backgroundColor = new Color(7, 55, 99);
        //the following arrays are initialized with a size of 2 for each player
        playerNames = new String[2];
        playerPoints = new int[2];
        answerTime = new int[2];
    }//class constructor

    //method that runs when the game starts and performs necessary tasks for the game to run smoothly
    private static void gameStart() throws IOException {
        //fills the category database
        for (int i = 0; i < databaseSize; i++) {
            catDatabase.fill(i, i);
        }
        splashScreen(); //calls the splashScreen method
        mainMenu(); //calls the mainMenu method once the splashScreen is finished
    }//ends gameStart method
    
    //method that prints the title
    private static void title() {
        //following arrays store coordinates for a polygon
        int xArr[] = {631, 637, 652, 658};
        int yArr[] = {45, 95, 95, 40};
        c.setColor(backgroundColor);
        c.fillRect(10, 10, 790, 650);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 90));
        c.setColor(new Color(248, 236, 208));
        //prints the title
        c.drawString("Jeopardy", 175, 110);
        //prints the dot of the exclamation mark
        c.fillOval(635, 105, 20, 20);
        //prints the body of the exclamation mark as a polygon
        c.fillPolygon(xArr, yArr, 4);
    }//ends title method
    
    //method that displays an animated splashScreen
    private static void splashScreen() {
        int increase = 0;
        title(); //calls the title method
        
        //animates the circle shrinking into a day
        for (int i = 0; i < 200; i++) {
            c.fillOval(-210 + i * 3, -300 + i * 3, 1200 - 6 * i, 1200 - 6 * i);
            //uses a try-catch block and a Thread.sleep to pause for 5 milliseconds
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            c.setColor(Color.white);
            c.fillRect(0, 0, 10, 660);
            c.fillRect(0, 0, 800, 10);
            c.setColor(backgroundColor);
            c.fillOval(-210 + i * 3, -300 + i * 3, 1200 - 6 * i, 1200 - 6 * i);
            c.setColor(new Color(248, 236, 208));
        }
        c.fillOval(380, 290, 20, 20);
        //uses a try-catch block and a Thread.sleep to pause for half a second
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        c.setColor(new Color(248, 236, 208));
        
        //animates the dot bouncing
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 20; i++) {
                increase += i;
                c.fillOval(380, 290 + increase, 20, 20);
                //uses a try-catch block and a Thread.sleep to pause for 30 milliseconds
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
                c.setColor(backgroundColor);
                c.fillOval(380, 290 + increase, 20, 20);
                c.setColor(new Color(248, 236, 208));
            }

            for (int i = 20; i > 0; i--) {
                increase -= i;
                c.fillOval(380, 290 + increase, 20, 20);
                //uses a try-catch block and a Thread.sleep to pause for 30 milliseconds
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
                c.setColor(backgroundColor);
                c.fillOval(380, 290 + increase, 20, 20);
                c.setColor(new Color(248, 236, 208));
            }
        }

        //moves the dot from (380,290) to (635,105) or (255,-185)
        //moves the dot diagonally
        for (int i = 0; i < 31; i++) {
            c.fillOval(380 + i * 5, 250 - i * 5, 20, 20);
            //uses a try-catch block and a Thread.sleep to pause for 30 milliseconds
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
            }    
            c.setColor(backgroundColor);
            c.fillOval(380 + i * 5, 250 - i * 5, 20, 20);
            c.setColor(new Color(248, 236, 208));
        }
        //moves the dot horizontally
        for (int i = 0; i < 14; i++) {
            c.fillOval(565 + i * 5, 105, 20, 20);
            //uses a try-catch block and a Thread.sleep to pause for 30 milliseconds
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
            }
            c.setColor(backgroundColor);
            c.fillOval(565 + i * 5, 105, 20, 20);
            c.setColor(new Color(248, 236, 208));
        }
        c.fillOval(635, 105, 20, 20);

        //animates the exclaimation mark moving down
        for (int i = 0; i < 20; i++) {
            int xArr[] = {631, 637, 652, 658};
            int yArr[] = {-55 + i * 5, -5 + i * 5, -5 + i * 5, -60 + i * 5};
            c.fillPolygon(xArr, yArr, 4);
            //uses a try-catch block and a Thread.sleep to pause for 30 milliseconds
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
            }
            c.setColor(Color.white);
            c.fillRect(0, 0, 10, 660);
            c.fillRect(0, 0, 800, 10);
            c.setColor(backgroundColor);
            c.fillPolygon(xArr, yArr, 4);
            c.setColor(new Color(248, 236, 208));
        }
        int xArr[] = {631, 637, 652, 658};
        int yArr[] = {45, 95, 95, 40};
        c.fillPolygon(xArr, yArr, 4);

        //animates the title sliding in
        for (int i = 0; i < 100; i++) {
            c.drawString("Jeopardy", -325 + i * 5, 110);
            //uses a try-catch block and a Thread.sleep to pause for 10 milliseconds
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            c.setColor(Color.white);
            c.fillRect(0, 0, 10, 660);
            c.fillRect(0, 0, 800, 10);
            c.setColor(backgroundColor);
            c.drawString("Jeopardy", -325 + i * 5, 110);
            c.setColor(new Color(248, 236, 208));
        }
    }//ends splashScreen method
    
    //method that displays the main menu
    private static void mainMenu() throws IOException {
        title(); //calls the title method
        c.drawString("Main Menu", 170, 200);
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 30));
        //prompts the user to enter a key
        c.drawString("Press <Enter> to start a game of", 80, 300);
        c.drawString("Jeopardy!", 80, 340);
        c.drawString("Press <L> to view the leaderboard.", 80, 400);
        c.drawString("Press <I> to view the instructions.", 80, 450);
        c.drawString("Press <Backspace> to exit Jeopardy.", 80, 500);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 90));
        pauseProgram(0); //calls the pauseProgram method so the user can enter a key
    }//ends mainMenu method

    //method that displays the instructions
    private static void instructions() throws IOException {
        title(); //calls the title method
        c.drawString("Instructions", 100, 200); //prints the title "Instructions"
        c.setFont(new Font("Serif", Font.PLAIN, 25));
        //the following print the instructions and rules of the game
        c.drawString("In this version of Jeopardy, there are two players. Each player starts", 60, 250);
        c.drawString("out with zero points. In the first round, there will be 5", 120, 275);
        c.drawString("categories of 5 questions each, in order of increasing", 120, 300);
        c.drawString("difficulty. The points value of the questions", 150, 325);
        c.drawString("in each category include 100,200,300,400, and 500.", 120, 350);
        c.drawString("Player 1 goes first. They will pick a question in the category", 100, 375);
        c.drawString("of their choosing. Then, they are given the question.", 140, 400);
        c.drawString("PLAYERS MUST ANSWER THE QUESTIONS IN QUESTION FORM.", 40, 425);
        c.drawString("Players are awarded the points if they answer the question right.", 60, 450);
        c.drawString("The players alternate picking and answering questions until no", 80, 500);
        c.drawString("questions are left unanswered. The second round begins, where", 75, 525);
        c.drawString("the value of all the points awards are doubled, and new", 130, 550);
        c.drawString("categories are provided.", 270, 575);
        c.drawString("The person who gets as many points as possible after two", 100, 600);
        c.drawString("rounds is the winner.", 280, 625);
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 18));
        //prompts the user to enter a key
        c.drawString("Press any key to go back to main menu", 20, 650);
        pauseProgram(1); //calls the pauseProgram method so the user can enter a key
    }//ends instructions method

    private static void leaderboard() throws IOException {
        title(); //calls title method
        //calls the method from the Leaderboard class that displays the leaderboard
        highScores.printLeaderboard();
        c.setColor(new Color(248, 236, 208));
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 18));
        //prompts the user to enter a key
        c.drawString("Press <C> to clear the leaderboard", 20, 620);
        c.drawString("Press any other key to go back to main menu", 20, 650);
        pauseProgram(2); //calls the pauseProgram method so the user can enter a key
    }//ends leaderboard method

    //the method that creates a goodbye page and then closes the program
    private static void goodbye() throws IOException {
        title (); //calls the title method
        c.setFont (new Font ("MonoSpaced", Font.PLAIN, 30));
        //displays messages for the goodbye page
        c.drawString ("This program was created ", 170, 350);
        c.drawString("by Kevin Zhan and Derek Ma.",160,390);
        c.drawString ("Thank you for playing Jeopardy!", 120, 430);
        //try-catch block to pause the program
        try {
            //pauses for 4 seconds
            Thread.sleep (4000);
        } catch (InterruptedException e) {
        }
        //exits the program
        System.exit(0);
    }//ends goodbye method

    //method that takes user input for the player's names
    private static void enterNames() throws IOException {
        title(); //calls title method
        c.setTextBackgroundColor(backgroundColor);
        c.setTextColor(Color.white);
        //loops until Player 1's name is entered
        while (true) {
            c.setColor(backgroundColor);
            c.fillRect(190, 280, 600, 20);
            c.setCursor(15, 25);
            //prompts user to enter a name
            c.print("Player 1, please enter your display name: ");
            //takes input for Player 1's name
            playerNames[0] = c.readLine();
            //if the entered name is of the right length
            if (playerNames[0].length() <= 12) {
                c.fillRect(190, 280, 600, 20);
                c.setCursor(15, 25);
                //breaks out of the loop
                break;
            } else { //if the name is too long
                new Message("Please keep your name under 12 characters."); //instructs player to shorten name
            }
        }
        //prints Player 1's name
        c.print("Player 1, your name is: " + playerNames[0]);
        //loops until Player 2's name is entered
        while (true) {
            c.fillRect(190, 320, 600, 20);
            c.setCursor(17, 25);
            //prompts user to enter a name
            c.print("Player 2, please enter your display name: ");
            //takes input for Player 2's name
            playerNames[1] = c.readLine();
            //if the entered name is of the right length and different from Player 1's
            if (playerNames[1].length() <= 12 && !playerNames[1].equals(playerNames[0])) {
                c.fillRect(190, 320, 600, 20);
                c.setCursor(17, 25);
                //breaks out of the loop
                break;
            } else if (!playerNames[1].equals(playerNames[0])) { //if the name is too long
                new Message("Please keep your name under 12 characters."); //instructs player to shorten name
            } else { //if it is the same name as Player 1
                new Message("The name " + playerNames[0] + " is already taken."); //instructs player to choose a new name
            }
        }
        //prints Player 2's name
        c.print("Player 2, your name is: " + playerNames[1]);
        //prompts user to enter a key
        c.setColor(new Color(248, 236, 208));
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 18));
        c.drawString("Press any key to continue", 20, 650);
        pauseProgram(3); //calls the pauseProgram method so the user can enter a key
    }//ends enterNames method

    //method that runs a new game
    private static void newGame() throws IOException {
        enterNames(); //calls the enterNames method so player's can enter their name
        c.setTextBackgroundColor(Color.white);
        c.setTextColor(Color.black);
        //chooses the categories for the game
        chooseCategories();
        //initializes the timer
        timer = new Clock();
        title(); //calls the title method
        c.setFont(new Font("MonoSpaced", Font.BOLD, 80));
        //prints Round 1 page
        c.drawString("Round 1", 230, 350);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        //prompts user to enter a key
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3); //calls the pauseProgram method so the user can enter a key
        //loops until the round 1 board is completely answered
        while (mainBoard.incomplete(1)) {
            title(); //calls the title method
            mainBoard.drawBoard(1, gameCategory.categoryName); //displays the board
            c.setFont(new Font("Monospaced", Font.BOLD, 15));
            c.setColor(Color.white);
            //displays players points
            c.drawString(playerNames[0] + ": $" + playerPoints[0], 140, 145);
            c.drawString(playerNames[1] + ": $" + playerPoints[1], 140, 165);
            //calls the selectQuestion method so the player can choose a question
            selectQuestion();
            //calls the runQuestion method so the player can answer the selected question
            runQuestion(1);
        }
        title(); //calls the title method
        c.setFont(new Font("MonoSpaced", Font.BOLD, 80));
        //prints Round 2 page
        c.drawString("Round 2", 230, 350);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        //prompts user to enter a key
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3); //calls the pauseProgram method so the user can enter a key
        //loops until the round 2 board is completely answered
        while (mainBoard.incomplete(2)) {
            title(); //calls the title method
            mainBoard.drawBoard(2, gameCategory.categoryName); //displays the board
            c.setFont(new Font("Monospaced", Font.BOLD, 15));
            c.setColor(Color.white);
            //displays players points
            c.drawString(playerNames[0] + ": $" + playerPoints[0], 140, 145);
            c.drawString(playerNames[1] + ": $" + playerPoints[1], 140, 165);
            //calls the selectQuestion method so the player can choose a question
            selectQuestion();
            //calls the runQuestion method so the player can answer the selected question
            runQuestion(2);
        }
        //resets all answered questions on the board
        mainBoard.resetAnswered();
        title(); //calls the title method
        c.setFont(new Font("MonoSpaced", Font.BOLD, 80));
        //prints Round 3 page
        c.drawString("Round 3", 230, 350);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        //explains Round 3 to the player
        c.drawString("Each player will receive a question.", 125, 400);
        c.drawString("Whoever answers in the least amount", 127, 440);
        c.drawString("of time will receive 1000 points.", 145, 480);
        //prompts user to enter a key
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3); //calls the pauseProgram method so the user can enter a key
        //runs a question for a player
        runQuestion(3);
        //runs a question for the next player
        runQuestion(3);
        title(); //calls title method
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        //compares the player's answer times for round 3 questions
        if(answerTime[0] < answerTime[1]) { //if Player 1 is faster
            //prints a message
            c.drawString(playerNames[0] + " was faster", 175, 400);
            c.drawString("and will receive 1000 points.", 175, 430);
            //adds 1000 points to Player 1's score
            playerPoints[0] += 1000;
        } else if(answerTime[1] < answerTime[0]) { //if Player 2 is faster
            //prints a message
            c.drawString(playerNames[1] + " was faster", 175, 400);
            c.drawString("and will receive 1000 points.", 175, 430);
            //adds 1000 points to Player 2's score
            playerPoints[1] += 1000;
        } else if(answerTime[0] == answerTime[1]) { //if both players tied
            //prints a message
            c.drawString("Both players were equally fast", 175, 400);
            c.drawString("so neither will receive points.", 175, 430);
        }
        //prompts user to enter a key
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3); //calls the pauseProgram method so the user can enter a key
        //Winning screen
        //if statement to check who has a higher score
        if(playerPoints[0] > playerPoints[1]) {
            c.setColor(backgroundColor);
            c.fillRect(10, 10, 790, 650);
            c.setColor(new Color(248, 236, 208));
            c.fillRoundRect(100,30,600,180, 35, 35);
            c.setColor(backgroundColor);
            c.fillRoundRect(120,50,560,140, 25, 25);
            c.setColor(new Color(248, 236, 208));
            c.setFont(new Font("MonoSpaced", Font.BOLD, 28));
            c.drawString("Congratulations, " + playerNames[0] + "!", 140, 80);
            c.drawString("You won this game of Jeopardy", 140, 118);
            c.drawString("with <" + playerPoints[0] + "> points!", 140, 156);
            //updates leaderboard with the winner's name and score
            highScores.updateLeaderboard(playerNames[0], playerPoints[0]);
        } else if(playerPoints[0] < playerPoints[1]) {
            c.setColor(backgroundColor);
            c.fillRect(10, 10, 790, 650);
            c.setColor(new Color(248, 236, 208));
            c.fillRoundRect(100,30,600,180, 35, 35);
            c.setColor(backgroundColor);
            c.fillRoundRect(120,50,560,140, 25, 25);
            c.setColor(new Color(248, 236, 208));
            c.setFont(new Font("MonoSpaced", Font.BOLD, 28));
            c.drawString("Congratulations, " + playerNames[1] + "!", 140, 80);
            c.drawString("You won this game of Jeopardy", 140, 118);
            c.drawString("with <" + playerPoints[1] + "> points!", 140, 156);
            //updates leaderboard with the winner's name and score
            highScores.updateLeaderboard(playerNames[1], playerPoints[1]);
        } else if(playerPoints[0] == playerPoints[1]) {
            c.setColor(backgroundColor);
            c.fillRect(10, 10, 790, 650);
            c.setColor(new Color(248, 236, 208));
            c.fillRoundRect(100,30,600,180, 35, 35);
            c.setColor(backgroundColor);
            c.fillRoundRect(120,50,560,140, 25, 25);
            c.setColor(new Color(248, 236, 208));
            c.setFont(new Font("MonoSpaced", Font.BOLD, 28));
            c.drawString(playerNames[0] + " & " + playerNames[1] + "!", 140, 80);
            c.drawString("You tied this game of Jeopardy", 140, 118);
            c.drawString("with <" + playerPoints[1] + "> points!", 140, 156);
            //updates leaderboard with both player's names and scores
            highScores.updateLeaderboard(playerNames[0], playerPoints[0]);             
            highScores.updateLeaderboard(playerNames[1], playerPoints[1]);
        } 
        c.drawString("Thank you for playing Jeopardy. You", 100, 300);
        c.drawString("may continue to the main menu,", 130, 340);
        c.drawString("where you may see your score on", 120, 380);
        c.drawString("the leaderboard.", 250, 420);
        c.setColor(new Color(248, 236, 208));
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 18));
        c.drawString("Press any key to continue", 20, 650);
        pauseProgram(1);
    }//ends newGame method

    //method that takes user input for which question the player wants to answer
    private static void selectQuestion() {
        c.setColor(Color.white);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 17));
        //prompts user to enter a column letter
        c.drawString(playerNames[whoseTurn] + ", enter the column for the question you wish to answer:", 20, 600);
        //loops until the player enters a valid column letter
        while (true) {
            char input = c.getChar(); //takes input for a character
            if ((input + "").toUpperCase().equals("A")) { //checks if the character is A
                questionColumn = 1; //selects column 1
                c.setCursor(32, 3);
                //displays the selected column
                c.print((input + "").toUpperCase());
                //decrements the column number to get the column array index
                questionColumn--;
                //breaks out of the loop
                break;
            } else if ((input + "").toUpperCase().equals("B")) { //checks if the character is B
                questionColumn = 2; //selects column 2
                c.setCursor(32, 3);
                //displays the selected column
                c.print((input + "").toUpperCase()); 
                //decrements the column number to get the column array index
                questionColumn--;
                //breaks out of the loop
                break;
            } else if ((input + "").toUpperCase().equals("C")) { //checks if the character is C
                questionColumn = 3; //selects column 3
                c.setCursor(32, 3);
                //displays the selected column
                c.print((input + "").toUpperCase());
                //decrements the column number to get the column array index
                questionColumn--;
                //breaks out of the loop
                break;
            } else if ((input + "").toUpperCase().equals("D")) { //checks if the character is D
                questionColumn = 4; //selects column 4
                c.setCursor(32, 3);
                //displays the selected column
                c.print((input + "").toUpperCase());
                //decrements the column number to get the column array index
                questionColumn--;
                //breaks out of the loop
                break;
            } else if ((input + "").toUpperCase().equals("E")) { //checks if the character is E
                questionColumn = 5; //selects column 5
                c.setCursor(32, 3);
                //displays the selected column
                c.print((input + "").toUpperCase());
                //decrements the column number to get the column array index
                questionColumn--;
                //breaks out of the loop
                break;
            }
        }
        c.setColor(backgroundColor);
        c.fillRect(20, 580, 1000, 25);
        c.setColor(Color.white);
        //prompts user to enter a row number
        c.drawString(playerNames[whoseTurn] + ", enter the row for the question you wish to answer:", 20, 600);
        c.setCursor(0, 2);
        //loops until the player enters a valid row number
        while (true) {
            //uses a try-catch block to error trap numeric input of the row number
            try {
                //takes input for row number as char, converts to string, parses to int, and subtracts 1 to get row array index
                questionRow = Integer.parseInt(c.getChar() + "") - 1;
                //checks if the number is between 0 and 4
                if (questionRow >= 0 && questionRow < 5) {
                    c.setCursor(32, 5);
                    //displays the selected row by printing one plus the row array index
                    c.print(questionRow + 1); 
                    //breaks out of the loop
                    break;
                } else { // if the number is bigger or smaller than the available rows
                    new Message("Please enter a valid column number.", "Error"); //gives error message
                }
            } catch (NumberFormatException e) { // if the inputted character is not a valid number
                new Message("Please enter a valid column number.", "Error"); //gives error message
            }
        }
        //uses a try-catch block and a Thread.sleep to pause for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }//ends selectQuestion method

    //method that displays a question and allows the player to answer it
    private static void runQuestion(int level) throws IOException {
        //checks which round it is
        if (level == 3) { //round 3
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            title(); //calls the title method
            //displays the question
            c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
            c.drawString(gameCategory.questions[4][10 + whoseTurn], 40, 250);
            //displays the category
            c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
            c.drawString("(" + gameCategory.categoryName[10 + whoseTurn] + ")", 40, 280);
            c.setColor(Color.white);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
            c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
            c.fillRect(10, 350, 790, 40);
            c.setCursor(19, 6);
            //prints first part of answer
            c.print("What is ");
            //starts the countdown
            runClock();
            //takes input for answer
            String answer = c.readLine();
            c.setColor(Color.white);
            c.fillRect(0,0,10,660);
            c.setCursor(19, 14);
            //displays inputted answer
            c.print(answer);
            //stops the countdown
            timer.clockClose();
            c.setCursor(19, 14 + answer.length());
            c.print("?");
            //checks if the answer is correct
            if (answer.toUpperCase().equals(gameCategory.answers[4][10 + whoseTurn].toUpperCase())) {
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(Color.green);
                //if time has not run out
                if (timer.clockQuery() > 0) {
                    //displays a message that the player's answer was correct
                    c.drawString("You are correct! You answered in " + (30 - timer.clockQuery()) + " seconds.", 40, 500);
                    //sets the player's answer time to the time it took to answer the question
                    answerTime[whoseTurn] = 30 - timer.clockQuery();
                } else { //if time has run out
                    //displays a message that the player's answer was correct but they timed out
                    c.drawString("You are correct but you ran out of time.", 40, 500);
                    //maxes out the player's answer time
                    answerTime[whoseTurn] = 31;
                }
            } else { //if the answer is incorrect
                //displays a message that the player's answer was incorrect
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(Color.red);
                c.drawString("That is incorrect.", 40, 500);
                //maxes out the player's answer time
                answerTime[whoseTurn] = 31;
            }
            c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
            c.setColor(new Color(248, 236, 208));
            c.drawString("Correct Answer: What is " + gameCategory.answers[4][10 + whoseTurn] + "?", 40, 450);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
            //prompts user to enter a key
            c.drawString("Press any key to continue", 200, 600);
            changeTurn(); //calls the changeTurn method to change whose turn it is
            pauseProgram(3); //calls the pauseProgram method for user to enter a key
        } else if (level == 2) { //round 2
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            //checks that the current question is in fact unanswered
            if (mainBoard.queryQuestion(questionRow, questionColumn + 5)) {
                title(); //calls the title method
                //displays the question
                c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                c.drawString(gameCategory.questions[questionRow][questionColumn + 5], 40, 250);
                //displays the category
                c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                c.drawString("(" + gameCategory.categoryName[questionColumn + 5] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                c.setColor(Color.white);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                c.fillRect(10, 350, 790, 40);
                c.setCursor(19, 6);
                //prints first part of answer
                c.print("What is ");
                //starts the countdown
                runClock();
                //takes input for answer
                String answer = c.readLine();
                c.setColor(Color.white);
                c.fillRect(0,0,10,660);
                c.setCursor(19, 14);
                //displays inputted answer
                c.print(answer);
                //stops the countdown
                timer.clockClose();
                c.setCursor(19, 14 + answer.length());
                c.print("?");
                //checks if the answer is correct
                if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn + 5].toUpperCase())) {
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.green);
                    //if timer has not run out
                    if (timer.clockQuery() > 0) {
                        //displays a message that the player's answer was correct
                        c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                        //increases players points
                        playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        changeTurn(); //calls the changeTurn method to change whose turn it is
                    } else { //if timer has run out
                        //displays a message that the player's answer was correct but they timed out
                        c.drawString("You are correct but you ran out of time.", 40, 500);
                        changeTurn(); //calls the changeTurn method to change whose turn it is
                    }
                } else {
                    changeTurn(); //calls the changeTurn method to change whose turn it is
                    //displays a message that the player's answer was incorrect
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.red);
                    c.drawString("That is incorrect.", 40, 500);
                    c.setColor(Color.white);
                    //gives the other player an opportunity to steal
                    c.drawString(playerNames[whoseTurn] + ", here's your chance to steal.", 40, 540);
                    c.drawString("Press any key to continue.", 40, 600);
                    c.getChar();
                    title(); //calls the title method
                    //displays the question
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                    c.drawString(gameCategory.questions[questionRow][questionColumn + 5], 40, 250);
                    //displays the category
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                    c.drawString("(" + gameCategory.categoryName[questionColumn + 5] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                    c.setColor(Color.white);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                    c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                    c.fillRect(10, 350, 790, 40);
                    c.setCursor(19, 6);
                    //prints first part of answer
                    c.print("What is ");
                    //starts the countdown
                    runClock();
                    //takes input for answer
                    answer = c.readLine();
                    c.setColor(Color.white);
                    c.fillRect(0,0,10,660);
                    c.setCursor(19, 14);
                    //displays inputted answer
                    c.print(answer);
                    //stops the countdown
                    timer.clockClose();
                    c.setCursor(19, 14 + answer.length());
                    c.print("?");
                    //checks if the answer is correct
                    if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn + 5].toUpperCase())) {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.green);
                        //if timer has not run out
                        if (timer.clockQuery() > 0) {
                            //displays a message that the player's answer was correct
                            c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                            //increases players points
                            playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        } else { //if timer has run out
                            //displays a message that the player's answer was correct but they timed out
                            c.drawString("You are correct, but you ran out of time.", 40, 500);
                        }
                    } else {
                        //displays a message that the player's answer was incorrect
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.red);
                        c.drawString("That is incorrect.", 40, 500);
                    }
                }
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(new Color(248, 236, 208));
                //display the correct answer
                c.drawString("Correct Answer: What is " + gameCategory.answers[questionRow][questionColumn + 5] + "?", 40, 450);
                //removes the question from the board
                mainBoard.removeQuestion(questionRow, questionColumn + 5);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                //prompts user to enter a key
                c.drawString("Press any key to continue", 200, 600);
                pauseProgram(3); //calls the pauseProgram method for user to enter a key
            }
        } else if (level == 1) { //round 1
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            //checks that the current question is in fact unanswered
            if (mainBoard.queryQuestion(questionRow, questionColumn)) {
                title(); //calls the title method
                //displays the question
                c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                c.drawString(gameCategory.questions[questionRow][questionColumn], 40, 250);
                //displays the category
                c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                c.drawString("(" + gameCategory.categoryName[questionColumn] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                c.setColor(Color.white);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                c.fillRect(10, 350, 790, 40);
                c.setCursor(19, 6);
                //prints first part of answer
                c.print("What is ");
                //starts the countdown
                runClock();
                //takes input for answer
                String answer = c.readLine();
                c.setColor(Color.white);
                c.fillRect(0,0,10,660);
                c.setCursor(19, 14);
                //displays inputted answer
                c.print(answer);
                //stops the countdown
                timer.clockClose();
                c.setCursor(19, 14 + answer.length());
                c.print("?");
                //checks if the answer is correct
                if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn].toUpperCase())) {
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.green);
                    //if timer has not run out
                    if (timer.clockQuery() > 0) {
                        //displays a message that the player's answer was correct
                        c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                        //increases players points
                        playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        changeTurn(); //calls the changeTurn method to change whose turn it is
                    } else { //if timer has run out
                        //displays a message that the player's answer was correct but they timed out
                        c.drawString("You are correct but you ran out of time.", 40, 500);
                        changeTurn(); //calls the changeTurn method to change whose turn it is
                    }
                } else {
                    changeTurn(); //calls the changeTurn method to change whose turn it is
                    //displays a message that the player's answer was incorrect
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.red);
                    c.drawString("That is incorrect.", 40, 500);
                    c.setColor(Color.white);
                    //gives the other player an opportunity to steal
                    c.drawString(playerNames[whoseTurn] + ", here's your chance to steal.", 40, 540);
                    //prompts user to enter a key
                    c.drawString("Press any key to continue.", 40, 600);
                    c.getChar(); //takes user input for any key
                    title(); //calls the title method
                    //displays the question
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                    c.drawString(gameCategory.questions[questionRow][questionColumn], 40, 250);
                    //displays the category
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                    c.drawString("(" + gameCategory.categoryName[questionColumn] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                    c.setColor(Color.white);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                    c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                    c.fillRect(10, 350, 790, 40);
                    c.setCursor(19, 6);
                    //prints first part of answer
                    c.print("What is ");
                    //starts the countdown
                    runClock();
                    //takes input for answer
                    answer = c.readLine();
                    c.setColor(Color.white);
                    c.fillRect(0,0,10,660);
                    c.setCursor(19, 14);
                    //displays inputted answer
                    c.print(answer);
                    //stops the countdown
                    timer.clockClose();
                    c.setCursor(19, 14 + answer.length());
                    c.print("?");
                    //checks if answer is correct
                    if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn].toUpperCase())) {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.green);
                        //if timer has not run out
                        if (timer.clockQuery() > 0) {
                            //displays a message that the player's answer was correct
                            c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                            //increases players points
                            playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        } else { //if timer has run out
                            //displays a message that the player's answer was correct but they timed out
                            c.drawString("You are correct, but you ran out of time.", 40, 500);
                        }
                    } else {
                        //displays a message that the player's answer was incorrect
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.red);
                        c.drawString("That is incorrect.", 40, 500);
                    }
                }
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(new Color(248, 236, 208));
                //display the correct answer
                c.drawString("Correct Answer: What is " + gameCategory.answers[questionRow][questionColumn] + "?", 40, 450);
                //removes the question from the board
                mainBoard.removeQuestion(questionRow, questionColumn);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                //prompts user to enter a key
                c.drawString("Press any key to continue", 200, 600);
                pauseProgram(3); //calls the pauseProgram method for user to enter a key
            }
        }
    }//ends runQuestion method

    //method that selects the categories that will be used in each game of Jeopardy
    private static void chooseCategories() throws IOException {
        //makes every element in the categoryNum array equal to its index
        for (int i = 0; i < databaseSize; i++) {
            categoryNum[i] = i;
        }
        //randomly shuffles all the elements in the categoryNum array
        for (int i = 0; i < databaseSize; i++) {
            //uses Math.random to choose which index to swap with
            int swap = (int) (Math.random() * databaseSize);
            //makes a temporary variable to store the swapped number
            int temp = categoryNum[swap];
            //swaps the current number to the number to swap with
            categoryNum[swap] = categoryNum[i];
            //changes the value of the current number to the temporary value
            categoryNum[i] = temp;
        }
        //uses the elements stored in the first 12 indices of categoryNum as the categories for the game
        for (int i = 0; i < 12; i++) {
            gameCategory.fill(categoryNum[i], i);
        }
    }//ends chooseCategories method

    //method that changes whose turn it is
    private static void changeTurn() {
        //if it is Player 1's turn
        if (whoseTurn == 0) {
            //switch turn to Player 2
            whoseTurn = 1;
        } else { //if it is Player 2's turn
            //switch turn to Player 1
            whoseTurn = 0;
        }
    }//ends changeTurn method
    
    //method that updates a players points
    //takes two parameters: the player whose turn it is, and the points that are being added
    private static void updatePoints(int turn, int points) {
        //adds the points to the players total score
        playerPoints[turn] += points;
    }//ends updatePoints method

    //method that runs the the timer
    private static void runClock() {
        //creates a new Thread for the timer to run ont
        Thread t = new Thread(timer);
        //starts the timer's thread
        t.start();
    }//ends runClock method

    //method that pauses the program and allowes users to enter a key to transition between stages of the game
    private static void pauseProgram(int state) throws IOException {
        if (state == 0) {
            //loops for user to enter a key and continues looping if the key pressed does not do anything
            while (true) {
                char input = c.getChar(); //allows user to enter a key
                if ((int) input == 10) { //checks to see if <ENTER> key was pressed
                    newGame(); //calls the newGame method to run a new game
                } else if ((int) input == 8) { //checks to see if <BACKSPACE> key was pressed
                    goodbye(); //calls the goodbye method to exit the program
                } else if ((input + "").toUpperCase().equals("L")) { //checks to see if <L> key was pressed
                    leaderboard(); //calls the leaderboard method to visit the leaderboard page
                } else if ((input + "").toUpperCase().equals("I")) { //checks to see if <I> key was pressed
                    instructions(); //calls the instructions method to visit the instructions page
                } else { //creates an error message if the wrong keys are pressed
                    new Message("Invalid selection");
                }
            }
        } else if (state == 1) {
            c.getChar(); //takes user input for any key
            mainMenu(); //calls the mainMenu method to go to the main menu page
        } else if (state == 2) {
            //leaderboard -> mainMenu/clear leaderboard
            if((c.getChar() + "").toUpperCase().equals("C")) { //takes input for a key a sees if it was the <C> key
                //calls the method from the Leaderboard class that clears all leaderboard entries from the file and from memory
                highScores.clearLeaderboard();
                //refreshes the leaderboard page
                leaderboard(); //calls the leaderboard method
            } else { //if it was any key other key
                //returns to the main menu
                mainMenu(); //call the mainMenu method
            }
        } else if (state == 3) {
            //takes input for any key to continue
            c.getChar();
        }
    }//ends pauseProgram method
    
    //main method
    public static void main(String[] args) throws IOException {
        JeopardyGame j = new JeopardyGame(24); //creates a new object of the JeopardyGame class with a database size of 24 (the number of categories created by us)
        j.gameStart(); //calls the gameStart method to start the game
    }//ends main method
}
