package mode.lobodebuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Sleep;
import mode.lobobuff.LoboBuffBase;
import util.AMath;

public class RB_db005 extends LoboBuffBase{
	public RB_db005() {
		id = 5;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%25 == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			ARSystem.playSound((Entity)p,"dream");
			ARSystem.giveBuff(p, new Sleep(p), 100 , 1.5+ 0.3*getlobo().cr.size());
		}
	}
}
