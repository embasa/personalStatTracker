import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple demo that uses java.util.Timer to schedule a task
 * to execute once 5 seconds have passed.
 */

public class Reminder
{
  Timer timer;
  Integer integer = null;
  long startTime = 0;
  public Reminder(int seconds)
  {
    this.startTime = System.currentTimeMillis();
    integer = seconds;
    timer = new Timer();
  }

  public void runProgram() throws InterruptedException
  {
    int seconds = 5;
    while(true){
      Thread.sleep(3000);
      timer.schedule(new RemindTask(), seconds * 1000);
    }
  }

  class RemindTask extends TimerTask
  {
    public void run()
    {
      System.out.format("Time's up!%n");
      try
      {
        File file = new File("file");

        File flag = new File("end");
    	/* This logic is to create the file if the
       * file is not already present
    	 */

        if (!file.exists())
        {
          System.out.println("FILE DOES NOT EXIST!!");
          file.createNewFile();
        }

        //Here true is to append the content to file
        FileWriter fw = new FileWriter(file, true);
        //BufferedWriter writer give better performance
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(String.valueOf((System.currentTimeMillis() - startTime)/1000.0) + "\n");
        //Closing BufferedWriter Stream
        bw.close();

        System.out.println("Data successfully appended at the end of file");
        if(integer == 0 || flag.exists())
        {
          System.out.println("ENDING!");
          timer.cancel(); //Terminate the timer thread
          System.exit(0);
        }
        System.out.println("current count: " + integer);
        integer--;// subtract from timer

      } catch (IOException ioe)
      {
        System.out.println("Exception occurred:");
        ioe.printStackTrace();
      }
    }
  }

  public static void main(String args[]) throws InterruptedException
  {
    Reminder reminder = new Reminder(20);
    reminder.runProgram();
    System.out.format("Task scheduled.%n");
  }
}