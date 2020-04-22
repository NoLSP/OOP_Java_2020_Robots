package gui;

public class PaintInfo {
	private double robotX;
	private double robotY;
	private double robotDirection;
	
	private int targetX;
	private int targetY;
	
	public PaintInfo(double rX, double rY, double rD, int tX, int tY)
	{
		robotX = rX;
		robotY = rY;
		robotDirection = rD;
		targetX = tX;
		targetY = tY;
	}
	
	public double robotX()
	{
		return robotX;
	}
	
	public double robotY()
	{
		return robotY;
	}
	
	public double robotDirection()
	{
		return robotDirection;
	}
	
	public int targetX()
	{
		return targetX;
	}
	
	public int targetY()
	{
		return targetY;
	}
}
