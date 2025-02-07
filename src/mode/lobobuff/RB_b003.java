package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b003 extends LoboBuffBase{
	public RB_b003() {
		id = 3;
	}	
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).skillmult += 0.15;
	}
	
	@Override
	public void onTime(int time) {
		if(time == 66) {
			for(Player p : Rule.c.keySet()) {
				Rule.c.get(p).skillmult += 0.35;
			}
			ARSystem.playSoundAll("wellcheers");
		}
	}
}