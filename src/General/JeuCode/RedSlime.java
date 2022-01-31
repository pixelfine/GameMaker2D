package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RedSlime extends PatternMob{
	public RedSlime (Vector pos) {
		super(pos);
	    super.limit=3;
	}

	@Override
	public Pane createView() {
		Rectangle r = new Rectangle(40,40);
		Image x = Controller.param.RedSlime;
	    r.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(r);
		return pane;
	}
	
	@Override
	public void applySound(){
		new SpEffect("Sounds/SoundEffects/SlimeSound.mp3",1);
	}
}