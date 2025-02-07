package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b023 extends LoboBuffBase{
	public RB_b023() {
		id = 23;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).skillmult -= 0.8;
		Rule.c.get(p).frist_damage *= 1.50;
	}
}