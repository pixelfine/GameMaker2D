package General.JeuCode;
import General.*;
import General.UtilCode.*;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class PhysicalObject extends Pane{
	
	Vector pos, velocity, acceleration;
	double mass, coR = 1, limit = 8;
	Pane view;
	double angle = 0;
	double angularVelocity = 50;
	double angularAcceleration = 0;
	boolean colliding = false;
	boolean inWater = false;
	Collision collision = new Collision();
	public PhysicalObject(Vector pos, Vector velocity, Vector acceleration, double mass) {
		this.pos=pos;
		this.velocity=velocity;
		this.acceleration=acceleration;
		this.mass=mass;
		view=createView();
		this.getChildren().add( view);
	}
	public PhysicalObject(Vector pos) {
		this.pos=pos;
		this.velocity=new Vector(0,0);
		this.acceleration=new Vector(0,0);
		this.mass=180;
		view=createView();
		this.getChildren().add(view);
	}
	
	public abstract Pane createView();
	
	public ArrayList<Water> collidrag(ArrayList<Water> waterArray) {
		for(int i=0;i<waterArray.size();i++) {
    		if(this.getBoundsInParent().intersects(waterArray.get(i).getBoundsInParent())) {
    			this.drag(waterArray.get(i));
    			this.colliding = true;
    		}
    	}
		return waterArray;
	}
	
	public void drag(Water l) {
		double speed=velocity.magnitude();
		double dragMagnitude = l.c*speed*speed;
		Vector drag = new Vector(velocity.x,velocity.y);
		drag.multiply(-1);
		drag.normalize();
		drag.multiply(dragMagnitude);
		applyForce(drag);
		this.inWater=true;
		this.colliding=true;
	}
	
	public Vector friction(double c) {
    	double normal = 1;
    	double frictionMag = c*normal;
    	Vector friction = new Vector(velocity.x,velocity.y);  
    	friction.multiply(-1);
    	friction.normalize();
    	friction.multiply(frictionMag);
    	return friction;
    }
	
	public void applyForce(Vector F) {
		Vector vect = new Vector (0,0);
		vect.x=F.x/mass;
		vect.y=F.y/mass;
		acceleration.add(vect);
	}
	
	public void update() {
		velocity.add(acceleration);
		pos.add(velocity);
		acceleration.multiply(0);
	}
	public void display() {
		this.setLayoutX(pos.x);
		this.setLayoutY(pos.y);
	}
	/*
	 * Collision, side detection
	 */
	public void collision(ArrayList<? extends Node> other) {
        for(int i=0;i<other.size();i++) {
        	if(boundIntersect(other.get(i), this)) {
	        	while(true) {
	        		if(checkSides(other.get(i))) {
		        		if(collision.centerYGreaterThanCenterX(pos.x, pos.y, this.getBoundsInParent().getWidth(), this.getBoundsInParent().getHeight(), other.get(i).getBoundsInParent().getMinX(), other.get(i).getBoundsInParent().getMinY(), other.get(i).getBoundsInParent().getWidth(), other.get(i).getBoundsInParent().getHeight())) {     		
			        		if(collision.collideDown(this.pos.y, this.getBoundsInParent().getHeight(), other.get(i).getBoundsInParent().getMinY(), other.get(i).getBoundsInParent().getHeight())){
			        			collideDownEffect(other.get(i), rebound(other.get(i))); break;
			        		}
			        		if(collision.collideUp(this.pos.y, this.getBoundsInParent().getHeight(), other.get(i).getBoundsInParent().getMinY(), other.get(i).getBoundsInParent().getHeight())) {
			        			collideUpEffect(other.get(i), rebound(other.get(i))); break;
			        		}
		        		}
		        		else {
			        		 if(collision.collideRight(this.pos.x, this.getBoundsInParent().getWidth(), other.get(i).getBoundsInParent().getMinX(), other.get(i).getBoundsInParent().getWidth())){
			        			 collideRightEffect(other.get(i), rebound(other.get(i))); break;
			        		 }
			        		 if(collision.collideLeft(this.pos.x, this.getBoundsInParent().getWidth(), other.get(i).getBoundsInParent().getMinX(), other.get(i).getBoundsInParent().getWidth())){
			        			 collideLeftEffect(other.get(i), rebound(other.get(i))); break;
			        		 }
		        		}
	        		}
	        		collideEffect(other.get(i)); break;
        		}
	        	if(removeOther(other.get(i))) other.remove(other.get(i));
	        	if(removeThis(other.get(i))) other.remove(this);
        	}
        } 
	}
	public boolean removeThis(Node other) {
		return false;
	}
	public boolean boundIntersect(Node other, Node me) {
		return other.getBoundsInParent().intersects(me.getBoundsInParent());
	}
	public boolean removeOther (Node other){
		return false;
	}
	public double rebound(Node other) {
		return 1;
	}
	public boolean checkSides(Node other) {
		return true;
	}
	public void collideEffect(Node other) {}	public void collideDownEffect(Node other, double reb) {
		this.velocity.y *= -this.coR*reb;
		this.pos.y = other.getBoundsInParent().getMinY()-this.getBoundsInParent().getHeight();
		this.colliding=true;
	}	public void collideUpEffect(Node other, double reb) {
		this.velocity.y *=coR*reb;
		this.pos.y=other.getBoundsInParent().getMaxY();
	}	public void collideRightEffect(Node other, double reb) {
		this.velocity.x=-coR*reb;
		this.pos.x=other.getBoundsInParent().getMinX()-this.getBoundsInParent().getWidth();
		//this.colliding=true;
	}	public void collideLeftEffect(Node other, double reb) {
		this.velocity.x *=-coR*reb;
		this.pos.x = other.getBoundsInParent().getMaxX();
		//this.colliding=true;
	}	
	public void maxVelocity() {
		if(velocity.x>limit)
			velocity.x=limit;
		if(velocity.x<-limit)
			velocity.x=-limit;
		if(velocity.y>limit*2)
			velocity.y=limit*2;
		if(velocity.y<-limit*1.5)
			velocity.y=-limit;
		
	}
}
