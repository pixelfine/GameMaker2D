package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.Random;

import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Snorlax extends PhysicalObject implements Intersectable, Detection{
	Rectangle rect;
	BoundingBox detectionBound;
	BoundingBox detectionBound2;
	Collision collision = new Collision();
	boolean isAsleep=true;
	int hit=0;
	private Random rand = new Random();
	public Snorlax(Vector pos){
		super(pos);
		super.velocity=new Vector(0,0);
		super.acceleration=new Vector(0,0);
		super.angle=0;
		super.coR=0;
	    super.limit=0;
	    super.mass=80;
	}
	@Override
	public Pane createView() {
		rect = new Rectangle(Monde.Bloc_width*2.5,Monde.Bloc_height*2.5);
		Image x = Controller.param.snorlax_hitR;
	    rect.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(rect);
		return pane;
	}
	@Override
	public boolean checkSides(Node other) {
		if(this!=other) {
			if(other instanceof Bloc || other instanceof UnstableBloc||other instanceof Obstacle)
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
	public void detection(Node other) {
		if(!isAsleep) {
			this.detectionBound=new BoundingBox(this.pos.x-600, this.pos.y-600, this.getBoundsInParent().getWidth()+1200, this.getBoundsInParent().getWidth()+1200);
			if(other.getBoundsInParent().intersects(detectionBound)) {
				int type=0;
				this.detectionBound2=new BoundingBox(this.pos.x-50, this.pos.y-50, this.getBoundsInParent().getWidth()+100, this.getBoundsInParent().getWidth()+100);
				if(other.getBoundsInParent().intersects(detectionBound2)) {
					type=1;
				}
				displayType(type, other);
			}
		}else {
			this.limit=0; this.rect.setFill(new ImagePattern(Controller.param.snorlax_sleeping));
		}
	}
	public void displayType(int type, Node other) {
		if(collision.centerYGreaterThanCenterX(this.pos.x, this.pos.y, this.getWidth(), this.getHeight(), ((PhysicalObject)other).pos.x, ((PhysicalObject)other).pos.y, ((PhysicalObject)other).getWidth(), ((PhysicalObject)other).getHeight())) {
			super.limit=9;
			this.rect.setFill(new ImagePattern(Controller.param.snorlax_jump));
			if(this.pos.y>((PhysicalObject)other).pos.y && (super.colliding)) {
				this.pos.y-=15;
				this.velocity.y-=9;
				super.update();
				colliding=false;
			}else this.acceleration.y+=0.53;
		}else {
			if(type==1) {
				super.limit=5;
				if(((PhysicalObject)other).pos.x>this.pos.x) {
					rect.setFill(new ImagePattern(Controller.param.snorlax_hitR));
					this.acceleration.x+=0.13;
				}else { this.acceleration.x-=0.13; rect.setFill(new ImagePattern(Controller.param.snorlax_hitL));}
			}else if(type==0) {
				super.limit=11;
				if(((PhysicalObject)other).pos.x>this.pos.x) {
					rect.setFill(new ImagePattern(Controller.param.snorlax_right));
					this.acceleration.x+=0.2;
				}else { this.acceleration.x-=0.2; rect.setFill(new ImagePattern(Controller.param.snorlax_left));}
			}
		}
	}
	public void hitTaken() {
		if(isAsleep) {
			isAsleep=false;
		}
		hit++;
		new SpEffect("Sounds/SoundEffects/Snorlax_Sound.mp3",0.7);
		if(hit==4) {
			int nombre = rand.nextInt(2 - 0 + 1) + 0;
			int type = rand.nextInt((2 - 1) + 1) + 1;
			if(nombre > 0) Jeu.arrayAdder.add(new Item(type, this.pos.x, this.pos.y + 5));
			Jeu.paneEraser.add(this); new SpEffect(SpEffect.typeEffect.POKEBALL, pos);
		}
	}
	@Override
	public void intersect(PhysicalObject other) {
	}
}