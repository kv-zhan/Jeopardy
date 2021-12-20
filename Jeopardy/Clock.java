import java.awt.*;
import hsa.*;
import java.lang.*;

public class Clock implements Runnable {
    private static Console c;
    public static int time;
    public boolean running;
    
    public Clock() {
        running = true;
    }
    
    public void clockRun() {
        time = 30;
        c = new Console(20, 40);
        while(time >= 0 && running) {
            c.println(time);
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
        c.close();
    }
    
    public void run() {
        clockRun();
    }
}
