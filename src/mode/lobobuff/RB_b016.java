package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Ice;
import buff.Nodamage;

public class RB_b016 extends LoboBuffBase{
	public RB_b016() {
		id = 16;
	}	
	
	@Override
	public void onTime(int time) {
		if(stageTime()%64 == 0) {
			ARSystem.playSoundAll("snowquine");
			for(LivingEntity e : getlobo().mobs) {
				ARSystem.giveBuff(e, new Ice(e, getlobo().bast), 140);
			}
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Nodamage(p), 140);
			}
		}
	}
}