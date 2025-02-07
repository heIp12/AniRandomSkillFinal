package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Silence;

public class RB_b039 extends LoboBuffBase{
	int max = 0;
	public RB_b039() {
		id = 39;
		max = Math.min(getlobo().cr.size(),5);
	}
	
	@Override
	public void onNextStage() {
		max = Math.min(getlobo().cr.size(),5);
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).frist_defence -= max*0.08;
	}
	
	@Override
	public void onPlayerDie(Player p) {
		for(Player pl : Rule.c.keySet()) {
			Rule.c.get(p).frist_defence += max*0.08;
		}
		max = 0;
	}
}