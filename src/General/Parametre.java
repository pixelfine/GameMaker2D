package General;
import General.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
/*
 * Classe de gestion de sources externe, elle permet une plus grande maniabilite en cas de changement de donnees externes. 
 * i.e img/gif/media...
 */
public class Parametre {
	//General sources modifiable par code
	//public String path = getClass().getResource(Controller.param.Songsnow).toExternalForm(); 
	public ClassLoader loader = Parametre.class.getClassLoader();
	
	private Image BLOC_IMG= new Image(getClass().getResource("Sprites/Others/grass.jpg").toExternalForm());
	private Image BACKGROUND_IMG = new Image(getClass().getResource("Sprites/Backgrounds/volcano.jpg").toExternalForm());
	private String idleSong = "Sounds/Musics/fantasy.mp4";

	//Sources fixe
	private Image spike = new Image(getClass().getResource("Sprites/Others/pique.png").toExternalForm());
	private Image iceBall = new Image(getClass().getResource("Sprites/Others/fireball.gif").toExternalForm());
	private Image pika = new Image(getClass().getResource("Sprites/Persos/2/pika.gif").toExternalForm());
	private Image boo = new Image(getClass().getResource("Sprites/Persos/3/boo.gif").toExternalForm());
	private Image portal = new Image(getClass().getResource("Sprites/Others/portal.gif").toExternalForm());
	
	private Image pika_bloc = new Image(getClass().getResource("Sprites/Others/blocPikachu.png").toExternalForm());
	
	public Image getPika_bloc() {
		return pika_bloc;
	}

	private Image boo_bloc = new Image(getClass().getResource("Sprites/Others/blocBoo.png").toExternalForm());
	private Image link_bloc = new Image(getClass().getResource("Sprites/Others/blocLink.png").toExternalForm());
	private Image perso_left = new Image(getClass().getResource("Sprites/Persos/0/perso_left.gif").toExternalForm());
	private Image perso_right = new Image(getClass().getResource("Sprites/Persos/0/perso_right.gif").toExternalForm());
	private Image perso_idle = new Image(getClass().getResource("Sprites/Persos/0/perso_idle.png").toExternalForm());
	private Image perso_up = new Image(getClass().getResource("Sprites/Persos/0/perso_jump.gif").toExternalForm());
	private Image perso_walk_left = new Image(getClass().getResource("Sprites/Persos/0/perso_walk_left.gif").toExternalForm());
	private Image perso_walk_right = new Image(getClass().getResource("Sprites/Persos/0/perso_walk_right.gif").toExternalForm());
	
	private Image ennemi = new Image(getClass().getResource("Sprites/Persos/0/ennemi.gif").toExternalForm());
	private Image rayman = new Image(getClass().getResource("Sprites/Persos/1/rayman.gif").toExternalForm());
	
	private Image cannon = new Image(getClass().getResource("Sprites/Others/canon.png").toExternalForm());
	
	private Image link = new Image(getClass().getResource("Sprites/Persos/4/link_walking_render.gif").toExternalForm());
	
	private Image rocket = new Image(getClass().getResource("Sprites/Others/rocket.png").toExternalForm());
	private Image barrel = new Image(getClass().getResource("Sprites/Others/tonneau.png").toExternalForm());
	private Image buttonTrigger = new Image(getClass().getResource("Sprites/Others/buttonOff.png").toExternalForm());
	private Image buttonTrigger2 = new Image(getClass().getResource("Sprites/Others/buttonOn.png").toExternalForm());
	
	private Image rock = new Image(getClass().getResource("Sprites/Others/Rock.jpg").toExternalForm());
	
	public Image cloud = new Image(getClass().getResource("Sprites/Others/cloud.png").toExternalForm());
	
	public Image aUp = new Image(getClass().getResource("Sprites/Others/Arrow/AUP.png").toExternalForm());
	public Image aDown = new Image(getClass().getResource("Sprites/Others/Arrow/ADown.png").toExternalForm());
	public Image aLeft = new Image(getClass().getResource("Sprites/Others/Arrow/ALeft.png").toExternalForm());
	public Image aRight = new Image(getClass().getResource("Sprites/Others/Arrow/ARight.png").toExternalForm());
	
