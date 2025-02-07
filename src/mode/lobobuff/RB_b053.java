package mode.lobobuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Nodamage;
import types.BuffType;
import util.Text;

public class RB_b053 extends LoboBuffBase{
	int count = 0;
	public RB_b053() {
		id = 53;
	}
	@Override
	public void onTime(int time) {
		if(stageTime()%15 == 0 && count < 3) {
			count++;
			Bukkit.broadcastMessage("§a§l"+Text.get("lobo:b40") +" §a : §f§l"+count);
		}
		
		Player t = null;
		float pw = 0;
		
		for(Player p : Rule.c.keySet()) {
			for(Buff b : Rule.buffmanager.selectBuffType(p, BuffType.DEBUFF)) {
				if(b.getTime() > pw) {
					t = p;
					pw = b.getTime();
				}
			}
		}
		if(count > 0 && t != null && pw >= 200) {
			count--;
			Bukkit.broadcastMessage("§a§l"+Text.get("lobo:b40") +" §a : §f§l"+count);
			ARSystem.playSound(t,"healbullet");
			for(Buff b : Rule.buffmanager.selectBuffType(t, BuffType.DEBUFF)) {
				b.setTime(0);
			}
		}
	
	}
}