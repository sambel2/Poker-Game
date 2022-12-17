import java.util.ArrayList;
import java.util.Random;

public class Deck extends ArrayList<Card> {

    // Get Suits for cards!
    public char suitCheck(int i, char suit) {
        switch (i) {
            case 1:
                suit = 'C'; //clubs
                break;
            case 2:
                suit = 'D'; //diamonds
                break;
            case 3:
                suit = 'S'; //spades
                break;
            case 4:
                suit = 'H'; //hearts
                break;
            default:
                suit = 'X'; // unknown
                break;
        }
        return suit;
    }

    // method which creates deck
    ArrayList<Card> createDeck(ArrayList<Card> tempDeck) {
        int count = 0;
        for (int x = 2; x < 15; x++) {
            for (int i = 1; i < 5; i++) {
                char suit = ' ';
                suit = suitCheck(i, suit);
                tempDeck.add(count, new Card(suit, x));
                count++;
            }
        }
        return tempDeck;
    }

    // Place cards inside deck in random order.
    void randomizeDeck (ArrayList<Card> tempDeck, int x) {
        boolean setCard = true;
        int value = ((new Random()).nextInt((14 - 2) + 1)) + 2; //between 2 and 14 inclusive
        int suitInt = ((new Random()).nextInt((4 - 1) + 1)) + 1; //between 1 and 4 inclusive
        char suit = ' ';
        // Check if suit is in deck else break
        suit = suitCheck(suitInt, suit);
        Card card = tempDeck.get(x);
        // randomize deck
        int randomIndex = (new Random()).nextInt(52);
        while (this.get(randomIndex).getSuit() != 'X') {
            randomIndex = (new Random()).nextInt(52);
        }
        this.set(randomIndex, card);
    }

    // This class represents a 52 card, standard deck, of playing cards.
    Deck() {
        //make a temporary deck with cards inside
        ArrayList<Card> tempDeck = new ArrayList<Card>();
        tempDeck = createDeck(tempDeck);
        // initialize the array
        for (int x = 0; x < 52; x++) {
            this.add(x, new Card('X', 0));
        }   // randomize deck method
        for (int x = 0; x < 52; x++) {
            randomizeDeck (tempDeck, x);
        }
    }

    /* The second method will clear all the cards and create a
        brand new deck of 52 cards sorted in random order.
    */
    void newDeck() {
        ArrayList<Card> tempDeck = new ArrayList<Card>(); //make a temporary deck with all cards in there
        tempDeck = createDeck(tempDeck);
        // initialize the array
        for (int x = 0; x < 52; x++) {
            this.add(x, new Card('X', 0));
        } // call randomize method
        for (int x = 0; x < 52; x++) {
            randomizeDeck (tempDeck, x);
        }
    }
}
