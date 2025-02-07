package mode.lobobuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import util.Text;

public class RB_b015 extends LoboBuffBase{
	public RB_b015() {
		id = 15;
	}	
	
	@Override
	public void onPlayerDie(Player p) {
		Player t=null;double h = 2;
		for(Player pl : Rule.c.keySet()) {
			if(pl.getHealth()/pl.getMaxHealth() < h) {
				t = pl;
				h = pl.getHealth()/pl.getMaxHealth();
			}
		}
		ARSystem.heal(t, 9999);
		Rule.c.get(t).frist_damage *= 1.25;
		Bukkit.broadcastMessage(Text.get("lobo:ad") + t.getName());
	}
}