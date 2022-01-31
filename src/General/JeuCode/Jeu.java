package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
public class Jeu {
	
    public Vector FORCE_GRAVITY = new Vector(0, 9.8);
    
    public ArrayList<PhysicalObject> playerArray = new ArrayList<PhysicalObject>();
    public  ArrayList<PhysicalObject> physicalArray = new ArrayList<PhysicalObject>();
    public  ArrayList<fixedObject> rectArray = new ArrayList<fixedObject>();
    private ArrayList<ButtonTrigger> buttonArray = new ArrayList<ButtonTrigger>(); //Algorithme speciale
    private ArrayList<Portal> portalArray = new ArrayList<Portal>(); //Algorithme speciale
    public static ArrayList<Node> paneEraser = new ArrayList<Node>();
    public static ArrayList<Object>arrayAdder = new ArrayList<Object>();
    
    private Parametre param = Controller.param;
    
    public BorderPane superRoot = new BorderPane();
    public Pane root = new Pane();
    private Pane component = new Pane();
    public Pane superBackground = new Pane();
    public ImageView background;
	
	private enum Input{
    	UP, DOWN, LEFT, RIGHT, SPACE, RLEFT, RRIGHT, S, Z;
    }
	private HashMap<Input,Boolean> inputMap = new HashMap<>();
	
    public static boolean isGameOver = false;
    public static boolean goalReached = false;
    
    Camera camera;
    public int currentPlayerIndex=0;
    private Player Player;
    private PortalLink linker;
    private Fireball fireball;
	public File level;
	Media song;
	MediaPlayer mediaPlayer;
	
	public Jeu(File level) {
		init_map(level);
		this.level=level;
	}
	public Jeu() {}
    
