package mode.lobobuff;

import org.bukkit.entity.LivingEntity;

import ars.ARSystem;
import buff.Exposure;

public class RB_b042 extends LoboBuffBase{
	public RB_b042() {
		id = 42;
	}	
	
	@Override
	public void onEntitySpawn(LivingEntity en) {
		ARSystem.giveBuff(en, new Exposure(en), 6000, 1);
	}
}