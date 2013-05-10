package org.isma.poker.game.step;

public enum StepEnum implements Step {
    BLINDS(1, new BlindStep()),
    HANDS_DEALING(2, new HandsDealing()),
    BETS_1(3, new FirstBetStep()),
    FLOP(4, new CommunityCardsDealing(3)),
    BETS_2(5, new BetStep()),
    TURN(6, new CommunityCardsDealing(1)),
    BETS_3(7, new BetStep()),
    RIVER(8, new CommunityCardsDealing(1)),
    BETS_4(9, new BetStep()),
    SHOWDOWN(10, new ShowDownStep()),
    END(11, new ResultsStep());

    private int order;
    private AbstractStep step;

    StepEnum(int order, AbstractStep step) {
        this.order = order;
        this.step = step;
        step.setStep(this);
    }

    StepEnum(int order) {
        this(order, null);
    }

    public int getOrder() {
        return order;
    }


    public StepEnum nextStep() {
        switch (this) {
            case END:
                return BLINDS;
            case BLINDS:
                return HANDS_DEALING;
            case HANDS_DEALING:
                return BETS_1;
            case BETS_1:
                return FLOP;
            case FLOP:
                return BETS_2;
            case BETS_2:
                return TURN;
            case TURN:
                return BETS_3;
            case BETS_3:
                return RIVER;
            case RIVER:
                return BETS_4;
            case BETS_4:
                return SHOWDOWN;
            case SHOWDOWN:
                return END;
        }
        throw new RuntimeException("not handled step");
    }

    public void setUp(PokerStepGame game) throws InvalidStepActionException {
        if (step != null) {
            step.setUp(game);
        }
    }
}
