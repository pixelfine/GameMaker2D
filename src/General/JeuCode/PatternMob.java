package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PatternMob extends MovingBloc{
	public PatternMob(Vector pos, Vector velocity, Vector acceleration, double mass) {
		super(pos, velocity, acceleration, mass);
	    super.limit=7;
	}
	public PatternMob (Vector pos) {
		super(pos);
	    super.limit=7;
	}

	@Override
	public Pane createView() {
		Rectangle r = new Rectangle(90,90);
		Image x = Controller.param.happyMob;
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
	public void applySound() {
		new SpEffect("Sounds/SoundEffects/happy.mp3",1);
	}
}
