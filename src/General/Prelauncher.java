package General;
import General.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Prelauncher extends Application{
	public enum SceneEnum {
		MAIN, PLAY, LEVELEDIT, OPTION, GAMEOVER;
	}
	private static Map<SceneEnum, Scene> scenes = new HashMap<>();
	@SuppressWarnings("unused")
	private static Map<SceneEnum, Scene> map = Collections.synchronizedMap(scenes);
	public static Stage stage;
	public static boolean changed=false;
	
	public static void main(String[] args) {
		launch(args);
	}
	public static Map<SceneEnum, Scene> getScenes() {
		return scenes;
	}
	@SuppressWarnings("unused")
	@Override
	public void start(Stage stage) {
		Prelauncher.stage=stage;
		stage.setHeight(620);
		stage.setWidth(1200);
		stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                stage.setWidth((double)number2);
                changed=true;
            }
        });
		

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
            	stage.setHeight((double)number2);
            	changed=true;
            }
        });
		Controller c = new Controller(Prelauncher.stage);
		scenes.put(SceneEnum.MAIN, Controller.Menu(0));
		stage.setTitle("Super Luigi Land !");
		stage.show();
	}
}
