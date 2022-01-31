package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.paint.Color;

public class Water extends fixedObject implements IntersectableByObject{
	public double x,y,c;
	public waterView view;
	public enum waterView{
		waterBloc
	}
	public Water(waterView view, double x, double y, double c){
		this.x=x;
		this.y=y;
		this.c=c;
		setTranslateX(this.x);
		setTranslateY(this.y);
		this.view=view;
		createView();
	}
	public void createView() {
		switch(this.view) {
		case waterBloc : this.setFill(Color.web("rgba(12%,7%,83%,0.6)"));
		}
		this.setHeight(Monde.Bloc_height);
		this.setWidth(Monde.Bloc_width);
	}
	@Override
	public void intersect(PhysicalObject p) {
		p.drag(this);
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
	}
	
}
