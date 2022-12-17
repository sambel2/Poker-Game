/*
	By: Sergio Ambelis Diaz
	CS 342			Fall 2022
	Description: TThree Card Poker is played as heads-up between the player's
	hand and the dealer's hand. After all ante wagers are placed, three cards
	are dealt to each player and the dealer. Players have a choice to either
	fold or continue in the game by placing a "play" wager equal to their ante.
 */
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import java.util.ArrayList;


public class ThreeCardPokerGame extends Application {
	 private Player playerOne = new Player();
	 private Player playerTwo = new Player();
	 private Dealer theDealer = new Dealer();

	 ArrayList<Player> players = new ArrayList<Player>();

	TextField dealerTitle , player1Status, player2Status,
			player1PlayWager, player1PairPlusWager,
			player2PlayWager, player2PairPlusWager,
			player1AnteBets, player2AnteBets,
			player1Winnings, player2Winnings,
			messageBoard;


	Button player1Title, player2Title,
			player1PlayButton, player2PlayButton,
			player1FoldButton, player2FoldButton,
			dealerDealButton;

	ArrayList<Image> cardImages = new ArrayList<Image>();
	ArrayList<ImageView> cardView = new ArrayList<ImageView>();

	// default boolean values
	Boolean player1Playing = true, player2Playing = true ,
			player1Folded = false, player1Played = false,
			player1Done = false, player2Done = false,
			player2Folded = false, player2Played = false, showScene1 = true;


	double twoMessageWait;
	double threeMessageWait;

	MenuBar menuBar;

	BorderPane pane;
	Image backgroundImage;
	BackgroundSize bgSize;
	Background background;

	void reset(MenuItem freshStart) {
		freshStart.setOnAction(actionEvent -> {
			// Clear dealer
			theDealer.getTheDeck().newDeck();
			theDealer.dealersHand.clear();
			playerOne.hand.clear();
			playerTwo.hand.clear();
			for (int x = 0; x < 9; x++) {
				cardView.get(x).setImage(new Image("poke_back.jpg"));
			} // reset winnings
			TextField[] field = {player1Winnings, player2Winnings};
			for (int x = 0; x < 2; x++) {
				players.get(x).setTotalWinnings(0);
				field[x].setText("Wins: $" + players.get(x).getTotalWinnings());
			}
			// clear all past wagers, bets and player button inputs
			TextField[] playerFields = {player1AnteBets, player1PlayWager, player2AnteBets, player2PlayWager};
			Button[] playerB = {player1PlayButton, player1FoldButton, player2PlayButton, player2FoldButton};
			for (int x = 0; x < 4; x++) {
				playerFields[x].clear();
				playerB[x].setDisable(true);
			}

			player1Title.setDisable(false);
			player2Title.setDisable(false);

			messageBoard.setText("Hit deal! Then enter your wager, ante, pair plus and submit as play or fold.");
			player1Status.setText("Playing");
			player2Status.setText("Playing");
		});
	}

	public static void main(String[] args) {
		//TODO: make sure methods and members specified are how they are specified in the PDF
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("UIC Three Card Poker Game!");
		twoMessageWait = 5; //5 seconds
		threeMessageWait = 5; //5 seconds

		// Build the Menu
		Menu menu = new Menu("Options");
		MenuItem freshStart = new MenuItem("Fresh Start");
		MenuItem newLook = new MenuItem("New Look");
		MenuItem exit = new MenuItem("Exit");

		menu.getItems().add(freshStart);
		menu.getItems().add(newLook);
		menu.getItems().add(exit);

		menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		// reset option eventHandler
		reset(freshStart);
		// Not operator swaps background
		newLook.setOnAction(actionEvent -> {
			showScene1 = !showScene1;
			setBackground();
			setStyle();
		});
		// exit
		exit.setOnAction(actionEvent -> System.exit(0));

		primaryStage.setScene(startingScene());
		listenForActions();
		primaryStage.show();
	}

	// Player message event handler
	String playerMessage(int playerTypeHand, String playerTypeHandMessage , Boolean isPlayer1) {
		if (playerTypeHand == 0) {
			if (isPlayer1)
				playerTypeHandMessage = "Player 1 has a high card";
			else
				playerTypeHandMessage = "Player 2 has a high card";
		} else if (playerTypeHand == 1) {
			if (isPlayer1)
				playerTypeHandMessage = "Player 1 has a straight flush";
			else
				playerTypeHandMessage = "Player 2 has a straight flush";
		}
		else if (playerTypeHand == 2) {
			if (isPlayer1)
				playerTypeHandMessage = "Player 1 has three of a kind";
			else
				playerTypeHandMessage = "Player 2 has three of a kind";
		}
		else if (playerTypeHand == 3) {
			if (isPlayer1)
				playerTypeHandMessage = "Player 1 has a straight";
			else
				playerTypeHandMessage = "Player 2 has a straight";
		}
		else if (playerTypeHand == 4) {
			if (isPlayer1)
				playerTypeHandMessage = "Player 1 has a flush";
			else
				playerTypeHandMessage = "Player 1 has a flush";
		} else {
			if (isPlayer1)
				playerTypeHandMessage = "Player 1 has a pair";
			else
				playerTypeHandMessage = "Player 2 has a pair";
		}
		return playerTypeHandMessage;
	}



