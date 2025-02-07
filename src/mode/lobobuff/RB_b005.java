package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b005 extends LoboBuffBase{
	public RB_b005() {
		id = 5;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).hp*=1.4;
	}
}