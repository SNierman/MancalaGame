package dataStructures1;

import java.util.Scanner;

public class MancalaMain {

	public static void main(String[] args) {
		// Mancala Game Played: players take turns distributing stones from
		// their pits throughout the board. The player with the most stones
		// in his mancala is the winner.
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter first player's name: ");
		Player player1 = new Player(keyboard.nextLine());
		System.out.println("Enter second player's name: ");
		Player player2 = new Player(keyboard.nextLine());		
		
		MancalaGame game = new MancalaGame(player1, player2);
		game.playGame();
	}

}