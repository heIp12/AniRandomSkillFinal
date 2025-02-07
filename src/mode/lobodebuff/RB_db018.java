package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db018 extends LoboBuffBase{
	public RB_db018() {
		id = 18;
		debuff = true;
	}
	int count = 0;
	int tick = 0;
	
	@Override
	public void onTick() {
		tick++;
		if(tick%5 == 0 && count > 0) {
			count--;
			ARSystem.playSoundAll("c1139s12");
			Location l = Map.getCenter().clone().add((50-AMath.random(100))*0.15,0,(50-AMath.random(100))*0.15);
			l.setY(Map.loc_f.getY()+1.5f);
			if(AMath.random(3) <= 1) l = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]).getLocation();
			l.setYaw(AMath.random(360));
			ARSystem.spellLocCast(NpcPlayer.npc(l),l,"lobo_matan");
		}
	}
	
	@Override
	public void onEntityDie(LivingEntity en, LivingEntity killer) {
		count++;
	}
}
