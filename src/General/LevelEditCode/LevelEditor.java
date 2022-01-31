package General.LevelEditCode;
import General.JeuCode.*;
import General.*;
import General.UtilCode.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class LevelEditor {
	public static final int size = 45;
	public static int currentMaxRows=30;
	public static int currentMaxCol=70;
	public static File currentFile;
	public String backgroundURL="Sprites/Backgrounds/levelDesign.jpg";
    public ImageView background;
	public ItemTile currentTile=new ItemTile(this, ItemTile.typeTile.RIGHT, new Vector(size,7*size), false);
	public TextField portaltxt = new TextField();
    public TextField buttontxt = new TextField();
    public ArrayList<ArrayList<String>> codeLevel = new ArrayList<ArrayList<String>>();
	public Label currentCursor= new Label("Cursor on:");
	
	private BorderPane root = new BorderPane();
	
	public Pane superComponent = new Pane();
	public Pane component = new Pane();
	public Pane selection = new Pane();
	public Pane info = new Pane();
	public boolean quitPressed=false;
	public boolean loadPressed=false;
	public boolean playPressed=false;
	Media song = new Media(Controller.param.getRessource(Controller.param.Songadventure));
	MediaPlayer songPlay = new MediaPlayer(song);
	
    private HashMap<Input,Boolean> inputMap = new HashMap<>();
    private enum Input{
    	UP, DOWN, LEFT, RIGHT
    }
    public LevelEditor(){}
	/**
	 * @return
	 * Retourne la scene du LevelEditor
	 */
	public Scene getScene() {
		songPlay.setOnEndOfMedia(new Runnable() {
    		public void run() {
    			songPlay.seek(Duration.ZERO);
    		}
    	});
		songPlay.play();
		init_input();
    	centerPane();
    	topPane();
    	rightPane();
		root.setPrefSize(27*size,15*size);
		root.setCenter(superComponent);
		root.setBottom(selection);
		root.setRight(info);
		Scene scene = new Scene(root);
		pressed(scene);
		released(scene);
		if(currentFile!=null) load(false);
		scene.setOnMouseClicked(e-> {
			if(quitPressed) {
				fin();
				currentFile=null;
				Controller.Menu(0);
			}
			if(loadPressed) {
				load(true);
			}
			if(playPressed) {
				play();
			}
				
		});
		AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(inputMap.get(Input.UP)) {
                	component.setTranslateY(component.getTranslateY()+size);
                }
                if(inputMap.get(Input.DOWN)) {
                	component.setTranslateY(component.getTranslateY()-size);
                }
                if(inputMap.get(Input.LEFT)) {
                	component.setTranslateX(component.getTranslateX()+size);
                }
                if(inputMap.get(Input.RIGHT)) {
                	component.setTranslateX(component.getTranslateX()-size);
                }
                if(Prelauncher.changed) {
                	Prelauncher.changed=false;
                	background.setFitWidth(Controller.Width.get());
            		background.setFitHeight(Controller.Height.get());
                }
                
            }
        };                 
        timer.start();
        return scene;
	}
	private void rightPane() {
		Rectangle background = new Rectangle(0,0,3*size,13*size);
		background.setFill(Color.SLATEGRAY);
		
		buttonEditor save = new buttonEditor(this, buttonEditor.typeButton.SAVE,new Vector(1.5*size,0.5*size),new Label("Save"));
		buttonEditor load = new buttonEditor(this, buttonEditor.typeButton.LOAD,new Vector(1.5*size, 1.7*size), new Label("Load"));
		buttonEditor quit = new buttonEditor(this, buttonEditor.typeButton.QUIT,new Vector(1.5*size, 2.9*size), new Label("Quit"));
		buttonEditor play = new buttonEditor(this, buttonEditor.typeButton.PLAY,new Vector(1.5*size, 4.1*size), new Label("Play"));
		buttonEditor backgroundC = new buttonEditor(this ,buttonEditor.typeButton.BACKGROUND,new Vector(1.5*size, 5.3*size), new Label("Screen"));
		
		Label current= new Label("Votre selection :");
		current.setTranslateY(6.5*size);
		ItemTile portalTile=new ItemTile(this, ItemTile.typeTile.RIGHT, new Vector(0.5*size,8.2*size), "(0)");
		portaltxt.setMaxWidth(45); portaltxt.setTranslateX(0.5*size); portaltxt.setTranslateY(9.2*size); portaltxt.setFocusTraversable(false);
		ItemTile buttonTile=new ItemTile(this, ItemTile.typeTile.RIGHT, new Vector(1.7*size,8.2*size), "%0%");
		buttontxt.setMaxWidth(45); buttontxt.setTranslateX(1.7*size); buttontxt.setTranslateY(9.2*size); buttontxt.setFocusTraversable(false);
		buttonEditor addTile1 = new buttonEditor(this, buttonEditor.typeButton.ADDDOWN,new Vector(9*size, 2.9*size), new Label(""));
		buttonEditor addTile2 = new buttonEditor(this, buttonEditor.typeButton.ADDRIGHT,new Vector(9*size, 2.9*size), new Label(""));
		currentCursor.setTranslateY(12*size);
		info.getChildren().addAll(
				background,
				new Label("LEVEL EDIT "),
				current, 
				currentTile, 
				portalTile,
				portaltxt,
				buttonTile,
				buttontxt,
				save.rect, 
				save.txt,
				load.rect,
				load.txt,
				quit.rect,
				quit.txt,
				currentCursor, 
				addTile1.pan,
				addTile2.pan,
				play.rect,
				play.txt,
				backgroundC.rect,
				backgroundC.txt
		);
	}
	private void topPane() {
		addEmptyTile(ItemTile.typeTile.TOP,2,27, selection, false,0,0);
    	initSelectionTile();
	}
	private void centerPane() {
		background = new ImageView(new Image(Controller.param.getRessource(backgroundURL)));
		background.setFitWidth(Controller.Width.get());
		background.setFitHeight(Controller.Height.get());
		//component.getChildren().add(0, background);
		if(currentFile==null) addEmptyTile(ItemTile.typeTile.CENTER,30,70, component, true,0,0);
		superComponent.getChildren().addAll(background, component);
	}
	
	/**
	 * @param scene
	 * Gestion d'un Input pressed
	 * Permet le de mettre à true les Imput dans inputMap
	 */
	public void pressed(Scene scene) {
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
			default:
				break;
			}
		});
	}
	/**
	 * @param scene
	 * Gestion d'un Input released
	 * Permet de mettre à false l'input dans inputMap
	 */
	public void released(Scene scene) {
		scene.setOnKeyReleased(e-> {
			switch(e.getCode()) {
			case LEFT : 
				inputMap.put(Input.LEFT,false);
				break;
			case RIGHT :
				inputMap.put(Input.RIGHT,false);
				break;
			case UP :
				inputMap.put(Input.UP,false);
				break;
			case DOWN :
				inputMap.put(Input.DOWN,false);
				break;
			default:
				break;
			}
		});
	}
	
	/**
	 * @param type
	 * @param rows
	 * @param col
	 * @param pane
	 * @param transparency
	 * @param startRow
	 * @param startCol
	 * Ajoute des Tile dont le code est vide
	 */
	public void addEmptyTile(ItemTile.typeTile type, int rows, int col, Pane pane, boolean transparency, int startRow,int startCol) {
		for(int i=startRow;i<rows;i++) {
			for(int j=startCol;j<col;j++) {
				pane.getChildren().add(new ItemTile(this, type,new Vector(j*size,i*size), transparency));
			}
		}
	}
	
	/**
	 * Initialise la liste de selection de Tile
	 */
	public void initSelectionTile() {
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(0,0), "1"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(size,0), "2"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(2*size,0), "3"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(3*size,0), "4"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(4*size,0), "7"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(5*size,0), "P"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(6*size,0), "C"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(7*size,0), "b"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(8*size,0), "R"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(9*size,0), "-"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(10*size,0), "+"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(11*size,0), "*"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(12*size,0), "(0)"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(13*size,0), "%0%"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(14*size,0), "<"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(15*size,0), ">"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(16*size,0), "^"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(17*size,0), "v"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(18*size,0), "~"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(19*size,0), "h"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(20*size,0), "o"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(21*size,0), "g"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(22*size,0), "s"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(23*size,0), "B"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(24*size,0), "D"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(25*size,0), "d"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(0,size), "&"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(size,size), "a"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(2*size,size), "z"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(3*size,size), "$"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(4*size,size), "q"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(5*size,size), "w"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(6*size,size), ","));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(7*size,size), ";"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(8*size,size), ":"));	
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(9*size,size), "#"));	
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(10*size,size), "8"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(11*size,size), "i"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(12*size,size), "j"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(13*size,size), "k"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(14*size,size), "9"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(15*size,size), "m"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(16*size,size), "X"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(17*size,size), "Y"));
		this.selection.getChildren().add(new ItemTile(this, ItemTile.typeTile.TOP,new Vector(18*size,size), "Z"));
	}
	
	/**
	 * Initialise les Input en les mettant à false dans InputMap
	 */
	public void init_input() {
    	inputMap.put(Input.UP, false);
    	inputMap.put(Input.DOWN, false);
    	inputMap.put(Input.LEFT, false);
    	inputMap.put(Input.RIGHT, false);
    }
	/**
	 * Gestion de sortie de LevelEdit.
	 */
	public void fin() {
		songPlay.stop();
		root = new BorderPane();
		superComponent = new Pane();
		component = new Pane();
		selection = new Pane();
		info = new Pane();
		quitPressed=false;
		currentMaxRows=30;
		currentMaxCol=70;
	}
	
	/**
	 * Applique la sortie du LevelEdit
	 */
	public void play() {
		this.fin();
	}
	
	/**
	 * @param loadFile
	 * Charge un LevelEdit, Si loadFile est à true, elle va charger le currentFile du LevelEdit, sinon elle va Charger le levelEdit selectionné
	 */
	public void load(boolean loadFile) {
		File selectedFile=null;
		try {
			if(loadFile) {
				FileChooser f = new FileChooser();
				f.setTitle("Open Resource File");
				f.setInitialDirectory(new File("src/General/EditedLevel/"));
				selectedFile = f.showOpenDialog(null);
				if(f!=null) {
					currentFile=selectedFile;
					currentMaxCol=0;
					currentMaxRows=0;
				}
			}else {selectedFile=currentFile;currentMaxCol=0;currentMaxRows=0;};
			component.getChildren().remove(0, component.getChildren().size());
			int count=0;
			try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
		        String line; String tmpCode=""; String tmp2="";
		        if((line = reader.readLine()) != null){
		        	backgroundURL=line; background=new ImageView(new Image(Controller.param.getRessource(backgroundURL))); refreshBackground();
		        	while ((line = reader.readLine()) != null) {currentMaxRows++;
		        		int offsetX=0;
				    	for(int i=0;i<line.length();i++) {currentMaxCol++;
				    		if(line.charAt(i)==' ')  component.getChildren().add(new ItemTile(this, ItemTile.typeTile.CENTER, new Vector((i-offsetX)*size, count*size), true));
				    		else if(line.charAt(i)=='(') {
				    			while(line.charAt(i)!=')') {
				    				tmpCode+=line.charAt(i); i++; offsetX++;
				    			}tmpCode+=line.charAt(i);
				    			component.getChildren().add(new ItemTile(this, ItemTile.typeTile.CENTER, new Vector((i-offsetX)*size, count*size), tmpCode));
				    			tmpCode="";
				    		}else if(line.charAt(i)=='%'){
				    			tmpCode+=line.charAt(i);i++; offsetX++; 
				    			while(line.charAt(i)!='%') {
				    				tmpCode+=line.charAt(i); i++; offsetX++;
				    			}tmpCode+=line.charAt(i);
				    			component.getChildren().add(new ItemTile(this, ItemTile.typeTile.CENTER, new Vector((i-offsetX)*size, count*size), tmpCode));
				    			tmpCode="";
				    		}else if(line.charAt(i)=='[') {
				    			tmpCode+=line.charAt(i);i++; offsetX++; 
				    			while(line.charAt(i)!='|') {
				    				tmpCode+=line.charAt(i); i++; offsetX++;
				    			}tmpCode+=line.charAt(i);i++; offsetX++;
				    			while(line.charAt(i)!=']') {
				    				tmpCode+=line.charAt(i); tmp2+=line.charAt(i); i++; offsetX++;
				    			}tmpCode+=line.charAt(i); 
				    			ItemTile tile = new ItemTile(this, ItemTile.typeTile.CENTER, new Vector((i-offsetX)*size, count*size), tmp2);
				    			tile.code=tmpCode; component.getChildren().add(tile);
				    			tmpCode=""; tmp2="";
				    		}else{
				    			tmpCode+=line.charAt(i);
				    			component.getChildren().add(new ItemTile(this, ItemTile.typeTile.CENTER, new Vector((i-offsetX)*size, count*size), tmpCode));
				    			tmpCode="";
				    		}
				    	}
				    	count++;
				    }currentMaxCol/=currentMaxRows;
		        }
		    } catch (IOException e) {e.printStackTrace();}
		}catch(NullPointerException e) {}
		finally {loadPressed=false;}
	}
	
	/**
	 * Permet d'appliquer le background au LevelEdit
	 */
	public void refreshBackground() {
		background.setFitWidth(Controller.Width.get());
		background.setFitHeight(Controller.Height.get());
		superComponent.getChildren().set(0, this.background);
	}
}
