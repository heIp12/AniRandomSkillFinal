package mode.lobodebuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;

public class RB_db001 extends LoboBuffBase{
	public RB_db001() {
		id = 1;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(stageTime()%10 == 0) {
			if(AMath.random(2) <= 1) {
				Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
				if(AMath.random(2) <= 1) {
					 ARSystem.giveBuff(p, new Silence(p), 100);
				} else {
					p.teleport(Map.randomLoc());
				}
				ARSystem.playSound(p,"defult");
			} else {
				LivingEntity mob = getlobo().mobs.get(AMath.random(getlobo().mobs.size())-1);
				ARSystem.playSound(mob,"defult");
				mob.teleport(Map.randomLoc());
			}
		}
	}
}
