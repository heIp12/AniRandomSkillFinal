package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import buff.Exposure;

public class RB_b021 extends LoboBuffBase{
	public RB_b021() {
		id = 21;
	}
	@Override
	public void onPlayerChar(Player p) {
		ARSystem.giveBuff(p, new Exposure(p), 200000, -2);
	}
}