	public Image happyMob = new Image(getClass().getResource("Sprites/Mobs/HappyGR.gif").toExternalForm());
	
	public Image grass1 = new Image(getClass().getResource("Sprites/Others/Bloc01/grass1.png").toExternalForm());
	public Image grass2 = new Image(getClass().getResource("Sprites/Others/Bloc01/grass2.png").toExternalForm());
	public Image grass3 = new Image(getClass().getResource("Sprites/Others/Bloc01/grass3.png").toExternalForm());
	public Image ground1 = new Image(getClass().getResource("Sprites/Others/Bloc01/ground1.png").toExternalForm());
	public Image ground2 = new Image(getClass().getResource("Sprites/Others/Bloc01/ground2.png").toExternalForm());
	public Image ground3 = new Image(getClass().getResource("Sprites/Others/Bloc01/ground3.png").toExternalForm());
	public Image groundL = new Image(getClass().getResource("Sprites/Others/Bloc01/groundL.png").toExternalForm());
	public Image groundR = new Image(getClass().getResource("Sprites/Others/Bloc01/groundR.png").toExternalForm());
	public Image groundC = new Image(getClass().getResource("Sprites/Others/Bloc01/groundC.png").toExternalForm());
	public Image sand = new Image(getClass().getResource("Sprites/Others/blocSand.jpg").toExternalForm());
	public Image ice1 = new Image(getClass().getResource("Sprites/Others/Bloc02/1.png").toExternalForm());
	public Image ice2 = new Image(getClass().getResource("Sprites/Others/Bloc02/2.png").toExternalForm());
	public Image ice3 = new Image(getClass().getResource("Sprites/Others/Bloc02/3.png").toExternalForm());
	public Image skyBloc = new Image(getClass().getResource("Sprites/Others/skyBloc.png").toExternalForm());
	public Image skyBloc2 = new Image(getClass().getResource("Sprites/Others/skyBloc2.png").toExternalForm());
	public Image brBlock = new Image(getClass().getResource("Sprites/Others/BrBlock.png").toExternalForm());
	public Image bob_ombL = new Image(getClass().getResource("Sprites/Mobs/bob_ombL.gif").toExternalForm());
	public Image bob_ombR = new Image(getClass().getResource("Sprites/Mobs/bob_ombR.gif").toExternalForm());
	public Image OrbMob = new Image(getClass().getResource("Sprites/Others/OrbMob.gif").toExternalForm());
	public Image snorlax_sleeping = new Image(getClass().getResource("Sprites/Mobs/snorlax/snorlax_sleeping.gif").toExternalForm());
	public Image snorlax_hitL = new Image(getClass().getResource("Sprites/Mobs/snorlax/hitleft.gif").toExternalForm());
	public Image snorlax_hitR = new Image(getClass().getResource("Sprites/Mobs/snorlax/hitright.gif").toExternalForm());
	public Image snorlax_jump = new Image(getClass().getResource("Sprites/Mobs/snorlax/jump.gif").toExternalForm());
	public Image snorlax_right = new Image(getClass().getResource("Sprites/Mobs/snorlax/right.gif").toExternalForm());
	public Image snorlax_left = new Image(getClass().getResource("Sprites/Mobs/snorlax/left.gif").toExternalForm());
	public Image booLeft = new Image(getClass().getResource("Sprites/Mobs/boo/booLeft.gif").toExternalForm());
	public Image booRight = new Image(getClass().getResource("Sprites/Mobs/boo/booRight.gif").toExternalForm());
	public Image booHidden = new Image(getClass().getResource("Sprites/Mobs/boo/booHidden.png").toExternalForm());
	public Image SlimeRight = new Image(getClass().getResource("Sprites/Mobs/SlimeRight.gif").toExternalForm());
	public Image SlimeLeft = new Image(getClass().getResource("Sprites/Mobs/SlimeLeft.gif").toExternalForm());
	public Image RedSlime = new Image(getClass().getResource("Sprites/Mobs/RedSlime.gif").toExternalForm());
	
