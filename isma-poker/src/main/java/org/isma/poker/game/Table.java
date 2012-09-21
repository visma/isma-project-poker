package org.isma.poker.game;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.isma.poker.model.Card;
import org.isma.poker.model.CommunityCards;
import org.isma.poker.model.Deck;
import org.isma.utils.collections.CircleList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    private static final int MAX_RAISES = 3;
    private final CommunityCards communityCards = new CommunityCards();
    private final CircleList<Player> players = new CircleList<Player>();
    private final CircleList<Player> inGamePlayers = new CircleList<Player>();
    private final CircleList<Player> remainingActionPlayers = new CircleList<Player>();
    private Player dealer;
    private Player smallBlindPlayer;
    private Player bigBlindPlayer;
    private Player underTheGunPlayer;
    private Player currentPlayer;

    private Pot pot = new Pot();
    private int currentBet;
    private int raisesRemaining;
    private final Map<Player, Integer> currentStepBet = new HashMap<Player, Integer>();

    /* ---------------------------------------------------------------------------------*/
    /* ------   GAME PREPARATION                                                        */
    /* ---------------------------------------------------------------------------------*/
    public void add(Player player) {
        players.add(player);
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   ROUND PREPARATION                                                       */
    /* ---------------------------------------------------------------------------------*/
    public void prepareNewRound() {
        pot.clear();
        inGamePlayers.clear();
        inGamePlayers.addAll(CollectionUtils.select(players, new HasMoneyPredicate()));
        for (Player inGamePlayer : inGamePlayers) {
            inGamePlayer.setFold(false);
        }
        moveButton();
        prepareNextBetStep(true);
    }

    protected void moveButton() {
        dealer = dealer == null ? inGamePlayers.get(0) : inGamePlayers.next(dealer);
        smallBlindPlayer = inGamePlayers.next(dealer);
        bigBlindPlayer = inGamePlayers.next(smallBlindPlayer);
        underTheGunPlayer = inGamePlayers.next(bigBlindPlayer);
        currentPlayer = underTheGunPlayer;
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   STEPS PREPARATION                                                       */
    /* ---------------------------------------------------------------------------------*/
    public void addSmallBlind(GameConfiguration gameConfiguration) {
        addToCurrentBet(gameConfiguration.getSmallBlindAmount());
    }

    public void addBigBlind(GameConfiguration gameConfiguration) {
        addToCurrentBet(gameConfiguration.getBigBlindAmount() - gameConfiguration.getSmallBlindAmount());
    }

    public void addBet(int amount) {
        addToCurrentBet(amount);
        betUpdateRemainingActionPlayers();
    }

    private void addToCurrentBet(int bet) {
        currentBet += bet;
    }

    public void prepareNextBetStep(boolean firstBetStep) {
        raisesRemaining = MAX_RAISES;
        if (!firstBetStep) {
            currentBet = 0;
            currentStepBet.clear();
            //TODO faux si underTheGUn est foldé
            currentPlayer = underTheGunPlayer;
        }
        updateRemainingActionPlayers();
    }

    public void prepareDealHandsStep(Deck deck) {
        int cardAmount = 2;
        List<Card> cards = deck.deal(inGamePlayers.size() * cardAmount);
        for (int i = 0; i < cardAmount; i++) {
            for (Player inGamePlayer : inGamePlayers) {
                inGamePlayer.getHand().add(cards.remove(0));
            }
        }
    }

    public void prepapreDealCommunityCardStep(Deck deck, int number) {
        List<Card> communityCards = deck.deal(number);
        this.communityCards.addAll(communityCards);
        for (Player inGamePlayer : inGamePlayers) {
            inGamePlayer.getHand().addAll(communityCards);
        }
    }

    public void prepareShowDown() {
        //TODO faux si underTheGUn est foldé
        currentPlayer = underTheGunPlayer;
        updateRemainingActionPlayers();
    }

    private void updateRemainingActionPlayers() {
        remainingActionPlayers.add(currentPlayer);
        while (remainingActionPlayers.size() < inGamePlayers.size()) {
            Player nextPlayer = inGamePlayers.next(remainingActionPlayers.get(remainingActionPlayers.size() - 1));
            remainingActionPlayers.add(nextPlayer);
        }
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   STEP ROTATION                                                           */
    /* ---------------------------------------------------------------------------------*/
    public boolean prepareNextPlayer() {
        remainingActionPlayers.remove(currentPlayer);
        if (existNextActionPlayer()) {
            return false;
        }
        currentPlayer = getNextPlayer();
        return true;
    }

    private boolean existNextActionPlayer() {
        return remainingActionPlayers.isEmpty() || isRoundOver();
    }

    public boolean isRoundOver() {
        return CollectionUtils.countMatches(inGamePlayers, new AlivePlayerPredicate()) == 1;
    }

    private void betUpdateRemainingActionPlayers() {
        int actionPlayersCount = CollectionUtils.countMatches(inGamePlayers, new PlayerActionPredicate());
        Player nextPlayer = inGamePlayers.next(currentPlayer, new PlayerActionPredicate());
        while (remainingActionPlayers.size() < actionPlayersCount) {
            if (!remainingActionPlayers.contains(nextPlayer)) {
                remainingActionPlayers.add(nextPlayer);
            }
            nextPlayer = inGamePlayers.next(nextPlayer, new PlayerActionPredicate());
        }
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   PLAYERS INTERACTIONS                                                    */
    /* ---------------------------------------------------------------------------------*/
    public void addToPot(Player player, int paid) {
        getPot().add(player, paid);
        int oldBet = currentStepBet.get(player) == null ? 0 : currentStepBet.get(player);
        currentStepBet.put(player, oldBet + paid);

    }

    public void removeFromRound(Player player) {
        player.setFold(true);
    }
    /* ---------------------------------------------------------------------------------*/

    public List<Player> getAllPlayers() {
        return players;
    }

    public List<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    public int getCurrentStepBet(Player player) {
        Integer currentBet = currentStepBet.get(player);
        return currentBet == null ? 0 : currentBet;
    }

    public Player getNextPlayer() {
        return inGamePlayers.next(currentPlayer, new PlayerActionPredicate());
    }

    public Player getDealer() {
        return dealer;
    }

    public Player getSmallBlindPlayer() {
        return smallBlindPlayer;
    }

    public Player getBigBlindPlayer() {
        return bigBlindPlayer;
    }

    public Player getUnderTheGunPlayer() {
        return underTheGunPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Pot getPot() {
        return pot;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public CommunityCards getCommunityCards() {
        return communityCards;
    }

    public int getRaisesRemaining() {
        return raisesRemaining;
    }

    public boolean hasRemainingPlayers() {
        return !remainingActionPlayers.isEmpty();
    }

    public void decreaseRaiseRemainings() {
        raisesRemaining--;
    }

    class HasMoneyPredicate implements Predicate {
        @Override
        public boolean evaluate(Object o) {
            return ((Player) o).hasChips();
        }
    }

    private class AlivePlayerPredicate implements Predicate {
        @Override
        public boolean evaluate(Object o) {
            return !((Player) o).isFold();
        }
    }

    public List<Player> getAlivePlayers() {
        List<Player> alivePlayers = new ArrayList<Player>(inGamePlayers);
        CollectionUtils.filter(alivePlayers, new AlivePlayerPredicate());
        return alivePlayers;
    }

    class PlayerActionPredicate implements Predicate {
        @Override
        public boolean evaluate(Object o) {
            return ((Player) o).hasChips() && !((Player) o).isFold();
        }
    }

}
