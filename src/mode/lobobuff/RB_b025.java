package mode.lobobuff;

import java.util.HashMap;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import util.NpcPlayer;

public class RB_b025 extends LoboBuffBase{
	HashMap<Player,Integer> hps = new HashMap<>();
	public RB_b025() {
		id = 25;
	}
	
	@Override
	public void onNextStage() {
		hps.clear();
	}
	
	@Override
	public void onPlayerDie(Player p) {
		hps.put(p,0);
	}
	
	@Override
	public void onTime(int time) {
		if(time%3 == 0) {
			for(Player p : Rule.c.keySet()) {
				if(p.getHealth() < p.getMaxHealth()) {
					ARSystem.spellCast(p,p, "b25");
					ARSystem.playSound(p, "0heal", 2, 0.05f);
					hps.put(p,0);
					ARSystem.heal(p, 3);
				}
			}
		}
		for(Player p : Rule.c.keySet()) {
			if(hps.get(p) == null) hps.put(p,0);
			hps.put(p, hps.get(p)+1);
			if(hps.get(p) > 45) {
				ARSystem.spellCast(p,p, "bload");
				Skill.remove(p, NpcPlayer.npc(p.getLocation()));
			}
		}
	}
}