package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements Observer
{
	private PaintInfo paintInfo = new PaintInfo(100, 100, 0, 150, 100);
	private RobotController robotController;
    
    public GameVisualizer() 
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(robotController != null)
                	robotController.updateController(e.getPoint());
                //System.out.println(e.getPoint().x + ", " + e.getPoint().y);
            }
        });
        setDoubleBuffered(true);
        
    }
    
    public void setController(RobotController rController)
    {
    	this.robotController = rController;
    }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        Painter.drawRobot(g2d, paintInfo.robotX(), paintInfo.robotY(), paintInfo.robotDirection());
        Painter.drawTarget(g2d, paintInfo.targetX(), paintInfo.targetY());
    }

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof RobotModel)
		{
			//System.out.println((PaintInfo) arg);
			paintInfo = (PaintInfo) arg;
			onRedrawEvent();
		}
		
	}
}
