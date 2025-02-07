package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b027 extends LoboBuffBase{
	public RB_b027() {
		id = 27;
	}
	
	@Override
	public void onEntityDie(LivingEntity en, LivingEntity killer) {
		for(Player p : Rule.c.keySet()) {
			ARSystem.heal(p, 1);
		}
	}
}