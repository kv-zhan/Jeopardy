import java.awt.*;
import hsa.Console;
import java.io.*;

public class Leaderboard {
    private Console c;
    public int scores[];
    public String names[];
    Color fillColor = new Color(255,220,100);
  
    public Leaderboard(Console con) {
        //initialize console
        c = con;
        //declares and initializes the integer array of the high score 
        scores = new int[11];
        //declares and initializes the String array of the users
        names = new String[11];
    }//class constructor
    
    public void sortLeaderboard() throws IOException {
        //declares a FileWriter object for datafiles/Leaderboard.txt
        FileWriter fwriter = new FileWriter("dataFiles/Leaderboard.txt");
        int count = 0;
        //loop which will repeat a maximum of 11 times
        for(int i = 0; i < 11; i++) {
            //if names of a term on the array is empty, 
            if(names[i] == null) {
                //the program breaks the loop
                break;
            } else {
                //the count will increase by one
                count++;
            }
        }
        
        //sorting
        //runs this for loop to find the empty index in the name array that the previous loop just found
        for(int i = 0; i < count - 1; i++) {
            //runs this loop to compare the score just inputted with every other score
            for(int j = 0; j < count - 1; j++) {
                //if the score being sorted is smaller than the score after it on the index, 
                if(scores[j] < scores[j + 1]) {
                    //the score and string will be stored as temporary variables
                    int temp = scores[j];
                    String tempStr = names[j];
                    //the current index of the score and username become the score of the index previously below that index, basically moving the score that was just compared UP on the leaderboard
                    scores[j] = scores[j + 1];
                    names[j] = names[j + 1];
                    //the index below the current index 
                    scores[j + 1] = temp;
                    names[j + 1] = tempStr;
                }//ends if statement
            }//ends comparison for loop
        }//ends for loop that counts the number of variables
        
        //for loop for writing each sorted term of the username and score on the file Leaderboard.txt (only repeats 10 times, the 11th term is left out)
        for(int i = 0; i < 10; i++) {
            if(names[i] == null) {
                break;
            } else {
                fwriter.write(names[i] + "\n");
                fwriter.write(scores[i] + "\n");
            }
        }
        //closes the FileWriter
        fwriter.close();
    }//end sortLeaderboard method
    
    
    public void updateLeaderboard(String updateName, int updateScore) throws IOException {
        //defaults updated to false
        boolean updated = false;
        //for loop that will check each term on the username and score arrays 
        for(int i = 0; i < 11; i++) {
            //if there is an empty index
            if(names[i] == null) {
                //the names and scores will be stored on that index
                names[i] = updateName;
                scores[i] = updateScore;
                updated = true;
                //after the program found a place to store the name and score update, it can stop checking empty indexes.
                break;
            }
        }
        //if there are no more terms in the 
        if(!updated) {
            names[10] = updateName;
            scores[10] = updateScore;
        }
        sortLeaderboard(); //runs the leaderboard sorting method
    }//ends updateLeaderboard method (takes a new username and score to sort onto the leaderboard)
    
    
    public void clearLeaderboard() throws IOException {
        //declares a FileWriter object for datafiles/Leaderboard.txt
        FileWriter fwriter = new FileWriter("dataFiles/Leaderboard.txt");
        //for loop, which goes down the terms of the leaderboard and clears every term in the name and score loops
        for(int i = 0; i < 11; i++) {
            scores[i] = 0;
            names[i] = null;
        }
        //keeps the file as an empty file
        fwriter.write("");
        //closes the filewriter
        fwriter.close();
    }
    
    public void printLeaderboard() throws IOException {
        //declares a BufferedReader object for datafiles/Leaderboard.txt
        BufferedReader input = new BufferedReader(new FileReader("dataFiles/Leaderboard.txt"));
        //setting up font and colour
        c.setFont(new Font ("MonoSpaced", Font.BOLD, 40));
        c.setColor(Color.white);
        //setting up font for leaderboard terms
        c.drawString("Leaderboard:", 270, 170);
        c.setFont(new Font ("MonoSpaced", Font.BOLD, 25));
        c.setColor(Color.black);
        //for loops that checks every position on the leaderboard(including hidden 11th position)
        for(int i = 0; i < 11; i++) {
            //sets str as whatever the BufferedReader found on the new line
            String str = input.readLine();
            //if the line isn't empty,
            if(str != null) {
                c.fillRect(140, 190 + 40 * i, 510, 45);
                c.setColor(new Color(255,220,100));
                c.fillRect(145, 195  + 40 * i, 248, 35);
                c.fillRect(397, 195  + 40 * i, 248, 35);
                c.setColor(Color.black);
                //formats the 10th line so that the numbers are aligned
                if(i == 9) {
                    c.drawString((i + 1) + "." + str, 150, 220 + i * 40);
                } else {
                    c.drawString((i + 1) + ". " + str, 150, 220 + i * 40);
                }
                c.drawString("Score: $" + input.readLine(), 400, 220 + i * 40);
            }
            //if the line is empty and this is the first iteration of the for loop, 
            else if (str == null && i == 0) {
                c.setColor(Color.white);
                c.setFont(new Font ("MonoSpaced", Font.BOLD, 20));
                //say there is no one on the leaderboard
                c.drawString("There are currently no players on the leaderboard.", 110, 250);
                //break out of the for loop
                break;  
            } 
            //if there aren't enough terms in Leaderboard.txt to fill up the leaderboard,
            else {
                //break out of the for loop, and say nothing (everything before this happens is printed).
                break;
            }
        }
    }//end of printLeaderboard method
}
