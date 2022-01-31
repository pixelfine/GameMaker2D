package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;

public class Monde {
	/*
	 * Classe qui permet d'analyser un Fichier et l'interpréter sous la forme initiale.
	 */
	File file;
	String firstLine="";
	//public boolean changeVolume=false;
	public double changeVolume=1;
	public Monde(File file) {
		this.file=file;
	}
	
	public static double Bloc_width = 52, Bloc_height = 52;
	private ArrayList<ButtonContent> contentArray = new ArrayList<ButtonContent>();
	
    public void readFile () {
    	ArrayList<String> level = new ArrayList<String>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        String line;
	        if((line = reader.readLine()) != null){
	        	Controller.param.setBackground(line); firstLine=line;
	        	int i=0;
	        	while ((line = reader.readLine()) != null) {
			    	level.add(line);
			    	System.out.println(line);
			    	lineAnalyser(line, i);
			    	i++;
			    }
	        	buttonContent();
	        }
	    } catch (IOException e) {
		    e.printStackTrace();
		}
    }
    
    public void lineAnalyser(String line, int i) {
    	Vector position = null;
    	int goTo=0;
    	String id="";
    	String code="";
		double offsetX=0;
		for(int j=0;j<line.length();j++) {
			if(goTo==0) {
				if(Monde.init_map(String.valueOf(line.charAt(j)), j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height, 0)!=null) {
					Jeu.arrayAdder.add(Monde.init_map(String.valueOf(line.charAt(j)), j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height, 0));
				}
				else switch (line.charAt(j)){
	                case '(' : 
	                	goTo=1;
	                	position = new Vector(j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height);
	                	break;
	                case '%' :
	                	goTo=2; 
	                	position = new Vector(j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height);
	    				break;
	                case '[' :
	                	goTo=3;
	                	position = new Vector(j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height);
	                	break;
    			}
			}else {
				if(goTo==1) {
					int count = 0;
					while(line.charAt(j)!=')'||line.charAt(j)==0) {
						id+=line.charAt(j);
						j++;
						count++;
					}
					count++;
					offsetX+=(count*Monde.Bloc_width);
					Jeu.arrayAdder.add(Monde.init_map("("+String.valueOf(id)+")", j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height, Integer.parseInt(id)));
					goTo=0;
					id="";
				}
				if(goTo==2) {
					int count = 0;
					while(line.charAt(j)!='%'||line.charAt(j)==0) {
						id+=line.charAt(j);
						j++;
						count++;
					}
					count++;
					offsetX+=(count*Monde.Bloc_width);
					ButtonTrigger button = (ButtonTrigger)Monde.init_map("%"+String.valueOf(id)+"%", j*Monde.Bloc_width-offsetX, i*Monde.Bloc_height, Integer.parseInt(id));
					Jeu.arrayAdder.add(button);
					goTo=0;
					id="";
				}
				if(goTo==3) {
					int count = 0;
					while(line.charAt(j)!='|'||line.charAt(j)==0) {
						id+=line.charAt(j);
						j++;
						count++;
					}j++;count++;
					while(line.charAt(j)!=']'||line.charAt(j)==0) {
						code+=line.charAt(j);
						j++;
						count++;
					}count++;
					offsetX+=(count*Monde.Bloc_width);
					contentArray.add(new ButtonContent(Integer.parseInt(id), code, position)); 
					goTo=0;
					id="";
					code=""; 
				}
			}
		}
    }
    public void buttonContent() {
    	for(int i=0;i<Jeu.arrayAdder.size();i++) {
    		if(Jeu.arrayAdder.get(i) instanceof ButtonTrigger) {
    			((ButtonTrigger)Jeu.arrayAdder.get(i)).setContent(contentArray);
    		}
    	}
    }
    
    /*
     * Fonction general, permettant l'analyse d'un caractère
     */
    public static Object init_map(String s, double posX, double posY, int id) {
	    switch (s){
	    			case "1": return new Bloc(Bloc.blocView.SAND, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "2" : return new Water(Water.waterView.waterBloc, posX, posY,0.2);
	                case "3" : return new Obstacle(Obstacle.obstacleView.SPIKE,posX, posY,Monde.Bloc_width, Monde.Bloc_height);
	                case "4" : return new Goal(Goal.goalView.ENTRANCE,posX, posY,Monde.Bloc_width, Monde.Bloc_height);
	                case "x" : return new Bloc(Bloc.blocView.ROCK, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "7" : return new Mob(new Vector(posX,posY), new Vector(0,0), new Vector(0,0), 45,"Goomba",20,10);
	                case "8" : return new Bloc(Bloc.blocView.SAND, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "9" : return new Bloc(Bloc.blocView.CLOUD, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "m" : return new Bloc(Bloc.blocView.CLOUD2, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "i" : return new Bloc(Bloc.blocView.ICE1, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "j" : return new Bloc(Bloc.blocView.ICE2, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "k" : return new Bloc(Bloc.blocView.ICE3, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "P" : {
	                	if(Controller.ancester == null) return new Player(new Vector(posX,posY),new Vector(0, 0), new Vector(0, 0), 45,"Row",null);
	                	else return new Player(new Vector(posX, posY), new Vector(0, 0), new Vector(0, 0), 45, "Legacy", Controller.ancester);
	                }
	                case "C" : return new Cannon(new Vector(posX,posY));
	                case "b" : return new MoveableObject(new Vector(posX,posY));
	                case "R" : return new Rocket(new Vector(posX,posY));
	                case "-" : return new CharacterBox(posX, posY, 40, 40, Controller.param.getLink_bloc(), Controller.param.getLink());
	                case "*" : return new CharacterBox(posX, posY, 80, 80, Controller.param.getPika_bloc(), Controller.param.getPika());
	                case "+" : return new CharacterBox(posX, posY, 110, 110, Controller.param.getBoo_bloc(), Controller.param.getBoo());
	                case "~" : return new MovingBloc(new Vector(posX,posY));
	                case "<" : return new Direction(Direction.directionView.LEFT, posX, posY, Bloc_width, Bloc_height);
	                case ">" : return new Direction(Direction.directionView.RIGHT, posX, posY, Bloc_width, Bloc_height);
	                case "^" : return new Direction(Direction.directionView.UP, posX, posY, Bloc_width, Bloc_height);
	                case "v" : return new Direction(Direction.directionView.DOWN, posX, posY, Bloc_width, Bloc_height);
	                case "h" : return new PatternMob(new Vector(posX,posY));
	                case "&" : return new Bloc(Bloc.blocView.GRASS1, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "a" : return new Bloc(Bloc.blocView.GRASS2, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "z" : return new Bloc(Bloc.blocView.GRASS3, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "$" : return new Bloc(Bloc.blocView.GROUND1, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "q" : return new Bloc(Bloc.blocView.GROUND2, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "w" : return new Bloc(Bloc.blocView.GROUND3, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "," : return new Bloc(Bloc.blocView.GROUNDL, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case ";" : return new Bloc(Bloc.blocView.GROUNDR, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case ":" : return new Bloc(Bloc.blocView.GROUNDC, posX, posY, Monde.Bloc_width, Monde.Bloc_height);
	                case "#" : return new UnstableBloc(new Vector(posX,posY));
	                case "o" : return new Bob_Omb(new Vector(posX,posY));
	                case "g" : return new MobGenerator(new Vector(posX,posY));
	                case "s" : return new Snorlax(new Vector(posX,posY));
	                case "B" : return new Boo(new Vector(posX,posY));
	                case "D" : return new BlueSlime(new Vector(posX,posY));
	                case "d" : return new RedSlime(new Vector(posX,posY));
	                case "X" : return new Item(1,posX,posY);
	                case "Y" : return new Item(2, posX, posY);
	                case "Z" : return new Item(3, posX, posY);
	    }
	    if(s.equals("("+id+")")) {
	    	return new Portal(new Vector(posX,posY),id);
	    }
	    if(s.equals("%"+id+"%")) {
	    	return new ButtonTrigger(new Vector(posX,posY),id);
	    }
	    return null;
    }
    public Media setSong() {
    	System.out.println(new File("").getPath());
    	switch(firstLine){
    	case "Sprites/Backgrounds/desert.jpg" : return new Media(Controller.param.getRessource(Controller.param.Songfantasy));
    	case "Sprites/Backgrounds/volcano.jpg" : return new Media(Controller.param.getRessource(Controller.param.Songvolcano)); 
    	case "Sprites/Backgrounds/Sky.gif" : return new Media(Controller.param.getRessource(Controller.param.Songsky)); 
    	case "Sprites/Backgrounds/levelDesign.jpg" : changeVolume=0.4; return new Media(Controller.param.getRessource(Controller.param.Songenchanted)); 
    	case "Sprites/Backgrounds/jungle.jpg" : return new Media(Controller.param.getRessource(Controller.param.Songjungle)); 
    	case "Sprites/Backgrounds/snowMap.jpg" : return new Media(Controller.param.getRessource(Controller.param.Songsnow)); 
    	}
    	return new Media(Controller.param.getRessource(Controller.param.Songenchanted));
    }
}
