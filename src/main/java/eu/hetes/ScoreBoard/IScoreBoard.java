package eu.hetes.ScoreBoard;

public interface IScoreBoard {

    /**
     * Starts new Match with specified teams
     * @param HomeTeam Home Team Name
     * @param AwayTeam Away Team Name
     * @return Created Score object of the Match or found Score object if Match already in progress
     */
    Score StartMatch(String HomeTeam, String AwayTeam);

    /**
     * Ends existing Match
     * @param HomeTeam Home Team Name
     * @param AwayTeam Away Team Name
     * @return Final Score object of the Match or null if Match not found
     */
    Score EndMatch(String HomeTeam, String AwayTeam);

    /**
     * Update Score for specified Match
     * @param HomeTeam Home Team Name
     * @param AwayTeam Away Team Name
     * @param HomeScore Current Score of Home Team
     * @param AwayScore Current Score of Away Team
     * @return Updated Score object of the specified Match or null if match not found
     */
    Score UpdateScore(String HomeTeam, String AwayTeam, int HomeScore, int AwayScore);

    /**
     * Gets ScoreBoard Data
     * @return Ordered Scores from ScoreBoard
     */
    Iterable<Score> ViewScoreBoard();

}
