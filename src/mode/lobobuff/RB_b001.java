package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b001 extends LoboBuffBase{
	public RB_b001() {
		id = 1;
	}
	@Override
	public void onTime(int time) {
		if(time == 111 || time == 77) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.heal(p, p.getMaxHealth());
				Rule.c.get(p).skillmult += 0.15f;
			}
			ARSystem.playSoundAll("fairy");
		}
	}
}