  // Function which is my event handler for various actions
	public void listenForActions() {
		dealerDealButton.setOnAction(actionEvent -> {
			theDealer.dealersHand = theDealer.dealHand();
			playerOne.hand = theDealer.dealHand();
			playerTwo.hand = theDealer.dealHand();
			// Check if player one is playing
			if (player1Playing) {
				player1Done = false;
			} else {
				player1Done = true;
			} // Check if player two is playing
			if (player2Playing) {
				player2Done = false;
			} else {
				player2Done = true;
			}
			showPlayer1Cards();
			showPlayer2Cards();
			// active all the buttons
			Button[] b = {player1PlayButton, player1FoldButton, player2PlayButton, player2FoldButton};
			for (int x = 0; x < 4; x++) {
				b[x].setDisable(false);
			}
			updatePlayerStatus();
		});

		// Display player 1 status if player 1 is playing
		player1Title.setOnAction(actionEvent -> {
			if (player1Playing) {
				if (player2Playing) {
					player1Done = true;
					player1Playing = false;
					messageBoard.setText("Player one has left the room!");
					updatePlayerStatus();
				} else {
					messageBoard.setText("You need at least one player to play game");
				}
			} else { // if player 1 wants to rejoin the game
				player1Done = false;
				player1Playing = true;
				updatePlayerStatus();
				showPlayer1Cards();
			}
		});

		// Display player 2 status if player 2 is playing
		player2Title.setOnAction(actionEvent -> {
			if (player2Playing) {
				if (player1Playing) {
					player2Done = true;
					player2Playing = false;
					messageBoard.setText("Player two has left the room");
					updatePlayerStatus();
				} else {
					messageBoard.setText("You need at least one player to play");
				}
			} else { // if player 2 wants to rejoin the game after waiting
				player2Done = false;
				player2Playing = true;
				updatePlayerStatus();
				showPlayer2Cards();
			}
		});

		// Decide what status player 1 is by the press of player1 button
		player1PlayButton.setOnAction(actionEvent -> {
			int anteBet = 0;
			if (!(player1AnteBets.getText().equals(""))) {
				anteBet = Integer.parseInt(player1AnteBets.getText());
			}

			if (anteBet <= 0) {
				messageBoard.setText("Player one: Invalid entry, ante wager must be positive");
			} else {  // set the $5 min and $25 limit
				if ((anteBet < 5) || (anteBet > 25)) {
					messageBoard.setText("Player one's ante wager must be between 5 and 25");
				} else {
					if (!(player1PlayWager.getText().equals(String.valueOf(anteBet)))) {
						messageBoard.setText("Player one's play wager must be equal to ante wager");
					} else {
						player1Done = true;
						player1Played = true;
						playerOne.setAnteBet(anteBet);
						playerOne.setPlayBet(anteBet);

						// Disable player 1
						player1PlayButton.setDisable(true);
						player1FoldButton.setDisable(true);

						if (player2Done) {
							showDealersCards(); // Show dealer once players turn is over

							// Get bet
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getAnteBet());
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPlayBet());

							// get pair plus bet if given
							if (!(player1PairPlusWager.getText().equals(""))) {
								int pairPlusBet = Integer.parseInt(player1PairPlusWager.getText());
								if (!((pairPlusBet < 5) || (pairPlusBet > 25))) {
									playerOne.setPairPlusBet(pairPlusBet);
								}
							} else {
								playerOne.setPairPlusBet(0);
							}

							// evaluate play and pair plus bet
							int player1TypeHand = ThreeCardLogic.evalHand(playerOne.hand);
							int player1PairPlusWon = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.getPairPlusBet());
							int player1WinningPerson = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);

							// calculate pair plus bet in winning
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPairPlusBet());
							playerOne.setTotalWinnings( playerOne.getTotalWinnings() + player1PairPlusWon);

							String player1TypeHandMessage = "";


							player1TypeHandMessage = playerMessage(player1TypeHand, player1TypeHandMessage , true);

							String player1PairPlusWonMessage = "Player one pair plus winning is " + player1PairPlusWon;
							String player1WinningPersonMessage;

							if (player1WinningPerson == 0) {
								player1WinningPersonMessage = "Draw Between Dealer and Player 1";
								// Dealer didn't have queen or higher, therefore all bets are off
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + playerOne.getAnteBet());
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + playerOne.getPlayBet());
							} else if (player1WinningPerson == 1) {
								player1WinningPersonMessage = "Player one loses to dealer";
							} else {
								player1WinningPersonMessage = "Player one beats dealer";
								// player will get double of what they put in
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + (playerOne.getPlayBet() * 2));
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + (playerOne.getPlayBet() * 2));
							}

							if (player2Playing) {
								// Takes bet
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getAnteBet());
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getPlayBet());

								// Receive a pair plus if available
								if (!(player2PairPlusWager.getText().equals(""))) {
									int pairPlusBet = Integer.parseInt(player2PairPlusWager.getText());
									if (!((pairPlusBet < 5) || (pairPlusBet > 25))) {
										playerTwo.setPairPlusBet(pairPlusBet);
									}
								} else {
									playerTwo.setPairPlusBet(0);
								}
								// After finished always display message
								int player2TypeHand = ThreeCardLogic.evalHand(playerTwo.hand);
								int player2PairPlusWon = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.getPairPlusBet());
								int player2WinningPerson = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);
								// calculate pair plus bet
								playerTwo.setTotalWinnings( playerTwo.getTotalWinnings() - playerTwo.getPairPlusBet());
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + player2PairPlusWon);

								String player2TypeHandMessage = "";
								// Call player message method
								player2TypeHandMessage = playerMessage(player2TypeHand, player2TypeHandMessage , false);
								String player2PairPlusWonMessage = "Player two pair plus winning is " + player2PairPlusWon;
								String player2WinningPersonMessage;

								if (player2WinningPerson == 0) {
									player2WinningPersonMessage = "Draw between Dealer and Player two.";
									// dealer didn't have queen or higher so all bets are off
									playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + playerTwo.getAnteBet());
									playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + playerTwo.getPlayBet());
								} else if (player2WinningPerson == 1) {
									player2WinningPersonMessage = "Player two lost to the dealer";
									// Lost money
								} else {
									player2WinningPersonMessage = "Player two beats dealer";
									// Player gets double of what they put for ante bet and play bet
									playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + (playerTwo.getAnteBet() * 2));
									playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + (playerTwo.getPlayBet() * 2));
								}

								// Check if player 2 played or folded and show message accordingly
								if (player2Played) {
									show3Message(
											player1TypeHandMessage + " and " + player2TypeHandMessage,
											player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
											player1WinningPersonMessage + " and " + player2WinningPersonMessage,
											threeMessageWait);
									endOfRound();
								}

								if (player2Folded) {
									show3Message(
											player1TypeHandMessage + " and " + player2TypeHandMessage,
											player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
											player1WinningPersonMessage,
											threeMessageWait);
									endOfRound();
								}

							} else {
								// show Player 1 message
								show3Message(
										player1TypeHandMessage,
										player1PairPlusWonMessage,
										player1WinningPersonMessage,
										threeMessageWait);
								endOfRound();
							}
						} else {
							// Player 2 has to wait prior to playing again
							show1Message("Waiting for player 2 to move.");
							player2Title.setDisable(true);
						}
					}
				}
			}
		});
		// Fold button action
		player1FoldButton.setOnAction(actionEvent -> {
			int pairPlusBet = 0;
			if (!(player1PairPlusWager.getText().equals(""))) {
				pairPlusBet = Integer.parseInt(player1PairPlusWager.getText());
			}

			if (pairPlusBet <= 0) {
				messageBoard.setText("Player one: Invalid entry, try a positive number");
			} else {
				if ((pairPlusBet < 5) || (pairPlusBet > 25)) {
					messageBoard.setText("Player one's pair plus wager must be between 5 and 25");
				} else { // player 1 folds and is done
					player1Done = true;
					player1Folded = true;
					player1PairPlusWager.setText(String.valueOf(pairPlusBet)); // put ante bet in play wager
					playerOne.setPairPlusBet(pairPlusBet);

					// Disable player 1's buttons
					player1PlayButton.setDisable(true);
					player1FoldButton.setDisable(true);
					// clear player 1 wager and bet
					player1PlayWager.clear();
					player1AnteBets.clear();

					if (player2Done) {
						showDealersCards(); // Once both players are finished then evaluate pair plus bet
						int player1TypeHand = ThreeCardLogic.evalHand(playerOne.hand);
						int player1PairPlusWon = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.getPairPlusBet());

						// calculate pair plus bet in winning
						playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPairPlusBet());
						playerOne.setTotalWinnings(playerOne.getTotalWinnings() + player1PairPlusWon);

						String player1TypeHandMessage = "";
						player1TypeHandMessage = playerMessage(player1TypeHand, player1TypeHandMessage , true);

						String player1PairPlusWonMessage = "Player one pair plus winning is " + player1PairPlusWon;

						if (player2Playing) {

							// Take in bet
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getAnteBet());
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getPlayBet());

							// get pair plus bet if available
							if (!(player2PairPlusWager.getText().equals(""))) {
								int player2PairPlusBet = Integer.parseInt(player2PairPlusWager.getText());
								if (!((player2PairPlusBet < 5) || (player2PairPlusBet > 25))) {
									playerTwo.setPairPlusBet(player2PairPlusBet);
								}
							} else {
								playerTwo.setPairPlusBet(0);
							}

							// Always display message when players are done
							int player2TypeHand = ThreeCardLogic.evalHand(playerTwo.hand);
							int player2PairPlusWon = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.getPairPlusBet());
							int player2WinningPerson = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);

							// Calculate pair plus bet in winnings box
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getPairPlusBet());
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + player2PairPlusWon);

							String player2TypeHandMessage = "";
							player2TypeHandMessage = playerMessage(player2TypeHand, player2TypeHandMessage , false);
							String player2PairPlusWonMessage = "Player two pair plus winning is " + player2PairPlusWon;
							String player2WinningPersonMessage;

							if (player2WinningPerson == 0) {
								player2WinningPersonMessage = "Tie between Dealer and Player two.";
								//dealer didn't have queen or higher so all bets are off
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + playerTwo.getAnteBet());
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + playerTwo.getPlayBet());
							} else if (player2WinningPerson == 1) {
								player2WinningPersonMessage = "Player two lost to the dealer";
								// lose money
							} else {
								player2WinningPersonMessage = "The Dealer lost to player two";
								// player gets double of what they put for ante bet and play bet
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + (playerTwo.getAnteBet() * 2));
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + (playerTwo.getPlayBet() * 2));
							}

							// check if player 2 played or folded and show message accordingly then edn round
							if (player2Played) {
								show3Message(
										player1TypeHandMessage + " and " + player2TypeHandMessage,
										player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
										player2WinningPersonMessage,
										threeMessageWait);
								endOfRound();
							}

							if (player2Folded) {
								show2Message(
										player1TypeHandMessage + " and " + player2TypeHandMessage,
										player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
										twoMessageWait);
								endOfRound();
							}

						} else {
							// show only player 1 message
							show2Message(
									player1TypeHandMessage,
									player1PairPlusWonMessage,
									twoMessageWait);
							endOfRound();
						}
					} else {
						// wait for player 2, then allow player 2 to play
						show1Message("Waiting for player 2 to take turn");
						player2Title.setDisable(true);
					}
				}
			}
		});

		player2PlayButton.setOnAction(actionEvent -> {
			int anteBet = 0;
			if (!(player2AnteBets.getText().equals(""))) {
				anteBet = Integer.parseInt(player2AnteBets.getText());
			}

			if (anteBet <= 0) {
				messageBoard.setText("Player two: Invalid entry, try a positive number");
			} else {
				if ((anteBet < 5) || (anteBet > 25)) {
					messageBoard.setText("Player two's ante wager must be between 5 and 25");
				} else {
					if (!(player2PlayWager.getText().equals(String.valueOf(anteBet)))) {
						messageBoard.setText("Player one's play wager must be equal to ante wager");
					} else {
						player2Done = true; //player 1 has taken the turn
						player2Played = true;
						playerTwo.setAnteBet(anteBet);
						playerTwo.setPlayBet(anteBet);

						// disable play buttons
						player2PlayButton.setDisable(true);
						player2FoldButton.setDisable(true);

						if (player1Done) {
							showDealersCards(); //since both players are done

							// Take in the bet
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getAnteBet());
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getPlayBet());

							// Take in the pair plus bet if available
							if (!(player2PairPlusWager.getText().equals(""))) {
								int pairPlusBet = Integer.parseInt(player2PairPlusWager.getText());
								if (!((pairPlusBet < 5) || (pairPlusBet > 25))) {
									playerTwo.setPairPlusBet(pairPlusBet);
								}
							} else {
								playerTwo.setPairPlusBet(0);
							}

							// Evaluate play and pair plus bet
							int player2TypeHand = ThreeCardLogic.evalHand(playerTwo.hand);
							int player2PairPlusWon = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.getPairPlusBet());
							int player2WinningPerson = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);

							// Calculate the pair plus bet in winning
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getPairPlusBet());
							playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + player2PairPlusWon);

							String player2TypeHandMessage ="";
							player2TypeHandMessage = playerMessage(player2TypeHand, player2TypeHandMessage , false);

							String player2PairPlusWonMessage = "Player two pair plus winning is " + player2PairPlusWon;
							String player2WinningPersonMessage;

							if (player2WinningPerson == 0) {
								player2WinningPersonMessage = "Tie between Dealer and Player two.";
								// Dealer didn't have queen or higher so all bets are off
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + playerTwo.getAnteBet());
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + playerTwo.getPlayBet());
							} else if (player2WinningPerson == 1) {
								player2WinningPersonMessage = "Player two lost to the dealer";
								// Lose money
							} else {
								player2WinningPersonMessage = "The Dealer lost to player two";
								// player gets double of what they put for ante bet and play bet
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + (playerTwo.getAnteBet() * 2));
								playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + (playerTwo.getPlayBet() * 2));
							}

							if (player1Playing) {
								// take in the bet
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getAnteBet());
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPlayBet());

								//get pair plus bet if put down
								if (!(player1PairPlusWager.getText().equals(""))) {
									int pairPlusBet = Integer.parseInt(player1PairPlusWager.getText());
									if (!((pairPlusBet < 5) || (pairPlusBet > 25))) {
										playerOne.setPairPlusBet(pairPlusBet);
									}
								} else {
									playerOne.setPairPlusBet(0);
								}

								// Always display the message
								int player1TypeHand = ThreeCardLogic.evalHand(playerOne.hand);
								int player1PairPlusWon = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.getPairPlusBet());
								int player1WinningPerson = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);

								// calculate pair plus bet in winning
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPairPlusBet());
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + player1PairPlusWon);

								String player1TypeHandMessage = "";
								player1TypeHandMessage = playerMessage(player1TypeHand, player1TypeHandMessage , true);

								String player1PairPlusWonMessage = "Player one pair plus winning is " + player1PairPlusWon;
								String player1WinningPersonMessage;

								if (player1WinningPerson == 0) {
									player1WinningPersonMessage = "Tie between Dealer and Player One.";
									// dealer didn't have queen or higher all bets are off
									playerOne.setTotalWinnings(playerOne.getTotalWinnings() + playerOne.getAnteBet());
									playerOne.setTotalWinnings(playerOne.getTotalWinnings() + playerOne.getPlayBet());
								} else if (player1WinningPerson == 1) {
									player1WinningPersonMessage = "Player One lost to the dealer";
									// Lose money
								} else {
									player1WinningPersonMessage = "The Dealer lost to player one";
									// player get double what they put in for ante bet and play bet
									playerOne.setTotalWinnings(playerOne.getTotalWinnings() + (playerOne.getAnteBet() * 2));
									playerOne.setTotalWinnings(playerOne.getTotalWinnings() + (playerOne.getPlayBet() * 2));
								}

								// check if player 2 played or folded and show message accordingly
								if (player1Played) {
									show3Message(
											player1TypeHandMessage + " and " + player2TypeHandMessage,
											player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
											player1WinningPersonMessage + " and " + player2WinningPersonMessage,
											threeMessageWait);
									endOfRound();
								}

								if (player2Folded) {
									show3Message(
											player1TypeHandMessage + " and " + player2TypeHandMessage,
											player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
											player1WinningPersonMessage,
											threeMessageWait);
									endOfRound();
								}
								endOfRound();
							}
							else {
								// show only player 2 message
								show3Message(
										player2TypeHandMessage,
										player2PairPlusWonMessage,
										player2WinningPersonMessage,
										threeMessageWait);
								endOfRound();
							}

						} else {
							// Player 1 waits for turn
							show1Message("Waiting for player 1 to take turn");
							player1Title.setDisable(true);
						}
					}
				}
			}
		});

		player2FoldButton.setOnAction(actionEvent -> {
			int pairPlusBet = 0;
			if (!(player2PairPlusWager.getText().equals(""))) {
				pairPlusBet = Integer.parseInt(player2PairPlusWager.getText());
			}

			if (pairPlusBet <= 0) {
				messageBoard.setText("Player two: Invalid entry, try a positive number");
			} else {
				if ((pairPlusBet < 5) || (pairPlusBet > 25)) {
					messageBoard.setText("Player two's pair plus wager must be between 5 and 25");
				} else {
					player2Done = true; //player 2 has taken the turn
					player2Folded = true;
					player2PairPlusWager.setText(String.valueOf(pairPlusBet)); //put ante bet in play wager
					playerTwo.setPairPlusBet(pairPlusBet);

					// Disable buttons
					player2PlayButton.setDisable(true);
					player2FoldButton.setDisable(true);

					player2PlayWager.clear();
					player2AnteBets.clear();

					if (player1Done) {
						showDealersCards();

						// evaluate pair plus bet
						int player2TypeHand = ThreeCardLogic.evalHand(playerTwo.hand);
						int player2PairPlusWon = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.getPairPlusBet());

						// calculate pair plus bet in winning
						playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() - playerTwo.getPairPlusBet());
						playerTwo.setTotalWinnings(playerTwo.getTotalWinnings() + player2PairPlusWon);

						String player2TypeHandMessage = "";
						player2TypeHandMessage = playerMessage(player2TypeHand, player2TypeHandMessage , false);

						String player2PairPlusWonMessage = "Player two pair plus winning is " + player2PairPlusWon;

						if (player1Playing) {
							// take in the bet
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getAnteBet());
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPlayBet());

							// Always display message
							int player1TypeHand = ThreeCardLogic.evalHand(playerOne.hand);
							int player1PairPlusWon = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.getPairPlusBet());
							int player1WinningPerson = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);

							// calculate pair plus bet in winning
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() - playerOne.getPairPlusBet());
							playerOne.setTotalWinnings(playerOne.getTotalWinnings() + player1PairPlusWon);

							// get pair plus bet if put down
							if (!(player1PairPlusWager.getText().equals(""))) {
								int player1PairPlusBet = Integer.parseInt(player1PairPlusWager.getText());
								if (!((player1PairPlusBet < 5) || (player1PairPlusBet > 25))) {
									playerOne.setPairPlusBet(player1PairPlusBet);
								}
							} else {
								playerTwo.setPairPlusBet(0);
							}

							String player1TypeHandMessage = "";
							player1TypeHandMessage = playerMessage(player1TypeHand, player1TypeHandMessage , true);

							String player1PairPlusWonMessage = "Player one pair plus winning is " + player1PairPlusWon;
							String player1WinningPersonMessage;

							if (player1WinningPerson == 0) {
								player1WinningPersonMessage = "Tie between Dealer and Player One.";
								// dealer doesn't have queen or higher so all bets are off
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + playerOne.getAnteBet());
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + playerOne.getPlayBet());
							} else if (player1WinningPerson == 1) {
								player1WinningPersonMessage = "Player One lost to the dealer";
								// lose money
							} else {
								player1WinningPersonMessage = "The Dealer lost to player one";
								// player get double what they put for ante bet and play bet
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + (playerOne.getAnteBet() * 2));
								playerOne.setTotalWinnings(playerOne.getTotalWinnings() + (playerOne.getPlayBet() * 2));
							}

							// check if player 2 played or folded and show message accordingly
							if (player1Played) {
								show3Message(
										player1TypeHandMessage + " and " + player2TypeHandMessage,
										player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
										player1WinningPersonMessage,
										threeMessageWait);
								endOfRound();
							}

							if (player1Folded) {
								show2Message(
										player1TypeHandMessage + " and " + player2TypeHandMessage,
										player1PairPlusWonMessage + " and " + player2PairPlusWonMessage,
										twoMessageWait);
								endOfRound();
							}

						} else {
							// show only player 1 message
							show2Message(
									player2TypeHandMessage,
									player2PairPlusWonMessage,
									twoMessageWait);
							endOfRound();
						}
					} else {
						// Player 1 waits for turn
						show1Message("Waiting for player 1 to take turn");
						player1Title.setDisable(true);
					}
				}
			}
		});
	}

	public void endOfRound() {
		player1Winnings.setText("Wins: $" + playerOne.getTotalWinnings());
		player2Winnings.setText("Wins: $" + playerTwo.getTotalWinnings());
		dealerDealButton.setDisable(true);
		player1PlayButton.setDisable(true);
		player1FoldButton.setDisable(true);
		player2PlayButton.setDisable(true);
		player2FoldButton.setDisable(true);
		player1Title.setDisable(false);
		player2Title.setDisable(false);

		theDealer.dealersHand.clear();
		playerOne.hand.clear();
		playerTwo.hand.clear();
		// pause transition
		PauseTransition wait = new PauseTransition(Duration.seconds(8));
		wait.setOnFinished((e) -> {
			for (int x = 0; x < 9; x++) {
				cardView.get(x).setImage(new Image("poke_back.jpg"));
			}
			// Clear all bets, wager, pair plus
			player1AnteBets.clear(); player2AnteBets.clear(); player1PlayWager.clear();
			player1PlayWager.clear(); player2PlayWager.clear(); player2PlayWager.clear();
			player1PairPlusWager.clear(); player2PairPlusWager.clear();

			if (player1Playing) {
				player1Done = false;
			} else {
				player1Done = true;
			}

			if (player1Playing) {
				player2Done = false;
			} else {
				player2Done = true;
			}
			//  All players reset
			player1Played = false;
			player1Folded = false;
			player2Played = false;
			player2Folded = false;

			dealerDealButton.setDisable(false);
			updatePlayerStatus();
		});
		wait.play();
	}

	public void show1Message(String message1) {
		messageBoard.setText(message1);
	}

	public void show2Message(String message1, String message2, double waitTime) {
		messageBoard.setText(message1);

		PauseTransition wait = new PauseTransition(Duration.seconds(waitTime));
		wait.setOnFinished((e) -> messageBoard.setText(message2));
		wait.play();
	}

	public void show3Message(String message1, String message2, String message3, double waitTime) {
		messageBoard.setText(message1);

		PauseTransition wait = new PauseTransition(Duration.seconds(waitTime));
		wait.setOnFinished((e) -> {
			messageBoard.setText(message2);

			PauseTransition wait2 = new PauseTransition(Duration.seconds(waitTime));
			wait2.setOnFinished((e2) -> messageBoard.setText(message3));
			wait2.play();
		});
		wait.play();
	}

	public void showDealersCards() {
		for (int x = 0; x < 3; x++) {
			cardView.get(x).setImage(new Image(theDealer.dealersHand.get(x).getValue() + String.valueOf(theDealer.dealersHand.get(x).getSuit()) + ".jpg"));
		}
	}

	public void showPlayer1Cards() {
		if ((player1Playing) && (playerOne.hand.size() == 3)) {
			for (int x = 0; x < 3; x++) {
				cardView.get(x + 3).setImage(new Image(playerOne.hand.get(x).getValue() + String.valueOf(playerOne.hand.get(x).getSuit())+".jpg"));
			}
		}
	}

	public void showPlayer2Cards() {
		if ((player2Playing) && (playerTwo.hand.size() == 3)){
			for (int x = 0; x < 3; x++) {
				cardView.get(x + 6).setImage(new Image(playerTwo.hand.get(x).getValue() + String.valueOf(playerTwo.hand.get(x).getSuit())+".jpg"));
			}
		}
	}

	public void updatePlayerStatus() {
		if (player1Playing) {
			player1Status.setText("Playing");
			if (playerOne.hand.size() == 3) {
				player1PlayButton.setDisable(false);
				player1FoldButton.setDisable(false);
			}
		} else {
			player1Status.setText("Not Playing");
			player1PlayButton.setDisable(true);
			player1FoldButton.setDisable(true);
			for (int x = 3; x < 6; x++) {
				cardView.get(x).setImage(new Image("poke_back.jpg"));
			}
		}

		if (player2Playing) {
			player2Status.setText("Playing");
			if (playerTwo.hand.size() == 3) {
				player2PlayButton.setDisable(false);
				player2FoldButton.setDisable(false);
			}
		} else {
			player2Status.setText("Not Playing");
			player2PlayButton.setDisable(true);
			player2FoldButton.setDisable(true);
			for (int x = 6; x < 9; x++) {
				cardView.get(x).setImage(new Image("poke_back.jpg"));
			}
		}
	}

	TextField setField (TextField x) {
		x.setMaxSize(100, 100);
		x.setFont(new Font(15));
		x.setAlignment(Pos.CENTER);
		x.autosize();
		return x;
	}

	Button setButton (Button x) {
		x.setFont(new Font(15));
		x.setAlignment(Pos.CENTER);
		x.autosize();
		return x;
	}

	public void initiateElements() {
		dealerTitle = new TextField("Dealer");
		dealerTitle.setEditable(false);
		dealerTitle = setField (dealerTitle);

		player1Title = new Button("Player One");
		player1Title.setMaxSize(100, 100);
		player1Title = setButton(player1Title);


		player1Status = new TextField("Playing");
		player1Status.setEditable(false);
		player1Status = setField (player1Status);

		player1PlayWager = new TextField("");
		player1PlayWager.setPromptText("Play Wager");
		player1PlayWager.setEditable(true);
		player1PlayWager = setField (player1PlayWager);
		player1PlayWager.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		player1PairPlusWager = new TextField("");
		player1PairPlusWager.setPromptText("Pair Plus");
		player1PairPlusWager.setEditable(true);
		player1PairPlusWager= setField (player1PairPlusWager);
		player1PairPlusWager.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		player1AnteBets = new TextField("");
		player1AnteBets.setPromptText("Ante Bets");
		player1AnteBets.setEditable(true);
		player1AnteBets = setField(player1AnteBets);
		player1AnteBets.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		player1Winnings = new TextField("Wins: $" + playerOne.getTotalWinnings());
		player1Winnings.setEditable(false);
		player1Winnings = setField(player1Winnings);

		messageBoard = new TextField("Click Deal to begin! Enter amount as desire and click either fold or play!");
		messageBoard.setEditable(false);
		messageBoard.setFont(new Font(15));
		messageBoard.setAlignment(Pos.BOTTOM_CENTER);
		messageBoard.autosize();

		player2Title = new Button("Player Two");
		player2Title.setMaxSize(100, 100);
		player2Title = setButton(player2Title);

		player2Status = new TextField("Playing");
		player2Status.setEditable(false);
		player2Status = setField(player2Status);

		player2PlayWager = new TextField("");
		player2PlayWager.setPromptText("Play Wager");
		player2PlayWager.setEditable(true);
		player2PlayWager = setField(player2PlayWager);
		player1PlayWager.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		player2PairPlusWager = new TextField("");
		player2PairPlusWager.setPromptText("Pair Plus");
		player2PairPlusWager.setEditable(true);
		player2PairPlusWager = setField(player2PairPlusWager);
		player2PlayWager.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		player2AnteBets = new TextField("");
		player2AnteBets.setPromptText("Ante Bets");
		player2AnteBets.setEditable(true);
		player2AnteBets = setField(player2AnteBets);
		player2AnteBets.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

		player2Winnings = new TextField("Wins: $" + playerTwo.getTotalWinnings());
		player2Winnings.setEditable(false);
		player2Winnings = setField(player2Winnings);

		dealerDealButton = new Button("Deal");
		dealerDealButton = setButton(dealerDealButton);

		player1PlayButton = new Button("Play");
		player1PlayButton = setButton(player1PlayButton);

		player1FoldButton = new Button("Fold");
		player1FoldButton = setButton(player1FoldButton);

		player2PlayButton = new Button("Play");
		player2PlayButton = setButton(player2PlayButton);

		player2FoldButton = new Button("Fold");
		player2FoldButton = setButton(player2FoldButton);

		player1PlayButton.setDisable(true);
		player1FoldButton.setDisable(true);
		player2PlayButton.setDisable(true);
		player2FoldButton.setDisable(true);
	}
   // Background method
	public void setBackground() {
		if (showScene1) {
			//setting the background
			backgroundImage = new Image("Poker_table_background1.jpg");
		} else {
			//setting the background
			backgroundImage = new Image("Poker_table_background2.png");
		}

		bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		BackgroundImage bg =
				new BackgroundImage(
						backgroundImage,
						BackgroundRepeat.NO_REPEAT,
						BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER,
						bgSize);
		background = new Background(bg);
		pane.setBackground(background);

	}
	// Style method
	public void setStyle() {
		if (showScene1) {
			player1Title.setStyle("-fx-text-fill: black;-fx-background-color: white");
			player2Title.setStyle("-fx-text-fill: black;-fx-background-color: white");
		} else {  // change screen
			player1Title.setStyle("-fx-text-fill: lightBlue;-fx-background-color: #00000000");
			player1Title.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
			player2Title.setStyle("-fx-text-fill: lightBlue;-fx-background-color: #00000000");
			player2Title.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
			dealerDealButton.setStyle("-fx-background-color: #8FBC8F");
			player1PlayButton.setStyle("-fx-background-color: #8FBC8F");
			player1FoldButton.setStyle("-fx-background-color: #8FBC8F");
			player2PlayButton.setStyle("-fx-background-color: #8FBC8F");
			player2FoldButton.setStyle("-fx-background-color: #8FBC8F");

			dealerTitle.setStyle("-fx-text-fill: black;-fx-background-color: #00000000");
			dealerTitle.setFont(Font.font("Cambria", FontWeight.BOLD, 20));
			messageBoard.setStyle("-fx-background-color: #6B8E23");
			player1Status.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
			player1Status.setStyle("-fx-text-fill: purple; -fx-background-color: #00000000;");
			player2Status.setStyle("-fx-text-fill: green; -fx-background-color: #00000000;");
			player2Status.setFont(Font.font("Cambria", FontWeight.BOLD, 18));
			player1Winnings.setStyle("-fx-text-fill: lightBlue; -fx-background-color: #8FBC8F");
			player1Winnings.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
			player2Winnings.setStyle("-fx-text-fill: blue; -fx-background-color: #8FBC8F");
			player2Winnings.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
		}
	}

	public Scene startingScene() {
		pane = new BorderPane();
		initiateElements();
		setStyle();
		setBackground();

		// Create back of card images
		for (int x = 0; x < 9; x++) {
			cardImages.add(x, new Image("poke_back.jpg"));
			cardView.add(x, new ImageView());
			cardView.get(x).setImage(cardImages.get(x));
			cardView.get(x).setPreserveRatio(true);
			cardView.get(x).setFitWidth(100);
		}

		players.add(0, playerOne);
		players.add(1, playerTwo);
		// create space fo cards
		int padding = 20;
		int spacing = 18;

		// Dealer card view
		HBox dealerCards = new HBox(spacing, cardView.get(0), cardView.get(1), cardView.get(2));
		dealerCards.setAlignment(Pos.CENTER);

		VBox dealerView = new VBox(spacing, dealerTitle, dealerCards, dealerDealButton);
		dealerView.setAlignment(Pos.TOP_CENTER);
		dealerView.setPadding(new Insets(padding, padding, padding, padding));


		// Player 1 card view
		HBox player1StatusView = new HBox(spacing, player1Title, player1Status, player1Winnings);
		player1StatusView.setAlignment(Pos.CENTER);

		HBox player1BetsView = new HBox(spacing, player1PlayWager, player1AnteBets, player1PairPlusWager);
		player1StatusView.setAlignment(Pos.CENTER);

		HBox player1Cards = new HBox(spacing, cardView.get(3), cardView.get(4), cardView.get(5));
		player1Cards.setAlignment(Pos.CENTER);

		HBox player1Buttons = new HBox(spacing, player1PlayButton, player1FoldButton);
		player1Buttons.setAlignment(Pos.CENTER);

		VBox player1View = new VBox(spacing, player1StatusView, player1BetsView, player1Cards, player1Buttons);
		player1View.setAlignment(Pos.CENTER);
		player1View.setPadding(new Insets(padding, padding, padding, padding));


		// Player 2 card view
		HBox player2StatusView = new HBox(spacing, player2Title, player2Status, player2Winnings);
		player2StatusView.setAlignment(Pos.CENTER);

		HBox player2BetsView = new HBox(spacing, player2PlayWager, player2AnteBets, player2PairPlusWager);
		player2StatusView.setAlignment(Pos.CENTER);

		HBox player2Cards = new HBox(spacing, cardView.get(6), cardView.get(7), cardView.get(8));
		player2Cards.setAlignment(Pos.CENTER);

		HBox player2Buttons = new HBox(spacing, player2PlayButton, player2FoldButton);
		player2Buttons.setAlignment(Pos.CENTER);

		VBox player2View = new VBox(spacing, player2StatusView, player2BetsView, player2Cards, player2Buttons);
		player2View.setAlignment(Pos.CENTER);


		HBox bothPlayerView = new HBox(player1View, player2View);
		bothPlayerView.setAlignment(Pos.CENTER);

		VBox messageAndPlayerView = new VBox(spacing, bothPlayerView, messageBoard );
		messageAndPlayerView.setAlignment(Pos.BOTTOM_CENTER);

		pane.setTop(menuBar);
		pane.setCenter(dealerView);
		pane.setBottom(messageAndPlayerView);
		return new Scene (pane, 750, 690);
	}
}