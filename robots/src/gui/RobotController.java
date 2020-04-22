package gui;

import java.awt.Point;
import java.util.Observable;

public class RobotController extends Observable {
    
    private RobotModel model;
	
    public RobotController(RobotModel model) 
    {
    	this.model = model;
    }
    
    public void updateController(Point mouseClick)
    {
    	model.update(this, mouseClick);
    }
}
