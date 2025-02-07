package mode.lobobuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;

public class RB_b019 extends LoboBuffBase{
	boolean timer = false;
	public RB_b019() {
		id = 19;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		if(timer) {
			Rule.c.get(p).hp*=0.8;
		}
	}
	
	@Override
	public void onTime(int time) {
		if(!timer && Rule.c.size() == 1 && getlobo().cr.size() >= 2) {
			ARSystem.playSoundAll("warptime");
			getlobo().count = 0;
			timer = true;
			getlobo().sleep = 10;
			for(Player pl : Rule.c.keySet()) {
				ARSystem.giveBuff(pl, new Nodamage(pl), 400);
			}
			
			ARSystem.opCommand("mm m killall");
			ARSystem.opCommand("killall monster");
			ARSystem.opCommand("killall animals");
			ARSystem.opCommand("killall villager");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				getlobo().nextGame();
			},140);
		}
	}
}