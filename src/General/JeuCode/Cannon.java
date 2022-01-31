package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Cannon extends PhysicalObject implements Intersectable{
	
	protected double playerWidth;
	protected double playerHeight;
	private int help = 0;
	
	public int getHelp() {
		return help;
	}

	public void setHelp(int help) {
		this.help = help;
	}

	public Cannon (Vector pos) {
		super(pos);
		super.angle = 0;
		super.coR = 0;
	    super.limit = 0;
	}
	
	public Cannon (Vector pos, Vector velocity, Vector acceleration, double mass) {
	    super(pos, velocity, acceleration, mass);
		super.angle = 0;
		super.coR = 0;
	    super.limit = 0;
	}
	
	public Pane createView() {
		Rectangle r = new Rectangle(100,100);
		Image x = Controller.param.getCannon();
	    r.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(r);
		return pane;
	}

	@Override
	public void collideEffect(Node other) {
		this.display();
		if(other instanceof IntersectableByObject) {
			((IntersectableByObject)other).intersect(this);
		}
	}
	
	@Override
	public boolean checkSides(Node other) {
		if(help > 30) help = 0;
		if(this!=other) {
			if( other instanceof Cannon || other instanceof MoveableObject) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double rebound(Node other) {
		return 0;
	}
	
	public void rotate(double acc){
		this.angle += acc;
		this.setRotate(this.angle);
	}
	
	@Override
	public void intersect(PhysicalObject p) {
		if(p instanceof Player)
		if (p.getBoundsInParent().intersects(this.getBoundsInParent().
				getMinX(), getBoundsInParent().getMinY(), 100, 100)) {
			help ++;
			if(help == 1) {((Player) p).setInCanon(true);}
			
			((Player)p).cannon = this;
			((Player)p).interact = true;
			
			this.playerWidth = p.getWidth();
			this.playerHeight = p.getHeight();
		}
	}
	
	public void launch(Player p) {
		if (p.getBoundsInParent().intersects(
				this.getBoundsInParent().getMinX()+10, getBoundsInParent().
				getMinY()+10, 60, 60)) {
			
			Vector force = new Vector(Math.cos(Math.toRadians(this.angle)
					),Math.sin(Math.toRadians(this.angle)));
			
			
			p.setLaunchCanon(true);
			
			p.limit=20;
			p.velocity.multiply(0);
			p.pos.x=this.pos.x+(4*force.x);
			p.pos.y=this.pos.y+(4*force.y);
			
			force.multiply(20);
			
			p.velocity.add(force);
			p.colliding = false;
			p.cannon = null;
			SpEffect e = new SpEffect(SpEffect.typeEffect.EXPLOSION, this.pos);
		}
	}
}