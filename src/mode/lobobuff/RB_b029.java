package mode.lobobuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.PowerUp;

public class RB_b029 extends LoboBuffBase{
	List<Player> bf = new ArrayList<>();
	public RB_b029() {
		id = 29;
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			if(Rule.buffmanager.isBuff(p, "rampage")) {
				ARSystem.giveBuff(p, new PowerUp(p), Rule.buffmanager.GetBuffTime(p, "rampage"), 10);
				ARSystem.giveBuff(p, new ArmorUp(p), Rule.buffmanager.GetBuffTime(p, "rampage"), 0.9);
				if(!bf.contains(p)) {
					bf.add(p);
				}
			} else {
				if(bf.contains(p)) {
					bf.remove(p);
					Rule.buffmanager.selectBuffTime(p, "powerup", 0);
					Rule.buffmanager.selectBuffTime(p, "armorup", 0);
				}
			}
		}
	}
}