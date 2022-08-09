package eu.hetes.ScoreBoard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreBoardFactoryTest {

    @Test
    public void test_GetInstance() {
        Assertions.assertTrue(ScoreBoardFactory.getInstance() instanceof ScoreBoard);
    }

}
