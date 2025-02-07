package mode.lobodebuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import types.box;
import util.AMath;
import util.Map;

public class RB_db030 extends LoboBuffBase{
	int havenTimer = 0;
	LivingEntity entity;
	public RB_db030() {
		id = 30;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		boolean pt = false;
		boolean not = false;
		for(LivingEntity m : getlobo().etcmobs) {
			if(entity == m) {
				for(Player p : Rule.c.keySet()) {
					for(Entity e : ARSystem.PlayerBeamBox(p, 50, 3, box.ALL)) {
						if(e == m) {
							havenTimer = 0;
						}
					}
				}
				pt = true;
				if(havenTimer <= 10) {
					havenTimer++;
				} else if(havenTimer > 10){
					if(AMath.random(10) <= 1) {
						m.teleport(Map.randomLoc());
					}
				}
			} else {
				not = true;
				havenTimer = 0;
			}
		}
		if(!pt && (not || getlobo().etcmobs.size() <= 0)) {
			entity = Map.spawnMM("click", Map.randomLoc());
			getlobo().etcmobs.add(entity);
		}
		
	}
}
