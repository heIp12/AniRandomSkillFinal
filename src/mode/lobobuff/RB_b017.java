package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b017 extends LoboBuffBase{
	public RB_b017() {
		id = 17;
	}
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
				Rule.buffmanager.selectBuffAddTime(p, "panic", -60);
			}
		}
	}
}