package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import util.Map;
import util.Text;

public class RB_db019 extends LoboBuffBase{
	public RB_db019() {
		id = 19;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%13 == 0) {
			ARSystem.playSoundAll("silnecetime");
			for(int i=0;i<3;i++) delay(()->{ARSystem.playSoundAll("0timer");},i*20);
			for(LivingEntity e : getlobo().mobs) {
				setSpeed(e);
			}
			for(LivingEntity e : getlobo().etcmobs) {
				setSpeed(e);
			}
			for(Player p : Rule.c.keySet()) {
				for(int i =0; i<10; i++) if(Rule.c.get(p).cooldown[i] > 0) Rule.c.get(p).cooldown[i] += 4.4;
				ARSystem.potion(p, 2, 60, 2);
			}
		}
	}
	
	void setSpeed(LivingEntity e) {
		double speed = e.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
		if(speed <= 0.5) speed += 0.05;
		else speed *= 1.1f;
		e.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
	}
}
