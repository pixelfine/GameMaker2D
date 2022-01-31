package General.LevelEditCode;
import General.*;
import General.UtilCode.*;
import General.JeuCode.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

public class buttonEditor {
	enum typeButton{SAVE, LOAD, QUIT, PLAY, BACKGROUND, ADDRIGHT, ADDDOWN}
	public Vector pos;
	public Label txt;
	public Rectangle rect;
	typeButton type;
	Parametre param = new Parametre();
	Alert alert;
	String saveMessage="";
	StackPane pan;
	LevelEditor level;
	
	/**
	 * @param level 
	 * @param type
	 * @param pos
	 * @param txt
	 * Créer un buttonEditor.
	 */
	public buttonEditor(LevelEditor level, typeButton type, Vector pos, Label txt) {
		pan=new StackPane();
		this.level=level;
		this.type=type;
		this.rect=new Rectangle(pos.x,pos.y, 1.5*LevelEditor.size,LevelEditor.size);
		this.txt=txt;
		txt.setTranslateX(pos.x-45);
		txt.setTranslateY(pos.y);
		CreateView();
		rect.setOnMouseClicked(this::handleMouseClick);
	}
	
	/**
	 * Créer la vue du ButtonEditor
	 */
	public void CreateView() {
		switch (type) {
		case ADDRIGHT: rect.setWidth(1*LevelEditor.size);
			rect.setFill(Color.web("rgba(70%,70%,0%,0.6)"));rect.setStroke(Color.web("rgba(100%,100%,100%,0.6)"));
			pan.getChildren().addAll(this.rect, new Label(">"));
			pan.setTranslateY(10.5*LevelEditor.size); pan.setTranslateX(1.5*LevelEditor.size);
			break;
		case ADDDOWN :rect.setWidth(1*LevelEditor.size);
			rect.setFill(Color.web("rgba(70%,70%,0%,0.6)"));rect.setStroke(Color.web("rgba(100%,100%,100%,0.6)"));
			pan.getChildren().addAll(this.rect, new Label("V"));
			pan.setTranslateY(10.5*LevelEditor.size); pan.setTranslateX(0.25*LevelEditor.size);
			break;
		default : rect.setFill(Color.web("rgba(0%,0%,0%,0.6)"));rect.setStroke(Color.web("rgba(100%,100%,100%,0.6)"));
		}
	}
	
	/**
	 * @param event
	 * Active une fonction en fonction du boutton lorsque le bouton reçoit un click
	 */
	public void handleMouseClick(MouseEvent event) {
		System.out.println("Clicked on "+type);
		level.superComponent.requestFocus();
		switch(type) {
		case SAVE : saveMessage(); break;
		case LOAD : load();break;
		case QUIT :	message("QUIT PRESSED", "Back to Main Menu.");quit(); break;
		case PLAY : play(); break;
		case BACKGROUND : background(); break;
		case ADDRIGHT : message("ADD TILE PRESSED", "Adding maxColumn by 50"); addTile(2); break;
		case ADDDOWN : message("ADD TILE PRESSED", "Adding maxRows by 50"); addTile(3); break;
		}
	}
	
	/**
	 * créer un background correspondant au fichier selectionné
	 */
	public void background() {
		File selectedFile=null;
		try {
			FileChooser f = new FileChooser();
			f.setTitle("Open Resource File");
			f.setInitialDirectory(new File("src/General/Sprites/Backgrounds"));
			selectedFile = f.showOpenDialog(null);
			if(selectedFile.exists()) {
				level.backgroundURL="Sprites/Backgrounds/"+selectedFile.getName();
				level.background=new ImageView(new Image(Controller.param.getRessource(level.backgroundURL)));
				level.refreshBackground();
			}
		}catch(Exception e) {}
	}
	
	/**
	 * Permet de lancer un level
	 */
	public void play() {
		if(LevelEditor.currentFile!=null) {
			Controller.lanceJeu(LevelEditor.currentFile);
			level.playPressed=true;
		}else {
			message("SAVE PRESSED", "ERREUR : Vous devez d'abord charger un niveau, ou sauvegarder votre niveau pour pouvoir le jouer !");
		}
	}
	
