package SQL;
import java.io.Serializable;

@SuppressWarnings("serial")
public class JavaMatches implements Serializable {

    int season;	
	String place;
    String team1;
    String team2;
    String winner;
    String winnerByRuns;
    String winnerByWickets;
    String playerOfMatch;

    public JavaMatches() {
    }

    public JavaMatches(int season, String place, String team1, String team2, 
    		String winner, String winnerByRuns, String winnerByWickets, String playerOfMatch) {
        this.season = season;
        this.place = place;
        this.team1 = team1;
        this.team2 = team2;
        this.winner = winner;
        this.winnerByRuns = winnerByRuns;
        this.winnerByWickets = winnerByWickets;
        this.playerOfMatch = playerOfMatch;
    }

    
    public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getWinnerByRuns() {
		return winnerByRuns;
	}

	public void setWinnerByRuns(String winnerByRuns) {
		this.winnerByRuns = winnerByRuns;
	}

	public String getWinnerByWickets() {
		return winnerByWickets;
	}

	public void setWinnerByWickets(String winnerByWickets) {
		this.winnerByWickets = winnerByWickets;
	}

	public String getPlayerOfMatch() {
		return playerOfMatch;
	}

	public void setPlayerOfMatch(String playerOfMatch) {
		this.playerOfMatch = playerOfMatch;
	}

	@Override
    public String toString() {
        return season+" --- "+place+" --- "+team1+" --- "+team2+" --- "+winner+" --- "+
        			winnerByRuns+" --- "+winnerByWickets+" --- "+playerOfMatch;
    }
}