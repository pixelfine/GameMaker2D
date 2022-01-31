package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.geometry.BoundingBox;
import javafx.scene.paint.ImagePattern;

public class Obstacle extends fixedObject{
	public enum obstacleView{
		SPIKE
	}
	public double posX,posY, width, height;
	public obstacleView view;
	public BoundingBox bounds;
	public Obstacle(obstacleView view, double posX, double posY, double width, double height) {
		this.posX=posX;
		this.posY=posY;
		this.width=width;
		this.height=height;
		this.view=view;
		setTranslateX(posX);
		setTranslateY(posY);
		this.bounds=new BoundingBox(posX+20, posY+20, width-40, height-40);
		createView();
	}
	public void createView() {
		switch(this.view) {
		case SPIKE : this.setFill(new ImagePattern(Controller.param.getSpike()));
		}
		this.setHeight(this.width);
		this.setWidth(this.height);
	}
	@Override
	public void intersect(PhysicalObject o) {
		if(o instanceof Player) {
			if(o.getBoundsInParent().intersects(this.bounds)){
	            Jeu.isGameOver=true;
	        }
		}
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
		// TODO Auto-generated method stub
	}
}
