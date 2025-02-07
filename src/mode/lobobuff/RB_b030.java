package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Silence;

public class RB_b030 extends LoboBuffBase{
	public RB_b030() {
		id = 30;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).skillmult += Math.min(getlobo().cr.size()*1.5,15)*0.1;
	}
	
	@Override
	public void onPlayerDie(Player p) {
		for(Player pl : Rule.c.keySet()) {
			ARSystem.giveBuff(pl, new Silence(pl), 20);
			Rule.c.get(pl).skillmult -= 0.2;
		}
	}
}