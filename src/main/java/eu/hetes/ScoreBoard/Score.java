package eu.hetes.ScoreBoard;

import java.util.Date;

public class Score implements Comparable<Score> {

    private final Date createdOn;
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Score(String HomeTeam, String AwayTeam) {
        this.createdOn = new Date();
        this.homeTeam = HomeTeam;
        this.awayTeam = AwayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public String getHomeTeam() {
        return this.homeTeam;
    }

    public String getAwayTeam() {
        return this.awayTeam;
    }

    public int getHomeScore() {
        return this.homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return this.awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public Score setScores(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        return this;
    }

    @Override
    public int compareTo(Score o) {
        int scoresDiff = (this.homeScore + this.awayScore) - (o.homeScore + o.awayScore);
        if (scoresDiff != 0) {
            return -scoresDiff;
        } else {
            return -this.createdOn.compareTo(o.createdOn);
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%d - %d) %s", this.homeTeam, this.homeScore, this.awayScore, this.awayTeam);
    }
}
