package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b049 extends LoboBuffBase{
	public RB_b049() {
		id = 49;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).frist_damage *= 2;
		Rule.c.get(p).frist_defence *= 2;
	}
}