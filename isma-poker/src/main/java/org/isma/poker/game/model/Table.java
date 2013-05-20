package org.isma.poker.game.model;

import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.isma.poker.model.Card;
import org.isma.poker.model.CommunityCards;
import org.isma.poker.model.Deck;
import org.isma.utils.StringHelper;
import org.isma.utils.collections.CircleList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.collections.CollectionUtils.*;

public class Table {
    private static final Logger LOG = Logger.getLogger(Table.class);

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

    private final Pot pot = new Pot();
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
    protected void moveButton() {
        dealer = dealer == null ? inGamePlayers.get(0) : inGamePlayers.next(dealer);
        smallBlindPlayer = inGamePlayers.next(dealer);
        bigBlindPlayer = inGamePlayers.next(smallBlindPlayer);
        underTheGunPlayer = inGamePlayers.next(bigBlindPlayer);
        currentPlayer = smallBlindPlayer;
    }

    public void smallBlindSitOut(){
        Player oldSmallBlindPlayer = smallBlindPlayer;
        smallBlindPlayer = bigBlindPlayer;
        bigBlindPlayer = inGamePlayers.next(smallBlindPlayer);
        underTheGunPlayer = inGamePlayers.next(bigBlindPlayer);
        if (underTheGunPlayer == oldSmallBlindPlayer){
            underTheGunPlayer = inGamePlayers.next(underTheGunPlayer);
        }
        remainingActionPlayers.clear();
        remainingActionPlayers.add(currentPlayer);
        remainingActionPlayers.add(smallBlindPlayer);
        remainingActionPlayers.add(bigBlindPlayer);
    }

