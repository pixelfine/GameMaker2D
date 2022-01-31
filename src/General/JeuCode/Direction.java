package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.paint.ImagePattern;

public class Direction extends fixedObject{
	public enum directionView{UP,DOWN,LEFT,RIGHT}
	public double posX,posY, width, height;
	public directionView view;
	public Direction(directionView view, double posX, double posY, double width, double height) {
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
		case UP : this.setFill(new ImagePattern(Controller.param.aUp)); break;
		case DOWN : this.setFill(new ImagePattern(Controller.param.aDown)); break;
		case LEFT : this.setFill(new ImagePattern(Controller.param.aLeft)); break;
		case RIGHT : this.setFill(new ImagePattern(Controller.param.aRight)); break;
		}
		this.setHeight(this.width);
		this.setWidth(this.height);
	}
	@Override
	public void intersect(PhysicalObject o) {
		if(o instanceof MovingBloc) {
			switch(this.view) {
			case UP : o.velocity=new Vector(0,-7); 	break;
			case DOWN : o.velocity=new Vector(0,7); 	break;
			case LEFT : o.velocity=(new Vector(-7,0));	break;
			case RIGHT : o.velocity=(new Vector(7,0));	break;
			}
		}else {
			if(o instanceof Player) {
				switch(this.view) {
				case UP : o.applyForce(new Vector(0,-7)); 	break;
				case DOWN : o.applyForce(new Vector(0,7)); 	break;
				case LEFT : o.applyForce(new Vector(-7,0));	break;
				case RIGHT : o.applyForce(new Vector(7,0));	break;
				}
				if(!((Player)o).inSound) {
					((Player)o).inSound=true;
					new SpEffect("Sounds/SoundEffects/windSound.mp3",1);
					((Player)o).setTimer(3200, 5);
				}
			}
		}
		
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
		// TODO Auto-generated method stub
		
	}
}
