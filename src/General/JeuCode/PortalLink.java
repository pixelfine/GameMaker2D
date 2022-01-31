package General.JeuCode;
import General.*;
import java.util.ArrayList;

/*
 * Class representant la liaison de chaque Portal
 */
public class PortalLink {
	ArrayList <Portal> portalList= new ArrayList<Portal>();
	ArrayList <Integer> pivotList = new ArrayList<Integer>();
	
	public PortalLink(ArrayList <Portal> portalList) {
		this.portalList=portalList;
		init_pivot();
	}
	public void init_pivot(){
		if(!portalList.isEmpty()) {
			pivotList.add(0);
			for(int i=0; i<portalList.size()-1;i++) {
				if(portalList.get(i).id!=portalList.get(i+1).id) {
					pivotList.add(i+1);
				}
			}
		}
	}
	public void intersectPortal(Player p) {
    	if(!portalList.isEmpty()) {
    		int index=0;
    		for(int i=0;i<portalList.size()-1;i++) {
    			if(p.getBoundsInParent().intersects(portalList.get(i).pos.x+10, portalList.get(i).pos.y+10,60,60)) {
    				if(portalList.get(i).id==portalList.get(i+1).id) {
    					p.pos.x=portalList.get(i+1).pos.x;
    					p.pos.y=portalList.get(i+1).pos.y;
    					new SpEffect("Sounds/SoundEffects/portal.mp3",1);
    				}else {
    					p.pos.x=portalList.get(pivotList.get(index)).pos.x;
    					p.pos.y=portalList.get(pivotList.get(index)).pos.y;
    					new SpEffect("Sounds/SoundEffects/portal.mp3",1);
    				}
    			}else {
    				if(portalList.get(i).id!=portalList.get(i+1).id) {
    					index++;
    				}
    			}
    		}
    		if(p.getBoundsInParent().intersects(portalList.get(portalList.size()-1).pos.x+10, portalList.get(portalList.size()-1).pos.y+10,60,60)) {
    			p.pos.x=portalList.get(pivotList.get(index)).pos.x;
				p.pos.y=portalList.get(pivotList.get(index)).pos.y;
				new SpEffect("Sounds/SoundEffects/portal.mp3",1);
    		}
    	}
    }
}