package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b008 extends LoboBuffBase{
	public RB_b008() {
		id = 8;
	}	
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).hpCost(8, true);
		Rule.c.get(p).skillmult += 0.5;
		Rule.c.get(p).frist_damage += 0.3;
	}
}