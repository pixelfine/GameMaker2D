package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class MobGenerator extends fixedObject{
	Vector pos;
	final int maxMob=2;
	boolean generated=false;
	ArrayList<Bob_Omb> arrList = new ArrayList<Bob_Omb>();
	MobGenerator(Vector pos){
		this.pos=pos;
		super.setX(pos.x);
		super.setY(pos.y);
		super.setWidth(2*Monde.Bloc_width);
		super.setHeight(2*Monde.Bloc_height);
		super.setFill(new ImagePattern(Controller.param.OrbMob));
	}
	public void generateBobOmb(int i) {
		if(arrList.size()<i && generated==false) {
			Bob_Omb bob=new Bob_Omb(new Vector(pos.x,pos.y));
			arrList.add(bob);
			Jeu.arrayAdder.add(bob);
			generated=true;
			setTimer(3000,1);
		}
	}
	
	public void removeMob() {
		for(int i=0;i<arrList.size();i++) {
			if(arrList.get(i).isErased) {
				arrList.remove(arrList.get(i));
			}
		}
	}
	public void update() {
		generateBobOmb(maxMob);
		removeMob();
	}
	@Override
	public void intersect(PhysicalObject o) {
	}
	@Override
	public void intersectEffect(PhysicalObject o) {
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	generated=false;
		}
	}
	
}
