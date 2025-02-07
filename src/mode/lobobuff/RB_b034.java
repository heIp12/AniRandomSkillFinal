package mode.lobobuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.Rule;
import util.Text;

public class RB_b034 extends LoboBuffBase{
	int damage = 0;
	boolean stop = false;
	public RB_b034() {
		id = 34;
	}
	
	@Override
	public void onNextStage() {
		if(!stop) {
			damage++;
			Bukkit.broadcastMessage(Text.get("lobo:b34") +" : " + damage);
		}
	}
	
	@Override
	public void onPlayerDie(Player p) {
		if(!stop) stop = true;
	}
	
	@Override
	public void onPlayerChar(Player p) {
		Rule.c.get(p).frist_damage += 0.05;
	}
}