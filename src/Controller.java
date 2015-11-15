import java.util.ArrayList;
import java.util.List;

/**
 *  Manages classes
 * Created by bruno on 11/15/15.
 */
public class Controller {
  public static void main(String[] args) {
    List<String> names = new ArrayList<>();
    names.add("embasa");
    LeagueMiner leagueMiner = new LeagueMiner("fdb891b5-2179-4de3-bb61-c9efcf41293e", names);
    System.out.println("main");
    //leagueMiner.clearContent();
    long start = System.currentTimeMillis();
    leagueMiner.getUpdatedMatchLists();
    System.out.println("end: " + ((System.currentTimeMillis() - start)/60000));
    //Thread thread = new Thread(leagueMiner);
    //thread.start();
  }
}

