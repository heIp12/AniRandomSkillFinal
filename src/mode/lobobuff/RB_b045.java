package mode.lobobuff;

import org.bukkit.entity.LivingEntity;

public class RB_b045 extends LoboBuffBase{
	int i = 0;
	public RB_b045() {
		id = 45;
	}	
	
	@Override
	public void onEntitySpawn(LivingEntity en) {
		i++;
		if(i == 13) {
			i = 0;
			en.setHealth(en.getHealth()*0.2);
			en.setMaxHealth(en.getMaxHealth()*0.2);
		}
	}
}