package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.paint.Color;

public class Goal extends fixedObject{
	public enum goalView{
		ENTRANCE
	}
	public double posX,posY, width, height;
	public goalView view;
	public Goal(goalView view, double posX, double posY, double width, double height) {
		this.posX=posX;
		this.posY=posY;
		this.width=width;
		this.height=height;
		this.view=view;
		setTranslateX(posX);
		setTranslateY(posY);
		createView();
	}
	public void createView() {
		switch(this.view) {
		case ENTRANCE : this.setFill(Color.web("rgba(256,256,256,0.8)"));
		}
		this.setHeight(this.width);
		this.setWidth(this.height);
	}
	@Override
	public void intersect(PhysicalObject o) {
		if(o instanceof Player)
			if(((Player)o).getBoundsInParent().intersects(((Player)o).getBoundsInParent())){
	            Jeu.goalReached=true;
	        }
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
		// TODO Auto-generated method stub
		
	}
}
