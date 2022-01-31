package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Mob extends PhysicalObject implements Intersectable{
	
	public static double width = 45;
	public static double height = 45;
	Rectangle r;
	public boolean colliding = false;
	private String mob_nom;
	private int mob_pdv;
	public double pattern;
	public double fixed_pattern;
	public double vitesse = 2;
	public double direction = -vitesse;
	//boolean isDead = false;
	
	private BoundingBox mobBound;
	
	private Random rand = new Random();
	
	public Mob(Vector pos, Vector velocity, Vector acceleration, double mass, String nom,int pdv,int pattern) {
		super(pos, velocity, acceleration, mass);
	    super.coR = 0.1;
	    super.limit = 3;
	    this.mob_nom = nom;
	    this.mob_pdv = pdv;
	    int a = (int) (Math.random() * (pattern - 1));
	    this.pattern = (pattern-a)*Monde.Bloc_width;
	    this.fixed_pattern = pattern*Monde.Bloc_width;
	}   
	
	public String getNom() {return this.mob_nom;}
	
	public int getMob_Pdv() {return this.mob_pdv;}
	@Override
	public Pane createView() {
		Rectangle r = new Rectangle(1.5*Monde.Bloc_width,1.5*Monde.Bloc_height);
	    Image x = Controller.param.getEnnemi();
	    r.setFill(new ImagePattern(x));
	    Pane pane = new Pane();
	    pane.getChildren().add(r);
	    return pane;
	}
	@Override
	public void collideEffect(Node other) {
		if(other instanceof IntersectableByObject) {
			((IntersectableByObject)other).intersect(this);
		}
	}
	@Override
	public boolean checkSides(Node other) {
		if(this!=other) {
			if(other instanceof Bloc || other instanceof UnstableBloc)
				return true;
		}
		return false;
	}
	
	public void moving () {
		if (pattern>0 && pattern<=fixed_pattern) {
			super.velocity.x = super.velocity.x+direction;
			pattern -= vitesse;
			if (pattern <= 0) {
				pattern = fixed_pattern;
				switching_side();
			}
		}
	}
	
	public void switching_side() {
		if (direction == vitesse) {
			direction = -vitesse;
		}else if (direction == -vitesse) {
			direction = vitesse;
		}
	}
	
	public void eraseMob() {
		int nombre = rand.nextInt(2 - 0 + 1) + 0;
		int type = rand.nextInt((2 - 1) + 1) + 1;
		if(nombre > 0) Jeu.arrayAdder.add(new Item(type, this.pos.x, this.pos.y + 5));
		new SpEffect("Sounds/SoundEffects/goomba0.mp3",1);
		Jeu.paneEraser.add(this);
		//isDead(true);
	}
	
	@Override
	public void intersect(PhysicalObject other) {
			//mobBound = new BoundingBox(this.pos.x+10, this.pos.y-10, this.getBoundsInParent().getWidth()-20, 15);
			if(other.getBoundsInParent().intersects(this.getBoundsInParent())) {
				eraseMob();
				other.velocity.y*=-1;
				other.update();
				other.display();
				other.colliding=true;
			}else {
				other.colliding=true;
				this.switching_side();
			}
	}
	/*public void isDead(boolean dead) {
		this.isDead=dead;
	}*/
	
}