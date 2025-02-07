package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.Rule;

public class RB_b037 extends LoboBuffBase{
	boolean on = false;
	public RB_b037() {
		id = 37;
	}
	@Override
	public void onPlayerChar(Player p) {
		if(p == getlobo().bast) on = false;
		if(getlobo().cr.size() <= 1) {
			on = true;
			Rule.c.get(p).frist_damage *= 1.6f;
			Rule.c.get(p).frist_defence *= 0.7;
			for(int i =0;i<10;i++) Rule.c.get(p).setcooldown[i] *= 0.8;
		}
	}
	
	@Override
	public void onPlayerDie(Player p) {
		Player bast = getlobo().bast;
		if(!on && Rule.c.size() <= 2 && bast != p) {
			for(Player pl : Rule.c.keySet()) {
				if(pl == bast) {
					on = true;
					Rule.c.get(pl).frist_damage *= 2;
					Rule.c.get(pl).frist_defence *= 0.6;
					for(int i =0;i<10;i++) Rule.c.get(pl).setcooldown[i] *= 0.6;
				}
			}
		}
	}
}