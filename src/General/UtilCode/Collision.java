package General.UtilCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.shape.Shape;

public class Collision {
	enum collisionType{
		RectRect
	}
	public Collision(Shape me, Shape other, collisionType type) {
		switch(type) {
		case RectRect : 
			break;
		}
	}
	public Collision() {}
	public boolean centerYGreaterThanCenterX(double myX, double myY, double myWidth, double myHeight, double oX, double oY, double oWidth, double oHeight) {
		return (
					Math.sqrt(
						Math.pow((myY+(myHeight/2))-(oY+(oHeight/2)),2)
					)  >
					Math.sqrt(
							Math.pow((myX+(myWidth/2))-(oX+(oWidth/2)),2)
					)
				);
	}
	public boolean collideDown(double myY, double myHeight, double oY, double oHeight) {
		return (myY+myHeight > oY && myY+myHeight < oY + oHeight) ;
	}
	public boolean collideUp(double myY, double myHeight, double oY, double oHeight) {
		return (myY < oY+oHeight && myY > oY) ;	
	}
	public boolean collideRight(double myX, double myWidth, double oX, double oWidth) {
		return (myX+myWidth > oX && myX+myWidth < oX+oWidth);
	}
	public boolean collideLeft(double myX, double myWidth, double oX, double oWidth) {
		return (myX < oX+oWidth && myX > oX);
	}
}