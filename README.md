# 3 Card-Poker-Game
Three Card Poker

I implemented a two player version of the popular casino game 3
Card Poker. This is a somewhat simple game to understand and play which is created in JavaFX and trying my hand at
event driven programing.

In three card poker, each player only plays against the dealers hand, not each other:

• Both players will start by placing an ante wager. We will limit the ante bet to $5 or
greater, up to $25.

• There is one optional bet the players can make called the Pair Plus wager. We will
also limit this bet to $5 or greater, up to $25. This is a separate bet that will win if a
players hand is at least a pair of 2’s. The payoff for this bet applies regardless of the
dealers hand and what happens in the rest of the game. (See below for payouts).

• After all bets are made(ante and/or pair plus), the cards are dealt out. Each player
and the dealer receive three cards each. The players cards are face up and the
dealers hand is face down.
Each player must decide if they will play or fold. If they fold, they lose their ante wager
and pair plus wager(if they made one).

• If the player wants to continue, they will make a play wager (this must be equal to the
amount of the ante wager).

• At this point, the dealer will show their cards. If the dealer does not have at least a
Queen high or better, the play wager is returned to the players who did not fold and
the ante bet is pushed to the next hand.

• If the dealer does have at least a Queen high or better, then each players hand, that
did not fold, is evaluated against the dealers hand (see below for order of winning
hands). If the dealer wins, the player loses both the ante and play wager. If the player
wins, they get paid out 1 to 1 (they get back double what they wagered). Say the
player bet $5 each for the ante and play wager and won, they would get back $20.


As long as the player does not fold, the Pair Plus wager gets evaluated regardless of if
their hand beat the dealers hand; it is a separate bet based solely on the players hand.
If the player does not have at least a pair of 2’s, they lose this bet. Otherwise, the
payouts are as follows:

• Straight Flush 40 to 1

• Three of a Kind 30 to 1

• Straight 6 to 1

• Flush 3 to 1

• Pair 1 to 1

Interface of game (with menu box opened):
![image](https://user-images.githubusercontent.com/118694086/208265297-c826bffa-56f0-41d3-a2b3-fe740ce17590.png)


Interface 2 of game:
![image](https://user-images.githubusercontent.com/118694086/208265321-2ef7be1d-b9e7-4e81-9cb4-9b6306262152.png)

