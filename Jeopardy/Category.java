/**
    Derek Ma & Kevin Zhan
    January 11, 2022
    Ms. Basaraba
    Category is a custom class created to store information regarding categories. Since each game
    of Jeopardy utilizes many categories of questions, and since using the same set  of questions
    for every game gets very repetitive, multiple sets of questions were created. However, there
    was no way to select which sets get chosen. Thus, this class was created to read through a file
    containing all category sets and storing them. Then another object of this class would randomly
    select a few categories to use in the game. This class directly interacts with a file to read and
    obtain the necessary information.
**/

import java.io.*;
import java.lang.*;

public class Category {
    public String categoryName[]; //array to store the category names
    public String questions[][]; //2D array to store the questions in a category
    public String answers[][]; //2D array to store the answers in a category
    
    public Category (int arrSize) {
        //initializes the following arrays with the size of the category database
        categoryName = new String[arrSize];
        questions = new String[5][arrSize];
        answers = new String[5][arrSize];
    }//class constructor

    //method that fills the database of categories with the category name, questions, and answers
    public void fill(int preceding, int index) throws IOException {
        //declares a BufferedReader object for dataFiles/JeopardyData.txt
        BufferedReader input = new BufferedReader(new FileReader("dataFiles/JeopardyData.txt"));
        //iterates through lines that were previously read
        for (int i = 0; i < preceding * 12; i++) {
            //reads a line but does not use the inputted information
            input.readLine();
        }
        //sets the category name to the next inputted string
        categoryName[index] = input.readLine();
        //loops 5 times to iterate through the next 10 lines
        for (int i = 0; i < 5; i++) {
            //reads and stores a question
            questions[i][index] = input.readLine();
            //reads and stores an answer
            answers[i][index] = input.readLine();
        }
    }//ends fill method
}
