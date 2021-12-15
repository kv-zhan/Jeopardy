import java.awt.*;
import hsa.Console;
import java.io.*;

public class Leaderboard {
    private Console c;
    public int scores[];
    public String names[];
    private Color fillColor = new Color(255,220,100);
  
    public Leaderboard(Console con) {
	c = con;
	scores = new int[11];
	names = new String[11];
    }
    
    public void sortLeaderboard() throws IOException {
	FileWriter fwriter = new FileWriter("dataFiles/Leaderboard.txt");
	int count = 0;
	for(int i = 0; i < 11; i++) {
	    if(names[i] == null) {
		break;
	    } else {
		count++;
	    }
	}
	
	//sorting
	for(int i = 0; i < count - 1; i++) {
	    for(int j = 0; j < count - 1; j++) {
		if(scores[j] < scores[j + 1]) {
		    int temp = scores[j];
		    String tempStr = names[j];
		    scores[j] = scores[j + 1];
		    names[j] = names[j + 1];
		    scores[j + 1] = temp;
		    names[j + 1] = tempStr;
		}
	    }
	}
	
	for(int i = 0; i < 10; i++) {
	    if(names[i] == null) {
		break;
	    } else {
		fwriter.write(names[i] + "\n");
		fwriter.write(scores[i] + "\n");
	    }
	}
	fwriter.close();
    }
    
    public void updateLeaderboard(String updateName, int updateScore) throws IOException {
	boolean updated = false;
	for(int i = 0; i < 11; i++) {
	    if(names[i] == null) {
		names[i] = updateName;
		scores[i] = updateScore;
		updated = true;
		break;
	    }
	}
	if(!updated) {
	    names[10] = updateName;
	    scores[10] = updateScore;
	}
	sortLeaderboard();
    }
    
    public void clearLeaderboard() throws IOException {
	FileWriter fwriter = new FileWriter("dataFiles/Leaderboard.txt");
	for(int i = 0; i < 11; i++) {
	    scores[i] = 0;
	    names[i] = null;
	}
	fwriter.write("");
	fwriter.close();
    }
    
    public void printLeaderboard() throws IOException {
	BufferedReader input = new BufferedReader(new FileReader("dataFiles/Leaderboard.txt"));
	c.setFont(new Font ("MonoSpaced", Font.BOLD, 40));
	c.setColor(Color.white);
	c.drawString("Leaderboard:", 270, 170);
	c.setFont(new Font ("MonoSpaced", Font.BOLD, 25));
	c.setColor(Color.black);
	for(int i = 0; i < 11; i++) {
	    String str = input.readLine();
	    if(str != null) {
		c.fillRect(140, 190 + 40 * i, 510, 45);
		c.setColor(new Color(255,220,100));
		c.fillRect(145, 195  + 40 * i, 248, 35);
		c.fillRect(397, 195  + 40 * i, 248, 35);
		c.setColor(Color.black);
		if(i == 9) {
		    c.drawString((i + 1) + "." + str, 150, 220 + i * 40);
		} else {
		    c.drawString((i + 1) + ". " + str, 150, 220 + i * 40);
		}
		c.drawString("Score: " + input.readLine(), 400, 220 + i * 40);
	    } else if (str == null && i == 0) {
		c.setColor(Color.white);
		c.setFont(new Font ("MonoSpaced", Font.BOLD, 20));
		c.drawString("There are currently no players on the leaderboard.", 110, 250);
		break;
	    } else {
		break;
	    }
	}
    }
}
