import java.sql.*;

/**
 * This class holds a connection to the database and inserts the
 * proper entries.
 * Created by bruno on 10/19/15.
 */
public class DatabaseManager
{

  public boolean addSummonerMatchEntry(SummonerMatchEntry entry){
    try {
      System.out.println("Creating statement...");
      String sql = "";

      //this.summonerTable.setLong(1,entry.getSummonerID());
      //this.summonerTable.setInt(1,entry.getSummonerID());
      //this.gameStatsTable.setLong();
      this.summonerTable.setString(2,entry.getSummonerName());
      this.summonerTable.executeUpdate();

      ResultSet rs = stmt.executeQuery(sql);
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      while (rs.next()) {
        //Retrieve by column name
        System.out.println(rs.getString(1));
      }
      //STEP 6: Clean-up environment
    }catch (SQLException e){
      e.printStackTrace();
    }finally {
      try
      {
        if (stmt != null)
          stmt.close();
      } catch (SQLException se2)
      {
        se2.printStackTrace();
      }// nothing we can do
      try
      {
        if (conn != null)
          conn.close();
      } catch (SQLException se)
      {
        se.printStackTrace();
      }//end finally try
    }
    return true;
  }

  public static void main(String[] args)
  {
    System.out.println("Goodbye!");
  }

  public DatabaseManager(){
    String gameChampion = "INSERT INTO gameChampions(summonerID,gameID,championID," +
        "kills,deaths,assist,gold) " +
        "values (?,?,?,?,?,?,?)";
    String gameStats = "INSERT INTO gameStats(gameID,dateCreated,timePlayed,winner) values (?,?,?,?)";
    String summoner = "INSERT INTO summoner(summonerID,summonerName) values (?,?)";
    String summonerGames = "INSERT INTO summonerGames(summonerID,gameID,teamCode) values (?,?,?)";
    String summonerRankDuringGame ="INSERT INTO summonerRankDuringGame(summonerID,gameID,leaguePoints," +
        "division,tier) values (?,?,?,?,?)";

    try {
      Class.forName(JDBC_DRIVER);
      System.out.println("Connecting to database...");
      this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
      this.gameChampionTable = this.conn.prepareStatement(gameChampion);
      this.gameStatsTable = this.conn.prepareStatement(gameStats);
      this.summonerTable = this.conn.prepareStatement(summoner);
      this.summonerGamesTable = this.conn.prepareStatement(summonerGames);
      this.summonerRankDuringGameTable = this.conn.prepareStatement(summonerRankDuringGame);
    }catch (SQLException | ClassNotFoundException e){
      e.printStackTrace();
    }
  }

  private Connection conn;
  private Statement stmt;
  private PreparedStatement gameChampionTable = null;
  private PreparedStatement summonerTable = null;
  private PreparedStatement summonerGamesTable = null;
  private PreparedStatement gameStatsTable = null;
  private PreparedStatement summonerRankDuringGameTable = null;
  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/leagueData";
  private static final String USER = "root";
  private static final String PASS = "pass";
}
