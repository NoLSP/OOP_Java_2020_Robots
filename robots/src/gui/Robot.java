package gui;

import java.awt.Point;

public class Robot {
	private volatile double m_PositionX = 100;
    private volatile double m_PositionY = 100; 
    private volatile double m_Direction = 0;
    
    private static final double maxVelocity = 0.1; 
    private static final double maxAngularVelocity = 0.001;
    
    public double posX()
    {
    	return m_PositionX;
    }
    
    public double posY()
    {
    	return m_PositionY;
    }
    
    public double direction()
    {
    	return m_Direction;
    }
    
    public void move(double robotX, double robotY, double targetX, double targetY, double duration)
    {
    	double distance = Calculator.getDistance(targetX, targetY, 
                robotX, robotY);
            if (distance < 0.5)
            {
                return;
            }
            double velocity = maxVelocity;
            double angleToTarget = Calculator.getAngleTo(robotX, robotY, targetX, targetY);
            double angularVelocity = 0;
            if (angleToTarget > m_Direction)
            {
                angularVelocity = maxAngularVelocity;
            }
            if (angleToTarget < m_Direction)
            {
                angularVelocity = -maxAngularVelocity;
            }
            
            move(velocity, angularVelocity, duration);
    }
    
    public void move(double velocity, double angularVelocity, double duration)
    {
        velocity = Calculator.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = Calculator.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_PositionX + velocity / angularVelocity * 
            (Math.sin(m_Direction  + angularVelocity * duration) -
                Math.sin(m_Direction));
        if (!Double.isFinite(newX))
        {
            newX = m_PositionX + velocity * duration * Math.cos(m_Direction);
        }
        double newY = m_PositionY - velocity / angularVelocity * 
            (Math.cos(m_Direction  + angularVelocity * duration) -
                Math.cos(m_Direction));
        if (!Double.isFinite(newY))
        {
            newY = m_PositionY + velocity * duration * Math.sin(m_Direction);
        }
        m_PositionX = newX;
        m_PositionY = newY;
        double newDirection = Calculator.asNormalizedRadians(m_Direction + angularVelocity * duration); 
        m_Direction = newDirection;
    }
}
