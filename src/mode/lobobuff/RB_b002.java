package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b002 extends LoboBuffBase{
	public RB_b002() {
		id = 2;
	}	
	
	@Override
	public void onNextStage() {
		for(Player p : Rule.c.keySet()) {
			if(Rule.buffmanager.GetBuffValue(p, "barrier") < 12) {
				Rule.buffmanager.selectBuffValue(p, "barrier", 12 + getlobo().level*2);
			}
		}
	}
}