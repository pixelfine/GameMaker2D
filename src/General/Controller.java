package General;
import General.LevelEditCode.*;
import General.LevelSelection.LevelSelection;
import General.LevelSelection.SaveSelectionFile;
import General.JeuCode.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    public static Player ancester = null;
    private static Jeu j;
    public static MediaPlayer m;
    public static Stage sG;
    private final static double L = 1200;
    private final static double l = 620;
    private final static int ESP = 45;
    private static int nbNiv = 1;
    public static Parametre param = new Parametre();
    public static SaveSelectionFile save = new SaveSelectionFile();
    public static ReadOnlyDoubleProperty Width = Prelauncher.stage.widthProperty();
    public static ReadOnlyDoubleProperty Height = Prelauncher.stage.heightProperty();
    public static Media song = new Media(Controller.param.getRessource(Controller.param.Songemo));
	public static MediaPlayer songPlay = new MediaPlayer(song);

    public Controller(Stage stage){
        sG = stage;
        j = new Jeu();
    }

    public static void lanceJeu() {
    	songPlay.stop();
        sG.setScene(new Jeu(param.getLevel()).getScene());
    }
    public static void lanceJeu(File f) {
    	songPlay.stop();
        sG.setScene(new Jeu(f).getScene());
    }

    public static Scene lanceOption(int i){
        Pane rootGO = creationPane(1,0);
        Text x1 = new Text("Luigi."); Text x2 = new Text("Rayman."); Text x3 = new Text("Pikachu.");
        Text x4 = new Text("Boo."); Text x5 = new Text("Link.");
        x1.setX(320); x2.setX(320); x3.setX(320); x4.setX(320); x5.setX(320);
        x1.setY(260); x2.setY(260+ESP-10); x3.setY(260+ESP*2-20); x4.setY(260+ESP*3-30); x5.setY(260+ESP*4-40);
        x1.setFill(Color.WHITE); x2.setFill(Color.WHITE); x3.setFill(Color.WHITE); x4.setFill(Color.WHITE); x5.setFill(Color.WHITE);
        x1.setFont(Font.font("Verdana", 30)); x2.setFont(Font.font("Verdana", 30)); x3.setFont(Font.font("Verdana", 30)); x4.setFont(Font.font("Verdana", 30)); 
        x5.setFont(Font.font("Verdana", 30));
        ImageView select = new ImageView(new Image(Controller.param.getRessource("Menu/arrow.gif"),30,30,false,false));
        select.setX(280);
        select.setY(260+(35*i)-25);
        rootGO.getChildren().addAll(x1,x2,x3,x4,x5,select);
        Scene sc = new Scene(rootGO, L, l);
        sc.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @SuppressWarnings("unused")
            @Override
            public void handle(KeyEvent event){
                switch(event.getCode()){
                    case DOWN:
                        if (i == 4){ Scene sc = lanceOption(i);break;}
                        Scene sc = lanceOption(i+1); break;
                    case UP:
                        if(i == 0){ sc = lanceOption(i); break;}
                        sc = lanceOption(i-1); break;
                    case ENTER: 
                        param.setParam(i);
                        sc = Menu(0); break;
                    default: break;
                }
            }
        });
        sG.setScene(sc);
        return sc;
    }

    public static void select(){
    	songPlay.stop();
    	sG.setScene(new LevelSelection().getScene());
        //System.exit(1);
    }

    public static void createLevel() {
    	songPlay.stop();
        sG.setScene(new LevelEditor().getScene());
    }

    public static Scene Menu(int i){
    	songPlay.setOnEndOfMedia(new Runnable() {
    		public void run() {
    			songPlay.seek(Duration.ZERO);
    		}
    	});
    	songPlay.play();
        Pane rootGO = creationPane(0,i);
        ImageView select = new ImageView(new Image(Controller.param.getRessource("Menu/arrow.gif"),35,35,false,false));
        select.setX(L/2-90);
        select.setY(l/2+(ESP*i)-55);
        rootGO.getChildren().addAll(select);
        Scene sc = new Scene(rootGO, L, l);
        sc.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @SuppressWarnings("unused")
            @Override
            public void handle(KeyEvent event){
                switch(event.getCode()){
                    case DOWN:
                        if (i == 3){ Scene sc = Menu(i);break;}
                        Scene sc = Menu(i+1); break;
                    case UP:
                        if(i == 0){ sc = Menu(i); break;}
                        sc = Menu(i-1); break;
                    case ENTER: 
                        if (i == 0){ select(); break;}
                        if (i == 1){ lanceOption(0); break;}
                        if (i == 2){ System.exit(1); break;}
                        if (i == 3){ createLevel(); break;}
                    default: break;
                }
            }
        });
        sG.setScene(sc);
        return sc;
    }

    // si i = 1 -> message gagnant si i = 0 -> message perdant
    public static void Message (String path, int i, int po) {
    	int sac = 3; int t = 0;
    	if (i==0){sac = 2;}
    	if (po == 0){t = 1;}
    	Pane rootGO = creationPane(sac,t);
        ImageView select = new ImageView(new Image(Controller.param.getRessource("Menu/arrow.gif"),30,30,false,false));
        select.setX(L/2-180+ESP*2);
        select.setY(l/2-30-(40*(po-1)));
        Media song = new Media(path);
        m = new MediaPlayer(song);
        rootGO.getChildren().addAll(select);
        rootGO.setPrefSize(L, l);
        Scene sc = new Scene(rootGO, L, l);
        sc.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @SuppressWarnings("unused")
            @Override
            public void handle(KeyEvent event){
                switch(event.getCode()){
                    case UP: if (po == 0){Message(path, i, 1); break;}else{Message(path, i, po); break;}
                    case DOWN: if (po == 1){Message(path, i, 0); break;}else{Message(path, i, po); break;}
                    case ENTER:
                        if(LevelEditor.currentFile==null) {
                        	if(i==1) Controller.save.onNextLevelWrite();
                            if(po == 0){
                                m.stop();
                                //param.nextLevel(nbNiv);
                                Scene sc = Menu(0); 
                                break;}
                            if(po == 1){
                                if(i == 0){
                                	j.fin();
                                    m.stop();
                                    //select();
                                    save.setTheme();
                                    lanceJeu(); 
                                    break;
                                }else{
                                    if(save.level<5){
                                        nbNiv++;
                                        j.fin();
                                        m.stop();
                                        //param.nextLevel(nbNiv);
                                        save.level++;
                                        save.setTheme();
                                        //select();
                                        lanceJeu();
                                        break;
                                    }else{
                                    	Controller.save.level=1;
                                        Scene sc = Menu(0); break;//CREDIT
                                    }
                                }
                            }
                        }else {m.stop(); createLevel(); }break;
                    default: break;
                }
            }
        });
        sG.setScene(sc);
    }

    // i == 1 ca cree le pane pour option et i == 0 le pane pour menu
    public static Pane creationPane(int i, int j){
        if(i == 0){
            Pane rootGO = new Pane();
            rootGO.setPrefSize(L, l);
            ImageView bc = new ImageView(new Image(Controller.param.getRessource("Menu/Menus"+Integer.toString(j)+".jpg"),L,l,false,false));
            rootGO.getChildren().add(bc);
            return rootGO;
        }
        if(i == 1) {
            Pane rootGO = new Pane();
            rootGO.setPrefSize(L, l);
            ImageView bc = new ImageView(new Image (Controller.param.getRessource("Menu/Option.jpg"),L,l,false,false));
            rootGO.getChildren().add(bc);
            return rootGO;
        }
        if(i == 2){
        	Pane rootGO = new Pane();
        	rootGO.setPrefSize(L, l);
            ImageView bc = new ImageView(new Image (Controller.param.getRessource("Menu/YouDied"+Integer.toString(j)+".jpg"),L,l,false,false));
            rootGO.getChildren().add(bc);
            return rootGO;
        }
        if(i == 3){
        	Pane rootGO = new Pane();
        	rootGO.setPrefSize(L, l);
            ImageView bc = new ImageView(new Image (Controller.param.getRessource("Menu/YouWin"+Integer.toString(j)+".jpg"),L,l,false,false));
            rootGO.getChildren().add(bc);
            return rootGO;
        }
        return null;
    }
}
