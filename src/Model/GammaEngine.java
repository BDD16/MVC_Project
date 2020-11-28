package Model;
import java.util.HashSet;

public abstract class GammaEngine {
	
	HashSet<String> usernames;
	HashSet<String> previousMoves;
	
	public abstract boolean moveNorth();
	public abstract boolean moveback();
	public abstract boolean moveEast();
	public abstract boolean moveWest();
	public abstract boolean SpecialMoves();
	public abstract boolean getName();
	public abstract boolean setName();
	public abstract boolean setUserName();
	public abstract boolean getUserName();
	public abstract boolean hasUserMoved();
	

}
