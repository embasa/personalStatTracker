package stats;

import java.util.ArrayList;
import java.util.List;

/**
 *  Manages classes
 * Created by bruno on 11/15/15.
 *
 */
public class Controller {
  public static void main(String[] args) {
    List<String> names = new ArrayList<>();
    //names.add("waflestomped");
    names.add("zlig");
    //names.add("caguana");
    LeagueMiner leagueMiner = new LeagueMiner("237e2c75-c0f3-47fa-855b-19f16d87adad", names);
    System.out.println("main");
    //leagueMiner.clearContent();
    long start = System.currentTimeMillis();
    leagueMiner.getUpdatedMatchLists();
    System.out.println("end: " + ((System.currentTimeMillis() - start)/60000));
    //Thread thread = new Thread(leagueMiner);
    //thread.start();
  }
}
