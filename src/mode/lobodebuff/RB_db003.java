package mode.lobodebuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Fascination;
import mode.lobobuff.LoboBuffBase;
import util.AMath;

public class RB_db003 extends LoboBuffBase{
	public RB_db003() {
		id = 3;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%33 == 0) {
			int level = getlobo().level;
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			ARSystem.playSound((Entity)p,"sakura");
			ARSystem.giveBuff(p, new Fascination(p, getlobo().mobs.get(AMath.random(getlobo().mobs.size())-1)), 60 + 10*level , 0.2+ 0.1*level);
		}
	}
}
