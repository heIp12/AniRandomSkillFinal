package mode.lobodebuff;

import org.bukkit.Location;

import ars.ARSystem;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db015 extends LoboBuffBase{
	public RB_db015() {
		id = 15;
		debuff = true;
	}
	@Override
	public void onTime(int time) {
		if(stageTime()%39-Math.min(20,getlobo().level*5) == 0) {
			Location l = Map.getCenter();
			ARSystem.playSoundAll("shark");
			if(AMath.random(3) <= 1) {
				l.setX(111);l.setZ(262);l.setPitch(0);
				if(AMath.random(3) <= 1) {
					l.setY(50);l.setYaw(180);
				} else {
					l.setY(56);l.setYaw(0);
				}
			} else {
				l = Map.getCenter().clone().add((50-AMath.random(100))*0.2,0,(50-AMath.random(100))*0.2);
				l.setY(Map.loc_f.getY()+1.5f);
				l.setYaw(AMath.random(360));
			}
			ARSystem.spellLocCast(NpcPlayer.npc(l), l, "shark");
		}
	}
}
