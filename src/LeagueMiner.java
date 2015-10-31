import constant.QueueType;
import constant.Region;
import constant.Season;
import dto.Champion.Champion;
import dto.Match.*;
import dto.MatchList.MatchList;
import dto.MatchList.MatchReference;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BLAH BLAH BLAH BLAH
 * Created by bruno on 10/13/15.
 */
public class LeagueMiner {
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
    names.add( summonerName );
    names.add( "embasa" );
    //names.add("luciddark");
    LeagueMiner leagueMiner = new LeagueMiner( "fdb891b5-2179-4de3-bb61-c9efcf41293e", names );
    System.out.println( "monkey food" );
    leagueMiner.getUpdatedMatchLists();

    System.out.println( "Goodbye!" );

  }

  /**
   * This method will be invoked once every 5 hours.
   */
  public void getUpdatedMatchLists() {
    MatchList matchList = null;
    List< MatchReference > matches;

    for ( int i = 0; i < summonerNamesToMonitor.size(); i++ ) {
      try {
        matchList = keyChain.getMatchList( keyChain.getSummonerByName( summonerNamesToMonitor.get( i ) ).getId() );
      } catch ( RiotApiException rl ) {

        if ( rl.getErrorCode() == RiotApiException.RATE_LIMITED ) {
          try {
            Thread.sleep( 1000 );
          } catch ( InterruptedException ie ) {
            ie.printStackTrace();
          }
          i--;
          System.out.println( "RATE_LIMIT" );
        }
      }
      if ( matchList != null ) {
        matches = matchList.getMatches();
        if ( matches != null ) {
          System.out.println( "entries in matchList:" + matches.size() );
          for ( MatchReference match : matches ) {
            try {
              parseMatch( keyChain.getMatch( Region.NA, match.getMatchId() ) );
            } catch ( RiotApiException ee ) {
              if ( ee.getErrorCode() == RiotApiException.RATE_LIMITED ) {
                try {
                  Thread.sleep( 1000 );
                } catch ( InterruptedException ie ) {
                  ie.printStackTrace();
                }
                i--;
                System.out.println( "RATE_LIMIT" );
              }
            }
            System.out.println( "match count: " + matches.size() );
          }
        }
      }
    }
  }


  public void parseMatch( MatchDetail match ) {
    System.out.print( "queuetype: " + match.getQueueType() + "\n" );
    if ( ! match.getQueueType().equals( "RANKED_SOLO_5x5" ) ) {
      System.out.println();
      return;
    }
    List< Team > teams = match.getTeams();
    int winningTeamCode = 100;

    if ( teams.size() > 1 ) {
      if ( teams.get( 0 ).isWinner() ) {
        winningTeamCode = teams.get( 0 ).getTeamId();
      } else {
        winningTeamCode = teams.get( 1 ).getTeamId();
      }

    }
    long matchCreation = match.getMatchCreation();//#2
    long matchDuration = match.getMatchDuration();//#3
    long matchID = match.getMatchId();

    List< ParticipantIdentity > identityList = match.getParticipantIdentities();
    List< Participant > participantList = match.getParticipants();
    System.out.println( "match ID: " + match.getMatchId() );

    SummonerMatchEntry entry;
    System.out.printf( "%-12s%25s%10s%10s%10s%10s%12s%10s%20s%10s%40s%10s%n", "summonerID", "summoner name",
        "champID", "kills", "deaths", "assists", "gold spend", "teamid", " match id", "win team", "create",
        "duration" );
    for ( int i = 0; i < identityList.size(); i++ ) {
      Participant participant = participantList.get( i );
      Player player = identityList.get( i ).getPlayer();
      ParticipantStats participantStats = participantList.get( i ).getStats();
      System.out.printf( "%-12d%25s%10d%10d%10d%10d%12d%10d%20d%10d%40s%10d%n", player.getSummonerId(),
          player.getSummonerName(), participantList.get( i ).getChampionId(), participantStats.getKills(),
          participantStats.getDeaths(), participantStats.getAssists(), participantStats.getGoldSpent(),
          participantList.get( i ).getTeamId(), matchID, winningTeamCode, ( new Date( matchCreation ) ).toString(), matchDuration );

      entry = new SummonerMatchEntry( player.getSummonerId(),
          player.getSummonerName(), participantList.get( i ).getChampionId(), participantStats.getKills(),
          participantStats.getDeaths(), participantStats.getAssists(), participantStats.getGoldSpent(),
          participantList.get( i ).getTeamId(), matchID, winningTeamCode, matchCreation, matchDuration );
    }


  }
}
