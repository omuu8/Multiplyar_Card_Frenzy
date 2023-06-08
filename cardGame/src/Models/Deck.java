package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    // This class will provide shuffled deck of cards
    private ArrayList<Card> deck;

    public Deck(){
        deck = new ArrayList<Card>();
        // will add cards in deck
        for(Suits s : Suits.values()){
            for(int i = 1;i<=13;i++){
                deck.add(new Card(i,s));
                // added each card of suits in deck
            }
        }
        Collections.shuffle(deck);
    }

    public Deck(ArrayList<Card> deck){
        this.deck = deck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }
}
