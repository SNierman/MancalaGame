package dataStructures1;

public class Player { // has a set of pits and a mancala

	private String name;
	private Mancala mancala;
	private Pit[] side = new Pit[6];
	
	public Player(String name) {
		this.name = name;
		for(int i = 0; i < side.length; i++) {
			side[i] = new Pit(4);
		}
		mancala = new Mancala();
	}
	
	public String getName() {
		return name;
	}
		
	public Pit[] getSide() {
		return side;
	}

	public void addToMancala() {
		mancala.addToPit();
	}
	
	public void addToMancala(int pieces) {
		for (int i = 0; i < pieces; i++) {
			mancala.addToPit();
		}
	}
	
	public int getMancala() {
		return this.mancala.getPit();
	}
	
	public void setMancala(int pieces) {
		int manc = this.mancala.getPit();
		manc = pieces;
	}
	
	public int getPit() {
		return this.getPit();
	}
	
	@Override
	public String toString() {
		StringBuilder side = new StringBuilder();
		for (int i = 0; i < this.side.length; i++) {
			side.append(this.side[i].getPit() + " ");
		}
		return side.toString();
	}
}
