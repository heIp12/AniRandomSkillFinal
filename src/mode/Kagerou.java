package mode;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import util.AMath;
import util.Local;
import util.MSUtil;
import util.Map;
import util.NpcPlayer;

public class Kagerou {
	public static int count = 0;
	static Location local = null;
	public static void tick(int time) {
		if(Bgm.bgmcode.contains("mk16")) {
			ARSystem.AniRandomSkill.time = 0;
			ARSystem.playSoundAll("mkb8");
			Bgm.setForceBgm("mk8");
		}
		if(Bgm.bgmcode.contains("mk8") && Bgm.getTime() <= 1) {
			ARSystem.playSoundAll("mkb5");
			Bgm.setForceBgm("mk5");
			while(local == null) {
				local = Map.randomLoc();
				for(Player p : Rule.c.keySet()) {
					if(p.getLocation().distance(local) < 10 || local.clone().add(0,-1,0).getBlock().getTypeId() == 8 || local.clone().add(0,-1,0).getBlock().getTypeId() == 9) {
						local = null;
					}
				}
			}
			ARSystem.spellLocCast(NpcPlayer.npc(local), local, "mary");
		} else if(Bgm.bgmcode.contains("mk8")) {
			for(int i=0; i< AMath.random(10); i++) {
				Location loc = Map.randomLoc();
				loc = Local.offset(loc, new Vector(AMath.random(15),AMath.random(15),AMath.random(15)));
				ARSystem.spellLocCast(NpcPlayer.npc(loc), loc, "azami_snake4");
			}
			for(int i=1; i< AMath.random(3); i++)ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), Map.randomLoc(), "azami_snake3");
			if(Bgm.getTime() <= 20) {
				for(int i=0; i< AMath.random(20); i++) {
					Location locf = ARSystem.RandomPlayer().getLocation();
					Location loc = locf.clone();
					loc = Local.offset(loc, new Vector(AMath.random(15),AMath.random(15),AMath.random(15)));
					
					ARSystem.spellLocCast(NpcPlayer.npc(loc), Local.lookAt(loc, locf), "azami_snake4");
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
				if(p.getLocation().distance(local) > 8) {
					Location loc = p.getLocation().add(0,5,0);
					loc.setPitch(-90);
					ARSystem.spellLocCast(NpcPlayer.npc(loc), loc, "azami_snake3");
					Skill.quit(p);
				}
			}
		}
	}
	
}
