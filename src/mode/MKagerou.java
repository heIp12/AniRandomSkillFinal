package mode;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vindicator;
import org.bukkit.util.Vector;

import Main.Main;
import util.AMath;
import ars.ARSystem;
import ars.Rule;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MKagerou extends ModeBase{
	public static int count = 0;
	static Location local = null;
	LivingEntity mary = null;
	public MKagerou(){
		super();
		isSecret = true;
		isOnlyOne = true;
		modeName = "kagerou";
		disPlayName = "kagerou";
	}
	
	public void tick(int time) {
		if(Bgm.bgmcode.contains("mk16")) {
			ARSystem.AniRandomSkill.time = 0;
			Map.spawn("mary",new Location(Bukkit.getWorld("world"),325,23,-214), 1);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
				for(Entity e : Bukkit.getWorld("world").getEntities()) {
					if(e.getName().contains("Marry")) {
						mary = (LivingEntity)e;
					}
				}
			},10);
			ARSystem.playSoundAll("mkb8");
			Bgm.setForceBgm("mk8");
		}
		if(Bgm.bgmcode.contains("mk8") && Bgm.getTime() <= 1) {
			ARSystem.playSoundAll("mkb5");
			Bgm.setForceBgm("mk5");
			while(local == null) {
				local = Map.randomLoc();
				for(Player p : Rule.c.keySet()) {
					if(p.getLocation().distance(local) < 10 || local.clone().add(0,-1,0).getBlock().getTypeId() == 8 || local.clone().add(0,-1,0).getBlock().getTypeId() == 9 || local.getY() > 25) {
						local = null;
					}
				}
			}
			ARSystem.spellLocCast(NpcPlayer.npc(local), local, "mary");
		} else if(Bgm.bgmcode.contains("mk8")) {
			if(time%4 == 0 && mary != null) {
				Vindicator mob = (Vindicator) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"),326,20,-222), EntityType.VINDICATOR);
				mob.damage(0.1,mary);
				
			}
			if(mary != null && time > 10 && mary.isDead()) {
				for(Player p : Rule.c.keySet()) {
					Skill.quit(p);
				}
			}
		}
		if(Bgm.bgmcode.contains("mk5") && Bgm.getTime() <= 1) {
			ARSystem.playSoundAll("mkb17");
			Bgm.setForceBgm("mk17");
			ArrayList<Player> pp =  new ArrayList<>();
			for(Player p : Rule.c.keySet()) {
				pp.add(p);
			}
			
			for(Player p : pp) {
				if(p.getLocation().distance(local) > 12) {
					Location loc = p.getLocation().add(0,5,0);
					loc.setPitch(-90);
					ARSystem.spellLocCast(NpcPlayer.npc(loc), loc, "azami_snake3");
					Skill.quit(p);
				}
			}
		}
	}
	
}
