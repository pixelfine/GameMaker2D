package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class CharacterBox extends fixedObject{
	double x,y, transHeight, transWidth;
	Image box, trans;
	public CharacterBox(double posX, double posY, double transHeight, double transWidth, Image box, Image trans){
		this.x=posX;
		this.y=posY;
		this.transHeight=transHeight;
		this.transWidth=transWidth;
		this.box=box;
		this.trans=trans;
		setTranslateX(this.x);
		setTranslateY(this.y);
		createView();
	}
	public void createView() {
		this.setFill(new ImagePattern(this.box));
		this.setHeight(80);
		this.setWidth(80);
	}
	@Override
	public void intersect(PhysicalObject p) {
		if(p instanceof Player) {
			if (p.getBoundsInParent().intersects(this.getBoundsInParent().getMinX()+10, getBoundsInParent().getMinY()+10, 60, 60)) {
					change((Player)p, transHeight, transWidth, this.trans);
			}
		}
	}
	public void change(Player p, double height, double width, Image img) {
		p.r.setHeight(height);
		p.r.setWidth(width);
		p.initialLimit=(height/30)+4;
		//p.r.setFill(new ImagePattern(img));
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
	}
}
