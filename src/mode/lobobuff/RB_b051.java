package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Bload;
import buff.NoCC;

public class RB_b051 extends LoboBuffBase{
	public RB_b051() {
		id = 51;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.buffmanager.selectBuffValue(p, "buffac",1.5f);
	}
}