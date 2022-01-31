package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SpEffect extends Rectangle{
	enum typeEffect{EXPLOSION, JUMP, DAMAGE, BRIGHT, BLOCEXPLOSION, COUNTDOWN, POKEBALL}
	public typeEffect type;
	public Vector pos;
	public Media media ;
	public static SpEffect get;
	SpEffect(typeEffect type, Vector pos){
		this.type=type;
		this.pos=pos;
		this.setTranslateX(pos.x);
		this.setTranslateY(pos.y);
		CreateView();
		Jeu.arrayAdder.add(this);
	}
	SpEffect(typeEffect type, Vector pos, double width, double height){
		this.type=type;
		this.pos=pos;
		this.setTranslateX(pos.x);
		this.setTranslateY(pos.y);
		this.setWidth(width);
		this.setHeight(height);
		CreateView();
		Jeu.arrayAdder.add(this);
	}
	SpEffect(String path, double volume){
		applyClip(path, volume);
	}
	public void CreateView() {
		switch(type) {
		case EXPLOSION : explosion(); break;
		case JUMP : jump(); break;
		case DAMAGE : damage(); break;
		case BRIGHT : bright(); break;
		case BLOCEXPLOSION : blocExplosion(); break;
		case COUNTDOWN : countDown(); break;
		case POKEBALL : pokeball(); break;
		}
	}
	public void pokeball() {
		this.setTranslateY(this.pos.y+60);
		this.setWidth(30);
		this.setHeight(30);
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/pokeball.gif"))));
		applyClip("Sounds/SoundEffects/pokeball.mp3",1);
		setTimer(2000,1);
	}
	public void countDown() {
		this.setTranslateY(this.pos.y-60);
		this.setWidth(30);
		this.setHeight(30);
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/countDown.gif"))));
		setTimer(1700,1);
	}
	public void bright() {
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/brightness.gif"))));
		applyClip("Sounds/SoundEffects/magic.mp3",0.7);
		setTimer(1000,1);
	}
	public void damage() {
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/damageEffect2.png"))));
		setTimer(300,1);
	}
	public void blocExplosion() {
		this.setTranslateX(this.pos.x-30);
		this.setWidth(100);
		this.setHeight(100);
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/Blocexplosion.gif"))));
		applyClip(Controller.param.explosionSound,1);
		setTimer(1400,1);
	}
	public void explosion() {
		this.setWidth(100);
		this.setHeight(100);
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/explosion.gif"))));
		applyClip(Controller.param.explosionSound,1);
		setTimer(800,1);
	}
	public void jump() {
		this.setFill(new ImagePattern(new Image(Controller.param.getRessource("Sprites/Others/SpEffect/jumpEffect1.gif"))));
    	applyClip("Sounds/SoundEffects/jump.mp3",0.2);
		setTimer(800,1);
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	Jeu.paneEraser.add(this); break;
		}
	}
	
	public void setMedia(String path, Duration time) {
		media= new Media(getClass().getResource(path).toExternalForm());
		MediaPlayer x = new MediaPlayer(media);
    	x.play();
    	x.setStopTime(time);
	}
	public void applyClip(String path, double volume) {
		AudioClip sound = new AudioClip(Controller.param.getRessource(path));
		sound.setVolume(volume);
		sound.play();
	}
}
