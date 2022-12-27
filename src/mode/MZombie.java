package mode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import manager.Bgm;
import util.AMath;
import util.MSUtil;
import util.Map;

public class MZombie {

	public static void tick(int time) {
		if(time == 180) {
			int i = 0;
			int br = 0;
			String st = "";
			for(Player play :Rule.c.keySet()) {
				if(Rule.c.get(play).getCode() != 100001) {
					st += play.getName() + " ";
					i++;
				} else {
					br++;
				}
			}
			
			for(Player play :Rule.c.keySet()) {
				if(Rule.c.get(play).getCode() != 100001) {
					Rule.playerinfo.get(play).addcradit((3*br),Main.GetText("main:msg103"));
					play.sendTitle(" Win ", Main.GetText("main:msg41"),40,20,40);
				} else {
					play.sendTitle(" Lose ", Main.GetText("main:msg43"),40,20,40);
				}
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, new Runnable() {
				public void run() {
					ARSystem.GameStop();
				}
			}, 40);
			
		}
	}
	
}
