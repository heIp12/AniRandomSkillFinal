package mode.lobobuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.PowerUp;
import types.BuffType;

public class RB_b105 extends LoboBuffBase{
	public RB_b105() {
		id = 105;
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			if(Rule.buffmanager.selectBuffType(p, BuffType.HEADCC).size() > 0) {
				Rule.c.get(p).frist_damage += 0.03;
				ARSystem.heal(p, 2);
			}
		}
	}
}