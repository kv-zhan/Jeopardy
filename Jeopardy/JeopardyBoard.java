import java.awt.*;
import hsa.Console;

public class JeopardyBoard {
    public boolean answered[][] = new boolean[5][5];
    private Console c;
    private Color fillColor = new Color(255,220,100);
    private Color blankColor = new Color(80, 80, 80);
    
    public JeopardyBoard(Console con) {
        c = con;
    }
    
    public void resetAnswered() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                answered[i][j] = false;
            }
        }
    }

    public void drawBoard(int level, String nameArr[]) {
        //draw background
        c.setColor(Color.white);
        c.fillRect(10, 610, 790, 70);
        //draw categories
        for(int i = 0; i < 5; i++) {
            //draw block
            c.setColor(Color.black);
            c.fillRoundRect(126 + i * 117, 175, 115, 55, 15, 15);
            c.setColor(fillColor);
            c.fillRoundRect(128 + i * 117, 177, 111, 51, 15, 15);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 15));
            c.setColor(Color.black);
            c.drawString(nameArr[i], 132 + i * 117, 205);
        }
        for(int i = 0; i < 5; i++) {
            c.setColor(Color.white);
            c.setFont(new Font("MonoSpaced", Font.BOLD, 30));
            c.drawString(i + 1 + "", 173 + i * 117, 545);
            switch (i) {
                case 0:
                    c.drawString("A", 90, 270 + i * 57);
                    break;
                case 1:
                    c.drawString("B", 90, 270 + i * 57);
                    break;
                case 2:
                    c.drawString("C", 90, 270 + i * 57);
                    break;
                case 3:
                    c.drawString("D", 90, 270 + i * 57);
                    break;
                case 4:
                    c.drawString("E", 90, 270 + i * 57);
                    break;
            }
        }
    
        //for the question stuff
        if(level == 3) {
            //draw block for final question
        } else {
            for(int i = 0; i < 5; i++) {
                for(int j = 0; j < 5; j++) {
                    if(!answered[i][j]) {
                        c.setColor(Color.black);
                        c.fillRoundRect(126 + j * 117, 235 + i * 57, 115, 55, 15, 15);
                        c.setColor(fillColor);
                        c.fillRoundRect(128 + j * 117, 237 + i * 57, 111, 51, 15, 15);
                        c.setFont(new Font("Serif", Font.BOLD, 40));
                        c.setColor(Color.black);
                        if(level == 2 && i == 4) {
                            c.drawString((i + 1) * 100 * level + "", 140 + j * 117, 280 + i * 57);
                        } else {
                            c.drawString((i + 1) * 100 * level + "", 150 + j * 117, 280 + i * 57);
                        }
                    //draw the block
                    } else {
                        c.setColor(Color.black);
                        c.fillRoundRect(126 + j * 117, 235 + i * 57, 115, 55, 15, 15);
                        c.setColor(blankColor);
                        c.fillRoundRect(128 + j * 117, 237 + i * 57, 111, 51, 15, 15);
                    }
                }
            }
        }
    }
    
    public boolean incomplete() {
        boolean check = false;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(!answered[i][j]) {
                    check = true;
                }
            }
        }
        return check;
    }
    
    public void removeQuestion(int row, int column) {
        answered[row][column] = true;
    }
        
    public boolean queryQuestion(int row, int column) {
        if(answered[row][column]) {
            return false;
        }
        return true;
    }
}