	public Image heart = new Image(getClass().getResource("Items/1/heart.gif").toExternalForm());
	public Image firePower = new Image(getClass().getResource("Items/2/fire.gif").toExternalForm());
	public Image coin = new Image(getClass().getResource("Items/3/Coin.png").toExternalForm());
	
	public Image getRock() {
		return rock;
	}

	public String Songfantasy = "Sounds/Musics/fantasy.mp4";
	public String Songvolcano = "Sounds/Musics/lava_Temple.mp3";
	public String Songsky = "Sounds/Musics/sky.mp3";
	public String Songenchanted = "Sounds/Musics/enchanted.mp3";
	public String Songjungle = "Sounds/Musics/jungle.mp3";
	public String Songsnow = "Sounds/Musics/SnowyForest.mp3";
	
	public String Songadventure = "Sounds/Musics/adventure.mp3";
	public String Songemo = "Sounds/Musics/menuEmo.mp3";
	
	private String loose = "Sounds/SoundEffects/YouDie.mp3";
	private String win = "Sounds/SoundEffects/victory.mp3";
	public String explosionSound = "Sounds/SoundEffects/explosion.mp3";
	
	
	//private File level = new File(getClass().getResource("EditedLevel/LEVELEDIT_2.txt").getFile());
	private File level = new File(getClass().getResource("Levels/Snow/LEVEL3.txt").getFile());
	
	public void setLevel(String path) {this.level=new File(path);}
	
	public void setParam(int i){
		String r = "General/Sprites/Persos/"+Integer.toString(i);
		perso_left = new Image(r+"/perso_left.gif");
		perso_right = new Image(r+"/perso_right.gif");
		perso_idle = new Image(r+"/perso_idle.png");
		perso_up = new Image(r+"/perso_jump.gif");
		perso_walk_left = new Image(r+"/perso_walk_left.gif");
		perso_walk_right = new Image(r+"/perso_walk_right.gif");
		BACKGROUND_IMG = new Image(r+"/background.jpg");
		ennemi = new Image(r+"/ennemi.gif");
		BLOC_IMG= new Image(r+"/bloc.jpg");
	}
	
	public void setBackground(String path) {
		this.BACKGROUND_IMG=new Image(getClass().getResource(path).toExternalForm());
	}

	/*public void nextLevel(int i){
		this.level = new File(getClass().getResource("General/Levels/LEVEL"+Integer.toString(i)+".txt").getFile());
	}*/
	public Image getBLOC_IMG() {
		return BLOC_IMG;
	}

	public Image getBACKGROUND_IMG() {
		return BACKGROUND_IMG;
	}

	public Image getSpike() {
		return spike;
	}

	public Image getIceBall() {
		return iceBall;
	}

	public Image getPika() {
		return pika;
	}

	public Image getBoo() {
		return boo;
	}

	public Image getPortal() {
		return portal;
	}

	public Image getBoo_bloc() {
		return boo_bloc;
	}

	public Image getLink_bloc() {
		return link_bloc;
	}

	public Image getPerso_left() {
		return perso_left;
	}

	public Image getPerso_right() {
		return perso_right;
	}

	public Image getPerso_idle() {
		return perso_idle;
	}

	public Image getPerso_up() {
		return perso_up;
	}

	public Image getPerso_walk_left() {
		return perso_walk_left;
	}

	public Image getPerso_walk_right() {
		return perso_walk_right;
	}

	public Image getEnnemi() {
		return ennemi;
	}

	public Image getRayman() {
		return rayman;
	}

	public Image getCannon() {
		return cannon;
	}

	public Image getLink() {
		return link;
	}

	public Image getRocket() {
		return rocket;
	}

	public Image getBarrel() {
		return barrel;
	}

	public Image getButtonTrigger() {
		return buttonTrigger;
	}

	public Image getButtonTrigger2() {
		return buttonTrigger2;
	}

	public String getIdleSong() {
		return idleSong;
	}

	public String getLoose() {
		return loose;
	}

	public String getWin() {
		return win;
	}

	public File getLevel() {
		return level;
	}
	
	public String getRessource(String s) {
		return getClass().getResource(s).toExternalForm();
	}
	public String getFileRessource(String s) {
		return getClass().getResource(s).getFile();
	}
}
