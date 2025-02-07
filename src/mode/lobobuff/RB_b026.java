package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b026 extends LoboBuffBase{
	public RB_b026() {
		id = 26;
	}
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).frist_damage += 0.50;
		Rule.c.get(p).frist_defence += 0.30;
	}
}