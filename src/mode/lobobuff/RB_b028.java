package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b028 extends LoboBuffBase{
	boolean cd = false;
	public RB_b028() {
		id = 28;
	}
	
	@Override
	public void onNextLevel() {
		cd = false;
	}
	
	@Override
	public void onTime(int time) {
		boolean range = true;
		for(Player p : Rule.c.keySet()) {
			for(Player p2: Rule.c.keySet()) {
				if(p != p2 && p.getLocation().distance(p2.getLocation()) < 6) {
					range = false;
				}
			}
		}
		if(range) {
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.size() > 1) {
					Rule.c.get(p).skillmult += 0.01;
				} else {
					Rule.c.get(p).skillmult += 0.003;
				}
			}
		} else if(!range) {
			for(Player p : Rule.c.keySet()) {
				Rule.c.get(p).skillmult -= 0.05;
			}
		}
	}
}