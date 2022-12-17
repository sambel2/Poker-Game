public class Card
{

    private char suit;
    private int value;

    void setSuit (char c) {this.suit = c;}
    char getSuit () {return this.suit;}

    void setValue (int x) {this.value = x;}
    int getValue() {return this.value;}

    // Card constructor
    Card(char suit, int value) {
        // suits: C = clubs, D = diamonds, S = spades, H = hearts
        // values: 2 - 14 an ace being 14, king 13, queen 12, jack 11, ten 10…..and so on.
        this.suit = suit;
        this.value = value;
    }
}
