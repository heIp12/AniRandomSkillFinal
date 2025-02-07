package mode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import ars.Rule;
import util.Text;

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
	public void PlayerDeathEvent(Player p, Entity killer) {}
	public void EntityDeathEvent(Entity p, Entity killer) {}
	public void Interact(PlayerInteractEntityEvent e) {}
	
	//initialize > option > start > firstTick > tick &tick2 > end
	public void initialize() {}
	public void option() {}
	public void start() {}
	public void firstTick() {}
	public void tick(int time) {}
	public void end() {}
	public void tick2() {}
	
	
	public int getInt(String s) {
		return Rule.Var.Loadint("System.mode."+modeName+"."+s);
	}
	public boolean getBool(String s) {
		return (boolean)Rule.Var.Load("System.mode."+modeName+"."+s);
	}
	public String get(String s) {
		return Rule.Var.Load("System.mode."+modeName+"."+s).toString();
	}
	public List<Player> getPlayers(String s) {
		try {
			return (List<Player>)Rule.Var.Load("System.mode."+modeName+"."+s);
		} catch (Exception e) {
			return new ArrayList<Player>();
		}
	}

	public void delay(Runnable event,int delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, event, delay);
	}
	
	public String Text(String s) {
		return Text.get("mode:"+s);
	}
}
