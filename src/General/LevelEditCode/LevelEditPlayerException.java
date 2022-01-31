package General.LevelEditCode;
public class LevelEditPlayerException extends Exception { 
	public LevelEditPlayerException(){
		super("Failed : Number of Player must be equal to 1");
	}        
}