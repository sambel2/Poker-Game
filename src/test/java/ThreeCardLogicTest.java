import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ThreeCardLogicTest {

    Player playerOne;
    Player playerTwo;
    Dealer theDealer;

    @BeforeEach
    void init() {
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();
    }

    @Test
    void straightTest1() {
        playerOne.hand.add(new Card('D', 6));
        playerOne.hand.add(new Card('S', 7));
        playerOne.hand.add(new Card('C', 8));
        assertEquals(3, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a straight");
    }

    @Test
    void straightTest2() {
        playerOne.hand.add(new Card('S', 5));
        playerOne.hand.add(new Card('D', 3));
        playerOne.hand.add(new Card('C', 4));
        assertEquals(3, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a straight");
    }

    @Test
    void straightTest3() {
        playerOne.hand.add(new Card('C', 9));
        playerOne.hand.add(new Card('D', 8));
        playerOne.hand.add(new Card('H', 7));
        assertEquals(3, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a straight");
    }

    @Test
    void straightTest4() {
        playerOne.hand.add(new Card('S', 8));
        playerOne.hand.add(new Card('H', 7));
        playerOne.hand.add(new Card('C', 6));
        assertEquals(3, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a straight");
    }

    @Test
    void straightFlushTest1() {
        for (int x = 3; x < 6; x++) {
            playerOne.hand.add(new Card('C', x));
        }
        assertEquals(1, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 1 straight flush");
    }

    @Test
    void straightFlushTest2() {
        for (int x = 3; x < 6; x++) {
            playerOne.hand.add(new Card('H', x));
        }
        assertEquals(1, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 1 straight flush");
    }

    @Test
    void straightFlushTest3() {
        for (int x = 3; x < 6; x++) {
            playerOne.hand.add(new Card('S', x));
        }
        assertEquals(1, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 1 straight flush");
    }

    @Test
    void straightFlushTest4() {
        for (int x = 3; x < 6; x++) {
            playerOne.hand.add(new Card('D', x));
        }
        assertEquals(1, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 1 straight flush");
    }

    @Test
    void threeOfKindTest1() {
        playerOne.hand.add(new Card('H', 8));
        playerOne.hand.add(new Card('D', 8));
        playerOne.hand.add(new Card('S', 8));
        assertEquals(2, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 3 of a kind");
    }

    @Test
    void threeOfKindTest2() {
        playerOne.hand.add(new Card('H', 4));
        playerOne.hand.add(new Card('S', 4));
        playerOne.hand.add(new Card('C', 4));
        assertEquals(2, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 3 of a kind");
    }

    @Test
    void threeOfKindTest3() {
        playerOne.hand.add(new Card('D', 2));
        playerOne.hand.add(new Card('H', 2));
        playerOne.hand.add(new Card('S', 2));
        assertEquals(2, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 3 of a kind");
    }

    @Test
    void threeOfKindTest4() {
        playerOne.hand.add(new Card('H', 7));
        playerOne.hand.add(new Card('C', 7));
        playerOne.hand.add(new Card('H', 7));
        assertEquals(2, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to 3 of a kind");
    }

    @Test
    void flushTest1() {
        playerOne.hand.add(new Card('C', 1));
        playerOne.hand.add(new Card('C', 3));
        playerOne.hand.add(new Card('C', 5));
        assertEquals(4, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a flush");
    }

    @Test
    void flushTest2() {
        playerOne.hand.add(new Card('S', 5));
        playerOne.hand.add(new Card('S', 8));
        playerOne.hand.add(new Card('S', 13));
        assertEquals(4, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a flush");
    }

    @Test
    void flushTest3() {
        playerOne.hand.add(new Card('D', 2));
        playerOne.hand.add(new Card('D', 8));
        playerOne.hand.add(new Card('D', 3));
        assertEquals(4, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal toe a flush");
    }

    @Test
    void flushTest4() {
        playerOne.hand.add(new Card('H', 8));
        playerOne.hand.add(new Card('H', 4));
        playerOne.hand.add(new Card('H', 9));
        assertEquals(4, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a flush");
    }

    @Test
    void pairTest1() {
        playerOne.hand.add(new Card('S', 2));
        playerOne.hand.add(new Card('H', 2));
        playerOne.hand.add(new Card('S', 5));
        assertEquals(5, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a pair");
    }

    @Test
    void pairTest2() {
        playerOne.hand.add(new Card('S', 5));
        playerOne.hand.add(new Card('C', 5));
        playerOne.hand.add(new Card('S', 8));
        assertEquals(5, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a pair");
    }

    @Test
    void pairTest3() {
        playerOne.hand.add(new Card('S', 3));
        playerOne.hand.add(new Card('H', 6));
        playerOne.hand.add(new Card('C', 3));
        assertEquals(5, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a pair");
    }

    @Test
    void pairTest4() {
        playerOne.hand.add(new Card('C', 6));
        playerOne.hand.add(new Card('D', 6));
        playerOne.hand.add(new Card('H', 2));
        assertEquals(5, ThreeCardLogic.evalHand(playerOne.hand), "Test must equal to a pair");
    }

    @Test
    void pairPlusBetTest1() {
        // straight flush
        playerOne.hand.add(new Card('C', 1));
        playerOne.hand.add(new Card('C', 2));
        playerOne.hand.add(new Card('C', 3));
        assertEquals(41, ThreeCardLogic.evalPPWinnings(playerOne.hand, 1), "Bet returned has to equal to 41");
    }

    @Test
    void pairPlusBetTest2() {
        // straight
        playerOne.hand.add(new Card('S', 4));
        playerOne.hand.add(new Card('D', 5));
        playerOne.hand.add(new Card('C', 3));
        assertEquals(14, ThreeCardLogic.evalPPWinnings(playerOne.hand, 2), "Bet returned has to equal to 14");
    }

    @Test
    void pairPlusBetTest3() {
        // three of a kind
        playerOne.hand.add(new Card('H', 3));
        playerOne.hand.add(new Card('D', 3));
        playerOne.hand.add(new Card('S', 3));
        assertEquals(93, ThreeCardLogic.evalPPWinnings(playerOne.hand, 3), "Bet returned has to equal to 93");
    }


    @Test
    void compareHandsTest1() {
        playerOne.hand.add(new Card('C', 13));
        playerOne.hand.add(new Card('H', 13));
        playerOne.hand.add(new Card('S', 5));

        theDealer.dealersHand.add(new Card('C', 13));
        theDealer.dealersHand.add(new Card('S', 12));
        theDealer.dealersHand.add(new Card('H', 5));

        assertEquals(2, ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand), "Player should be the winner");
    }

    @Test
    void compareHandsTest2() {
        theDealer.dealersHand.add(new Card('C', 8));
        theDealer.dealersHand.add(new Card('H', 9));
        theDealer.dealersHand.add(new Card('S', 12));

        playerOne.hand.add(new Card('C', 9));
        playerOne.hand.add(new Card('S', 8));
        playerOne.hand.add(new Card('H', 4));
        assertEquals(1, ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand), "Dealer has to win");
    }

    @Test
    void compareHandsTest3() {
        theDealer.dealersHand.add(new Card('C', 5));
        theDealer.dealersHand.add(new Card('H', 6));
        theDealer.dealersHand.add(new Card('S', 5));

        playerOne.hand.add(new Card('C', 5));
        playerOne.hand.add(new Card('H', 6));
        playerOne.hand.add(new Card('S', 5));

        assertEquals(0, ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand), "Draw, both hands are equal");
    }

    @Test
    void testPlayerOneClass() {
        assertEquals("Player", playerOne.getClass().getName(), "Player class not found");
    }

    @Test
    void testPlayerTwoClass() {
        assertEquals("Player", playerTwo.getClass().getName(), "Player class not found");
    }

    @Test
    void testDealerClass() {
        assertEquals("Dealer", theDealer.getClass().getName(), "Dealer class not found");
    }
}
