package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import ars.ARSystem;
import mode.lobobuff.LoboBuffBase;
import util.Map;
import util.Text;

public class RB_db002 extends LoboBuffBase{
	public RB_db002() {
		id = 2;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		int level = getlobo().level;
		double hpmult = getlobo().hpmult;
		
		if(stageTime()%44-Math.min(22,level*5) == 0) {
			Location l = Map.randomLoc();
			ARSystem.playSoundAll("spiderpop");
			for(int i =0; i<3*getlobo().v; i++) {
				LivingEntity e = (LivingEntity) l.getWorld().spawnEntity(Map.randomLoc(), EntityType.SPIDER);
				e.setMaxHealth((6 + level*2));
				e.setHealth((6 + level*2));
				e.setCustomName("§f"+Text.get("lobo:o12"));
				getlobo().addMob(e);
			}
		}
	}
}