	/**
	 * @param i
	 * Augmente le nombre maximum de Tile dans le LevelEdit
	 * i==2 => Augmente de droite
	 * i==3 => Augmente de bas
	 */
	public void addTile(int i) {
		//Right
		if(i==2) {
			level.addEmptyTile(ItemTile.typeTile.CENTER, LevelEditor.currentMaxRows, LevelEditor.currentMaxCol+50, level.component, true, 0, LevelEditor.currentMaxCol);
			LevelEditor.currentMaxCol+=50;
		}
		//DOWN
		else if(i==3) {
			level.addEmptyTile(ItemTile.typeTile.CENTER, LevelEditor.currentMaxRows+50, LevelEditor.currentMaxCol, level.component, true, LevelEditor.currentMaxRows, 0);
			LevelEditor.currentMaxRows+=50;
		}
	}
	
	/**
	 * Active le Load en mettant loadPressed à true;
	 */
	public void load() {
		level.loadPressed=true;
	}
	
	/**
	 * @param message
	 * @param message2
	 * Genere un message sous forme d'une fenêtre
	 */
	public void message(String message, String message2) {
		this.alert=new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(message);
		alert.setContentText(message2);
		alert.showAndWait();
	}
	/**
	 * Genere un message sous forme de fenêtre appliqué seulement à Save
	 */
	public void saveMessage() {
		try {
			save();
		} catch (LevelEditPlayerException e) {
			message("SAVE PRESSED",e.getMessage());
			return;
		} catch (IOException e) {
			message("SAVE PRESSED","IOException Fail");
			return;
		}
		message("SAVE PRESSED", saveMessage);
	}
	/**
	 * @throws LevelEditPlayerException
	 * @throws IOException
	 * Genere la sauvegarde d'un fichier
	 */
	public void save() throws LevelEditPlayerException, IOException {
		leveltoString();
		if(!onePlayer()) throw new LevelEditPlayerException();
		else{
			File file;
			String name="LEVELEDIT";
			String path="src/General/EditedLevel/"+name;
			int count=0;
			while(true) {
				file = new File(path+"_"+String.valueOf(count)+".txt");
				try {if (file.createNewFile()){
						break;
					} else {count++;}
				} catch (IOException e) {}
			}
			FileWriter writer;
			try {writer = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(writer);
				out.write(level.backgroundURL+"\n");
				for(int i=0;i<level.codeLevel.size();i++) {
					for(int j=0;j<level.codeLevel.get(i).size();j++) {
						out.write(level.codeLevel.get(i).get(j));
					}out.newLine();
				}out.close();
				writer.close();
			} catch (IOException e) {}
			Controller.param.setLevel(path+"_"+String.valueOf(count)+".txt");
			saveMessage=("File successfully created as : "+ path+String.valueOf(count)+".txt");
			LevelEditor.currentFile=file;
		}
	}
	/**
	 * @return
	 * Verifie s'il y a un et un seul Joueur sur la grille
	 */
	public boolean onePlayer() {
		int nbP=0;
		for(int i=0;i<level.codeLevel.size();i++) {
			for(int j=0;j<level.codeLevel.get(i).size();j++) {
				if(level.codeLevel.get(i).get(j).equals("P")) nbP++;
			}
		}return (nbP==1);
	}
	/**
	 * Ajoute le level dans un StringArray
	 */
	public void leveltoString() {
		level.codeLevel.clear();
			for(int rows=0;rows<LevelEditor.currentMaxRows;rows++) {
				ArrayList<String> tmp = new ArrayList<String>();
				for(int col=0;col<LevelEditor.currentMaxCol;col++) {
					for(int i=0;i<level.component.getChildren().size();i++) {
						if(((ItemTile)level.component.getChildren().get(i)).pos.x==col*LevelEditor.size && ((ItemTile)level.component.getChildren().get(i)).pos.y==rows*LevelEditor.size) {
							tmp.add(((ItemTile)level.component.getChildren().get(i)).code);
						}
					}
				}level.codeLevel.add(tmp);
			}
	}
	
	/**
	 * Active quitPressed, elle permet de sortir du LevelEdit
	 */
	public void quit() {
		level.quitPressed=true;
	}
	
}
