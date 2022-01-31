package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Boo extends PhysicalObject implements Intersectable, Detection{
	Rectangle rect;
	BoundingBox detectionBound;
	Collision collision = new Collision();
	boolean isHidden=false;
	int hit=0;
	public Boo(Vector pos){
		super(pos);
		super.velocity=new Vector(0,0);
		super.acceleration=new Vector(0,0);
		super.angle=0;
		super.coR=0;
	    super.limit=1;
	    super.mass=80;
	}
	@Override
	public Pane createView() {
		rect = new Rectangle(Monde.Bloc_width*1,Monde.Bloc_height*1);
		Image x = Controller.param.booRight;
	    rect.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(rect);
		return pane;
	}
	@Override
	public boolean checkSides(Node other) {
		return false;
	}
	@Override
	public void collideEffect(Node other) {
		if(other instanceof IntersectableByObject) {
			((IntersectableByObject)other).intersect(this);
		}
	}
	@Override
	public void detection(Node other) {	
		this.detectionBound=new BoundingBox(this.pos.x-700, this.pos.y-700, this.getBoundsInParent().getWidth()+1400, this.getBoundsInParent().getWidth()+1400);
		if(other.getBoundsInParent().intersects(detectionBound)) {
			int type=0;
			if(this.pos.x> ((PhysicalObject)other).pos.x) {
				this.velocity.x-=1;
				type=1;
			}else {
				this.velocity.x+=1;
				type=2;
			}if(this.pos.y>((PhysicalObject)other).pos.y) {
				this.velocity.y-=0.11;
			}else this.velocity.y+=0.11;
			displayType(type);
		}
		intersect((PhysicalObject)other);
	}
	public void displayType(int type) {
		if(!this.isHidden) {
			switch(type) {
			case 1 : this.rect.setFill(new ImagePattern(Controller.param.booLeft)); break;
			case 2 : this.rect.setFill(new ImagePattern(Controller.param.booRight)); break;
			}
		}
	}
	@Override
	public void intersect(PhysicalObject other) {
		if(other.getBoundsInParent().intersects(this.getBoundsInParent())) {
			if(other instanceof Player) {
				if(!((Player)other).isInvicible) {
					((Player)other).applyInvincibility(10);
					new SpEffect("Sounds/SoundEffects/Boo_Sound.mp3",0.7);
					System.out.println(isHidden);
					isHidden=true;
					rect.setFill(new ImagePattern(Controller.param.booHidden));
					setTimer(1000, 1);
					fadeOut();
				}
			}
		}
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	isHidden=false;
		case 2 : 	fadeIn();
		}
	}
	public void fadeOut() {
		FadeTransition fadeOut = new FadeTransition(Duration.millis(1000),this);
		fadeOut.setFromValue(1.0);fadeOut.setToValue(0.0);
		setTimer(1000,2);
		fadeOut.play();
	}
	public void fadeIn() {
		FadeTransition fadeIn = new FadeTransition(Duration.millis(1000),this);
		fadeIn.setFromValue(0.0);fadeIn.setToValue(1.0);
		fadeIn.play();
	}
}