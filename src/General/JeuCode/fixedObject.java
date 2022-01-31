package General.JeuCode;
import General.*;
import javafx.scene.shape.Rectangle;

abstract class fixedObject extends Rectangle implements Intersectable{
	public abstract void intersect(PhysicalObject o);
	public abstract void intersectEffect(PhysicalObject o) ;
}
