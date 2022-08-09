package eu.hetes.ScoreBoard;

import java.util.HashMap;
import java.util.Map;

class ScoreBoard implements IScoreBoard {

    private final Map<String, Score> board = new HashMap<>();
    private final Map<String, Boolean> teams = new HashMap<>();
    private final Object lock = new Object();

    @Override
    public Score StartMatch(String HomeTeam, String AwayTeam) {
        this.validateParams(HomeTeam, AwayTeam);
        String key = ScoreBoard.createScoreKey(HomeTeam, AwayTeam);
        // Check if this match is not already in progress - return it if so
        synchronized (this.lock) {
            Score currentScore = this.board.get(key);
            if (currentScore != null) {
                return currentScore;
            }
        }
        // Check if any of specified teams is already playing - error if true
        synchronized (this.lock) {
            if (this.teams.containsKey(HomeTeam) || this.teams.containsKey(AwayTeam)) {
                throw new IllegalArgumentException("Cannot create new match with team active on board");
            }
        }
        // Create new Match and mark teams as playing
        synchronized (this.lock) {
            this.teams.put(HomeTeam, true);
            this.teams.put(AwayTeam, true);
            return this.board.put(key, new Score(HomeTeam, AwayTeam));
        }
    }

    @Override
    public Score EndMatch(String HomeTeam, String AwayTeam) {
        this.validateParams(HomeTeam, AwayTeam);
        String key = ScoreBoard.createScoreKey(HomeTeam, AwayTeam);
        // Clear Playing teams and return final match score if found, otherwise null
        synchronized (this.lock) {
            Score finalScore = this.board.remove(key);
            if (finalScore != null) {
                this.teams.remove(HomeTeam);
                this.teams.remove(AwayTeam);
            }
            return finalScore;
        }
    }

    @Override
    public Score UpdateScore(String HomeTeam, String AwayTeam, int HomeScore, int AwayScore) {
        this.validateParams(HomeTeam, AwayTeam, HomeScore, AwayScore);
        String key = ScoreBoard.createScoreKey(HomeTeam, AwayTeam);
        // Find ongoing match, update score and return it
        synchronized (this.lock) {
            Score ongoingScore = this.board.get(key);
            if (ongoingScore != null) {
                ongoingScore.setScores(HomeScore, AwayScore);
            }
            return ongoingScore;
        }
    }

    @Override
    public Iterable<Score> ViewScoreBoard() {
        synchronized (this.lock) {
            return this.board.values().stream().sorted().toList();
        }
    }

    private void validateParams(String HomeTeam, String AwayTeam) {
        if (HomeTeam == null) {
            throw new NullPointerException("Home Team cannot be null");
        }
        if (HomeTeam.length() == 0) {
            throw new IllegalArgumentException("Home Team cannot be empty string");
        }
        if (AwayTeam == null) {
            throw new NullPointerException("Away Team cannot be null");
        }
        if (AwayTeam.length() == 0) {
            throw new IllegalArgumentException("Away Team cannot be empty string");
        }
    }

    private void validateParams(String HomeTeam, String AwayTeam, int HomeScore, int AwayScore) {
        this.validateParams(HomeTeam, AwayTeam);
        if (HomeScore < 0) {
            throw new IllegalArgumentException("Home Score must be positive integer");
        }
        if (AwayScore < 0) {
            throw new IllegalArgumentException("Away Score must be positive integer");
        }
    }

    private static String createScoreKey(String HomeTeam, String AwayTeam) {
        return HomeTeam + "_" + AwayTeam;
    }
}
