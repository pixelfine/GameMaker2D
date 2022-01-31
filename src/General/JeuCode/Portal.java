package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/*
 * Class Objet Portal, cette class n'extends pas fixedObject car elle est dependante de PortalLink 
 * qui a un algorithme speciale et incompatible avec l'interface Intersectable
 */
public class Portal extends Rectangle{
	Vector pos;
	int id;
	public Portal(Vector pos, int id) {
		this.pos=pos;
		this.id=id;
		setTranslateX(this.pos.x);
		setTranslateY(this.pos.y);
		createView();
	}
	public void createView() {
		this.setFill(new ImagePattern(Controller.param.getPortal()));
		this.setHeight(80);
		this.setWidth(80);
	}
}
