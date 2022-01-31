package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class MoveableObject extends PhysicalObject {
	ArrayList<PhysicalObject> physicalArray = new ArrayList<PhysicalObject>();

	public MoveableObject (Vector pos) {
		super(pos);
		super.angle=0;
		super.coR=0;
	    super.limit=6;
	    super.mass=80;
	}
	public MoveableObject (Vector pos, Vector velocity, Vector acceleration, double mass) {
	    super(pos, velocity, acceleration, mass);
		super.angle=0;
		super.coR=0;
	    super.limit=6;
	}
	public Pane createView() {
		Rectangle r = new Rectangle(1.8*Monde.Bloc_width,1.8*Monde.Bloc_height);
		Image x = Controller.param.getBarrel();
	    r.setFill(new ImagePattern(x));
		Pane pane= new Pane();
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
		if(this !=other) {
			if(other instanceof Bloc || other instanceof Obstacle || other instanceof MoveableObject || other instanceof UnstableBloc) {
				return true;
			}
		}return false;
	}
	@Override
	public double rebound(Node other) {
		if(other instanceof Bloc)
			return 0.5;
		return super.rebound(other);
	}
}