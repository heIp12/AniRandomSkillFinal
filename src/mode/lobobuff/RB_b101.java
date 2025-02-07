package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b101 extends LoboBuffBase{
	public RB_b101() {
		id = 101;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		if(p == getlobo().bast) {
			ARSystem.addItem(p, 100011);
		}
	}
}