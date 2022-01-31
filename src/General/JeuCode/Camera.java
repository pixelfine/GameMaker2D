package General.JeuCode;
import General.*;
import General.UtilCode.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;

public class Camera {
	Player player;
	Pane root;
	ChangeListener focusX;
	ChangeListener focusY;
	int camX=300;
	int camY=0;
	int lastX=0;
	int translationX=0;
	int lastY=0;
	int translationY=0;
	Camera(Player player, Pane root){
		this.player=player;
		this.root=root;
		focusCam(player);
	}
	public void focusCam(Player camera) {
    	focusCamX(camera);
    	focusCamY(camera);
    	applyListener();
    }
	public void focusCamX(Player camera) {
		this.focusX=new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				lastX= ((Number)newValue).intValue();
	    		translationX = -(lastX-camX);
	    		updateX();
			}
    	};
	}
	public void focusCamY(Player camera) {
		this.focusY=new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				lastY= ((Number)newValue).intValue();
				translationY = -(lastY-200)-camY;
				updateY();
			}
    	};
	}
	public void updateX() {
		for (int i=1;i<root.getChildren().size();i++) {
            root.getChildren().get(i).setLayoutX(-(lastX-camX));
		}
		root.getChildren().get(0).setLayoutX((-(lastX-camX))/10);
	}
	public void updateY() {
		for(int i=1;i<root.getChildren().size();i++) {
        	root.getChildren().get(i).setLayoutY((-(lastY-200)-camY));
    	}
	}
	public void update() {
		updateX(); updateY();
	}
	public void applyListener() {
		player.layoutXProperty().addListener(focusX);
    	player.layoutYProperty().addListener(focusY);
	}
	public void removeListener() {
		player.layoutXProperty().removeListener(focusX);
		player.layoutYProperty().removeListener(focusY);
	}
}
