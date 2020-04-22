package gui;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class RobotPositionWindow extends JInternalFrame
{
    private final PositionVisualizer visualizer;
    
    public RobotPositionWindow() 
    {
        super("Позиция робота", true, true, true, true);
        visualizer = new PositionVisualizer();
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
    
	public FrameInfo getInfo() {
		return new FrameInfo(getWidth(), getHeight(), getLocation(), isMaximum(), isIcon());
	}

	public PositionVisualizer getVisualizer() {
		return visualizer;
	}
}
