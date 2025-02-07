package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b014 extends LoboBuffBase{
	public RB_b014() {
		id = 14;
	}	
	
	@Override
	public void onEntityDie(LivingEntity en, LivingEntity killer) {
		for(Player p : Rule.c.keySet()) {
			for(int i =0; i<10; i++) {
				if(Rule.c.get(p).cooldown[i] > 0) {
					Rule.c.get(p).cooldown[i] *= 0.92;
				}
			}
			Rule.c.get(p).skillmult += 0.02f;
		}
	}
}