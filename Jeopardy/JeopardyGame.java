import java.awt.*;
import hsa.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class JeopardyGame {
    private static Console c;
    private static int databaseSize;
    private static Category catDatabase;
    public static Category gameCategory;
    private static int categoryNum[];
    private static JeopardyBoard mainBoard;
    private static Leaderboard highScores;
    private static int whoseTurn;
    private static Color backgroundColor;
    private static String playerNames[];
    private static int playerPoints[];
    private static Clock timer;
    private static int questionRow;
    private static int questionColumn;
    private static int answerTime[];

    public JeopardyGame(int size) {
        c = new Console(33, 100, "Jeopardy!");
        databaseSize = size;
        catDatabase = new Category(databaseSize);
        gameCategory = new Category(12);
        categoryNum = new int[databaseSize];
        mainBoard = new JeopardyBoard(c);
        highScores = new Leaderboard(c);
        backgroundColor = new Color(7, 55, 99);
        playerNames = new String[2];
        playerPoints = new int[2];
        answerTime = new int[2];
    }

    private static void pauseProgram(int state) throws IOException {
        if (state == 0) {
            //main menu -> leaderboard/mainMenu/newGame/goodbye
            while (true) {
                char input = c.getChar();
                if ((int) input == 10) {
                    newGame();
                } else if ((int) input == 8) {
                    goodbye();
                } else if ((input + "").toUpperCase().equals("L")) {
                    leaderboard();
                } else if ((input + "").toUpperCase().equals("I")) {
                    instructions();
                }
            }
        } else if (state == 1) {
            //instructions/win screen -> mainMenu
            c.getChar();
            mainMenu();
        } else if (state == 2) {
            //leaderboard -> mainMenu/clear leaderboard
            if((c.getChar() + "").toUpperCase().equals("C")) {
                highScores.clearLeaderboard();
                leaderboard();
            } else {
                mainMenu();
            }
        } else if (state == 3) {
            //press any key to continue
            c.getChar();
        }
    }

    private static void title() {
        int xArr[] = {631, 637, 652, 658};
        int yArr[] = {45, 95, 95, 40};
        c.setColor(backgroundColor);
        c.fillRect(10, 10, 790, 650);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 90));
        c.setColor(new Color(248, 236, 208));
        c.drawString("Jeopardy", 175, 110);
        c.fillOval(635, 105, 20, 20);
        c.fillPolygon(xArr, yArr, 4);
    }
    
    private static void splashScreen() {
        //splash screen
        int increase = 0;
        title();
        for (int i = 0; i < 200; i++) {
            c.fillOval(-210 + i * 3, -300 + i * 3, 1200 - 6 * i, 1200 - 6 * i);
            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }
            c.setColor(Color.white);
            c.fillRect(0, 0, 10, 660);
            c.fillRect(0, 0, 800, 10);
            c.setColor(backgroundColor);
            c.fillOval(-210 + i * 3, -300 + i * 3, 1200 - 6 * i, 1200 - 6 * i);
            c.setColor(new Color(248, 236, 208));
        }

        c.fillOval(380, 290, 20, 20);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
        
        c.setColor(new Color(248, 236, 208));
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 20; i++) {
                increase += i;
                c.fillOval(380, 290 + increase, 20, 20);
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                }
                c.setColor(backgroundColor);
                c.fillOval(380, 290 + increase, 20, 20);
                c.setColor(new Color(248, 236, 208));
            }


            for (int i = 20; i > 0; i--) {
                increase -= i;
                c.fillOval(380, 290 + increase, 20, 20);
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                }
                c.setColor(backgroundColor);
                c.fillOval(380, 290 + increase, 20, 20);
                c.setColor(new Color(248, 236, 208));
            }
        }

        //dot from (380,290) to (635,105) or (255,-185)
        for (int i = 0; i < 31; i++) {
            c.fillOval(380 + i * 5, 250 - i * 5, 20, 20);
            try {
                Thread.sleep(30);
            } catch (Exception e) {
            }    
            c.setColor(backgroundColor);
            c.fillOval(380 + i * 5, 250 - i * 5, 20, 20);
            c.setColor(new Color(248, 236, 208));

        }

        for (int i = 0; i < 14; i++) {
            c.fillOval(565 + i * 5, 105, 20, 20);
            try {
                Thread.sleep(30);
            } catch (Exception e) {
            }
            c.setColor(backgroundColor);
            c.fillOval(565 + i * 5, 105, 20, 20);
            c.setColor(new Color(248, 236, 208));
        }

        c.fillOval(635, 105, 20, 20);

        //exclaimation mark coming down
        for (int i = 0; i < 20; i++) {
            int xArr[] = {631, 637, 652, 658};
            int yArr[] = {-55 + i * 5, -5 + i * 5, -5 + i * 5, -60 + i * 5};
            c.fillPolygon(xArr, yArr, 4);
            try {
                Thread.sleep(30);
            } catch (Exception e) {
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

        //words slide in
        for (int i = 0; i < 100; i++) {
            c.drawString("Jeopardy", -325 + i * 5, 110);
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
            c.setColor(Color.white);
            c.fillRect(0, 0, 10, 660);
            c.fillRect(0, 0, 800, 10);
            c.setColor(backgroundColor);
            c.drawString("Jeopardy", -325 + i * 5, 110);
            c.setColor(new Color(248, 236, 208));
        }
    }
    
    private static void mainMenu() throws IOException {
        title();
        c.drawString("Main Menu", 170, 200);
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 30));
        c.drawString("Press <Enter> to start a game of", 80, 300);
        c.drawString("Jeopardy!", 80, 340);
        c.drawString("Press <L> to view the leaderboard.", 80, 400);
        c.drawString("Press <I> to view the instructions.", 80, 450);
        c.drawString("Press <Backspace> to exit Jeopardy.", 80, 500);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 90));
        pauseProgram(0);
    }

    private static void instructions() throws IOException {
        title();
        c.drawString("Instructions", 100, 200);
        c.setFont(new Font("Serif", Font.PLAIN, 25));
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
        c.drawString("Press any key to go back to main menu", 20, 650);
        pauseProgram(1);
    }

    private static void leaderboard() throws IOException {
        title();
        highScores.printLeaderboard();
        c.setColor(new Color(248, 236, 208));
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 18));
        c.drawString("Press <C> to clear the leaderboard", 20, 620);
        c.drawString("Press any key to go back to main menu", 20, 650);
        pauseProgram(2);
    }

    private static void goodbye() throws IOException {
        System.exit(0);
    }

    private static void enterNames() throws IOException {
        title();
        c.setTextBackgroundColor(backgroundColor);
        c.setTextColor(Color.white);
        while (true) {
            c.setColor(backgroundColor);
            c.fillRect(190, 280, 600, 20);
            c.setCursor(15, 25);
            c.print("Player 1, please enter your display name: ");
            playerNames[0] = c.readLine();
            if (playerNames[0].length() <= 12) {
                c.fillRect(190, 280, 600, 20);
                c.setCursor(15, 25);
                break;
            } else {
                new Message("Please keep your name under 12 characters.");
            }
        }
        c.print("Player 1, your name is: " + playerNames[0]);
        while (true) {
            c.fillRect(190, 320, 600, 20);
            c.setCursor(17, 25);
            c.print("Player 2, please enter your display name: ");
            playerNames[1] = c.readLine();
            if (playerNames[1].length() <= 12 && !playerNames[1].equals(playerNames[0])) {
                c.fillRect(190, 320, 600, 20);
                c.setCursor(17, 25);
                break;
            } else if (!playerNames[1].equals(playerNames[0])) {
                new Message("Please keep your name under 12 characters.");
            } else {
                new Message("The name " + playerNames[0] + " is already taken.");
            }
        }
        c.print("Player 2, your name is: " + playerNames[1]);
        c.setColor(new Color(248, 236, 208));
        c.setFont(new Font("MonoSpaced", Font.PLAIN, 18));
        c.drawString("Press any key to continue", 20, 650);
        pauseProgram(3);
    }

    private static void changeTurn() {
        if (whoseTurn == 0) {
            whoseTurn = 1;
        } else {
            whoseTurn = 0;
        }
    }

    private static void gameStart() throws IOException {
        //fill the category database
        for (int i = 0; i < databaseSize; i++) {
            catDatabase.fill(i, i);
        }
        splashScreen();
        mainMenu();
    }

    private static void newGame() throws IOException {
        enterNames();
        c.setTextBackgroundColor(Color.white);
        c.setTextColor(Color.black);
        chooseCategories();
        timer = new Clock();
        title();
        c.setFont(new Font("MonoSpaced", Font.BOLD, 80));
        c.drawString("Round 1", 230, 350);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3);
        while (mainBoard.incomplete(1)) {
            title();
            mainBoard.drawBoard(1, gameCategory.categoryName);
            c.setFont(new Font("Monospaced", Font.BOLD, 25));
            c.setColor(Color.white);
            c.drawString(playerNames[0] + ": " + playerPoints[0], 140, 160);
            c.drawString(playerNames[1] + ": " + playerPoints[1], 380, 160);
            selectQuestion();
            runQuestion(1);
        }
        title();
        c.setFont(new Font("MonoSpaced", Font.BOLD, 80));
        c.drawString("Round 2", 230, 350);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3);
        while (mainBoard.incomplete(2)) {
            title();
            mainBoard.drawBoard(2, gameCategory.categoryName);
            c.setFont(new Font("Monospaced", Font.BOLD, 25));
            c.setColor(Color.white);
            c.drawString(playerNames[0] + ": " + playerPoints[0], 140, 160);
            c.drawString(playerNames[1] + ": " + playerPoints[1], 380, 160);
            selectQuestion();
            runQuestion(2);
        }
        mainBoard.resetAnswered();
        title();
        c.setFont(new Font("MonoSpaced", Font.BOLD, 80));
        c.drawString("Round 3", 230, 350);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        c.drawString("Each player will receive a question.", 125, 400);
        c.drawString("Whoever answers in the least amount", 127, 440);
        c.drawString("of time will receive 1000 points.", 145, 480);
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3);
        runQuestion(3);
        runQuestion(3);
        title();
        c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
        if(answerTime[0] < answerTime[1]) {
            c.drawString(playerNames[0] + " was faster", 175, 400);
            c.drawString("and will receive 1000 points.", 175, 430);
            playerPoints[0] += 1000;
        } else if(answerTime[1] < answerTime[0]) {
            c.drawString(playerNames[1] + " was faster", 175, 400);
            c.drawString("and will receive 1000 points.", 175, 430);
            playerPoints[1] += 1000;
        } else if(answerTime[0] == answerTime[1]) {
            c.drawString("Both players were equally fast", 175, 400);
            c.drawString("so neither will receive points.", 175, 430);
        }
        c.drawString("Press any key to continue", 200, 600);
        pauseProgram(3);
        //Winning screen
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
    }

    private static void updatePoints(int turn, int points) {
        playerPoints[turn] += points;
    }

    private static void selectQuestion() {
        c.setColor(Color.white);
        c.setFont(new Font("MonoSpaced", Font.BOLD, 17));
        c.drawString(playerNames[whoseTurn] + ", enter the row for the question you wish to answer:", 20, 600);
        while (true) {
            char input = c.getChar();
            if ((input + "").toUpperCase().equals("A")) {
                questionRow = 1;
            } else if ((input + "").toUpperCase().equals("B")) {
                questionRow = 2;
            } else if ((input + "").toUpperCase().equals("C")) {
                questionRow = 3;
            } else if ((input + "").toUpperCase().equals("D")) {
                questionRow = 4;
            } else if ((input + "").toUpperCase().equals("E")) {
                questionRow = 5;
            }
            if (questionRow != 0) {
                c.setCursor(32, 3);
                c.print((input + "").toUpperCase());
                questionRow--;
                break;
            }
        }
        c.setColor(backgroundColor);
        c.fillRect(20, 580, 1000, 25);
        c.setColor(Color.white);
        c.drawString(playerNames[whoseTurn] + ", enter the column for the question you wish to answer:", 20, 600);
        c.setCursor(0, 2);
        while (true) {
            try {
                questionColumn = Integer.parseInt(c.getChar() + "") - 1;
                if (questionColumn >= 0 && questionColumn < 5) {
                    c.setCursor(32, 5);
                    c.print(questionColumn + 1);
                    break;
                } else {
                    new Message("Please enter a valid column number.", "Error");
                }
            } catch (NumberFormatException e) {
                new Message("Please enter a valid column number.", "Error");
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private static void runQuestion(int level) throws IOException {
        if (level == 3) {
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            title();
            c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
            c.drawString(gameCategory.questions[4][10 + whoseTurn], 40, 250);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
            c.drawString("(" + gameCategory.categoryName[10 + whoseTurn] + ")", 40, 280);
            c.setColor(Color.white);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
            c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
            c.fillRect(10, 350, 790, 40);
            c.setCursor(19, 6);
            c.print("What is ");
            runClock();
            String answer = c.readLine();
            c.setColor(Color.white);
            c.fillRect(0,0,10,660);
            c.setCursor(19, 14);
            c.print(answer);
            timer.clockClose();
            c.setCursor(19, 14 + answer.length());
            c.print("?");
            if (answer.toUpperCase().equals(gameCategory.answers[4][10 + whoseTurn].toUpperCase())) {
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(Color.green);
                if (timer.clockQuery() > 0) {
                    c.drawString("You are correct! You answered in " + (30 - timer.clockQuery()) + " seconds.", 40, 500);
                    answerTime[whoseTurn] = 30 - timer.clockQuery();
                } else {
                    c.drawString("You are correct but you ran out of time.", 40, 500);
                    answerTime[whoseTurn] = 31;
                }
            } else {
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(Color.red);
                c.drawString("That is incorrect.", 40, 500);
                answerTime[whoseTurn] = 31;
            }
            c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
            c.setColor(new Color(248, 236, 208));
            c.drawString("Correct Answer: What is " + gameCategory.answers[4][10 + whoseTurn] + "?", 40, 450);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
            c.drawString("Press any key to continue", 200, 600);
            changeTurn();
            pauseProgram(3);
        } else if (level == 2){
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            if (mainBoard.queryQuestion(questionRow, questionColumn + 5)) {
                title();
                c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                c.drawString(gameCategory.questions[questionRow][questionColumn + 5], 40, 250);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                c.drawString("(" + gameCategory.categoryName[questionColumn + 5] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                c.setColor(Color.white);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                c.fillRect(10, 350, 790, 40);
                c.setCursor(19, 6);
                c.print("What is ");
                runClock();
                String answer = c.readLine();
                c.setColor(Color.white);
                c.fillRect(0,0,10,660);
                c.setCursor(19, 14);
                c.print(answer);
                timer.clockClose();
                c.setCursor(19, 14 + answer.length());
                c.print("?");
                if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn + 5].toUpperCase())) {
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.green);
                    if (timer.clockQuery() > 0) {
                        c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                        playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        changeTurn();
                    } else {
                        c.drawString("You are correct but you ran out of time.", 40, 500);
                        changeTurn();
                    }
                } else {
                    changeTurn();
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.red);
                    c.drawString("That is incorrect.", 40, 500);
                    c.setColor(Color.white);
                    c.drawString(playerNames[whoseTurn] + ", here's your chance to steal.", 40, 540);
                    c.drawString("Press any key to continue.", 40, 600);
                    c.getChar();
                    title();
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                    c.drawString(gameCategory.questions[questionRow][questionColumn + 5], 40, 250);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                    c.drawString("(" + gameCategory.categoryName[questionColumn + 5] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                    c.setColor(Color.white);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                    c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                    c.fillRect(10, 350, 790, 40);
                    c.setCursor(19, 6);
                    c.print("What is ");
                    runClock();
                    answer = c.readLine();
                    c.setColor(Color.white);
                    c.fillRect(0,0,10,660);
                    c.setCursor(19, 14);
                    c.print(answer);
                    timer.clockClose();
                    c.setCursor(19, 14 + answer.length());
                    c.print("?");
                    if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn + 5].toUpperCase())) {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.green);
                        if (timer.clockQuery() > 0) {
                            c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                            playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        } else {
                            c.drawString("You are correct, but you ran out of time.", 40, 500);
                        }
                    } else {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.red);
                        c.drawString("That is incorrect.", 40, 500);
                    }
                }
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(new Color(248, 236, 208));
                c.drawString("Correct Answer: What is " + gameCategory.answers[questionRow][questionColumn + 5] + "?", 40, 450);
                mainBoard.removeQuestion(questionRow, questionColumn + 5);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Press any key to continue", 200, 600);
                pauseProgram(3);
            }
        } else if (level == 1){
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            if (mainBoard.queryQuestion(questionRow, questionColumn)) {
                title();
                c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                c.drawString(gameCategory.questions[questionRow][questionColumn], 40, 250);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                c.drawString("(" + gameCategory.categoryName[questionColumn] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                c.setColor(Color.white);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                c.fillRect(10, 350, 790, 40);
                c.setCursor(19, 6);
                c.print("What is ");
                runClock();
                String answer = c.readLine();
                c.setColor(Color.white);
                c.fillRect(0,0,10,660);
                c.setCursor(19, 14);
                c.print(answer);
                timer.clockClose();
                c.setCursor(19, 14 + answer.length());
                c.print("?");
                if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn].toUpperCase())) {
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.green);
                    if (timer.clockQuery() > 0) {
                        c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                        playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        changeTurn();
                    } else {
                        c.drawString("You are correct but you ran out of time.", 40, 500);
                        changeTurn();
                    }
                } else {
                    changeTurn();
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.red);
                    c.drawString("That is incorrect.", 40, 500);
                    c.setColor(Color.white);
                    c.drawString(playerNames[whoseTurn] + ", here's your chance to steal.", 40, 540);
                    c.drawString("Press any key to continue.", 40, 600);
                    c.getChar();
                    title();
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
                    c.drawString(gameCategory.questions[questionRow][questionColumn], 40, 250);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                    c.drawString("(" + gameCategory.categoryName[questionColumn] + " for " + (100 * level * (questionRow + 1)) + ")", 40, 280);
                    c.setColor(Color.white);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                    c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                    c.fillRect(10, 350, 790, 40);
                    c.setCursor(19, 6);
                    c.print("What is ");
                    runClock();
                    answer = c.readLine();
                    c.setColor(Color.white);
                    c.fillRect(0,0,10,660);
                    c.setCursor(19, 14);
                    c.print(answer);
                    timer.clockClose();
                    c.setCursor(19, 14 + answer.length());
                    c.print("?");
                    if (answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn].toUpperCase())) {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.green);
                        if (timer.clockQuery() > 0) {
                            c.drawString("You are correct! Your score increased by " + (100 * level * (questionRow + 1)) + " points.", 40, 500);
                            playerPoints[whoseTurn] += 100 * level * (questionRow + 1);
                        } else {
                            c.drawString("You are correct, but you ran out of time.", 40, 500);
                        }
                    } else {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.red);
                        c.drawString("That is incorrect.", 40, 500);
                    }
                }
                c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                c.setColor(new Color(248, 236, 208));
                c.drawString("Correct Answer: What is " + gameCategory.answers[questionRow][questionColumn] + "?", 40, 450);
                mainBoard.removeQuestion(questionRow, questionColumn);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Press any key to continue", 200, 600);
                pauseProgram(3);
            }
        }
    }

    private static void chooseCategories() throws IOException {
        for (int i = 0; i < databaseSize; i++) {
            categoryNum[i] = i;
        }
        for (int i = 0; i < databaseSize; i++) {
            int swap = (int) (Math.random() * databaseSize);
            int temp = categoryNum[swap];
            categoryNum[swap] = categoryNum[i];
            categoryNum[i] = temp;
        }
        for (int i = 0; i < 12; i++) {
            gameCategory.fill(categoryNum[i], i);
        }
    }

    private static void runClock() {
        Thread t = new Thread(timer);
        t.start();
    }

    public static void main(String[] args) throws IOException {
        JeopardyGame j = new JeopardyGame(12);
        j.gameStart();
    }
}
