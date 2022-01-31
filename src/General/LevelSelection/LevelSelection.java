package General.LevelSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import General.*;
import General.LevelEditCode.ItemTile;
import General.UtilCode.Vector;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelSelection {
	public static final int MAXLEVEL=5;
	public ArrayList<Integer> clearedList=new ArrayList<Integer>();
	public VBox linePane = new VBox();
	public boolean clicked=false;
	Media song = new Media(Controller.param.getRessource(Controller.param.Songadventure));
	MediaPlayer songPlay = new MediaPlayer(song);
	
	public LevelSelection(){
		File file = new File(getClass().getResource("save.txt").getFile());
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	System.out.println(line);
	        	clearedList.add(Integer.parseInt(line));
			}
	    } catch (IOException e) {e.printStackTrace();}
	}
	public Scene getScene() {
		songPlay.setOnEndOfMedia(new Runnable() {
    		public void run() {
    			songPlay.seek(Duration.ZERO);
    		}
    	});
		songPlay.play();
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: grey;");
		addTile();
		root.setCenter(linePane);
		Scene scene = new Scene(root);
    	return scene;
    }
	public void addTile() {
		for(int i=0; i<clearedList.size();i++) {
			switch(i) {
			case 0 : setComponent(i,new Image(Controller.param.getRessource("Sprites/Backgrounds/volcano.jpg"))); break;
			case 1 : setComponent(i,new Image(Controller.param.getRessource("Sprites/Backgrounds/snowMap.jpg"))); break;
			case 2 : setComponent(i,new Image(Controller.param.getRessource("Sprites/Backgrounds/Sky.gif"))); break;
			case 3 : setComponent(i,new Image(Controller.param.getRessource("Sprites/Backgrounds/desert.jpg"))); break;
			case 4 : setComponent(i,new Image(Controller.param.getRessource("Sprites/Backgrounds/jungle.jpg"))); break;
			case 5 : setComponent(i,new Image(Controller.param.getRessource("Sprites/Backgrounds/levelDesign.jpg"))); break;
			}
		}
	}
	public void setComponent(int num,Image background) {
		HBox line = new HBox(); line.setSpacing(80); line.setPadding(new Insets(10,10,10,180));
		Pane component = new Pane();
		Rectangle backgrnd = new Rectangle(Controller.Width.get(),Controller.Height.get()/5);
		backgrnd.setFill(new ImagePattern(background));
		for(int i=0;i<MAXLEVEL;i++) {
			LevelTile tile;
			if(i<=clearedList.get(num)) {tile = new LevelTile(this, LevelTile.state.UNLOCKED,getLevelRessource(num, i),75, 75, num, i+1);}
			else {tile = new LevelTile(this, LevelTile.state.LOCKED,getLevelRessource(num, i), 75, 75, num, i+1);}
			line.getChildren().add(tile);
		}
		component.getChildren().addAll(backgrnd, line);
		linePane.getChildren().add(component);
	}
	public String getLevelRessource(int i, int j) {
		switch(i) {
		case 0 : return "Levels/Volcano/LEVEL"+(j+1)+".txt";
		case 1 : return "Levels/Snow/LEVEL"+(j+1)+".txt";
		case 2 : return "Levels/Sky/LEVEL"+(j+1)+".txt";
		case 3 : return "Levels/Desert/LEVEL"+(j+1)+".txt";
		case 4 : return "Levels/Jungle/LEVEL"+(j+1)+".txt";
		case 5 : return "Levels/LevelEdit/LEVEL"+(j+1)+".txt";
		}
		return "";
	}
}
