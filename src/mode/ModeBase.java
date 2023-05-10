package mode;

public class ModeBase {
	protected String modeName = "";
	protected String disPlayName = "";
	protected boolean isOnlyOne = false;
	protected boolean isOne = false;
	protected boolean isSecret = false;

	public boolean IsOnlyOne() { return isOnlyOne;}
	public boolean IsOne() { return isOne; }
	public boolean IsSecret() { return isSecret; }
	public String getModeName() {return modeName;}
	public String getDisPlayName() { return disPlayName; }
	
	//initialize > option > start > firstTick > tick &tick2 > end
	public void initialize() {}
	public void option() {}
	public void start() {}
	public void firstTick() {}
	public void tick(int time) {}
	public void end() {}
	public void tick2() {}
}
