package mode.lobodebuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import manager.Bgm;
import mode.lobobuff.LoboBuffBase;
import types.box;
import util.AMath;
import util.Map;

public class RB_db031 extends LoboBuffBase{
	LivingEntity entity = null;
	int cooldown = 0;
	public RB_db031() {
		id = 31;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(cooldown > 0) cooldown--;
		
		if(cooldown <= 0 && entity == null && (Bgm.bgmcode.equals("m3") || Bgm.bgmcode.equals("m4"))) {
			cooldown = 10000000;
			entity = Map.spawnMM("magic1_2", Map.randomLoc());
			getlobo().etcmobs.add(entity);
		}
		if(cooldown > 1000 && (entity == null || entity.isDead())) {
			cooldown = 120;
		}
	}
}