    public void bigBlindSitOut(){
        bigBlindPlayer = inGamePlayers.next(bigBlindPlayer);
        underTheGunPlayer = inGamePlayers.next(bigBlindPlayer);
        remainingActionPlayers.clear();
        remainingActionPlayers.add(currentPlayer);
        remainingActionPlayers.add(bigBlindPlayer);
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   STEPS PREPARATION                                                       */
    /* ---------------------------------------------------------------------------------*/
    public void prepareBlindsStep() {
        clearTable();
        moveButton();
        remainingActionPlayers.add(smallBlindPlayer);
        remainingActionPlayers.add(bigBlindPlayer);

    }

    private void clearTable() {
        pot.clear();
        communityCards.clear();
        inGamePlayers.clear();
        inGamePlayers.addAll(select(players, new HasMoneyPredicate()));
        for (Player inGamePlayer : inGamePlayers) {
            inGamePlayer.setFold(false);
        }
        remainingActionPlayers.clear();
        for (Player player : players) {
            player.getHand().clear();
        }
    }


    //TODO faire des test sur cette méthode pour bien controler les joueurs restants
    public void prepareBetStep(boolean firstBetStep) {
        raisesRemaining = MAX_RAISES;
        if (!firstBetStep) {
            currentBet = 0;
            currentStepBet.clear();
        }
        currentPlayer = underTheGunPlayer;
        updateCurrentPlayer(new PlayerBetPredicate());
        if (currentPlayer != null) {
            updateRemainingActionPlayers(new PlayerBetPredicate());
        }
    }

    //TODO faire des test sur cette méthode pour bien controler les joueurs restants
    public void prepareShowDown() {
        currentPlayer = underTheGunPlayer;
        updateCurrentPlayer(new AlivePlayerPredicate());
        updateRemainingActionPlayers(new AlivePlayerPredicate());
    }

    private void updateCurrentPlayer(Predicate predicate) {
        currentPlayer = underTheGunPlayer;
        while (!predicate.evaluate(currentPlayer)) {
            currentPlayer = inGamePlayers.next(currentPlayer, predicate);
            if (currentPlayer == null) {
                return;
            }
        }
    }

    //TODO TU pour controler l'ordre de distribution en fonction du bouton
    public void prepareDealHoleCardsStep(Deck deck) {
        int cardAmount = 2;
        List<Card> cards = deck.deal(inGamePlayers.size() * cardAmount);
        for (int i = 0; i < cardAmount; i++) {
            underTheGunPlayer.getHand().add(cards.remove(0));
            for (Player currPlayer : inGamePlayers.nextList(underTheGunPlayer)) {
                currPlayer.getHand().add(cards.remove(0));
            }
        }
    }

    public List<Card> prepapreDealCommunityCardStep(Deck deck, int number) {
        List<Card> newCards = deck.deal(number);
        this.communityCards.addAll(newCards);
        for (Player inGamePlayer : inGamePlayers) {
            inGamePlayer.getHand().addAll(newCards);
        }
        return newCards;
    }

    private void updateRemainingActionPlayers(Predicate predicate) {
        remainingActionPlayers.add(currentPlayer);
        int remainingCount = countMatches(inGamePlayers, predicate);
        while (remainingActionPlayers.size() < remainingCount) {
            Player nextPlayer = inGamePlayers.next(remainingActionPlayers.get(remainingActionPlayers.size() - 1), predicate);
            if (predicate.evaluate(nextPlayer)) {
                remainingActionPlayers.add(nextPlayer);
            }
        }
        LOG.debug("remainingActionPlayers : " + StringHelper.join(remainingActionPlayers, ", ", new StringHelper.JoinValueExtractor<Player>() {
            @Override
            public String joinValue(Player element) {
                return element.getNickname();
            }
        }));
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   MONEY HANDLING                                                           */
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

    /* ---------------------------------------------------------------------------------*/
    /* ------   STEP ROTATION                                                           */
    /* ---------------------------------------------------------------------------------*/
    public boolean prepareNextPlayer(boolean showdownStep) {
        remainingActionPlayers.remove(currentPlayer);
        if (existNextActionPlayer()) {
            return false;
        }
        currentPlayer = getNextPlayer(showdownStep);
        return true;
    }

    private boolean existNextActionPlayer() {
        return remainingActionPlayers.isEmpty() || isRoundOver();
    }

    public boolean isRoundOver() {
        return countMatches(inGamePlayers, new AlivePlayerPredicate()) == 1;
    }

    private void betUpdateRemainingActionPlayers() {
        //int actionPlayersCount = CollectionUtils.countMatches(inGamePlayers, new PlayerBetPredicate());
        Player firstEligiblePlayer = inGamePlayers.next(currentPlayer, new PlayerBetPredicate());
        if (firstEligiblePlayer == currentPlayer || firstEligiblePlayer == null) {
            return;
        }
        Player currentEligiblePlayer = firstEligiblePlayer;
        do {
            if (!remainingActionPlayers.contains(currentEligiblePlayer)) {
                remainingActionPlayers.add(currentEligiblePlayer);
            }
            currentEligiblePlayer = inGamePlayers.next(currentEligiblePlayer, new PlayerBetPredicate());
        } while (currentEligiblePlayer != firstEligiblePlayer && currentEligiblePlayer != null);
    }

    /* ---------------------------------------------------------------------------------*/
    /* ------   PLAYERS INTERACTIONS                                                    */
    /* ---------------------------------------------------------------------------------*/
    public void addToPot(Player player, int paid) {
        getPot().add(player, paid);
        int oldBet = currentStepBet.get(player) == null ? 0 : currentStepBet.get(player);
        currentStepBet.put(player, oldBet + paid);

    }

    public void handleFold(Player player) {
        player.setFold(true);
    }

    public void handleSitOut(Player player) {
        players.remove(players.indexOf(player));
        inGamePlayers.remove(inGamePlayers.indexOf(player));
        if (inGamePlayers.size() < 2){
            clearTable();
            dealer = null;
            currentPlayer = null;
            smallBlindPlayer = null;
            bigBlindPlayer = null;
            underTheGunPlayer = null;
        }
    }
    /* ---------------------------------------------------------------------------------*/

    public List<Player> getAllPlayers() {
        return players;
    }

    public int getCurrentStepBet(Player player) {
        Integer currentBet = currentStepBet.get(player);
        return currentBet == null ? 0 : currentBet;
    }

    public Player getNextPlayer(boolean showdownStep) {
        if (showdownStep) {
            return inGamePlayers.next(currentPlayer, new AlivePlayerPredicate());
        }
        return inGamePlayers.next(currentPlayer, new PlayerBetPredicate());
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

    public void decreaseRaiseRemainings() {
        raisesRemaining--;
    }

    public List<Player> getAliveWithChipsPlayers() {
        List<Player> alivePlayers = new ArrayList<Player>(inGamePlayers);
        filter(alivePlayers, new PlayerBetPredicate());
        return alivePlayers;
    }

    public List<Player> getAlivePlayers() {
        List<Player> alivePlayers = new ArrayList<Player>(inGamePlayers);
        filter(alivePlayers, new AlivePlayerPredicate());
        return alivePlayers;
    }

    private class HasMoneyPredicate implements Predicate {
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

    private class PlayerBetPredicate implements Predicate {
        @Override
        public boolean evaluate(Object o) {
            return ((Player) o).hasChips() && !((Player) o).isFold();
        }
    }

}
