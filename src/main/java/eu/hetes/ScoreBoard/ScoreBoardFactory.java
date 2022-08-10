package eu.hetes.ScoreBoard;

import java.util.ServiceLoader;

public class ScoreBoardFactory {

    public static IScoreBoard getInstance() {
        return ServiceLoader.load(IScoreBoard.class).findFirst().orElseGet(ScoreBoard::new);
    }

}
