package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Player extends PhysicalObject{
	public static double width = 80;
	public static double height = 80;
	public static int pdvInitial;
	Rectangle r;
	private final String nom;
	private double pdv = 100;
	private final Player ancetre;
	private ArrayList<Item> powers = new ArrayList<Item>();
	public ArrayList<Item> getPowers(){
		return this.powers;
	}
	private boolean isRight = true;
	public boolean isRight() {return this.isRight;}
	public void setRight(boolean value) {this.isRight = value;}
	boolean interact = false;
	Cannon cannon;
	double initialLimit=(height/30)+4;
	boolean collidable=false;
	public boolean isInvicible = false;	
	private HealthBar HP = new HealthBar(0, 0, 200, 45, this);
	private final int invTime=1000;
	private boolean inCanon = false;
	private boolean launchCanon = false;
	public boolean collideCanon = false;
	public boolean ballCoolingDown=false;
	public boolean sideJumpCoolingDown=false;
	public boolean inSound=false;
	public boolean isLaunchCanon() {return launchCanon;}
	public void setLaunchCanon(boolean launchCanon) {this.launchCanon = launchCanon;}
	public void setInCanon(boolean inCanon) {this.inCanon = inCanon;}
	public boolean isInCanon() {return inCanon;}
	public Player(Vector pos, Vector velocity, Vector acceleration, double mass, 
			String nom, Player ancetre) {
	    super(pos, velocity, acceleration, mass);
	    super.coR = 0.53;
	    super.limit = 6;
	    
	    this.nom = nom;
	    this.ancetre = ancetre; 
	   
	    if(ancetre != null) {
	    	legacy();
	    }
	    
	    pdvInitial = 100;
	}
	public void legacy() {
		Random rand = new Random();
		if(ancetre.getPowers().size()>0) {
			int nombre = rand.nextInt((this.ancetre.getPowers().size()-1) - 0 + 1) + 0;
			Item item = this.ancetre.getPowers().get(nombre);
			switch(item.getType()) {
				case 2 : 
					this.powers.add(new Item(2, 0, 0));
					
					break;
				default : break;
			}
		}
	}
	public String getNom() {return this.nom;}
	public Player getAncetre() {return this.ancetre;}
	public double getPdv() {return pdv;}
	public void setPdv(double d) {this.pdv = d;}
	public HealthBar getHP() {
		return HP;
	}
	public void setInvicibility(boolean value) {this.isInvicible = value;}
	@Override
	public Pane createView() {
	    this.r = new Rectangle(width,height);
	    Image x = Controller.param.getRayman();
	    r.setFill(new ImagePattern(x));
	    Pane pane = new Pane();
	    pane.getChildren().add(r);
	    return pane;
	}
	@Override 
	public boolean boundIntersect(Node other, Node me) {
		if(other instanceof Detection) {((Detection)other).detection(me);}
		if(other instanceof Mob) return(other.getBoundsInParent().intersects(this.pos.x-5,this.pos.y, this.getBoundsInParent().getWidth()+10, this.getBoundsInParent().getHeight()));
		return super.boundIntersect(other, me);
	}
	@Override
	public boolean checkSides(Node other) {
		if(!(other instanceof Water)) {inWater=false;}
		if(other instanceof Bloc || other instanceof MoveableObject || other instanceof Mob || other instanceof MovingBloc || other instanceof Bob_Omb || other instanceof Snorlax|| other instanceof BlueSlime) {
			if(other instanceof PatternMob || other instanceof Mob || other instanceof Bob_Omb|| other instanceof Snorlax|| other instanceof BlueSlime) {
				if(!this.isInvicible) {
					if(other instanceof PatternMob) {
						applyInvincibility(10); ((PatternMob)other).applySound();
					}
					else return true;
				    return false;
				}else {return false;}
			}
			this.collidable = true;
			this.interact=false;
			return true;
		}
		this.collidable = false;
		return false;
	}
	@Override
	public double rebound(Node other) {
		if(other instanceof Bloc)
			return ((Bloc)other).reboundEffect();
		if(other instanceof MovingBloc)
			return 0.1;
		if(other instanceof PhysicalObject)
			return 2;
		return super.rebound(other);
	}
	
	@Override
	public void collideEffect(Node other) {
		if(collidable) { 
		}else {
			if(other instanceof Intersectable) {
				if(!(other instanceof Cannon)) {
					((Intersectable) other).intersect(this);
					this.collideCanon = false;
				}
				else {
					if(!this.launchCanon) {
						this.pos.x = ((Cannon) other).pos.x;
						this.pos.y = ((Cannon) other).pos.y;
						((Intersectable) other).intersect(this);
					}
					this.collideCanon = true;
				};
			}
		}
	}
	
	@Override
	public void collideDownEffect(Node other, double reb) {
		if(other instanceof Bloc) {
			((Bloc)other).applyFricOn(this);
		}
		else this.applyForce(this.friction(10));
        if(other instanceof Mob || other instanceof Bob_Omb || other instanceof BlueSlime) {
        	((Intersectable)other).intersect(this);
        }else {
            if(other instanceof PhysicalObject) {
            	if(!(other instanceof MovingBloc)) {
                PhysicalObject tmp = (PhysicalObject) other;
                tmp.velocity.y+=(this.velocity.y/10);
            	}else {((MovingBloc)other).setEffect(this, 1);}
            }
            super.collideDownEffect(other, reb);
        }
    }
	@Override
	public void collideUpEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			if(!(other instanceof MovingBloc)) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.y+=(this.velocity.y/10);
			}else {((MovingBloc)other).setEffect(this, 2);}
		}
		super.collideUpEffect(other, reb);
	}
	@Override
	public void collideLeftEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			if(!(other instanceof MovingBloc)) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.x+=(this.velocity.x/10);
			}
			if(other instanceof Mob || other instanceof Bob_Omb || other instanceof Snorlax && ((Snorlax)other).isAsleep==false|| other instanceof BlueSlime) {
				if(!this.isInvicible) {
					if(other instanceof Bob_Omb) ((Bob_Omb)other).eraseMob(0);
					applyInvincibility(10);
				}
				if(other instanceof Mob)((Mob)other).switching_side();
			}
			
		}if(!sideJumpCoolingDown) { this.colliding=true; sideJumpCoolingDown=true; setTimer(400,3);}
		super.collideLeftEffect(other, reb);
	}
	@Override
	public void collideRightEffect(Node other, double reb) {
		if(other instanceof PhysicalObject) {
			if(!(other instanceof MovingBloc)) {
			PhysicalObject tmp = (PhysicalObject) other;
			tmp.velocity.x+=(this.velocity.x/10);
			}
			if(other instanceof Mob || other instanceof Bob_Omb || (other instanceof Snorlax && ((Snorlax)other).isAsleep==false)|| other instanceof BlueSlime) {
				if(!this.isInvicible) {
					if(other instanceof Bob_Omb) ((Bob_Omb)other).eraseMob(0);
					applyInvincibility(10); 
				}if (other instanceof Mob) ((Mob)other).switching_side();
			}
		}if(!sideJumpCoolingDown) {this.colliding=true; sideJumpCoolingDown=true; setTimer(400,3);}
		super.collideRightEffect(other, reb);
	}
	public void moveX(int n) {
		double right = (n > 0)? 4:-4;
		this.setLayoutX(this.pos.x + right);
		this.velocity.x = this.velocity.x + right;
	}
	public void moveY(int n) {
		double down = (n >0)? 10:-10;
		if(colliding) {
			if(down<0 && !inWater) {
	        	new SpEffect(SpEffect.typeEffect.JUMP, this.pos, this.getWidth(), this.getHeight());
			}
			this.velocity.y=this.velocity.y + down;
			colliding=false;
		}
		else if((!colliding)&&n>0) {
			this.velocity.y=this.velocity.y + down;
		}
	}
	public boolean canFire() {
		if(!ballCoolingDown) {
			for(Item i : this.powers) {
				if(i.getType() == 2) {ballCoolingDown=true; return true;}
			}
		}return false;
	}
	public void applyInvincibility(double hit) {
		if(!isInvicible) {
			this.pdv -= hit;
			this.HP.looseHealth(hit);
			new SpEffect(SpEffect.typeEffect.DAMAGE, new Vector(this.pos.x, this.pos.y-this.getHeight()), this.getWidth(), this.getHeight());
			this.setInvicibility(true);
			fadeOut();
			setTimer(invTime,1);
		}
	}
	public void setTimer(int time, int n){
    	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(time),s -> endOfTimer(n)));
    	timeline.play();
    }
	public void endOfTimer(int n) {
		switch(n) {
		case 1 :	isInvicible=false; new SpEffect(SpEffect.typeEffect.BRIGHT, this.pos, this.getWidth(), this.getHeight()); break;
		case 2 :	ballCoolingDown=false; break;
		case 3 : 	sideJumpCoolingDown=false; break;
		case 4 :    fadeIn(); break;
		case 5 :	inSound=false; break;
		}
	}
	public void fadeOut() {
		FadeTransition fadeOut = new FadeTransition(Duration.millis(invTime/2),this);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.play();
		setTimer(invTime/2, 4);
	}
	public void fadeIn() {
		FadeTransition fadeIn = new FadeTransition(Duration.millis(invTime/2),this);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		fadeIn.play();
	}
	public void setSprite() {
    	if(colliding==false) r.setFill(new ImagePattern(Controller.param.getPerso_up()));
    	else {
    		if(velocity.x>0.2) {
    			if(velocity.x>4) r.setFill(new ImagePattern(Controller.param.getPerso_right()));
    			else r.setFill(new ImagePattern(Controller.param.getPerso_walk_right()));
    		}else if(velocity.x<-0.2){
    			if(velocity.x<-4) r.setFill(new ImagePattern(Controller.param.getPerso_left()));
    			else r.setFill(new ImagePattern(Controller.param.getPerso_walk_left()));
    		}else r.setFill(new ImagePattern(Controller.param.getPerso_idle()));
    	}
    }
	@Override
	public void display() {super.display(); setSprite();
		if(this.inWater) {
			if(!inSound) {
				inSound=true; new SpEffect("Sounds/SoundEffects/Waves.mp3",1); setTimer(2000, 5);
			}
		}
	}
}
