import java.util.ArrayList;
import java.util.Arrays;

public class ThreeCardLogic {

    /*
        This class represents the logic in the game. The method evalHand will return an integer
        value representing the value of the hand passed in. It will return:
        • 0 if the hand just has a high card
        • 1 for a straight flush
        • 2 for three of a kind
        • 3 for a straight
        • 4 for a flush
        • 5 for a pai
     */
    public static int evalHand(ArrayList<Card> hand) {
        // Add values and suits into arrays
        ArrayList<Integer> cardValue = new ArrayList<Integer>();
        ArrayList<Character> cardSuit = new ArrayList<Character>();
        for (int x = 0; x < 3; x++) {
            cardValue.add(x, hand.get(x).getValue());
            cardSuit.add(x, hand.get(x).getSuit());
        }
        // Sort in ascending order
        int[] tempValueArray = {cardValue.get(0), cardValue.get(1), cardValue.get(2)};
        Arrays.sort(tempValueArray);
        for (int x = 0; x < 3; x++) {
            cardValue.add(x, tempValueArray[x]);
        }

        // Check for a straight flush- 	Three cards of the same suit in sequence
        if (((cardValue.get(0) + 1) == cardValue.get(1)) && ((cardValue.get(1) + 1) == cardValue.get(2))) {
            if ((cardSuit.get(0) == cardSuit.get(1)) && (cardSuit.get(1) == cardSuit.get(2))) {
                return 1;
            }
        }

        // Check for three of a kind - Three cards of the same value
        if ((cardValue.get(0) == cardValue.get(1)) && (cardValue.get(1) == cardValue.get(2))) {
            return 2;
        }

        // Check for a straight - Three cards-Non-suited-Non sequence
        if (((cardValue.get(0) + 1) == cardValue.get(1)) && ((cardValue.get(1) + 1) == cardValue.get(2))) {
            return 3;
        }

        // Check for a flush - 	Three cards of the same suit-not in sequence
        if ((cardSuit.get(0) == cardSuit.get(1)) && (cardSuit.get(1) == cardSuit.get(2))) {
            return 4;
        }

        // Check for pair - A single pair
        if ((cardValue.get(0) == cardValue.get(1)) || (cardValue.get(1) == cardValue.get(2))) {
            return 5;
        }
        // High card
        return 0;
    }

    /*
    The method evalPPWinnings will return the amount won for the PairPlus bet. It will
    evaluate the hand and then evaluate the winnings and return the amount won. If the
    player lost the Pair Plus bet, it will just return 0.
     */
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {

        // Straight Flush – 40:1 (bet + (bet * 40))
        if (evalHand(hand) == 1) {
            return bet + bet * 40 ;
        }

        // Three-of-a-Kind – 30:1 (bet + (bet * 30))
        if (evalHand(hand) == 2) {
            return bet + bet * 30;
        }

        // Straight – 6:1 (bet + (bet * 6))
        if (evalHand(hand) == 3) {
            return bet + bet * 6;
        }

        // Flush – 3:1 (bet + (bet * 3))
        if (evalHand(hand) == 4) {
            return bet + bet * 3;
        }

        // Pair – 1:1 (bet + bet)
        if (evalHand(hand) == 5) {
            return bet + bet;
        }
        // No Pair Plus - 0
        return 0;
    }

    // 0 if neither hand won
    // 1 if the dealer hand won
    // 2 if the player hand won
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        // Verify if dealer has a queen or higher else draw
        if ((dealer.get(0).getValue() < 12) && (dealer.get(1).getValue() < 12) && (dealer.get(2).getValue() < 12)) {
            return 0;
        }
        int dealerEval = evalHand(dealer);
        int playerEval = evalHand(player);

        // Player wins
        if ((dealerEval == 0) && (playerEval != 0)) {
            return 2;
        }
        // Dealer wins
        else if ((dealerEval != 0) && (playerEval == 0)) {
            return 1;
        }

        //both players have high cards
        else {
            ArrayList<Integer> playerCardValue = new ArrayList<Integer>();
            ArrayList<Integer> dealerCardValue = new ArrayList<Integer>();
            for (int x = 0; x < 3; x++) {
                playerCardValue.add(x, player.get(x).getValue());
                dealerCardValue.add(x, dealer.get(x).getValue());
            }
            // Sort player and dealer values in ascending order
            int[] playerValues = {playerCardValue.get(0), playerCardValue.get(1), playerCardValue.get(2)};
            int[] dealerValues = {dealerCardValue.get(0), dealerCardValue.get(1), dealerCardValue.get(2)};
            Arrays.sort(playerValues);
            Arrays.sort(dealerValues);

            if (playerValues[0] > dealerValues[0]) {
                return 2;             // Player wins
            } else if (playerValues[0] < dealerValues[0]) {
                return 1;             // Dealer wins
            } else {
                //  If first set of max values are same then compare second values
                if (playerValues[1] > dealerValues[1]) { // Player wins
                    return 2;
                } else if (playerValues[1] < dealerValues[1]) { // Dealer wins
                    return 1;
                } else {
                    //  If second set of max values are same then compare third values
                    if (playerValues[2] > dealerValues[2]) { // Player wins
                        return 2;
                    } else if (playerValues[2] < dealerValues[2]) { // Dealer wins
                        return 1;
                    } else { // conclude draw
                        return 0;
                    }
                }
            }
        }
    }
}