package mode.lobobuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.Rule;
import util.Text;

public class RB_b048 extends LoboBuffBase{
	boolean on = true;
	List<Player> p = new ArrayList<Player>();
	public RB_b048() {
		id = 48;
	}
	
	@Override
	public void onNextStage() {
		on = true;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		if(!this.p.contains(p) && on) {
			on = false;
			Bukkit.broadcastMessage(Text.get("lobo:b48") +" : " + p.getName());
			this.p.add(p);
			Rule.c.get(p).frist_damage += 0.5;
			Rule.c.get(p).skillmult += 0.5;
			Rule.c.get(p).hp *= 1.5;
		} else {
			if(this.p.size() == getlobo().cr.size()) {
				this.p.clear();
			}
		}
	}
}