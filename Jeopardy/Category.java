import java.io.*;
import java.lang.*;

public class Category {
    public String categoryName[];
    public String questions[][];
    public String answers[][];
    
    public Category (int arrSize) {
	categoryName = new String[arrSize];
	questions = new String[5][arrSize];
	answers = new String[5][arrSize];
    }

    public void fill(int preceding, int index) throws IOException {
	BufferedReader input = new BufferedReader(new FileReader("dataFiles/JeopardyData.txt"));
	for (int i = 0; i < preceding * 12; i++) {
	    input.readLine();
	}
	categoryName[index] = input.readLine();
	for (int i = 0; i < 5; i++) {
	    questions[i][index] = input.readLine();
	    answers[i][index] = input.readLine();
	}
    }
}
