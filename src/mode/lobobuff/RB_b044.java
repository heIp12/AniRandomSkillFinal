package mode.lobobuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import types.box;
import util.AMath;
import util.Map;

public class RB_b044 extends LoboBuffBase{
	public RB_b044() {
		id = 44;
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			for(Player e : ARSystem.PlayerOnlyBeamBox(p, 50, 3, box.ALL)) {
				if(Rule.buffmanager.isBuff(e, "panic")) Rule.buffmanager.selectBuffAddTime(e, "panic", -120);
				if(Rule.buffmanager.isBuff(e, "silence")) Rule.buffmanager.selectBuffAddTime(e, "silence", -120);
				if(Rule.buffmanager.isBuff(e, "fascination")) Rule.buffmanager.selectBuffAddTime(e, "fascination", -120);
			}
		}
	}
}