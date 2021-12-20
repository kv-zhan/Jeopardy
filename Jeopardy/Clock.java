import java.awt.*;
import hsa.*;
import java.lang.*;

public class Clock implements Runnable {
    private static Console clockCon;
    public static int time;
    private boolean running;
    
    public Clock() {
        running = true;
    }
    
    public void clockRun() {
        time = 30;
        clockCon = new Console(20, 40);
        while(time >= 0 && running) {
            clockCon.println(time);
            //Animation stuff
            try {
                Thread.sleep(1000);
                time--;
            } catch (InterruptedException e){ 
            }
        }
    }
    
    public boolean clockQuery() {
        return time == 0;
    }
    
    public void clockClose() {
        running = false;
        clockCon.close();
    }
    
    public void run() {
        clockRun();
    }
}
