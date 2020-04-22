package gui;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class RobotModel extends Observable implements Observer{
	
	private final Timer m_timer = initTimer();
	private GameVisualizer robotView;
	private PositionVisualizer posView;
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
	
	private Robot robot = new Robot();
    
    private Target target = new Target();
    
    public RobotModel(GameVisualizer robotView, PositionVisualizer posView) 
    {
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        this.robotView = robotView;
        this.posView = posView;
        
    }

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof RobotController)
			target.setPosition((Point) arg);
		//System.out.println("Model: " + target.posX() + ", " + target.posY());
	}
	
	protected void onModelUpdateEvent()
    {
    	robot.move(robot.posX(), robot.posY(), target.posX(), target.posY(), 10);
    	robotView.update(this, new PaintInfo(robot.posX(), robot.posY(), robot.direction(), target.posX(), target.posY()));
    	posView.update(this, new PointF(robot.posX(), robot.posY()));
    }
	
	
}
