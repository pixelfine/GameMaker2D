package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class MovingBloc extends PhysicalObject {

	public MovingBloc(Vector pos, Vector velocity, Vector acceleration, double mass) {
		super(pos, velocity, acceleration, mass);
		super.angle=0;
		super.coR=0;
	    super.limit=2;
	}
	public MovingBloc (Vector pos) {
		super(pos);
		super.velocity=new Vector(0,6);
		super.acceleration=new Vector(0,0);
		super.angle=0;
		super.coR=0;
	    super.limit=2;
	    super.mass=15;
	}

	@Override
	public Pane createView() {
		Rectangle r = new Rectangle(100,100);
		Image x = Controller.param.cloud;
	    r.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(r);
		return pane;
	}
	@Override
	public void collideEffect(Node other) {
		if(other instanceof Direction) {
			((Direction)other).intersect(this);
		}
	}
	@Override
	public boolean checkSides(Node other) {
		return false;
	}
	public void setEffect(PhysicalObject other, int n) {
		if(n==1||n==2)
			setRelativeVelocity(other);
	}
	public void setRelativeVelocity(PhysicalObject other) {
		other.velocity.x=this.velocity.x;
	}
}
