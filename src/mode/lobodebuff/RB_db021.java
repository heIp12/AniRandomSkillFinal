package mode.lobodebuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Exposure;
import mode.lobobuff.LoboBuffBase;

public class RB_db021 extends LoboBuffBase{
	public RB_db021() {
		id = 21;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%123 == 0) {
			double hp = 0;
			Player f = null;
			for(Player p : Rule.c.keySet()) {
				if(p.getHealth() > hp) {
					hp = p.getHealth();
					f = p;
				}
			}
			ARSystem.playSound((Entity)f, "0swrod5", 0.2f, 3);
			f.setHealth(1);
		}
	}
	
	@Override
	public void onPlayerChar(Player p) {
		if(!Rule.buffmanager.isBuff(p, "exposure")) {
			ARSystem.giveBuff(p, new Exposure(p), 200000, 5);
		} else {
			Rule.buffmanager.selectBuffAddValue(p, "exposure", 5);
		}
	}
}
