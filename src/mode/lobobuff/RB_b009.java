package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import buff.ChoSan;

public class RB_b009 extends LoboBuffBase{
	public RB_b009() {
		id = 9;
	}
	@Override
	public void onPlayerChar(Player p) {
		if(p == getlobo().bast) ARSystem.giveBuff(p, new ChoSan(p), 999999999);
	}
}