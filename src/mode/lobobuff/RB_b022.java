package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b022 extends LoboBuffBase{
	public RB_b022() {
		id = 22;
	}
	@Override
	public void onPlayerChar(Player p) {
		if(p == lobo.bast) ARSystem.playSoundAll("girlkiss");
		if(!Rule.c.get(p).hpCost(p.getMaxHealth()*0.8, false)) {
			p.setHealth(1);
		}
		Rule.buffmanager.selectBuffAddValue(p, "barrier", (int)p.getMaxHealth()*4);
	}
}