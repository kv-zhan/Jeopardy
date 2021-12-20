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
  
    public JeopardyGame (int size) {
        c = new Console(33, 100);
        databaseSize = size;
        catDatabase = new Category(databaseSize);
        gameCategory = new Category(6);
        categoryNum = new int[databaseSize];
        mainBoard = new JeopardyBoard(c);
        highScores = new Leaderboard(c);
        backgroundColor = new Color(7,55,99);
        playerNames = new String[2];
        playerPoints = new int[2];
    }    
    
    private static void pauseProgram(int state) throws IOException {
        if(state == 0) {
            //print instructions for keys
            while(true) {
                char input = c.getChar();
                if((int)input == 10) {
                    newGame();
                } else if((int)input == 8) {
                    goodbye();
                } else if((input + "").toUpperCase().equals("L")){
                    leaderboard();
                } else if((input + "").toUpperCase().equals("I")){
                    instructions();
                }
            }
        ///main menu -> leaderboard/mainMenu/newGame/goodbye
        } else if (state == 1) {
            //press any key to continue
            c.getChar();
        } else if (state == 2) {
        //enterNames pause
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
        title();
        c.drawString("SplashScreen",100,100);
    }
    
    private static void mainMenu() throws IOException {
        title();
        c.drawString("Main Menu", 100, 200);
        pauseProgram(0);
    }
    
    private static void instructions() throws IOException {
        title();
        c.drawString("Instructions", 200, 200);
        pauseProgram(1);
    }
    
    
    private static void leaderboard() throws IOException {
        title();
        for(int i = 0; i < 14; i++) {
            highScores.updateLeaderboard(i + "", i);
        }
        highScores.printLeaderboard();
        highScores.clearLeaderboard();
        pauseProgram(1);
    }
    
    private static void goodbye() throws IOException {
        highScores.clearLeaderboard();
        System.exit(0);
    }
    
    private static void enterNames() throws IOException {
        title();
        c.setTextBackgroundColor(backgroundColor);
        c.setTextColor(Color.white);
        while(true) {
            c.setColor(backgroundColor);
            c.fillRect(190,280,600,20);
            c.setCursor(15, 25);
            c.print("Player 1, please enter your display name: ");
            playerNames[0] = c.readLine();
            if(playerNames[0].length() <= 12) {
                c.fillRect(190,280,600,20);
                c.setCursor(15, 25);
                break;
            } else {
                new Message("Please keep your name under 12 characters.");
            }
        }
        c.print("Player 1, your name is: " + playerNames[0]);
        while(true) {
            c.fillRect(190,320,600,20);
            c.setCursor(17, 25);
            c.print("Player 2, please enter your display name: ");
            playerNames[1] = c.readLine();
            if(playerNames[1].length() <= 12 && !playerNames[1].equals(playerNames[0])) {
                c.fillRect(190,320,600,20);
                c.setCursor(17, 25);
                break;
            } else if(!playerNames[1].equals(playerNames[0])){
                new Message("Please keep your name under 12 characters.");
            } else {
                new Message("The name " + playerNames[0] + " is already taken.");
            }
        }
        c.print("Player 2, your name is: " + playerNames[1]);
        pauseProgram(2);
    }

    private static void changeTurn() {
        if(whoseTurn == 0) {
            whoseTurn = 1;
        } else {
            whoseTurn = 0;
        }
    }
    
    private static void gameStart() throws IOException {
        //fill the category database
        for(int i = 0; i < databaseSize; i++) {
            catDatabase.fill(i, i);
        }
        splashScreen();
        mainMenu();
    }
    
    private static void newGame() throws IOException {
        enterNames();
        c.setTextBackgroundColor(Color.white);
        c.setTextColor(Color.black);
        //round 1
        chooseCategories();
        while(mainBoard.incomplete()) {
            title();
            mainBoard.drawBoard(1, gameCategory.categoryName);
            selectQuestion();
            runQuestion(1);
        }
        mainBoard.resetAnswered();
        //round 2
        chooseCategories();
        while(mainBoard.incomplete()) {
            title();
            mainBoard.drawBoard(2, gameCategory.categoryName);
            selectQuestion();
            runQuestion(2);
        }
        mainBoard.resetAnswered();
        //round 3
    }
    
    private static void updatePoints (int turn, int points) {
        playerPoints[turn] += points;
    }
    
    private static void selectQuestion () {
            c.setColor(Color.white);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 17));
            c.drawString(playerNames[whoseTurn] + ", enter the row for the question you wish to answer:", 20, 600);
            while(true) {
                char input = c.getChar();
                if((input + "").toUpperCase().equals("A")) {
                    questionRow = 1;
                } else if((input + "").toUpperCase().equals("B")) {
                    questionRow = 2;
                } else if((input + "").toUpperCase().equals("C")) {
                    questionRow = 3;
                } else if((input + "").toUpperCase().equals("D")) {
                    questionRow = 4;
                } else if((input + "").toUpperCase().equals("E")) {
                    questionRow = 5;
                } 
                if(questionRow != 0) {
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
            c.setCursor(0,2);
            while(true) {
                try {
                    questionColumn = Integer.parseInt(c.getChar() + "") - 1;
                    if(questionColumn >= 0 && questionColumn < 5) {
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
        if(level == 3) {
            //level 3 question
            pauseProgram(3);
        } else {
            c.setColor(backgroundColor);
            c.fillRect(20, 580, 1000, 25);
            if(mainBoard.queryQuestion(questionRow,questionColumn)) {
                title();
                c.setFont(new Font("MonoSpaced", Font.BOLD, 50));
                c.drawString(gameCategory.questions[questionRow][questionColumn], 40, 250);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                c.drawString("(" + gameCategory.categoryName[questionColumn] + ")", 40, 280);
                c.setColor(Color.white);
                c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                c.fillRect(10, 350, 790, 40);
                c.setCursor(19,6);
                c.print("What is ");
                runClock();
                String answer = c.readLine();
                c.setCursor(19,14);
                c.print(answer);
                timer.clockClose();
                c.setCursor(19,14 + answer.length());
                c.print("?");
                if(answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn].toUpperCase())) {
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                    c.setColor(Color.green);
                    if(!timer.clockQuery()) {
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
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 50));
                    c.drawString(gameCategory.questions[questionRow][questionColumn], 40, 250);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                    c.drawString("(" + gameCategory.categoryName[questionColumn] + ")", 40, 280);
                    c.setColor(Color.white);
                    c.setFont(new Font("MonoSpaced", Font.BOLD, 25));
                    c.drawString("Your Answer(" + playerNames[whoseTurn] + "):", 40, 340);
                    c.fillRect(10, 350, 790, 40);
                    c.setCursor(19,6);
                    c.print("What is ");
                    runClock();
                    answer = c.readLine();
                    c.setCursor(19,14);
                    c.print(answer);
                    timer.clockClose();
                    c.setCursor(19,14 + answer.length());
                    c.print("?");
                    if(answer.toUpperCase().equals(gameCategory.answers[questionRow][questionColumn].toUpperCase())) {
                        c.setFont(new Font("MonoSpaced", Font.BOLD, 20));
                        c.setColor(Color.green);
                        if(!timer.clockQuery()) {
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
                c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
                c.setColor(new Color(248, 236, 208));
                c.drawString("Correct Answer: What is " + gameCategory.answers[questionRow][questionColumn] + "?", 40, 450);
                mainBoard.removeQuestion(questionRow,questionColumn);
                pauseProgram(3);
            }
        }
    }

    private static void chooseCategories() throws IOException {
        for (int i = 0; i < databaseSize; i++) {
            categoryNum[i] = i;
        }
        for (int i = 0; i < databaseSize; i++) {
            int swap = (int)(Math.random() * databaseSize);
            int temp = categoryNum[swap];
            categoryNum[swap] = categoryNum[i];
            categoryNum[i] = temp;
        }
        for(int i = 0; i < 6; i++) {
            gameCategory.fill(categoryNum[i], i);
        }
    }
    
    private static void runClock() {
        timer = new Clock();
        Thread t = new Thread(timer);
        t.start();
    }
    
    public static void main(String[] args) throws IOException {
        JeopardyGame j = new JeopardyGame(10);
        j.gameStart();
        j.splashScreen();
        j.mainMenu();
    }
}
