package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b007 extends LoboBuffBase{
	public RB_b007() {
		id = 7;
	}	
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).skillmult += 0.8;
	}
	
	@Override
	public void onPlayerDie(Player p) {
		if(getlobo().bast == p) {
			for(Player pl : Rule.c.keySet()) {
				Rule.c.get(pl).skillmult -= 1.2;
			}
		}
	}
}