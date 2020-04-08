package gui;

import java.awt.Point;

public class FrameInfo {
	private int width;
	private int height;
	private boolean isMax;
	private boolean isMin;
	private Point location;
	
	public FrameInfo(int width, int height, Point location, boolean isMax, boolean isMin)
	{
		this.width = width;
		this.height = height;
		this.isMax =isMax;
		this.isMin = isMin;
		this.location = location;
	}
	
	public int width()
	{
		return width;
	}
	
	public int height()
	{
		return height;
	}
	
	public boolean isMax()
	{
		return isMax;
	}
	
	public boolean isMin()
	{
		return isMin;
	}
	
	public Point location()
	{
		return location;
	}
}
