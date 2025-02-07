package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import util.AMath;

public class RB_b104 extends LoboBuffBase{
	public RB_b104() {
		id = 104;
		getlobo().upcount +=1;
	}
	
	@Override
	public void onTime(int time) {
		if(time%20 == 0 && AMath.random(10) <= 1) {
			if(!getlobo().isdebuff(22)) {
				getlobo().adddebuff("22");
			}
		}
	}
}