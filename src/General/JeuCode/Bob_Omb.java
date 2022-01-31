package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bob_Omb extends PhysicalObject implements Intersectable, Detection{
	Rectangle rect;
	BoundingBox detectionBound;
	BoundingBox explodingBound;
	boolean isCountingDown=false;
	boolean isErased=false;
	
	public Bob_Omb(Vector pos) {
		super(pos);
		super.velocity=new Vector(0,0);
		super.acceleration=new Vector(0,0);
		super.angle=0;
		super.coR=0;
	    super.limit=7;
	    super.mass=80;
	}
	@Override
	public Pane createView() {
		rect = new Rectangle(Monde.Bloc_width*1.5,Monde.Bloc_height*1.5);
		Image x = Controller.param.bob_ombL;
	    rect.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(rect);
		return pane;
	}
	@Override
	public void display() {
		super.display();
		if(this.velocity.x>=0.1) this.rect.setFill(new ImagePattern(Controller.param.bob_ombR));
		else this.rect.setFill(new ImagePattern(Controller.param.bob_ombL));
	}
	@Override
	public boolean checkSides(Node other) {
		if(this!=other) {
			if(other instanceof Bloc || other instanceof UnstableBloc)
				return true;
		}
		return false;
	}
	@Override
	public void collideEffect(Node other) {
		if(other instanceof IntersectableByObject) {
			((IntersectableByObject)other).intersect(this);
		}
	}
	@Override
	public void intersect(PhysicalObject other) {
		//mobBound = new BoundingBox(this.pos.x+10, this.pos.y-10, this.getBoundsInParent().getWidth()-20, 15);
		if(other.getBoundsInParent().intersects(this.getBoundsInParent())) {
			this.velocity.x=0;
			if(!isCountingDown) eraseMob(1);
			other.velocity.y*=-1;
			other.update();
			other.display();
			other.colliding=true;
		}else {
			other.colliding=true;
		}
	}
	public void eraseMob(int i) {
		if(!isCountingDown && i==1) {
			new SpEffect(SpEffect.typeEffect.COUNTDOWN, this.pos);
			isCountingDown=true;
			setTimer(1700, 1);
		}else {eraseMob();}
	}
	@Override
	public void detection(Node other) {
		if(!isCountingDown) {
			this.detectionBound=new BoundingBox(this.pos.x-450, this.pos.y-450, this.getBoundsInParent().getWidth()+900, this.getBoundsInParent().getWidth()+900);
			if(other.getBoundsInParent().intersects(detectionBound)) {
				if(((PhysicalObject)other).pos.x>this.pos.x) {
					this.acceleration.x+=0.13;
				}else { this.acceleration.x-=0.13;}
			}
		}
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	eraseMob(); break;
		}
	}
	public void eraseMob() {
		new SpEffect(SpEffect.typeEffect.EXPLOSION, this.pos); Jeu.paneEraser.add(this);
		explodingBound=new BoundingBox(this.getLayoutX()-90, this.getLayoutY()-90, this.getBoundsInParent().getWidth()+180, this.getBoundsInParent().getHeight()+180);
		isErased=true;
	}
	
}
