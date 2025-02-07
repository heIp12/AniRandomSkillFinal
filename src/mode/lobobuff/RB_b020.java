package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_b020 extends LoboBuffBase{
	public RB_b020() {
		id = 20;
	}
	
	@Override
	public void onTime(int time) {
		if(time%2 == 0) {
			if(time%8 == 0) {
				Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
				p.damage(p.getHealth() *0.4 + Math.min(0.4, 0.05f * Rule.c.keySet().size()),NpcPlayer.npc(Map.randomLoc()));
			}
			for(Player p : Rule.c.keySet()) {
				ARSystem.heal(p, 3);
			}
		}
	}
}