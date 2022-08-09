package eu.hetes.ScoreBoard;

import java.util.ServiceLoader;

public class ScoreBoardFactory {

    private static final IScoreBoard scoreBoard = ServiceLoader.load(IScoreBoard.class).findFirst().orElseGet(ScoreBoard::new);

    public static IScoreBoard getInstance() {
        return ScoreBoardFactory.scoreBoard;
    }

}
