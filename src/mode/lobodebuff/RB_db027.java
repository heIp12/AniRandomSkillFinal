package mode.lobodebuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import mode.lobobuff.RB_b046;
import util.Map;
import util.NpcPlayer;

public class RB_db027 extends LoboBuffBase{
	public RB_db027() {
		id = 27;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
				Rule.buffmanager.selectBuffAddTime(p, "panic", -80);
				Rule.c.get(p).hpCost(3, true);
			}
		}
	}
}
