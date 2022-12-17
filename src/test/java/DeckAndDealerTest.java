import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckAndDealerTest {

    Dealer theDealer;
    Deck deck;

    @BeforeEach
    void init() {
        theDealer = new Dealer();
    }

    @Test
    void deckClassTest1() {
        deck = new Deck();
        assertEquals("Deck", deck.getClass().getName(), "Class needs to be equal to Deck");
    }

    @Test
    void deckClassTest2() {
        deck = new Deck();
        assertNotEquals("NotDeck", deck.getClass().getName(), "Deck class is anything but Deck");
    }

    @Test
    void theDealerTest1() {
        assertEquals("Deck", theDealer.getTheDeck().getClass().getName(), "Class needs to be to Deck");
    }

    @Test
    void theDealerTest2() {
        assertEquals(0, theDealer.dealersHand.size(), "Dealer is not null");
    }

    @Test
    void theDealerSizeTest1() {
        theDealer.dealersHand.add(new Card('C', 3));
        assertEquals(1, theDealer.dealersHand.size(), "Size of dealer's hand should be equal to 1");
    }

    @Test
    void theDealerSizeTest2() {
        theDealer.dealersHand.add(new Card('S', 6));
        theDealer.dealersHand.add(new Card('D', 8));
        assertEquals(2, theDealer.dealersHand.size(), "Size of dealer's hand should be equal to 2");
    }

    @Test
    void theDealerSizeTest3() {
        for (int x = 7; x < 12; x++) {
            theDealer.dealersHand.add(new Card('H', x));
        }
        assertEquals(5, theDealer.dealersHand.size(), "Size of dealer's hand should be equal to 5");
    }

    @Test
    void theDealerSizeTest4() {
        for (int x = 7; x < 12; x++) {
            theDealer.dealersHand.add(new Card('H', x));
        }
        theDealer.dealersHand.clear();
        assertEquals(0, theDealer.dealersHand.size(), "Size of dealer's hand should be equal to 0");
    }

    @Test
    void theDealerSizeTest5() {
        theDealer.dealersHand.add(new Card('H', 5));
        theDealer.dealersHand.add(new Card('S', 7));
        theDealer.dealersHand.clear();
        assertEquals(0, theDealer.dealersHand.size(), "Size of dealer's hand should be equal to 0");
    }

    @Test
    void theDealerSizeTest6() {
        theDealer.dealersHand.add(new Card('C', 4));
        theDealer.dealersHand.clear();

        assertEquals(0, theDealer.dealersHand.size(), "Size of dealer's hand should be 0");
    }
}


