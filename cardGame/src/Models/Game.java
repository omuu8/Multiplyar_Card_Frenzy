package Models;

import Exceptions.PlayerCntException;
import UnitTests.TestForUniquePlayers;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private int playersCnt;

    private ArrayList<Player> players;

    private Player winner;
    ArrayList<Card> deckOfCards;

    private ArrayList<Card> drawPile;
    private ArrayList<Card> discardPile;

    public ArrayList<Card> getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(ArrayList<Card> drawPile) {
        this.drawPile = drawPile;
    }

    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

    private GameState gameState;

    private int nextMovePlayerIdx;

    public GameState getGameState() {
        return gameState;
    }
    public void dealCards(ArrayList<Player> players){
        // First getting the deck of cards
        deckOfCards = new Deck().getDeck();

        for(Player p : players){
            for(int cnt = 1;cnt<=5;cnt++){
                p.addCard(deckOfCards.get(deckOfCards.size()-1));
                deckOfCards.remove(deckOfCards.size()-1);
                // drawing last 5 cards from the deck and adding to each player p
            }
        }

    }
    public void createPiles(){

        discardPile = new ArrayList<>();
        discardPile.add(deckOfCards.get(0));
        deckOfCards.remove(0);

        // now the remaining part of deck will form drawPile
        drawPile = new ArrayList<>();

        drawPile.addAll(deckOfCards);

    }

    public void gameBegin(ArrayList<Player> ps){

        // Testing whether each player for its uniqueness

        int players_turn = 0;//we need to acces players from arraylist. so index starts from 0
        int gameDir = 1; // direction of player be clockwise

        int cardsDrawn = 1; // number of cards drawn from draw pile by one particular player
        int playersNum = ps.size();
        while (true)
        {

            if(drawPile.size() < cardsDrawn) // pile is not sufficient match draw!
            {
                gameState = GameState.DRAW;
                System.out.println("----- Result : GAME DRAW! Draw pile empty! -----");
                System.exit(0);
            }


            players_turn = players_turn % playersNum;

            if(players_turn < 0) // if turn goes in negative value if we change game flow/direction will make it positive
                players_turn = players_turn + playersNum;

            players_turn = players_turn % playersNum;


            //now check if current players card and top card of discard pile is matched or not.

            boolean cardMatched = false;
            int   matchNumber = -1;

            Card  tcOfDiscardPile= discardPile.get(discardPile.size()-1); // we are going to try matching with this card.

            System.out.println(" Discard pile top card is : " + tcOfDiscardPile);

            //now we will try to match this card with players card.

            for( Card strikePlayerCard : players.get(players_turn).getCards())
            {
                //if discard pile top card and any of players card has same suit or number then its a match.
                if(strikePlayerCard.getRank() == tcOfDiscardPile.getRank() || strikePlayerCard.getSuits() == tcOfDiscardPile.getSuits())
                {
                    int top_discard_card_num = tcOfDiscardPile.getRank();
                    //we need to check if top card of discard pile is action card. cause action cards are not stackable
                    if (top_discard_card_num == 1 || top_discard_card_num == 11 || top_discard_card_num == 12 || top_discard_card_num == 13)
                    {
                        // player cannot play same action card even if available so he will skip.
                        if (strikePlayerCard.getRank() == top_discard_card_num)
                            continue;
                    }



                    //let's check if current player needs to draw extra cards because of previous player used some action card

                    if(cardsDrawn > 1)//prev player used either Q or J
                    {
                        while (cardsDrawn > 0)
                        {
                            Card drawnCard = drawPile.get(drawPile.size()-1);
                            int currentPlayer = players.get(players_turn).getId();
                            System.out.println("player "+ currentPlayer +" drawing card:"+drawnCard);
                            //add in players hand
                            players.get(players_turn).addCard(drawnCard);
                            drawPile.remove(drawPile.size()-1);
                            cardsDrawn--;
                        }
                        cardsDrawn = 1; //reset the value to 1
                    }


                    //if card is matched then player puts the card in discard pile
                    System.out.println("--- Player "+players.get(players_turn).getId()+"'s card matched ---");
                    System.out.println("--- Now discard pile's top card is : "+strikePlayerCard+"---");

                    players.get(players_turn).removeCard(strikePlayerCard);
                    discardPile.add(strikePlayerCard);
                    cardMatched = true;
                    matchNumber = strikePlayerCard.getRank();
                    break;
                }
            }

            //if card is not matched then current player draws one card from draw pile

            if (cardMatched == false)
            {
                System.out.println("no matching card! player "+players.get(players_turn).getId()+" drawing "+cardsDrawn+" cards from draw pile");
                //if previous player used any action card then need to draw more cards

                while (cardsDrawn > 0)
                {
                    players.get(players_turn).addCard(drawPile.get(drawPile.size()-1));
                    drawPile.remove(drawPile.size()-1);
                    cardsDrawn--;
                }
                cardsDrawn = 1; //reset to 1
            }

            // if current players all cards are finished then he is the winner
            if (cardMatched == true && players.get(players_turn).getCards().size() == 0)
            {
                System.out.println("--- Congratulations player: " + players.get(players_turn).getId() + " won the match! ---");

                System.exit(0);
            }

            // Need to check Action Cards mathc

            //if player used Ace then next player's turn will be skipped
            if(cardMatched == true && matchNumber == 1)
            {
                players_turn = players_turn + gameDir;
            }

            //king : reverse the direction of game

            if(cardMatched == true && matchNumber == 13)
            {
                gameDir = gameDir * -1;
            }

            //Queen : next player needs to draw 2 cards

            if (cardMatched == true && matchNumber == 12) {
                cardsDrawn = 2;
            }

            //Jack : next player needs to draw 4 cards
            if (cardMatched == true && matchNumber == 11) {
                cardsDrawn = 4;
            }


            //determine next player's turn

            players_turn += gameDir;


            System.out.println("--------------------------------------------");
        }
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public static Builder getBuilder(){
        return new Builder();
    }
    private Game(int numOfPlayers,ArrayList<Player> players){
        this.nextMovePlayerIdx = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.playersCnt = numOfPlayers;
        this.players = players;
    }
    public static class Builder {
        private int playersCnt;

        private ArrayList<Player> players;

        private Builder() {
            this.playersCnt = 0;
            this.players = new ArrayList<>();
        }

        public Builder setPlayersCnt(int playersCnt) {
            this.playersCnt = playersCnt;
            return this;
        }

        public Builder setPlayers(ArrayList<Player> players) {
            this.players = players;
            return this;
        }
        public Builder addPlayer(Player player){
            this.players.add(player);
            return this;
        }
        public Game build() throws PlayerCntException{

            validate();

            return new Game(playersCnt,players);
        }
        private boolean validateNumberOfPlayersCnt() throws PlayerCntException {
            if(playersCnt < 2 && playersCnt > 4){
                return false;
            }
            return true;
        }
        private void validate() throws PlayerCntException{
            validateNumberOfPlayersCnt();

        }
    }

    }




