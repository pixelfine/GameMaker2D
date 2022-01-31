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

public class Fireball extends PhysicalObject implements IntersectableByObject{
	public static double width = 80;
	public static double height = 80;
	public static boolean colliding = false;
	boolean bloc = false;
	boolean erased = false;
	
	public Fireball(Vector pos, Vector velocity, Vector acceleration, double mass) {
	    super(pos, velocity, acceleration, mass);
	    super.coR=1.53;
	    super.limit=12;
	    setTimer(3000,1);
	}
	
	@Override
	public Pane createView() {
		Rectangle r = new Rectangle(50,50);
	    Image x = Controller.param.getIceBall();
	    r.setFill(new ImagePattern(x));
	    Pane pane = new Pane();
	    pane.getChildren().add(r);
	    return pane;
	}
	@Override
	public boolean checkSides(Node other) {
		return false;
	}
	public void eraseItem(Pane other) {
		Jeu.paneEraser.add(other);
		if(other instanceof Fireball) {
			SpEffect eff = new SpEffect(SpEffect.typeEffect.EXPLOSION, this.pos);
			erased=true;
		}
	}
	
	@Override
	public void collideEffect(Node other) {
		if(other instanceof Bloc || other instanceof Obstacle || other instanceof Bob_Omb || other instanceof UnstableBloc || other instanceof Snorlax || other instanceof BlueSlime) {
			if(other.getBoundsInParent().intersects(this.pos.x+10, this.pos.y-10, this.getBoundsInParent().getWidth()-20, this.getBoundsInParent().getHeight()-20)) {
				eraseItem(this);
				if(other instanceof Bob_Omb) ((Bob_Omb)other).eraseMob();
				if(other instanceof BlueSlime) ((BlueSlime)other).eraseMob();
			}
		}
	}
	@Override
	public boolean removeOther(Node other) {
		if(other instanceof Mob) {
			if(other.getBoundsInParent().intersects(this.pos.x+10, this.pos.y-10, this.getBoundsInParent().getWidth()-20, this.getBoundsInParent().getHeight()-20)) {
				eraseItem(this);
				((Mob) other).eraseMob();
				return true;
			}
		}
		return false;
	}
	@Override
	public void intersect(PhysicalObject other) {

	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(1)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	if(erased==false)eraseItem(this);
		
		}
	}
	
}