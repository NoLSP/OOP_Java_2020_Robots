package gui;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PositionVisualizer extends JPanel implements Observer{
	private PointF robot;
	private JLabel text = new JLabel();
	
	public PositionVisualizer()
	{
		text.setBounds(this.getBounds());
		this.add(text);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof RobotModel)
		{
			//System.out.println((PaintInfo) arg);
			PointF robot = (PointF) arg;
			text.setText("Robot position - (" + (int)robot.getX() + ", " + (int)robot.getY() + ")");
			EventQueue.invokeLater(this::repaint);
		}
		
	}
}
