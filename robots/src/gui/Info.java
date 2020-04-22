package gui;

public class Info {
	private FrameInfo logFrame;
	private FrameInfo gameFrame;
	private FrameInfo posFrame;
	
	public Info(FrameInfo logFrame, FrameInfo gameFrame, FrameInfo posFrame)
	{
		this.logFrame = logFrame;
		this.gameFrame = gameFrame;
		this.posFrame = posFrame;
	}
	
	public FrameInfo getLogFrame()
	{
		return logFrame;
	}
	
	public FrameInfo getGameFrame()
	{
		return gameFrame;
	}
	
	public FrameInfo getPosFrame()
	{
		return posFrame;
	}
}
