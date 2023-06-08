package UnitTests;

import Models.Card;
import Models.Deck;
import Models.Suits;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TestShuffledDeck {

    @Test
    public void checkDeckIfShuffled(){
        // This will test weather deck is shuffled or not
        // will create un-shuffled deck and will compare with shuffled deck
        ArrayList<Card> unShuffeldDeck = new ArrayList<Card>();
        for(Suits s : Suits.values()){
            for(int i = 1;i<=13;i++){
                unShuffeldDeck.add(new Card(i,s));
            }
        }
        Deck d = new Deck();// takin shuffled deck

        ArrayList<Card> shuffledDeck = d.getDeck();

        //now comparing shuffled and un-shuffled deck they should not be same
        Assert.assertFalse(shuffledDeck.toString() == unShuffeldDeck.toString());
    }
}
