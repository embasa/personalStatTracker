import dto.Match.ParticipantIdentity;

import java.util.List;

/**
 * Created by bruno on 10/29/15.
 */
public class SummonerMatchEntry {
  private long summonerID;
  private String summonerName;
  private long gameID;
  private int teamCode;
  private int winningTeamCode;
  private long matchCreation;
  private long matchDuration;
  private int championID;
  private long kills;
  private long deaths;
  private long assists;
  private long gold;
  private String tier;
  private String division;
  private int leaguePoints;



  public SummonerMatchEntry( long summonerID, String summonerName, int championID,
                             long kills, long deaths, long assists, long gold, int teamCode,
                             long gameID,int winningTeamCode, long matchCreation, long matchDuration){
    this.summonerID = summonerID;
    this.summonerName = summonerName;
    this.gameID = gameID;
    this. teamCode = teamCode;
    this.matchCreation = matchCreation;
    this.matchDuration = matchDuration;
    this.championID = championID;
    this.kills = kills;
    this.deaths = deaths;
    this.assists = assists;
    this.gold = gold;
  }

}
