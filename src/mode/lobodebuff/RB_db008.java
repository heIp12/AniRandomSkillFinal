package mode.lobodebuff;

import org.bukkit.entity.Player;

import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;

public class RB_db008 extends LoboBuffBase{
	public RB_db008() {
		id = 8;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(stageTime()%(80-Math.min(60,getlobo().cr.size()*5)) == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			getlobo().etcmobs.add(Map.spawnMM("helper", p.getLocation()));
		}
	}
}
