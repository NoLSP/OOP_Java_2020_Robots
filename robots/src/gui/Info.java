package gui;

public class Info {
	private FrameInfo internal1Frame;
	private FrameInfo internal2Frame;
	
	public Info(FrameInfo internal1Frame, FrameInfo internal2Frame)
	{
		this.internal1Frame = internal1Frame;
		this.internal2Frame = internal2Frame;
	}
	
	public FrameInfo getFrame1()
	{
		return internal1Frame;
	}
	
	public FrameInfo getFrame2()
	{
		return internal2Frame;
	}
}
