package eu.hetes.ScoreBoard;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class ScoreTest {

    @Test
    public void test_Score() {
        String hTeam = "Slovakia";
        String aTeam = "USA";
        Score score = new Score(hTeam, aTeam);
        Assertions.assertEquals(hTeam, score.getHomeTeam());
        Assertions.assertEquals(aTeam, score.getAwayTeam());
        Assertions.assertEquals(0, score.getHomeScore());
        Assertions.assertEquals(0, score.getAwayScore());
        score.setHomeScore(3);
        score.setAwayScore(2);
        Assertions.assertEquals(3, score.getHomeScore());
        Assertions.assertEquals(2, score.getAwayScore());
        Assertions.assertEquals("Slovakia (3 - 2) USA", score.toString());
    }


    @Test
    public void test_Ordering() throws InterruptedException {
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("Mexico", "Canada").setScores(0, 5));
        Thread.sleep(100);
        scores.add(new Score("Spain", "Brazil").setScores(10, 2));
        Thread.sleep(100);
        scores.add(new Score("Germany", "France").setScores(2, 2));
        Thread.sleep(100);
        scores.add(new Score("Uruguay", "Italy").setScores(6, 6));
        Thread.sleep(100);
        scores.add(new Score("Argentina", "Australia").setScores(3, 1));
        Thread.sleep(100);
        Assertions.assertEquals(5, scores.size());
        List<Score> sorted = scores.stream().sorted().toList();
        Assertions.assertEquals("Uruguay (6 - 6) Italy", sorted.get(0).toString());
        Assertions.assertEquals("Spain (10 - 2) Brazil", sorted.get(1).toString());
        Assertions.assertEquals("Mexico (0 - 5) Canada", sorted.get(2).toString());
        Assertions.assertEquals("Argentina (3 - 1) Australia", sorted.get(3).toString());
        Assertions.assertEquals("Germany (2 - 2) France", sorted.get(4).toString());
    }

}
