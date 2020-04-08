package gui;

import java.awt.Point;

public class Target {
	private volatile int m_PositionX = 150;
    private volatile int m_PositionY = 100;
    
    public int posX()
    {
    	return m_PositionX;
    }
    
    public int posY()
    {
    	return m_PositionY;
    }
    
    public void setPosition(Point p)
    {
    	m_PositionX = p.x;
    	m_PositionY = p.y;
    }
}
