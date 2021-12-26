/**
    Derek Ma & Kevin Zhan
    December 25, 2021
    Ms. Basaraba
    JeopardyBoard is a custom class created to track answered and unanswered questions for
    the ongoing Jeopardy game. It also uses this information to display the board with its
    answered and unanswered question blocks and point values. This class also contains a
    few methods that allows the main game class to interact with the board and remove 
    questions that have been answered.
**/

import java.awt.*;
import hsa.Console;

public class JeopardyBoard {
    public boolean answered[][] = new boolean[5][10]; //2d boolean array that tracks which questions are answered
    private Console c;
    Color fillColor = new Color(255,220,100);
    Color blankColor = new Color(80, 80, 80);
    
    public JeopardyBoard(Console con) {
        //initializes console
        c = con;
    }//class constructor
    
    
    public void resetAnswered() {
        //loop that iterates through the rows
        for(int i = 0; i < 5; i++) {
            //loop that iterates through the columns
            for(int j = 0; j < 10; j++) {
                //sets the element to false indicating that the question is unanswered
                answered[i][j] = false;
            }
        }
    }//end resetAnswered method

    public void drawBoard(int level, String nameArr[]) {
        //draw background
        c.setColor(Color.white);
        c.fillRect(10, 610, 790, 70);
        
        //draw categories
        for(int i = 0; i < 5; i++) {
            //draw blocks
            c.setColor(Color.black);
            c.fillRoundRect(126 + i * 117, 175, 115, 55, 15, 15);
            c.setColor(fillColor);
            c.fillRoundRect(128 + i * 117, 177, 111, 51, 15, 15);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
            c.setColor(Color.black);
            //if statement to check which round it is since both rounds share the answered[][] array
            if(level == 1) {
                //prints the round 1 category names
                c.drawString(nameArr[i], 132 + i * 117, 205);
            } else if (level == 2) {
                //prints the round 2 category names
                c.drawString(nameArr[i + 5], 132 + i * 117, 205);
            }
        }
        
        //uses a for loop to print letters and numbers instead of hard coding
        for(int i = 0; i < 5; i++) {
            c.setColor(Color.white);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
            //prints the number
            c.drawString(i + 1 + "", 90, 270 + i * 57);
            //switch block that determines which letter to print depending on the iteration of the loop
            switch (i) {
                //first iteration
                case 0:
                    c.drawString("A", 173 + i * 117, 545);
                    //breaks out of the switch block
                    break;
                //second iteration
                case 1:
                    c.drawString("B", 173 + i * 117, 545);
                    //breaks out of the switch block
                    break;
                //third iteration
                case 2:
                    c.drawString("C", 173 + i * 117, 545);
                    //breaks out of the switch block
                    break;
                //fourth iteration
                case 3:
                    c.drawString("D", 173 + i * 117, 545);
                    //breaks out of the switch block
                    break;
                //fifth iteration
                case 4:
                    c.drawString("E", 173 + i * 117, 545);
                    //breaks out of the switch block
                    break;
            }
        }
    
        //printing the blocks on the board
        //checks if it is the first round
        if (level == 1) {
            //loops through the rows of the board
            for(int i = 0; i < 5; i++) {
                //loops through the columns of the board
                for(int j = 0; j < 5; j++) {
                    //checks if the question is unanswered
                    if(!answered[i][j]) {
                        c.setColor(Color.black);
                        //draws the border for the block
                        c.fillRoundRect(126 + j * 117, 235 + i * 57, 115, 55, 15, 15);
                        c.setColor(fillColor);
                        //draws the inside of the block
                        c.fillRoundRect(128 + j * 117, 237 + i * 57, 111, 51, 15, 15);
                        c.setFont(new Font("Serif", Font.BOLD, 40));
                        c.setColor(Color.black);
                        //prints point value
                        c.drawString((i + 1) * 100 * level + "", 150 + j * 117, 280 + i * 57);
                    //draws the grey block if question is answered
                    } else {
                        c.setColor(Color.black);
                        c.fillRoundRect(126 + j * 117, 235 + i * 57, 115, 55, 15, 15);
                        c.setColor(blankColor);
                        c.fillRoundRect(128 + j * 117, 237 + i * 57, 111, 51, 15, 15);
                    }//ends "answered" if statements
                }//ends column for loop
            }//ends height for loop
        } else if (level == 2) { //runs if it is the second round
            //loops through the rows of the board
            for(int i = 0; i < 5; i++) {
                //loops through the columns of the board
                for(int j = 0; j < 5; j++) {
                    //checks if the question is unanswered
                    if(!answered[i][j + 5]) {
                        c.setColor(Color.black);
                        //draws the border for the block
                        c.fillRoundRect(126 + j * 117, 235 + i * 57, 115, 55, 15, 15);
                        c.setColor(fillColor);
                        //draws the inside of the block
                        c.fillRoundRect(128 + j * 117, 237 + i * 57, 111, 51, 15, 15);
                        c.setFont(new Font("Serif", Font.BOLD, 40));
                        c.setColor(Color.black);
                        //checks if it is drawing the final row
                        if(i == 4) {
                            //coordinates of string are shifted because 1000 is 1 character wider than other numbers
                            c.drawString((i + 1) * 100 * level + "", 140 + j * 117, 280 + i * 57);
                        } else {
                            //prints point value
                            c.drawString((i + 1) * 100 * level + "", 150 + j * 117, 280 + i * 57);
                        }
                    //draws the grey block if question is answered
                    } else {
                        c.setColor(Color.black);
                        c.fillRoundRect(126 + j * 117, 235 + i * 57, 115, 55, 15, 15);
                        c.setColor(blankColor);
                        c.fillRoundRect(128 + j * 117, 237 + i * 57, 111, 51, 15, 15);
                    }//ends "answered" if statements
                }//ends column for loop
            }//ends height for loop
        }//ends level if statement
    }//ends drawBoard method
    
    //checks to see if there are any questions left
    public boolean incomplete(int level) {
        boolean check = false; //boolean variable representing whether or not the board is incomplete
        //loops through rows of the array
        for(int i = 0; i < 5; i++) {
            //loops through columns of the board
            for(int j = 0; j < 5; j++) {
                //checks for round in order to determine position on the array
                if(level == 1) {
                    //if unanswered
                    if(!answered[i][j]) {
                        //sets incomplete to true
                        check = true;
                    }
                } else if (level == 2) {
                    if(!answered[i][j + 5]) {
                        //sets incomplete to true
                        check = true;
                    }
                }
            }
        }
        //returns the value of the boolean variable
        return check;
    }//ends incomplete method
    
    //method that removes a question from the board by marking it as answered
    //takes the row and column of the question as parameters
    public void removeQuestion(int row, int column) {
        //sets the questions position in the boolean array to true
        answered[row][column] = true;
    }//ends removeQuestion method
    
    //method that returns whether or not a specific question is unanswered 
    //takes the row and column of the question as parameters
    public boolean queryQuestion(int row, int column) {
        //checks if the question is answered
        if(answered[row][column]) {
            //if question is answered it means that "unanswered" is false
            return false;
        }
        //if it skips the return statement in the previous if statement that means it is unanswered
        return true;
    }//ends queryQuestion method
}
