package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Panic;

public class RB_b024 extends LoboBuffBase{
	public RB_b024() {
		id = 24;
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
				double damage = Rule.buffmanager.GetBuffTime(p, "panic") *0.0125;
				Rule.buffmanager.selectBuffTime(p, "panic", 0);
				Rule.c.get(p).hpCost(damage, true);
			}
		}
	}
}