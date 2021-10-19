package dataStructures1;

public class Mancala extends Pit { // is a pit

	private Pit pit;
	
	public Mancala() {
		this.pit = new Pit();
	}
	
	public Pit getMancala() {
		return pit;
	}
}
