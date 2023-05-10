package mode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import manager.Bgm;
import util.AMath;
import util.MSUtil;
import util.Map;
import util.Text;

public class MZombieAdv extends ModeBase{
	
	public MZombieAdv(){
		super();
		modeName = "zombieadv";
		disPlayName = Text.get("main:msg53")+" Adv";
	}
	
	public void tick(int time) {
		for(int i=0; i<1+time/50; i++) {
			Location loc = Map.randomLoc();
			if(loc.getWorld().getLivingEntities().size() < 200) {
				Zombie en = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
				en.setMaxHealth(1+ time/3);
				ARSystem.potion(en, 5, 100000, 0+time/50);
				ARSystem.potion(en, 1, 100000, 0+time/30);
				ARSystem.potion(en, 12, 100000, 0);
			} else {
				for(LivingEntity e : loc.getWorld().getLivingEntities()) {
					if(e instanceof Player) {
						e.damage(1);
					} else {
						e.damage(5);
					}
				}
			}
		}
	}
}
