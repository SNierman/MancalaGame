package dataStructures1;

import java.util.Scanner;

public class MancalaGame { // has 2 players
	
	private Player player1;
	private Player player2;
	private int stones;
	
	/**
	 * Set up empty mancala game
	 * @param player1 - get a new player1
	 * @param player2 - get a new player2
	 */
	public MancalaGame(Player player1, Player player2) {
		this.player1 = new Player(player1.getName());
		this.player2 = new Player(player2.getName());
	}
	
	/**
	 * Start gameplay
	 */
	public void playGame() {
		Scanner keyboard = new Scanner(System.in);
		// continue gameplay till there's a winner
		while (!hasWinner(player2, player1)) {
			System.out.println(this.toString());
			System.out.println(player1.getName() + ", choose a pit.");
			int pitIndex = keyboard.nextInt()-1;
			// do not allow an index less than 0
			if (pitIndex >= 0) {
				// start player 1's turn and print results
				takeTurn(pitIndex, player1, player2, keyboard);
				System.out.println(this.toString());
			}
			// continue to player 2 if there is no winner
			if (!hasWinner(player1, player2)) {
				System.out.println(player2.getName() + ", choose a pit.");
				pitIndex = keyboard.nextInt()-1;
				takeTurn(pitIndex, player2, player1, keyboard);
			}
		}
	}
	
	/**
	 * This method plays out whoever's turn it is
	 * @param i - get the index of the pit
	 * @param player1 - get the current player
	 * @param player2 - get the opponent
	 * @param keyboard - allow user input
	 */
	public void takeTurn(int i, Player player1, Player player2, Scanner keyboard) {
		// only allow the choice to go through if the pit has stones in it,
		// otherwise display error message
		if (player1.getSide()[i].getPit() == 0) {
			errorChoice(player1, player2, keyboard);
		}
		
		else {
			stones = player1.getSide()[i].getPit();
			// take all the stones out of the chosen pit
			for (int j = 0; j < stones+1; j++) {
				player1.getSide()[i].removeFromPit();
			}
			
			// if there are at least 12 stones, add a stone to each pit plus
			// current player's mancala
			while (stones >= 12) {
				for (int k = 0; k < 6; k++) {
					player1.getSide()[i].addToPit();
					player1.addToMancala();
					player2.getSide()[i].addToPit();
				}
				stones -= 12;
			}
			
			// count down stones as you distribute them throughout the board
			for (int l = stones+1; l > 0; l--) {
				// if index is less than 6, only add stones to current player's side
				if (i < 6) {
					player1.getSide()[i].addToPit();
					i++;
					stones--;
				}
				// if index is 6, add stone to current player's mancala
				else if (i == 6) {
					player1.addToMancala();
					i++;
					stones--;
				}
				// if there are more stones than what fits on current player's side,
				// add stones to opponent's side
				else {
					if (i <= 12) {
						player2.getSide()[i-7].addToPit();
						i++;
						stones--;
					}
					// if there are still more stones after filling opponent's side,
					// add more stones to current player's side
					else {
						player1.getSide()[i-13].addToPit();
						i++;
						stones--;
					}
				}
			}
			stones++;
		}
		
		// when no more stones
		if (stones == 0) {
			// if last stone lands in mancala, player goes again
			if (i == 7) {
				if (!hasWinner(player1, player2)) {
					System.out.println(player1.getName() + ", go again.");
					System.out.println(this.toString());
					goAgain(keyboard, player1, player2);
				}
			}
			// if last stone lands on player's side
			if (i < 7) {
				// and pit is empty, player gets that stone and all the stones 
				// in the pit opposite
				if (player1.getSide()[i-1].getPit() == 1) {
					collectStones(i, player1, player2);
				}
			}
			// if there is a winner, display results and exit the program
			if (hasWinner(player1, player2)) {
				System.out.println(this.toString());
				System.exit(0);
			}
		}
	}

	/**
	 * This method will display an error message if the player chooses a pit with
	 * no stones in it
	 * @param player1 - get current player
	 * @param player2 - get opponent
	 * @param keyboard - allow reselection
	 */
	private void errorChoice(Player player1, Player player2, Scanner keyboard) {
		System.out.println("ERROR! You must choose a pit with at least one "
							+ "piece in it."
							+ "\n" + player1.getName() + ", choose a pit.");
		goAgain(keyboard, player1, player2);
	}
	
	/**
	 * This method has the current player take another turn
	 * @param keyboard - allow user input
	 * @param player1 - get current player
	 * @param player2 - get opponent
	 */
	public void goAgain(Scanner keyboard, Player player1, Player player2) {
		takeTurn(keyboard.nextInt()-1, player1, player2, keyboard);
	}
	
	/**
	 * This method collects all the stones that belong in the current player's mancala
	 * @param i - get the index of the pits to collect from
	 * @param player1 - get current player
	 * @param player2 - get opponent
	 */
	private void collectStones(int i, Player player1, Player player2) {
		for (int m = player2.getSide()[i-1].getPit(); m > 0; m--) {
			player1.addToMancala();
			player2.getSide()[i-1].removeFromPit();
		}
		player1.getSide()[i-1].removeFromPit();
		player1.addToMancala();
	}
	
	/**
	 * This method determines if there is a winner
	 * @param player1 - get current player
	 * @param player2 - get opponent
	 * @return true if there is an empty side, false if both sides have stones in
	 * at least one pit
	 */
	public boolean hasWinner(Player player1, Player player2) {
		// determine whether all the pits are empty
		for (int i = 1; i <= player1.getSide().length; i++) {
			if (player1.getSide()[i-1].getPit() != 0) {
				return false;
			}
		}
		
		// if all pits are empty, find out who won
		determineWinner(player1, player2);
		return true;
	}
	
	/**
	 * This method sweeps all remaining stones into opponent's mancala
	 * @param player2 - get opponent
	 * @return number of pieces in the mancala
	 */
	public int sweepPieces(Player player2) {
		// loop through the side and add pieces to loser's mancala
		for (int i = 0; i < player2.getSide().length; i++) {
			player2.addToMancala(player2.getSide()[i].getPit());
		}
		return player2.getMancala();
	}

	/**
	 * This method will find out who the winner is
	 * @param player1 - get current player
	 * @param player2 - get opponent
	 */
	private void determineWinner(Player player1, Player player2) {
		// sweep remaining pieces into opponent's mancala
		player2.setMancala(sweepPieces(player2));
		// display both scores
		System.out.print(player1.getName() + "'s score: " + player1.getMancala()+ "\n");
		System.out.print(player2.getName() + "'s score: " + player2.getMancala()+ "\n");
		// display winner
		if (player1.getMancala() > player2.getMancala()) {
			System.out.println(player1.getName() + " WINS!");
		}
		else if (player1.getMancala() < player2.getMancala()) {
			System.out.println(player2.getName() + " WINS!");
		}
		else {
			System.out.println("IT'S A TIE!");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder board = new StringBuilder();
		board.append("Player 1:\t");
		board.append("| "+ player1.toString() + "| ");
		board.append(player1.getMancala() + " |\n");
		board.append("Player 2:\t");
		board.append("| "+ player2.toString() + "| ");
		board.append(player2.getMancala() + " |");
		return board.toString();
	}

}
