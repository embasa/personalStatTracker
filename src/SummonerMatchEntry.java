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


  public SummonerMatchEntry(long summonerID, String summonerName,String tier, String division, int leaguePoints,
                            long gameID, int teamCode, int championID,long kills, long deaths, long assists,
                            long gold,int winningTeamCode, long matchCreation, long matchDuration){
    /* summoner information */
    this.summonerID = summonerID;
    this.summonerName = summonerName;
    this.tier = tier;
    this.division = division;
    this.leaguePoints = leaguePoints;

    /* summoners game information */
    this.gameID = gameID;
    this.teamCode = teamCode;
    this.championID = championID;
    this.kills = kills;
    this.deaths = deaths;
    this.assists = assists;
    this.gold = gold;

    /* game information */
    this.winningTeamCode = winningTeamCode;
    this.matchCreation = matchCreation;
    this.matchDuration = matchDuration;
  }

  public SummonerMatchEntry(long summonerID, String summonerName,
                            long gameID, int teamCode, int championID,long kills, long deaths, long assists,
                            long gold,int winningTeamCode, long matchCreation, long matchDuration){
    this(summonerID,summonerName,null,null,0,gameID,teamCode,championID,kills,deaths,assists,
        gold,winningTeamCode,matchCreation,matchDuration);
  }

  public long getSummonerID(){
    return summonerID;
  }

  public void setSummonerID(long summonerID)
  {
    this.summonerID = summonerID;
  }

  public String getSummonerName(){
    return summonerName;
  }

  public void setSummonerName(String summonerName)
  {
    this.summonerName = summonerName;
  }

  public long getGameID()
  {
    return gameID;
  }

  public void setGameID(long gameID)
  {
    this.gameID = gameID;
  }

  public int getTeamCode()
  {
    return teamCode;
  }

  public void setTeamCode(int teamCode)
  {
    this.teamCode = teamCode;
  }

  public int getWinningTeamCode()
  {
    return winningTeamCode;
  }

  public void setWinningTeamCode(int winningTeamCode)
  {
    this.winningTeamCode = winningTeamCode;
  }

  public long getMatchCreation()
  {
    return matchCreation;
  }

  public void setMatchCreation(long matchCreation)
  {
    this.matchCreation = matchCreation;
  }

  public long getMatchDuration()
  {
    return matchDuration;
  }

  public void setMatchDuration(long matchDuration)
  {
    this.matchDuration = matchDuration;
  }

  public int getChampionID()
  {
    return championID;
  }

  public void setChampionID(int championID)
  {
    this.championID = championID;
  }

  public long getKills()
  {
    return kills;
  }

  public void setKills(long kills)
  {
    this.kills = kills;
  }

  public long getDeaths()
  {
    return deaths;
  }

  public void setDeaths(long deaths)
  {
    this.deaths = deaths;
  }

  public long getAssists()
  {
    return assists;
  }

  public void setAssists(long assists)
  {
    this.assists = assists;
  }

  public long getGold()
  {
    return gold;
  }

  public void setGold(long gold)
  {
    this.gold = gold;
  }

  public String getTier()
  {
    return tier;
  }

  public void setTier(String tier)
  {
    this.tier = tier;
  }

  public String getDivision()
  {
    return division;
  }

  public void setDivision(String division)
  {
    this.division = division;
  }

  public int getLeaguePoints()
  {
    return leaguePoints;
  }

  public void setLeaguePoints(int leaguePoints)
  {
    this.leaguePoints = leaguePoints;
  }

}
