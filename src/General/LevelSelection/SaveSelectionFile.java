package General.LevelSelection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import General.Controller;

public class SaveSelectionFile {
	
	public static int theme=0;
	public static ArrayList<Integer> clearedList;
	public int level=1;
    public void onNextLevelWrite() {
    	if(checkLevel()) {
	    	FileWriter writer;
			try {writer = new FileWriter(Controller.param.getFileRessource("LevelSelection/save.txt"));
				BufferedWriter out = new BufferedWriter(writer);
				for(int i=0;i<clearedList.size();i++) {
					if(i==theme) {
						clearedList.set(i, clearedList.get(i)+1);
					}
					out.write(clearedList.get(i).toString());
					out.newLine();
				}out.close();
				writer.close();
			} catch (IOException e) {}
    	}
    }
    public boolean checkLevel() {
    	for(int i=0;i<clearedList.size();i++) {
    		if(i==theme && level==(clearedList.get(i)+1)) {
    			return true;
    		}
    	}return false;
    }
    public void setTheme() {
    	switch(theme) {
    	case 0 : Controller.param.setLevel(Controller.param.getFileRessource("Levels/Volcano/LEVEL"+level+".txt")); break;
    	case 1 : Controller.param.setLevel(Controller.param.getFileRessource("Levels/Snow/LEVEL"+level+".txt")); break;
    	case 2 : Controller.param.setLevel(Controller.param.getFileRessource("Levels/Sky/LEVEL"+level+".txt")); break;
    	case 3 : Controller.param.setLevel(Controller.param.getFileRessource("Levels/Desert/LEVEL"+level+".txt")); break;
    	case 4 : Controller.param.setLevel(Controller.param.getFileRessource("Levels/Jungle/LEVEL"+level+".txt")); break;
    	case 5 : Controller.param.setLevel(Controller.param.getFileRessource("Levels/LevelEdit/LEVEL"+level+".txt")); break;
    	}
    }
}
