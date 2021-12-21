import java.awt.*;
import hsa.*;
import java.lang.*;

public class Clock implements Runnable {
    private static Console clockCon;
    public static int time;
    private boolean running;

    public Clock () {
        running = true;
    } //class constructor

    public void clockRun () {
        time = 30;
        clockCon = new Console (20, 40);
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
        clockCon.setFont (new Font ("Monospaced", Font.PLAIN, 200));
        while (time >= 0 && running) {
            //erasure comes before
            clockCon.setColor (new Color (1, 55, 99));
            clockCon.drawString (time + 1 + "", 40, 230);

            //drawing stuff
            try {
                clockCon.setColor (Color.white);
                clockCon.drawString (time + "", 40, 230);
                Thread.sleep (1000);
                
                time--;
            } catch (InterruptedException e) {
            }
        }
    }

    public boolean clockQuery () {
        return time == 0;
    }


    public void clockClose () {
        running = false;
        clockCon.close ();
    }

    public void run () {
        clockRun ();
    }
}
