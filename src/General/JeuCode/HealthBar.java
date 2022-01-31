package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar extends Rectangle{
	
	private double maxWidth = 200;
	private Player joueur;
	private StackPane stack = new StackPane();
	public StackPane getStack() {
		return stack;
	}

	private Rectangle loose = new Rectangle();
	
	public HealthBar(double x, double y, double width, double height, Player p) {
		this.joueur = p;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height - 10);
		this.setFill(Color.web("#E82A53"));
		this.setArcHeight(20);
		this.setArcWidth(20);
		this.setStroke(Color.BLACK);
		loose.setX(this.getX());
		loose.setY(this.getY());
		loose.setWidth(this.getWidth());
		loose.setHeight(this.getHeight());
		loose.setFill(Color.BLACK);
		loose.setArcHeight(20);
		loose.setArcWidth(20);
		loose.setStroke(Color.BLACK);
		
		this.stack.getChildren().addAll(loose, this);
	}
	
	public void looseHealth(double hit) {
		if(/*hit == joueur.getPdv() ||*/ hit > joueur.getPdv()) {
			Jeu.isGameOver=true;
		}
		double percent = (((this.maxWidth/Player.pdvInitial)*hit)*this.maxWidth*100) 
				/ (Player.pdvInitial*this.maxWidth);
		this.setWidth(this.getWidth()-percent);
	}
	
	public void gainHealth(double ajout) {
		double percent = (((this.maxWidth/Player.pdvInitial)*ajout)*this.maxWidth*100) 
				/ (Player.pdvInitial*this.maxWidth);
		this.setWidth(this.getWidth()+percent);
		//double x = this.getWidth() + ajout;
	}
}
