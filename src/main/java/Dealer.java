import java.util.ArrayList;

//  The Dealer class must check to see if there are more than 34 cards left in the deck.
//  If not, theDeck must be reshuffled with a new set of 52 cards in random order.
public class Dealer {
     Deck theDeck;

    public Deck getTheDeck() {
        return theDeck;
    }
    public void setTheDeck(Deck x) {
        this.theDeck = x;
    }

    // hold the dealers hand in each game
    ArrayList<Card> dealersHand;

    //initialize theDeck
    Dealer() {
        this.theDeck = new Deck();
        this.dealersHand = new ArrayList<Card>();
    }

    //will return an ArrayList<Card> of three cards removed from theDeck
    public ArrayList<Card> dealHand() {
        ArrayList<Card> toReturn = new ArrayList<Card>();
        int count = 0, x = 0;
        while (true) {
            /*
             Place 3 cards in array and check if card is picked from the deck
             else check to make new deck.
             */
            if (this.theDeck.get(x).getSuit() != 'X') {
                toReturn.add(this.theDeck.get(x));
                this.theDeck.set(x, new Card('X', 0));
                count++;
            } else {
                x++;
                continue;
            }
            if (count == 3) {
                break;
            }
            /*
            Before each game starts, the Dealer class must check to
            see if there are more than 34 cards left in the deck.
            If not, theDeck must be reshuffled with a new set of 52
            cards in random order.
             */
            if (this.theDeck.get(18).getSuit() == 'X') {
                this.theDeck.newDeck();
            }
        }
        return toReturn;
    }
}
