package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

public class ButtonTrigger extends fixedObject implements IntersectableByObject{
	ArrayList<ButtonContent> buttonContent = new ArrayList<ButtonContent>();
	Vector pos;
	int id;
	boolean isOn=false;
	boolean activated=true;
	public ButtonTrigger(Vector pos, int id) {
		this.pos=pos;
		this.id=id;
		setTranslateX(this.pos.x);
		setTranslateY(this.pos.y);
		createView();
	}
	public void createView() {
		this.setFill(new ImagePattern(Controller.param.getButtonTrigger()));
		this.setHeight(Monde.Bloc_height);
		this.setWidth(Monde.Bloc_width);
	}
	@Override
	public void intersect(PhysicalObject p) {
		if(p instanceof Player || p instanceof MoveableObject)
		if(p.getBoundsInParent().intersects(this.pos.x+5, this.pos.y+35, this.getWidth()-10, this.getHeight()-40)) {
			this.setFill(new ImagePattern(Controller.param.getButtonTrigger2()));
			p.pos.y -= 0.7;
			p.velocity.y *= -1;
			p.colliding=true;
			isOn=true;
		}
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
		// TODO Auto-generated method stub
	}
	public void setContent(ArrayList<ButtonContent> arr) {
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).id==this.id) {
				this.buttonContent.add(arr.get(i));
				//arr.remove(i);
			}
		}
	}
	public void initItem() {
		for(int i=0;i<this.buttonContent.size();i++) {
				Jeu.arrayAdder.add(Monde.init_map(buttonContent.get(i).code, buttonContent.get(i).pos.x, buttonContent.get(i).pos.y, extractCode(this.buttonContent.get(i))));
		}
	}
	public Integer extractCode(ButtonContent button) {
		String res="";
		if(button.code.charAt(0)=='('||button.code.charAt(0)=='%') {
			for(int i=1;i<button.code.length()-1;i++) {
				res+=button.code.charAt(i);
			}
			return Integer.parseInt(res);
		}else return 0;
	}
}
