package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;

public class Item extends fixedObject implements Intersectable{
	
	private Image image;
	private int type;
	
	/**
	 * La variable type represente la nature de l'item.
	 * 1 : coeur
	 * 2 : pouvoir de la boule de feu
	 * @param type
	 */
	public Item(int type, double x, double y) {
		this.setX(x);
		this.setY(y);
		this.type = type;
		switch (type) {
		case 1:
			this.image = Controller.param.heart;
			break;
		case 2 :
			this.image = Controller.param.firePower;
			break;
		case 3 : 
			this.image = Controller.param.coin;
		default:
			break;
		}
		createView();
	}
	
	public void createView() {
		this.setFill(new ImagePattern(this.image));
		this.setWidth(40);
		this.setHeight(40);
	}

	@Override
	public void intersect(PhysicalObject o) {
		if(o instanceof Player) {
			switch(type) {
			case 1 :
				new SpEffect("Sounds/SoundEffects/heal.mp3",0.7);
				((Player)o).setPdv(((Player)o).getPdv()+10);
				((Player)o).getHP().gainHealth(10);
				eraseItem();
				break;
			case 2 :
				if(this.verification(((Player)o).getPowers(), this)) {
					((Player)o).getPowers().add(this);
					eraseItem();
				}else eraseItem();
				new SpEffect("Sounds/SoundEffects/FireSound.mp3",1);
				break;
			case 3 : 
				new SpEffect("Sounds/SoundEffects/CoinSound.mp3",0.3); eraseItem(); break;
			default :
				break;
			}
		}
	}
	
	public void eraseItem() {
		Jeu.paneEraser.add(this);
	}

	@Override
	public void intersectEffect(PhysicalObject o) {
	}
	
	public boolean verification(ArrayList<Item> o, Item i) {
		for(Item item : o) {
			if(item.type == i.type) return false;
		}
		return true;
	}

	public int getType() {
		return type;
	}
}
