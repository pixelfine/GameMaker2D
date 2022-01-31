package General.LevelSelection;
import General.UtilCode.*;

import java.io.File;

import General.*;
import General.JeuCode.Jeu;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelTile extends Rectangle{
	public enum state{UNLOCKED, LOCKED}
	public final int theme;
	public String level;
	public state itemState;
	public boolean playable = false;
	public double width; double height;
	public LevelSelection selec;
	public int numLvl;
	LevelTile(LevelSelection selec, state state, String level, double width, double height, int theme, int numLvl){
		this.numLvl=numLvl;
		this.theme=theme;
		this.selec=selec;
		this.itemState=state;
		this.level=level;
		this.width=width;
		this.height=height;
		CreateView();
		this.setOnMouseClicked(this::handleMouseClick);
	}
	public void CreateView() {
		super.setWidth(width); super.setHeight(height);
		this.setStroke(Color.BLACK);
		switch(itemState) {
		case UNLOCKED : super.setFill(new ImagePattern(new Image(getClass().getResource("UnlockedW.png").toExternalForm()))); playable=true; break;
		case LOCKED : super.setFill(new ImagePattern(new Image(getClass().getResource("LockedW.png").toExternalForm()))); playable = false; break;
		}
		fadeOut();
	}
	public void handleMouseClick(MouseEvent event) {
		if(playable && (!selec.clicked)) {
			selec.clicked=true;
			fadeIn(2);
		}else {
			fadeIn(1);
		}
	}
	public void fadeOut() {
		FadeTransition fadeOut = new FadeTransition(Duration.millis(500),this);
		fadeOut.setFromValue(1.0);fadeOut.setToValue(0.5);
		fadeOut.play();
	}
	public void fadeIn(int i) {
		FadeTransition fadeIn = new FadeTransition(Duration.millis(800),this);
		fadeIn.setFromValue(0);fadeIn.setToValue(1);
		fadeIn.play();
		setTimer(800,i);
	}
	public void launchLevel() {
		selec.songPlay.stop();
		SaveSelectionFile.theme=this.theme;
		SaveSelectionFile.clearedList=selec.clearedList;
		Controller.save.level=numLvl;
		Controller.sG.setScene(new Jeu(new File(Controller.param.getFileRessource(level))).getScene()); 
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n))); timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :    fadeOut(); break;
		case 2 :    launchLevel(); break;
		}
	}
	
}
