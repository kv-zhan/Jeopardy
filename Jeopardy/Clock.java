import java.awt.*;
import hsa.*;
import java.lang.*;

public class Clock implements Runnable {
    Console clockCon;
    public int time;
    boolean running;

    public Clock () {
        running = true;
        clockCon = new Console (16, 40, "Timer");
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
    } //class constructor

    public void clockRun () {
        running = true;
        time = 30;
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
        clockCon.setFont (new Font ("Monospaced", Font.PLAIN, 200));
        while (time >= 0 && running) {
            //erasure comes before
            clockCon.setColor (new Color (1, 55, 99));
            clockCon.drawString (time + 1 + "", 45, 220);
            //drawing stuff
            try {
                clockCon.setColor (Color.white);
                clockCon.drawString (time + "", 45, 220);
                Thread.sleep (1000);
                time--;
            } catch (InterruptedException e) {
            }
        }
    }

    public int clockQuery () {
        return time;
    }


    public void clockClose () {
        running = false;
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
    }

    public void run () {
        clockRun ();
    }
}
