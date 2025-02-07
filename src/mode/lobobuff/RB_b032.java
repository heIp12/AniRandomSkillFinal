package mode.lobobuff;

import org.bukkit.entity.LivingEntity;

import ars.ARSystem;
import buff.PowerUp;
import buff.Rampage;

public class RB_b032 extends LoboBuffBase{
	public RB_b032() {
		id = 32;
	}
	
	@Override
	public void onTime(int time) {
		if(time%100 == 0) {
			for(LivingEntity e : getlobo().mobs) {
				ARSystem.giveBuff(e, new PowerUp(e), 60, 10);
				ARSystem.giveBuff(e, new Rampage(e), 60, 2);
			}
		}
	}
}