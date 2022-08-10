package eu.hetes.ScoreBoard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class ScoreBoardTest {

    @Test
    public void test_NewMatch() {
        IScoreBoard scoreBoard = ScoreBoardFactory.getInstance();
        // New Match started with zero scores
        Score score1 = scoreBoard.StartMatch("Peru", "Moldova");
        Assertions.assertEquals("Peru (0 - 0) Moldova", score1.toString());
        // Update score & create same match - expect same score object
        score1.setScores(5, 2);
        Score score2 = scoreBoard.StartMatch("Peru", "Moldova");
        Assertions.assertEquals("Peru (5 - 2) Moldova", score2.toString());
        Assertions.assertEquals(score1, score2);
        // Create new match with one team in progress - exception
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.StartMatch("Peru", "USA"),
                "Cannot create new match with team active on board"
        );
    }

    @Test
    public void test_EndMatch() {
        IScoreBoard scoreBoard = ScoreBoardFactory.getInstance();
        // End non-existing match - expecting null
        Score s1 = scoreBoard.EndMatch("Jamaica", "China");
        Assertions.assertNull(s1);
        // Create new match, update its score, end it
        Score s2 = scoreBoard.StartMatch("Jamaica", "China");
        Assertions.assertNotNull(s2);
        s2.setScores(1, 2);
        Assertions.assertEquals("Jamaica (1 - 2) China", s2.toString());
        Score s3 = scoreBoard.EndMatch("Jamaica", "China");
        Assertions.assertEquals("Jamaica (1 - 2) China", s3.toString());
        Assertions.assertEquals(s2, s3);
    }

    @Test
    public void test_UpdateScore() {
        IScoreBoard scoreBoard = ScoreBoardFactory.getInstance();
        // Update score on non-existing match - expect null
        Score s1 = scoreBoard.UpdateScore("Mongolia", "Hungary", 2, 1);
        Assertions.assertNull(s1);
        // Create match, update score, expect same object with updated score
        Score s2 = scoreBoard.StartMatch("Mongolia", "Hungary");
        Score s3 = scoreBoard.UpdateScore("Mongolia", "Hungary", 2, 1);
        Assertions.assertEquals(s2, s3);
        Assertions.assertEquals("Mongolia (2 - 1) Hungary", s2.toString());
    }

    @Test
    public void test_ViewScoreBoard() throws InterruptedException {
        IScoreBoard scoreBoard = ScoreBoardFactory.getInstance();
        // Test from documentation sample
        scoreBoard.StartMatch("Mexico", "Canada");
        Thread.sleep(100);
        scoreBoard.StartMatch("Spain", "Brazil");
        Thread.sleep(100);
        scoreBoard.StartMatch("Germany", "France");
        Thread.sleep(100);
        scoreBoard.StartMatch("Uruguay", "Italy");
        Thread.sleep(100);
        scoreBoard.StartMatch("Argentina", "Australia");
        Thread.sleep(100);
        scoreBoard.UpdateScore("Mexico", "Canada", 0, 5);
        scoreBoard.UpdateScore("Spain", "Brazil", 10, 2);
        scoreBoard.UpdateScore("Germany", "France", 2, 2);
        scoreBoard.UpdateScore("Uruguay", "Italy", 6, 6);
        scoreBoard.UpdateScore("Argentina", "Australia", 3, 1);
        Iterator<Score> boardIterator = scoreBoard.ViewScoreBoard().iterator();
        Score s1 = boardIterator.next();
        Assertions.assertEquals("Uruguay (6 - 6) Italy", s1.toString());
        Score s2 = boardIterator.next();
        Assertions.assertEquals("Spain (10 - 2) Brazil", s2.toString());
        Score s3 = boardIterator.next();
        Assertions.assertEquals("Mexico (0 - 5) Canada", s3.toString());
        Score s4 = boardIterator.next();
        Assertions.assertEquals("Argentina (3 - 1) Australia", s4.toString());
        Score s5 = boardIterator.next();
        Assertions.assertEquals("Germany (2 - 2) France", s5.toString());
        Assertions.assertFalse(boardIterator.hasNext());
    }

    @Test
    public void test_ValidateParams() {
        IScoreBoard scoreBoard = ScoreBoardFactory.getInstance();
        Assertions.assertThrows(
                NullPointerException.class,
                () -> scoreBoard.UpdateScore(null, "Germany", 1, 2),
                "Home Team cannot be null"
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.UpdateScore("", "Germany", 1, 2),
                "Home Team cannot be empty string"
        );
        Assertions.assertThrows(
                NullPointerException.class,
                () -> scoreBoard.UpdateScore("Poland", null, 1, 2),
                "Away Team cannot be null"
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.UpdateScore("Poland", "", 1, 2),
                "Away Team cannot be empty string"
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.UpdateScore("Poland", "Germany", -1, 2),
                "Home Score must be positive integer"
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.UpdateScore("Poland", "Germany", 1, -2),
                "Away Score must be positive integer"
        );
    }
}
