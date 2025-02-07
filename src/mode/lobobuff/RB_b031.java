package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b031 extends LoboBuffBase{
	public RB_b031() {
		id = 31;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).frist_damage *= 1.3;
	}
	
	@Override
	public void onPlayerDie(Player p) {
		if(p == getlobo().bast) {
			for(Player pl : Rule.c.keySet()) {
				Rule.c.get(pl).hpCost(p.getMaxHealth()*0.5 + 5, true);
			}
		}
	}
}