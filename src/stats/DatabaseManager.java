package stats;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

/**
 * This class holds a connection to the database and inserts the
 * proper entries.
 * Created by bruno on 10/19/15.
 */

public class DatabaseManager
{

  private void createTables(){
    Statement statement;
    try {
      statement = this.conn.createStatement();
      /*
      String dropSummonerGameTable = "DROP TABLE IF EXISTS summonerGames";
      String dropSummonerTable = "DROP TABLE IF EXISTS summoner";
      String dropGameStatsTable = "DROP TABLE IF EXISTS gameStats";

      statement.executeUpdate(dropSummonerGameTable);
      statement.executeUpdate(dropSummonerTable);

      */
      statement.executeUpdate("DROP TABLE IF EXISTS gameChampions");

      String summonerGameTable = "CREATE TABLE IF NOT EXISTS summonerGames(" +
          "summonerID BIGINT NOT NULL, gameID BIGINT NOT NULL, " +
          "teamCode INT,championID INT, kills BIGINT, deaths BIGINT, assists BIGINT, gold BIGINT," +
          "leaguePoints INT,division VARCHAR(255),tier VARCHAR(255)," +
          "PRIMARY KEY(summonerID,gameID)," +
          "CONSTRAINT FOREIGN KEY (summonerID) REFERENCES summoner(summonerID)," +
          "CONSTRAINT FOREIGN KEY (gameID) REFERENCES gameStats(gameID))" ;

      String summonerTable = "CREATE TABLE IF NOT EXISTS summoner(" +
          "summonerID BIGINT NOT NULL, summonerName VARCHAR(255) NOT NULL," +
          "PRIMARY KEY(summonerID))";

      String gameStatsTable = "CREATE TABLE IF NOT EXISTS gameStats(" +
          "gameID BIGINT NOT NULL," +
          "dateCreated BIGINT NOT NULL," +
          "timePlayed BIGINT NOT NULL ," +
          "winner INT NOT NULL," +
          "PRIMARY KEY(gameID))";


      statement.executeUpdate(summonerTable);
      statement.executeUpdate(gameStatsTable);
      statement.executeUpdate(summonerGameTable);
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean addSummonerMatchEntry(SummonerMatchEntry entry){
    try {
      String gameStats = "INSERT INTO gameStats(gameID,dateCreated,timePlayed,winner) values (?,?,?,?)";
      String summoner = "INSERT INTO summoner(summonerID,summonerName) values (?,?)";
      String summonerGames = "INSERT INTO summonerGames(summonerID,gameID,teamCode,championID,kills," +
          "deaths,assists,gold,leaguePoints,division,tier) values (?,?,?,?,?,?,?,?,?,?,?)";
      PreparedStatement stmt = this.conn.prepareStatement(gameStats);
      stmt.setLong(1,entry.getGameID());
      stmt.setLong(2,entry.getMatchCreation());
      stmt.setLong(3,entry.getMatchDuration());
      stmt.setInt(4,entry.getWinningTeamCode());
      try {
      //System.out.println("g: " + stmt.executeUpdate());
      stmt.executeUpdate();
      }catch (MySQLIntegrityConstraintViolationException e){

      }

      stmt.clearParameters();
      stmt = this.conn.prepareStatement(summoner);
      stmt.setLong(1,entry.getSummonerID());
      stmt.setString(2,entry.getSummonerName());

      try {
        //System.out.println("g: " + stmt.executeUpdate());
        stmt.executeUpdate();
      }catch (MySQLIntegrityConstraintViolationException e){
      }
      stmt.clearParameters();
      stmt = this.conn.prepareStatement(summonerGames);
      stmt.setLong(1,entry.getSummonerID());
      stmt.setLong(2,entry.getGameID());
      stmt.setInt(3,entry.getTeamCode());
      stmt.setInt(4,entry.getChampionID());
      stmt.setLong(5,entry.getKills());
      stmt.setLong(6,entry.getDeaths());
      stmt.setLong(7,entry.getAssists());
      stmt.setLong(8,entry.getGold());
      stmt.setInt(9,entry.getLeaguePoints());
      stmt.setString(10,entry.getDivision());
      stmt.setString(11,entry.getTier());
      try {
        //System.out.println("g: " + stmt.executeUpdate());
        stmt.executeUpdate();
      }catch (MySQLIntegrityConstraintViolationException e){
      }
      stmt.close();
    }catch (SQLException e){
      e.printStackTrace();
    }
    return true;
  }

  public DatabaseManager(){
    try {
      Class.forName(JDBC_DRIVER);
      System.out.println("Connecting to database...");
      this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
      this.createTables();
    }catch (SQLException | ClassNotFoundException e){
      e.printStackTrace();
    }
  }
  public void clearTables(){
    try {
      Statement statement = this.conn.createStatement();
      statement.executeUpdate("DELETE FROM summonerGames");
      statement.executeUpdate("DELETE FROM gameStats");
      statement.executeUpdate("DELETE FROM summoner");
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }


  private Connection conn;
  private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/leagueData";
  private static final String USER = "root";
  private static final String PASS = "pass";
}
