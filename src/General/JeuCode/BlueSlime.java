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

public class BlueSlime extends PhysicalObject implements Intersectable, Detection{
	Rectangle rect;
	BoundingBox detectionBound;
	int hitTaken=0;
	private Random rand = new Random();
	
	public BlueSlime(Vector pos) {
		super(pos);
		super.velocity=new Vector(0,0);
		super.acceleration=new Vector(0,0);
		super.angle=0;
		super.coR=0;
	    super.limit=6;
	    super.mass=91;
	}
	@Override
	public Pane createView() {
		rect = new Rectangle(1.5*Monde.Bloc_width,1.5*Monde.Bloc_height);
		Image x = Controller.param.SlimeLeft;
	    rect.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(rect);
		return pane;
	}
	@Override
	public void display() {
		super.display();
		if(this.velocity.x>=0.1) this.rect.setFill(new ImagePattern(Controller.param.SlimeRight));
		else this.rect.setFill(new ImagePattern(Controller.param.SlimeLeft));
	}
	@Override
	public boolean checkSides(Node other) {
		if(this!=other) {
			if(other instanceof Bloc || other instanceof UnstableBloc|| other instanceof Obstacle)
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
			eraseMob();
			other.velocity.y*=-1;
			other.update();
			other.display();
			other.colliding=true;
		}else {
			other.colliding=true;
		}
	}
	public void eraseMob() {
			hitTaken++;
			reshape();
			new SpEffect("Sounds/SoundEffects/SlimeSound.mp3",1);
			if(hitTaken>2) { 
				int nombre = rand.nextInt(2 - 0 + 1) + 0;
				int type = rand.nextInt((2 - 1) + 1) + 1;
				if(nombre > 0) Jeu.arrayAdder.add(new Item(type, this.pos.x, this.pos.y + 5));
				Jeu.paneEraser.add(this);}
	}
	public void reshape() {
		this.rect.setWidth(this.rect.getWidth()-30);
		this.rect.setHeight(this.rect.getHeight()-30);
	}
	@Override
	public void detection(Node other) {
			this.detectionBound=new BoundingBox(this.pos.x-300, this.pos.y-300, this.getBoundsInParent().getWidth()+600, this.getBoundsInParent().getWidth()+600);
			if(other.getBoundsInParent().intersects(detectionBound)) {
				if(((PhysicalObject)other).pos.x>this.pos.x) {
					this.acceleration.x+=0.13;
				}else { this.acceleration.x-=0.13;}
			}
	}
}
