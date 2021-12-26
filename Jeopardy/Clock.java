/**
    Derek Ma & Kevin Zhan
    December 25, 2021
    Ms. Basaraba
    Clock is a custom class created for the purpose of a making a countdown timer
    when players are answering questions. This class implements Runnable in order
    to run on a separate thread from the main game, thus allowing the countdown
    to decide if a player runs out of time. The clock runs and is animated on its
    own separate console.
**/

import java.awt.*;
import hsa.*;
import java.lang.*;

public class Clock implements Runnable {
    Console clockCon; //separate console for this class
    int time; //instance variable for the time on the timer
    boolean running; //instance variable for if the timer is running

    public Clock () {
        running = true;
        clockCon = new Console (16, 40, "Timer");
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
    }//class constructor

    //method that starts the timer
    public void clockRun () {
        //sets running variable to true
        running = true;
        //sets time to 30
        time = 30;
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
        clockCon.setFont (new Font ("Monospaced", Font.PLAIN, 200));
        //loops until time is out
        //animates the timer
        while (time >= 0 && running) {
            //erases the previous time
            clockCon.setColor (new Color (1, 55, 99));
            clockCon.drawString (time + 1 + "", 45, 220);
            //try-catch statement for animating
            try {
                //displays the current time
                clockCon.setColor (Color.white);
                clockCon.drawString (time + "", 45, 220);
                //pauses for 1 second before the next time is displayed
                Thread.sleep (1000);
                //decrements the time
                time--;
            } catch (InterruptedException e) {
            }
        }
    }//ends clockRun method

    //method that returns the time remaining on the timer
    public int clockQuery () {
        //returns value of the time instance variable
        return time;
    }//ends clockQuery method

    //method that stops the current countdown
    public void clockClose () {
        //sets the running variable to false so that the clock is not running
        running = false;
        //fills the entire console with one colour to essentially clear it
        clockCon.setColor (new Color (1, 55, 99));
        clockCon.fillRect (10, 10, 390, 390);
    }//ends clockClose method

    //run method inherited from Runnable
    public void run () {
        //calls the clockRun method to start the countdown timer
        clockRun ();
    }//ends run method
}
