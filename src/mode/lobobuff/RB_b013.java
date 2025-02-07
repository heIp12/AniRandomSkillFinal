package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b013 extends LoboBuffBase{
	public RB_b013() {
		id = 13;
	}
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			if(stageTime()%66 == 0) {
				if(p.getHealth() < p.getMaxHealth()) {
					ARSystem.spellCast(p,p, "bload");
					Rule.c.get(p).hpCost((p.getMaxHealth()-p.getHealth())*3, true);
				}
			}
			ARSystem.heal(p, 1.5);
		}
	}
}