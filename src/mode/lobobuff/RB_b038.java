package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import buff.Bload;

public class RB_b038 extends LoboBuffBase{
	public RB_b038() {
		id = 38;
	}
	@Override
	public void onPlayerChar(Player p) {
		ARSystem.giveBuff(p, new Bload(p), 200000, 0.05);
	}
}