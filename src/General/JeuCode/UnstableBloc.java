package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class UnstableBloc extends MovingBloc{

	public UnstableBloc(Vector pos) {
		super(pos);
		this.velocity.y=0;
	}
	@Override
	public Pane createView() {
		Rectangle r = new Rectangle(Monde.Bloc_height, Monde.Bloc_width);
		Image x = Controller.param.brBlock;
	    r.setFill(new ImagePattern(x));
		Pane pane= new Pane();
		pane.getChildren().add(r);
		return pane;
	}
	@Override
	public void setEffect(PhysicalObject other, int n) {
		if(n==1) {
			this.acceleration.y+=0.5;
			setTimer(800,1);
		}
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	eraseThis(); break;
		}
	}
	public void eraseThis() {
		new SpEffect(SpEffect.typeEffect.BLOCEXPLOSION, this.pos); 
		Jeu.paneEraser.add(this);
	}
}
