package mode.lobodebuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db007 extends LoboBuffBase{
	public RB_db007() {
		id = 7;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%5 == 0) {
			if(AMath.random(5)<=1) {
				Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
				ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), p.getLocation().add((30-AMath.random(60))*0.1,0,(30-AMath.random(60))*0.1), "lobo_prj");
			} else {
				ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), Map.randomLoc(), "lobo_prj");
			}
		}
	}
}
