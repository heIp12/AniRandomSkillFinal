package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import ars.ARSystem;
import mode.MLoboTomy;
import util.AMath;
import util.Text;

public class RB_b050 extends LoboBuffBase{
	public RB_b050() {
		id = 50;
		delay(()->{
			MLoboTomy.text(393,Text.get("lobo:b50_t0"));
		},1);
	}
	
	
	@Override
	public void onTime(int time) {
		if(time%30 == 0) {
			for(LivingEntity en : getlobo().mobs) {
				ARSystem.potion(en, 24, 200, 1);
			}
			if(AMath.random(10) <= 5) {
				MLoboTomy.text(393,"...");
			} else {
				MLoboTomy.text(393,Text.get("lobo:b50_t"+AMath.random(10)));
			}
		}
	}
}