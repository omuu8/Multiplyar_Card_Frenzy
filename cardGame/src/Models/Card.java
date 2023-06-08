package Models;

public class Card {
    // Here will make constructor for number of card,suits
    private int rank;
    private Suits suits;

    public Card(int rank, Suits suits) {
        this.rank = rank;
        this.suits = suits;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Suits getSuits() {
        return suits;
    }

    public void setSuits(Suits suits) {
        this.suits = suits;
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suits=" + suits +
                '}';
    }
}
