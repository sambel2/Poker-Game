import java.util.ArrayList;

/*
    Description:
        This class represents a player in the game. It keeps track of each games current hand
        and current bets as well as the total winnings for that player across multiple games. If
        the player has lost more than he/she has won, that number can be negative. Provide a
        no argument constructor for this class
 */

public class Player {
    ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private int totalWinnings;

    void setAnteBet(int x) {this.anteBet = x;}
    int getAnteBet() {return this.anteBet;}
    void setPlayBet(int x) {this.playBet = x;}
    int getPlayBet() {return this.playBet;}
    void setPairPlusBet(int x) {this.pairPlusBet = x;}
    int getPairPlusBet() {return this.pairPlusBet;}
    void setTotalWinnings(int x) {this.totalWinnings = x;}
    int getTotalWinnings() {return this.totalWinnings;}

    // no argument default constructor
    Player() {
        this.hand = new ArrayList<Card>();
        this.anteBet = 0;
        this.playBet = 0;
        this.pairPlusBet = 0;
        this.totalWinnings = 0;
    }
}
