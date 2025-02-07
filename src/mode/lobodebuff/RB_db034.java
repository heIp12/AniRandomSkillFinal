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

public class RB_db034 extends LoboBuffBase{
	int havenTimer = 0;
	int noeyetime = 0;
	LivingEntity entity;
	
	public RB_db034() {
		id = 34;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		for(LivingEntity m : getlobo().etcmobs) {
			if(m == entity) {
				boolean iseye = false;
				for(Player p : Rule.c.keySet()) {
					for(Entity e : ARSystem.PlayerBeamBox(p, 50, 4, box.ALL)) {
						if(e == m) {
							iseye = true;
						}
					}
				}
				if(iseye) {
					havenTimer++;
					noeyetime = 0;
					if(havenTimer > 1){
						havenTimer = 0;
						ARSystem.playSoundAll("dontwatch");
						for(Player p : Rule.c.keySet()) {
							p.damage(8, entity);
						}
					}
				} else {
					havenTimer = 0;
					noeyetime++;
					if(noeyetime > 5) {
						if(AMath.random(10) <= 2) entity.teleport(Map.randomLoc());
					}
				}
			}
		}

		if(entity == null || entity.isDead()) {
			entity = Map.spawnMM("camera", Map.randomLoc());
			getlobo().etcmobs.add(entity);
		}
		
	}
}
