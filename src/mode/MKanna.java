package mode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import Main.Main;
import ars.Rule;
import manager.AdvManager;
import util.Map;

public class MKanna {
	public static void tick(int time) {
		if(time == 0) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:mode6"));
			}
		}
		if(!isBoom() && time > 0) {
			Map.spawn("boom", Map.getCenter(), 1);
			if(time > 45) {
				Map.spawn("boom", time/45);
			}
		}
		
	}
	public static boolean isBoom() {
		for(LivingEntity e : Bukkit.getWorld("world").getLivingEntities()) {
			if(e instanceof Creeper && e.getCustomName() != null && e.getCustomName().equals("Boom")) {
				return true;
			}
		}
		return false;
	}
}
