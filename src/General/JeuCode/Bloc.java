package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.paint.ImagePattern;

public class Bloc extends fixedObject {
	public enum blocView{
		SAND,
		ROCK,
		GRASS1,GRASS2,GRASS3,
		GROUND1,GROUND2,GROUND3,GROUNDL,GROUNDR, GROUNDC,
		ICE1, ICE2, ICE3,
		CLOUD, CLOUD2
	}
	public double posX,posY, width, height;
	public blocView view;
	public double rebound=0;
	public double fric;
	public Bloc(blocView view, double posX, double posY, double width, double height) {
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
		case SAND : this.setFill(new ImagePattern(Controller.param.sand)); rebound=0.5; fric=8; break;
		case ROCK : this.setFill(new ImagePattern(Controller.param.getRock())); rebound=1.5; fric=10; break;
		case GRASS1 : this.setFill(new ImagePattern(Controller.param.grass1)); rebound=1; fric=12; break;
		case GRASS2 : this.setFill(new ImagePattern(Controller.param.grass2)); rebound=1; fric=12; break;
		case GRASS3 : this.setFill(new ImagePattern(Controller.param.grass3)); rebound=1; fric=12; break;
		case GROUND1 : this.setFill(new ImagePattern(Controller.param.ground1)); rebound=1; fric=12; break;
		case GROUND2 : this.setFill(new ImagePattern(Controller.param.ground2)); rebound=1; fric=12; break;
		case GROUND3 : this.setFill(new ImagePattern(Controller.param.ground3)); rebound=1; fric=12; break;
		case GROUNDL : this.setFill(new ImagePattern(Controller.param.groundL)); rebound=1; fric=12; break;
		case GROUNDR : this.setFill(new ImagePattern(Controller.param.groundR)); rebound=1; fric=12; break;
		case GROUNDC : this.setFill(new ImagePattern(Controller.param.groundC)); rebound=1; fric=12; break;
		case ICE1 : this.setFill(new ImagePattern(Controller.param.ice1)); rebound=1.5; fric=1.1; break;
		case ICE2 : this.setFill(new ImagePattern(Controller.param.ice2)); rebound=1.5; fric=1.1; break;
		case ICE3 : this.setFill(new ImagePattern(Controller.param.ice3)); rebound=1.5; fric=1.1; break;
		case CLOUD : this.setFill(new ImagePattern(Controller.param.skyBloc)); rebound=0; fric=10; break;
		case CLOUD2 : this.setFill(new ImagePattern(Controller.param.skyBloc2)); rebound=0; fric=10; break;
		}
		this.setHeight(this.width);
		this.setWidth(this.height);
	}
	@Override
	public void intersect(PhysicalObject o) {
		// TODO Auto-generated method stub
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
		// TODO Auto-generated method stub
		
	}
	public double reboundEffect() {
		return rebound;
	}
	public void applyFricOn(PhysicalObject o) {
		o.applyForce(o.friction(fric));
	}
}
