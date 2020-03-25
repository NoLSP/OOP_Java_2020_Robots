package gui;

import java.awt.Point;

public class FrameInfo {
	public int Width;
	public int Height;
	public boolean IsMax;
	public boolean IsMin;
	public Point Location;
	
	public FrameInfo(int width, int height, Point location, boolean isMax, boolean isMin)
	{
		Width = width;
		Height = height;
		IsMax =isMax;
		IsMin = isMin;
		Location = location;
	}
}