    public Scene getScene() {
    	Player = (Player)playerArray.get(currentPlayerIndex);
    	Controller.ancester = Player;
    	
    	init_input();

    	linker = new PortalLink(portalArray);
        background = new ImageView(this.param.getBACKGROUND_IMG());

        //root.setPrefSize(1200,620);
        background.setFitWidth(Controller.Width.get()*2);
		background.setFitHeight(Controller.Height.get());
		
		//superRoot.getChildren().add(0, background);
		root.getChildren().add(0, background);
        root.getChildren().add(component);
        camera = new Camera(Player, root);
        superRoot.setCenter(root);
        //superRoot.setTop(Player.getHP().getStack());
        superBackground.setStyle("-fx-background-color: rgba(0, 0, 0, 1);");
        superBackground.getChildren().addAll(superRoot, Player.getHP().getStack());
        Scene scene = new Scene(superBackground);
        scene.setOnKeyPressed(e-> {
			switch(e.getCode()) {
			case LEFT : 
				inputMap.put(Input.LEFT,true);
				break;
			case RIGHT :
				inputMap.put(Input.RIGHT,true);
				break;
			case UP :
				inputMap.put(Input.UP,true);
				break;
			case DOWN :
				inputMap.put(Input.DOWN,true);
				break;
			case SPACE :
				inputMap.put(Input.SPACE, true);
				break;
			case Z :
				if(Player.isInCanon()) {
				Player.cannon.launch(Player);
				Player.setInCanon(false);
				inputMap.put(Input.Z, false);
				}
				break;
			case F : camera.camX+=20; camera.updateX(); break;
			case H : camera.camX-=20; camera.updateX(); break;
			case T : camera.camY-=20; camera.updateY(); break;
			case G : camera.camY+=20; camera.updateY(); break;
			default:
				break;
			}
		});
        scene.setOnKeyReleased(e -> {
			switch(e.getCode()) {
			case LEFT : 
				inputMap.put(Input.LEFT,false);
				break;
			case RIGHT :
				inputMap.put(Input.RIGHT,false);
				break;
			case UP :
				inputMap.put(Input.UP,false);
				Player.setLaunchCanon(false);
				Player.limit=6;
				break;
			case DOWN :
				inputMap.put(Input.DOWN,false);
				break;
			case SPACE :
				inputMap.put(Input.SPACE, false);
				break;
			case Z :
				linker.intersectPortal(Player);
				Player.setLaunchCanon(false);
				Player.limit=6;
				inputMap.put(Input.Z, false);
				break;
			default:
				break;
			}
		});
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	try {
            		physicalArray_physics();
            	}
            	catch(Exception e) {}
            	player_physics();
            	updateGenerator();
                if(inputMap.get(Input.UP)) {
                	if(Player.isInCanon()) {
                    	if(Player.cannon instanceof Rocket) ((Rocket) Player.cannon).thrust();
                    	else {
                    		Player.cannon.launch(Player);
                    		Player.setInCanon(false);
                    	}
                	}
                	else {
	                	Player.moveY(-1);
                	}
                }
                if(inputMap.get(Input.DOWN)) if(!Player.isInCanon()) Player.moveY(1);
                if(inputMap.get(Input.LEFT)) {
                	Player.setRight(false);
                	if(Player.isInCanon()) Player.cannon.rotate(-1.5);                	
                	else Player.moveX(-1);
                }  
                if(inputMap.get(Input.RIGHT)) {
                	Player.setRight(true);
                	if(Player.isInCanon()) Player.cannon.rotate(1.5);                	
                	else Player.moveX(1);
                }                
                if(inputMap.get(Input.SPACE)) {
                	if(Player.canFire()) {
                    	init_ball();
                    	Player.setTimer(500, 2);;
                	}
                }
                if(Player.interact == false) Player.limit=Player.initialLimit;
                for(int i=0;i<buttonArray.size();i++) {
                	if(buttonArray.get(i).isOn && buttonArray.get(i).activated) {
                		buttonArray.get(i).initItem();
                		buttonArray.get(i).activated=false;
                	}
                }
                arrayAdder();
                erasePane();
                if(isGameOver) {
                	this.stop();
                	mediaPlayer.stop();
                	fadeOut(1);
                }
                if(goalReached) {
                	this.stop();
                	mediaPlayer.stop();
                    fadeOut(2);
                }
                if(Prelauncher.changed) {
                	Prelauncher.changed=false;
                	background.setFitWidth(Controller.Width.get()*2);
            		background.setFitHeight(Controller.Height.get());
                }
            }
        };                 
        timer.start();
        fadeIn();
        return scene;
    }
    public void init_map(File f) {
    	Monde world = new Monde(f);
    	world.readFile();
    	Pane p = new Pane();
    	arrayAdder();
    	portalArray.sort((Portal portalA, Portal portalB)->portalA.id-portalB.id);
    	root.getChildren().add(p);
    	song = world.setSong();
    	mediaPlayer = new MediaPlayer(song);
    	mediaPlayer.setVolume(world.changeVolume);
    	mediaPlayer.setOnEndOfMedia(new Runnable() {
    		public void run() {
    			mediaPlayer.seek(Duration.ZERO);
    		}
    	});
    	//mediaPlayer.setVolume(0.7);
    	mediaPlayer.play();
    }
    public void physicalArray_physics(){
    	physicalArray.forEach(s -> {
    		s.maxVelocity();
    		if( (s instanceof MovingBloc || s instanceof Cannon || s instanceof Boo)) {}else {s.applyForce(FORCE_GRAVITY);}
    		if(!(s instanceof MovingBloc))s.applyForce(s.friction(5));
    		s.update();
    		s.display();
    		s.collision(rectArray);
    		s.collision(physicalArray);
    		s.collision(buttonArray);
    		if(s instanceof Mob) ((Mob)s).moving();
    	});
    }
    public void updateGenerator(){
    	for(int i=0;i<rectArray.size();i++) {
    		if(rectArray.get(i) instanceof MobGenerator) {
    			((MobGenerator)rectArray.get(i)).update();
    		}
    	}
    }
    public void player_physics() {
    	playerArray.forEach(s ->{
    		s.maxVelocity();
    		s.applyForce(FORCE_GRAVITY);
    		s.applyForce(s.friction(5));
    		s.update();
    		s.display();
    		s.collision(rectArray);
    		s.collision(physicalArray);
    		s.collision(buttonArray);
    	});
    }
    public void init_ball() {
    	Vector pos;
    	Vector velocity;    	
    	if(Player.isRight()) {
    		pos = new Vector (Player.pos.x + (Player.getWidth()/2), Player.pos.y);
    		velocity = new Vector (10, 0);
    	}
    	else {
    		velocity = new Vector(-10, 0);
    		pos = new Vector (Player.pos.x - (Player.getWidth()/2), Player.pos.y);
    	}    	
    	Vector acceleration = new Vector(Player.velocity.x*3, 0);    	
    	this.fireball = new Fireball(pos, velocity, acceleration, 1000);    	
    	physicalArray.add(fireball);
    	component.getChildren().add(fireball);
    }
    public void init_input() {
    	inputMap.put(Input.UP, false);
    	inputMap.put(Input.DOWN, false);
    	inputMap.put(Input.LEFT, false);
    	inputMap.put(Input.RIGHT, false);
    	inputMap.put(Input.SPACE,false);
    	inputMap.put(Input.RLEFT, false);
    	inputMap.put(Input.RRIGHT, false);
    	inputMap.put(Input.S,false);
    	inputMap.put(Input.Z, false);
    }
    public void activateExploding(Bob_Omb a) {
    	if(Player.getBoundsInParent().intersects(a.explodingBound)) Player.applyInvincibility(10);
    	for(int i=0;i<this.physicalArray.size();i++) {
    		if(this.physicalArray.get(i).getBoundsInParent().intersects(a.explodingBound) && (physicalArray.get(i) instanceof UnstableBloc)) 
    			((UnstableBloc)physicalArray.get(i)).eraseThis();
    		if(this.physicalArray.get(i).getBoundsInParent().intersects(a.explodingBound) && (physicalArray.get(i) instanceof Snorlax)) {
    			((Snorlax)physicalArray.get(i)).hitTaken(); 
    		}
    	}
    }
    public void erasePane() {
    	if(!paneEraser.isEmpty()) {
    		for(int i=0;i<paneEraser.size();i++) {
    			if(paneEraser.get(i) instanceof PhysicalObject) {
    				if(paneEraser.get(i) instanceof Bob_Omb) {activateExploding((Bob_Omb)paneEraser.get(i));}
    				this.physicalArray.remove(paneEraser.get(i));
    			}else if(paneEraser.get(i) instanceof Item) {
    				this.rectArray.remove(paneEraser.get(i));
    			}
    			component.getChildren().remove(paneEraser.get(i));
    			paneEraser.remove(i);
    		}
    	}
    }
    public void arrayAdder() {
    	if(!arrayAdder.isEmpty()) {
	    	for(int i=0; i<arrayAdder.size();i++) {
	    		if(arrayAdder.get(i) instanceof fixedObject) {
	    			rectArray.add((fixedObject) arrayAdder.get(i));
	    		}
	    		if(arrayAdder.get(i) instanceof PhysicalObject) {
	    			if(!(arrayAdder.get(i) instanceof Player))
	    			physicalArray.add((PhysicalObject) arrayAdder.get(i));
	    		}
	    		if(arrayAdder.get(i) instanceof Portal) {
	    			portalArray.add((Portal)arrayAdder.get(i));
	    			portalArray.sort((Portal portalA, Portal portalB)->portalA.id-portalB.id);
	    			linker = new PortalLink(portalArray);
	    		}
	    		if(arrayAdder.get(i) instanceof ButtonTrigger) {
	    			buttonArray.add((ButtonTrigger)arrayAdder.get(i));
	    		}
	    		if(arrayAdder.get(i) instanceof Player) {
	    			playerArray.add((Player)arrayAdder.get(i));
	    		}
	    		component.getChildren().add((Node) arrayAdder.get(i));
	    	}
	    	arrayAdder.clear();
    	}
    }
    public void fadeOut(int typeTimer) {
		FadeTransition fadeOut = new FadeTransition(Duration.millis(500),this.superRoot);
		fadeOut.setFromValue(1.0);fadeOut.setToValue(0.0);
		switch(typeTimer) {
		case 1 : superBackground.setBackground(new Background(new BackgroundImage(new Image(Controller.param.getRessource("Menu/YouDied0.jpg")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,  new BackgroundSize(superRoot.getWidth(), superRoot.getHeight(), false, false, true, true)))); break;
		case 2 : superBackground.setBackground(new Background(new BackgroundImage(new Image(Controller.param.getRessource("Menu/YouWin0.jpg")), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,  new BackgroundSize(superRoot.getWidth(), superRoot.getHeight(), false, false, true, true)))); break;
		}
		fadeOut.play();
		setTimer(500, typeTimer);
	}
	public void fadeIn() {
		FadeTransition fadeIn = new FadeTransition(Duration.millis(1000),this.superRoot);
		fadeIn.setFromValue(0.0);fadeIn.setToValue(1.0);
		fadeIn.play();
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n))); timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :    fin();
			        Controller.Message(Controller.param.getRessource(param.getLoose()),0,1);
			        Controller.m.play(); break;
		case 2 :    fin();
					Controller.Message(Controller.param.getRessource(param.getWin()),1,1);
					Controller.m.setVolume(0.4);
        			Controller.m.play(); break;
		}
	}
    public void fin(){
    	Jeu.isGameOver=false;
        Jeu.goalReached=false;
        this.inputMap = new HashMap<>();
        playerArray = new ArrayList<>();
        rectArray = new ArrayList<fixedObject>();
        paneEraser.clear();
        arrayAdder.clear();
        root = new Pane();
        component = new Pane();
        superRoot = new BorderPane();
    }
}