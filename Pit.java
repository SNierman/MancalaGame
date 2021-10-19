package dataStructures1;

public class Pit {

	private int pieces;
	
	public Pit() {
		// pits start off with 4 pieces each
		this.pieces = 0;
	}
	
	public Pit(int pieces) {
		this.pieces = pieces;
	}
	
	public void addToPit() {
		this.pieces++;
	}
	
	public void removeFromPit() {
		this.pieces--;
	}
	
	public int getPit() {
		return pieces;
	}
	
}
