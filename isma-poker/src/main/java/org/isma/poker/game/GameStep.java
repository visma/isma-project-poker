package org.isma.poker.game;

public class GameStep {
    private StepEnum step;
    private GameSession gameSession;
    private boolean stepOver;

    public GameStep(GameSession gameSession) {
        this.gameSession = gameSession;
        step = StepEnum.END;
    }

    public void nextStep() throws Exception {
        stepOver = false;
        step = step.nextStep();
        step.setUp(gameSession);
    }

    public void gotoEnd() {
        step = StepEnum.END;
    }


    public StepEnum getStep() {
        return step;
    }

    public void finish() {
        stepOver = true;
    }

    public boolean isOver() {
        return stepOver;
    }

    @Override
    public String toString() {
        return step.name();
    }

}
