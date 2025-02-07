package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db013 extends LoboBuffBase{
	public RB_db013() {
		id = 13;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(stageTime()%77 == 0) {
			ARSystem.playSoundAll("train");
			Location l = Map.getCenter().clone().add((50-AMath.random(100))*0.2,0,(50-AMath.random(100))*0.2);
			l.setY(Map.loc_f.getY()+2.5f);
			if(AMath.random(10) <= 3) l = ((Player)Rule.c.keySet().toArray()[AMath.random(getlobo().mobs.size())-1]).getLocation();
			if(AMath.random(15) <= 1) { l.setX(111);l.setZ(262);l.setPitch(0);l.setY(56);l.setYaw(0); }
			ARSystem.spellLocCast(NpcPlayer.npc(l), l, "lobo_trin");
		}
	}
}
