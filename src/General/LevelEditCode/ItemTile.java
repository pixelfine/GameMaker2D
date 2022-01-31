package General.LevelEditCode;
import General.JeuCode.*;
import General.*;
import General.UtilCode.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ItemTile extends Rectangle{
	enum typeTile{ RIGHT, CENTER, TOP }
	public Vector pos;
	public String code;
	public Image img;
	public typeTile type;
	LevelEditor level;
	/**
	 * @param level
	 * @param type
	 * @param pos
	 * @param code
	 * Créer un ItemTile avec un code prédéfini, le code représente l'id de l'objet permettant de generer l'objet en fonction de l'image
	 */
	public ItemTile(LevelEditor level, typeTile type, Vector pos, String code) {
		this.level=level;
		this.type=type;
		this.pos=pos;
		this.code=code;
		CreateViewImg();
		this.setOnMouseClicked(this::handleMouseClick);
		this.setOnMouseEntered(this::handleMouseEntered);
	}
	/**
	 * @param level
	 * @param type
	 * @param pos
	 * @param transparency
	 * Créer un ItemTile avec un code Vide, donc sans Image
	 */
	public ItemTile(LevelEditor level, typeTile type, Vector pos, boolean transparency) {
		this.level=level;
		this.type=type;
		this.pos=pos;
		this.code=" ";
		CreateViewEmpty(transparency);
		this.setOnMouseClicked(this::handleMouseClick);
		this.setOnMouseEntered(this::handleMouseEntered);
	}
	
	/**
	 * Applique la vue de l'ItemTile avec Image
	 */
	public void CreateViewImg() {
		//this = new Rectangle(pos.x,pos.y, LevelEditor.size, LevelEditor.size);
		this.setWidth(level.size); this.setHeight(level.size);this.setX(pos.x);this.setY(pos.y);
		if(code.equals("2")) {
			this.setFill(Color.web("rgba(12%,7%,83%,0.6)"));
		}else if(code.equals("4")) {this.setFill(Color.DIMGREY);}
		else {this.img=setImage(this.code);
			this.setFill(new ImagePattern(img));
		}
		this.setStroke(Color.BLACK);
	}
	/**
	 * @param transparency
	 * Applique la vue de l'ItemTile sans Image
	 */
	public void CreateViewEmpty(boolean transparency) {
		this.setWidth(level.size); this.setHeight(level.size);this.setX(pos.x);this.setY(pos.y);
		if (transparency) { this.setFill(Color.web("rgba(0%,0%,0%,0.4)"));this.setStroke(Color.web("rgba(100%,100%,100%,0.6)"));}
		else {this.setFill(Color.web("rgba(33%,33%,33%,1)"));this.setStroke(Color.DARKBLUE);}
	}
	/**
	 * @param event
	 * applique une fonction en fonction de Tile pressé
	 */
	public void handleMouseClick(MouseEvent event) {
		System.out.println("Clicked on : X = "+pos.x+"\t Y = "+pos.y+"\t| code :"+this.code+"\t| type :"+type);
		level.superComponent.requestFocus();
		switch(type) {
		case TOP : changeTile(this, level.currentTile); 			break;
		case CENTER : changeCenterTile(level.currentTile, this);  break;
		case RIGHT :													break;
		}
	}
	
	/**
	 * @param event
	 * Permet de mettre le text du current image qui sera affiché
	 */
	public void handleMouseEntered(MouseEvent event) {
		level.currentCursor.setText("Cursor on: "+this.code);
	}
	
	/**
	 * @param me
	 * @param target
	 * Remplace le code du Tile présent target par le Tile me
	 */
	public void changeTile(ItemTile me, ItemTile target) {
		target.code=me.code;
		target.setFill(me.getFill());
	}
	
	/**
	 * @param me
	 * @param target
	 * Permet de changer d'appliquer l'image en fonction du String code de current Tile. 
	 */
	public void changeCenterTile(ItemTile me, ItemTile target) {
		if(me.code.equals(" ")) {
			target.code=me.code;
			target.setFill(Color.web("rgba(0%,0%,0%,0.4)"));
		}else {
			if(level.buttontxt.getText().isEmpty() && level.portaltxt.getText().isEmpty())
				changeTile(me,target);
			else {
				if(me.code!="P") {
					if(level.buttontxt.getText().isEmpty()&&(!level.portaltxt.getText().isEmpty())) {
						if(isInt(level.portaltxt.getText()) && isPortal(me.code)) {
							target.code="("+level.portaltxt.getText()+")";
							target.setFill(me.getFill());
						}
					}else if(!level.buttontxt.getText().isEmpty()) {
						if(level.portaltxt.getText().isEmpty() && isButton(me.code)) {
							target.code="%"+level.buttontxt.getText()+"%";
							target.setFill(me.getFill());
						}else if(level.portaltxt.getText().isEmpty() && me.code.charAt(0)!='(') {
							target.code="["+level.buttontxt.getText()+"|"+me.code+"]";
							target.setFill(me.getFill());
						}else if((!level.portaltxt.getText().isEmpty())&& me.code.charAt(0)=='('&& isPortal(me.code)) {
							target.code="["+level.buttontxt.getText()+"|("+level.portaltxt.getText()+")]";
							target.setFill(me.getFill());
						}
					}
				}
			}
		}
	}
	/**
	 * @param s
	 * @return
	 * Permet de renvoyer true si c'est un Int false sinon
	 */
	public static boolean isInt(String s) {
		boolean res=true;
		try { Integer.parseInt(s); }  catch (NumberFormatException e)  { res=false;}
		return res;
	}
	/**
	 * @param s
	 * @return
	 * Retourne l'image en fonction du code s
	 */
	public static Image setImage(String s) {
		if(s.length()==1) {
			switch(s) {
			case "1" : return Controller.param.getBLOC_IMG();
			case "3" : return Controller.param.getSpike();
			case "7" : return Controller.param.getEnnemi();
			case "P" : return Controller.param.getPerso_right();
			case "C" : return Controller.param.getCannon();
			case "b" : return Controller.param.getBarrel();
			case "R" : return Controller.param.getRocket();
			case "-" : return Controller.param.getLink_bloc();
			case "*" : return Controller.param.getPika_bloc();
			case "+" : return Controller.param.getBoo_bloc();
			case "<" : return Controller.param.aLeft;
            case ">" : return Controller.param.aRight;
            case "^" : return Controller.param.aUp;
            case "v" : return Controller.param.aDown;
            case "~" : return Controller.param.cloud;
            case "h" : return Controller.param.happyMob;
            case "&" : return Controller.param.grass1;
            case "a" : return Controller.param.grass2;
            case "z" : return Controller.param.grass3;
            case "$" : return Controller.param.ground1;
            case "q" : return Controller.param.ground2;
            case "w" : return Controller.param.ground3;
            case "," : return Controller.param.groundL;
            case ";" : return Controller.param.groundR;
            case ":" : return Controller.param.groundC;
            case "#" : return Controller.param.brBlock;
            case "o" : return Controller.param.bob_ombL;
            case "g" : return Controller.param.OrbMob;
            case "s" : return Controller.param.snorlax_sleeping;
            case "B" : return Controller.param.booLeft;
            case "8" : return Controller.param.sand;
            case "i" : return Controller.param.ice1;
            case "j" : return Controller.param.ice2;
            case "k" : return Controller.param.ice3;
            case "9" : return Controller.param.skyBloc;
            case "m" : return Controller.param.skyBloc2;
            case "D" : return Controller.param.SlimeLeft;
            case "d" : return Controller.param.RedSlime;
            case "X" : return Controller.param.heart;
            case "Y" : return Controller.param.firePower;
            case "Z" : return Controller.param.coin;
			}
		}else {
			if(isPortal(s)) return Controller.param.getPortal();
			if(isButton(s)) return Controller.param.getButtonTrigger();
		}return null;
	}
	/**
	 * @param s
	 * @return
	 * Permet de détecter le début du code d'un portail
	 */
	public static boolean isPortal(String s) {
		return(s.charAt(0)=='('&&s.charAt(s.length()-1)==')');
	}
	/**
	 * @param s
	 * @return
	 * Permet de detecter le debut d'un code d'un button
	 */
	public static boolean isButton(String s) {
		return(s.charAt(0)=='%'&&s.charAt(s.length()-1)=='%');
	}

}