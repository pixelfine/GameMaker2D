package General.UtilCode;
import General.*;
public class Vector {
    public double x;
    public double y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setVector(Vector v) {
    	this.x=v.x;
    	this.y=v.y;
    }

    public double magnitude() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void multiply(double n) {
        this.x *= n;
        this.y *= n;
    }

    public void div(double n) {
        this.x /= n;
        this.y /= n;
    }

    public void normalize() {
        double m = magnitude();
        if (m != 0 && m != 1) {
            div(m);
        }
    }

    public void limit(double max) {
        if (magnitude() > max) {
            normalize();
            multiply(max);
        }
    }

    public Vector subtract(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }
    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y);
    }
    
    public Vector addNewVec(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }
    public Vector mulNewVec(Vector v) {
        return new Vector(x * v.x, y * v.y);
    }
    
    public double heading2D() {
        return Math.atan2(y, x);
    }
}
