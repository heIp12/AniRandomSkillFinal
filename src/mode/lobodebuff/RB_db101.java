package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import ars.ARSystem;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;
import util.Text;

public class RB_db101 extends LoboBuffBase{
	public RB_db101() {
		id = 101;
		debuff = true;
	}
	
	@Override
	public void onNextStage() {
		LivingEntity en = null;
		if(AMath.random(20) <= 1) {
			en = getlobo().spawn("c50_1", 1, true);
		} else if(AMath.random(10) <= 6){
			en = getlobo().spawn("hime2", 3, true);
		}  else {
			en = getlobo().spawn("hime3", 1, true);
		}
		if(en != null) {
			en.setMaxHealth(30+en.getMaxHealth()*2);
			en.setHealth(30+en.getMaxHealth()*2);
		}
	}
}
