package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Rocket extends Cannon {
	boolean inSound=false;
	public Rocket(Vector pos){
		super(pos);
		super.limit = 6;
	}
	
	public Pane createView() {
		Rectangle r = new Rectangle(100,100);
		Image x = Controller.param.getRocket();
	    r.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(r);
		return pane;
	}
	@Override
	public boolean checkSides(Node other) {
		if(super.getHelp() > 30) super.setHelp(0); 
		if(this!=other) {
			if( other instanceof Cannon || other instanceof MoveableObject || other instanceof Bloc) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void collideDownEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.y+=(this.velocity.y/10);
		}
		super.collideDownEffect(other, reb);
	}
	@Override
	public void collideUpEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.y+=(this.velocity.y/10);
		}
		super.collideUpEffect(other, reb);
	}
	@Override
	public void collideLeftEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.x+=(this.velocity.x/10);
		}
		super.collideLeftEffect(other, reb);
	}
	@Override
	public void collideRightEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.x+=(this.velocity.x/10);
		}
		super.collideRightEffect(other, reb);
	}
	public void thrust() {
	    Vector force = new Vector(Math.cos(Math.toRadians(this.angle)),
	    		Math.sin(Math.toRadians(this.angle)));
	    force.multiply(0.3);
	    this.velocity.add(force);
	    applySound();
	}
	public void applySound(){
		if(!inSound) {
			inSound=true;
			setTimer(1000,1);
			new SpEffect("Sounds/SoundEffects/driving.mp3",1);
		}
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	inSound=false; break;
		}
	}
}
