package mode.lobobuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.Bload;
import buff.PowerUp;
import types.BuffType;

public class RB_b106 extends LoboBuffBase{
	public RB_b106() {
		id = 106;
		for(Player p : Rule.c.keySet()) {
			ARSystem.giveBuff(p, new Bload(p), 2000000, 0.5);
		}
	}
	
	@Override
	public void onPlayerChar(Player p) {
		ARSystem.giveBuff(p, new Bload(p), 2000000, 0.5);
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			Rule.c.get(p).hpCost(p.getMaxHealth()*0.03, true);
		}
	}
}