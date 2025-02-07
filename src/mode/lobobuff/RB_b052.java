package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Bload;
import buff.NoCC;

public class RB_b052 extends LoboBuffBase{
	public RB_b052() {
		id = 52;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).hp += 5;
		Rule.c.get(p).frist_damage *= 1.1;
		Rule.c.get(p).frist_defence *= 0.9;
	}
}