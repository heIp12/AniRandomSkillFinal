package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b012 extends LoboBuffBase{
	public RB_b012() {
		id = 12;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).skillmult += 1;
		Rule.c.get(p).frist_damage += 0.8;
	}
	
	@Override
	public void onTime(int time) {
		if(stageTime()%50 == 0) {
			for(Player p : Rule.c.keySet()) {
				boolean d = false;
				for(int i =0; i<10; i++) {
					if(Rule.c.get(p).cooldown[i] > 0) {
						d = true;
						Rule.c.get(p).hpCost(Rule.c.get(p).setcooldown[i], true);
					}
				}
				if(d) ARSystem.spellCast(p,p, "bload");
			}
		}
	}
}