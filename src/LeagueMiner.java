import constant.Region;
import constant.Season;
import dto.Game.*;
import dto.League.League;
import dto.League.LeagueEntry;
import dto.Match.*;
import dto.Match.Player;
import dto.MatchList.MatchList;
import dto.MatchList.MatchReference;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blah * Created by bruno on 10/13/15. */ public class LeagueMiner {
  private int maxEntry = 20;// get full matchLists of 100 summoners
  private int count = 0;
  private RiotApi keyChain = null;
  private List< String > summonerNamesToMonitor = null;
  private List< SummonerMatchEntry > entryList;


  public LeagueMiner( String key, List< String > summonerNames ) {
    this.keyChain = new RiotApi( key );
    this.keyChain.setSeason( Season.CURRENT );
    if ( summonerNames == null ) {
      this.summonerNamesToMonitor = new ArrayList<>();
    } else {
      this.summonerNamesToMonitor = new ArrayList<>( summonerNames.size() );
      this.summonerNamesToMonitor.addAll( summonerNames.stream().collect( Collectors.toList() ) );
      System.out.println( this.summonerNamesToMonitor.size() );
    }
  }

  public LeagueMiner( String key ) {
    this( key, null );
  }

  public static void main( String[] args ) throws InterruptedException, RiotApiException {
    String summonerName = "zlig";
    List< String > names = new ArrayList<>();
    names.add( "embasa" );
    names.add( summonerName );
    LeagueMiner leagueMiner = new LeagueMiner( "fdb891b5-2179-4de3-bb61-c9efcf41293e", names );
    leagueMiner.getUpdatedMatchLists();
    System.out.println( "Goodbye!" );
  }

  /**
   * This method is invoked at the start of all ranked games
   */
  public void getUpdatedMatchLists() {
    MatchList matchList = null;
    List< MatchReference > matches;
    for ( int i = 0; i < summonerNamesToMonitor.size(); i++ ) {
      try {
        long summonerID = keyChain.getSummonerByName( summonerNamesToMonitor.get( i ) ).getId();
        matchList = keyChain.getMatchList( summonerID );
      } catch ( RiotApiException rl ) {
        i = handleRiotApiExceptionInForLoop(rl);
        matchList = null;
      } catch ( NullPointerException e ) {
        System.out.println( "NULL POINTER" );
      }

      if ( matchList != null ) {
        matches = matchList.getMatches();
        if ( matches != null ) {
          for ( int j=0;j<matches.size();j++ ) {
            try {
              parseMatch( keyChain.getMatch(Region.NA, matches.get(j).getMatchId()) );
            } catch ( RiotApiException ee ) {
              j = handleRiotApiExceptionInForLoop(ee);
            }
          }
        }
      }
    }
  }

  /**
   * Helper method for parseMatch()
   * @param match the match parseMatch() is parsing
   * @return the int representing the winning team
   */
  private int getWinningTeamCode(MatchDetail match){
    List< Team > teams = match.getTeams();
    if ( teams.size() > 1 ) {
      if ( teams.get( 0 ).isWinner() ) {
        return teams.get( 0 ).getTeamId();
      } else {
        return teams.get( 1 ).getTeamId();
      }
    }
    return -1;// this should never be executed since all ranked solo matches had 2 teams
  }

  /**
   * For use by getSummonerLeagueStats().
   * Recursive method for returning the League of the specified
   * summoner
   * @param summonerID
   * @return an instance of dto.League
   */
  private List<League> getLeagueBySummoner(long summonerID){
    try {
      return keyChain.getLeagueBySummoner( summonerID );
    } catch ( RiotApiException rl ) {
      handleRiotApiExceptionInForLoop(rl);
      if( rl.getErrorCode() != RiotApiException.RATE_LIMITED ){
        return null;
      }
      return getLeagueBySummoner(summonerID);
    }
  }

  /**
   * Doesn't make any API call itself, uses helper method
   * to recursively call.
   * @param entry
   */
  private void getSummonerLeagueStats(SummonerMatchEntry entry){
    List<League> summonerLeagues = getLeagueBySummoner(entry.getSummonerID());
    if ( summonerLeagues != null )
      for ( League league : summonerLeagues ) {
        if ( "RANKED_SOLO_5x5".equals(league.getQueue()) ) {
          entry.setTier(league.getTier());
          for ( LeagueEntry leagueEntry : league.getEntries() ) {
            if ( Integer.parseInt( leagueEntry.getPlayerOrTeamId() ) == entry.getSummonerID() ) {
              entry.setDivision(leagueEntry.getDivision());
              entry.setLeaguePoints(leagueEntry.getLeaguePoints());
            }
          }
        }
      }
  }

  public void parseMatch( MatchDetail match ) {
    int spacing = 50;
    System.out.print("queuetype: " + match.getQueueType() + "\n");
    if ( ! match.getQueueType().equals( "RANKED_SOLO_5x5" ) ) {
      return;
    }
    /* get static match information */
    int winningTeamCode = getWinningTeamCode(match);
    long matchCreation = match.getMatchCreation();//#2
    long matchDuration = match.getMatchDuration();//#3
    long matchID = match.getMatchId();

    List< ParticipantIdentity > identityList = match.getParticipantIdentities();
    List< Participant > participantList = match.getParticipants();

    System.out.println( "match ID: " + match.getMatchId() );
    SummonerMatchEntry entry;
    System.out.printf( "%-12s%18s%8s%5s%5s%8s%8s%8s%8s%8s%8s%5s%12s%30s%10s%n",
        "summonerID", "name","tier", "div.", "LP",
        "champID", "kills", "deaths", "assists", "gold","teamid","win", " match id",
        "create", "duration" );
    for ( int i = 0; i < identityList.size(); i++ ) {
      Participant participant = participantList.get( i );
      Player player = identityList.get( i ).getPlayer();
      ParticipantStats participantStats = participantList.get( i ).getStats();

      entry = new SummonerMatchEntry( player.getSummonerId(), player.getSummonerName(), matchID,
          participant.getTeamId(), participant.getChampionId(), participantStats.getKills(),
          participantStats.getDeaths(), participantStats.getAssists(),participantStats.getGoldSpent(),
          winningTeamCode,matchCreation,matchDuration);

      getSummonerLeagueStats(entry);

      System.out.printf("%-12d%18s%8s%5s%5d%8d%8d%8d%8d%8d%8d%5d%12d%30s%10d%n",entry.getSummonerID(),
          entry.getSummonerName(),entry.getTier(),entry.getDivision(),entry.getLeaguePoints(), entry.getChampionID(),
          entry.getKills(), entry.getDeaths(), entry.getAssists(), entry.getGold(),entry.getTeamCode(),
          entry.getWinningTeamCode(), entry.getGameID(), ( new Date( entry.getMatchCreation() ) ).toString(),
          entry.getMatchDuration());
      count++;
      if(count == spacing){
        count=0;
        this.maxEntry--;
        if(maxEntry >  0)
          this.summonerNamesToMonitor.add(entry.getSummonerName());
      }
    }
  }

  public int handleRiotApiExceptionInForLoop(RiotApiException e){
    if ( e.getErrorCode() == RiotApiException.RATE_LIMITED ) {
      try {
        Thread.sleep( 1000 );
      } catch ( InterruptedException ie ) {
        ie.printStackTrace();
      }
      System.out.println( "RATE_LIMIT" );
      return -1;
    }
    return 0;
  }
}
