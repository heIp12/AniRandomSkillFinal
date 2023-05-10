package mode;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSinfo;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.MapType;

import util.AMath;
import util.Map;
import util.NpcPlayer;
import util.Text;

public class MNormal extends ModeBase{
	public MNormal(){
		super();
		modeName = "normal";
		disPlayName = Text.get("main:mode1");
	}
	
	public void tick(int time) {
		if(Map.mapid == 15) {
			if(Bgm.bgmcode.equals("mk16")) {
				ARSystem.AniRandomSkill.time-=2;
				if(ARSystem.AniRandomSkill.time < 50) {
					ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), Map.randomLoc(), "azami_snake3");
					ARSystem.spellLocCast(NpcPlayer.npc(ARSystem.RandomPlayer().getLocation()), ARSystem.RandomPlayer().getLocation(), "azami_snake3");
				}
				Location loc = Map.randomLoc();
				if(AMath.random(10) == 2) loc = ARSystem.RandomPlayer().getLocation();
				ARSystem.spellLocCast(NpcPlayer.npc(loc), loc, "azami_snake3");
				
				if(ARSystem.AniRandomSkill.time <= 2 && ARSystem.AniRandomSkill.time > 1) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule,()->{
						ARSystem.addGameMode(new MKagerou());
					},10);
					ARSystem.AniRandomSkill.time = 0;
				}
			}
			if(!Bgm.bgmcode.equals("mk16")) {
				Location loc = new Location(Bukkit.getWorld("world"),325.5,31,-216.5,-130,0);
				if(time == 120) {
					boolean azami = false;
					for(Entity e : ARSystem.locEntity(loc, new Vector(99,99,99), null)) {
						if(e.getCustomName() != null && e.getCustomName().contains("Azami")) {
							azami = true;
						}
					}
					if(azami == false) {
						Map.spawn("azami",loc, 1);
					}
				}
				if(time > 123 && time < 130) {
					boolean azami = false;
					for(Entity e : ARSystem.locEntity(loc, new Vector(99,99,99), null)) {
						if(e.getCustomName() != null && e.getCustomName().contains("Azami")) {
							azami = true;
						}
					}
					if(azami == false) {
						ARSystem.playSoundAll("mkb16");
						Bgm.setForceBgm("mk16");
					}
				}
				if(time == 130) {
					for(Entity e : ARSystem.locEntity(loc, new Vector(99,99,99), null)) {
						if(e.getCustomName() != null && e.getCustomName().contains("Azami")) {
							Skill.remove(e, e);
						}
					}
				}
			}
		}
		
		if(!ARSystem.isGameMode("team") && Map.mapType == MapType.NORMAL &&time >= 120 && time%30 == 0) {
			ARSystem.playSoundAll("0select2");
			if(time <= 180) {
				RandomEvent(AMath.random(7));
			} else if(time <= 240)  {
				RandomEvent(AMath.random(8));
			} else if(time <= 300) {
				RandomEvent(7+AMath.random(2));
			} else {
				RandomEvent(10);
			}
		}
		
		if(Map.mapType == MapType.BIG &&time >= 60 && time%Integer.parseInt(Main.GetText("general:bigmap_time_value")) == 0) {
			if(Boolean.parseBoolean(Main.GetText("general:bigmap_time"))) {
				ARSystem.playSoundAll("0select2");
				Map.sizeM(-1);
			}
		}
		
		if(Map.mapType == MapType.NORMAL &&time >= 120 && time%30 == 0) {
			ARSystem.playSoundAll("0select2");
			if(time <= 180) {
				RandomEvent(AMath.random(8));
			} else if(time <= 240)  {
				RandomEvent(AMath.random(9));
			} else if(time <= 300) {
				RandomEvent(8+AMath.random(2));
			} else {
				RandomEvent(10);
			}
		}
	}

	public static void RandomEvent(int i) {
		if(i == 1) {
			for(Player p :Rule.c.keySet()) {
				Rule.c.get(p).sskillmult+=0.25;
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg3") +" +25% ");
			}
		}
		if(i == 2) {
			for(Player p :Rule.c.keySet()) {
				for(int j = 0; j<10; j++) {
					Rule.c.get(p).cooldown[j] = 0;
				}
				AdvManager.set(p, 388, 0, Main.GetText("main:msg2") +" "+ Main.GetText("main:msg4"));
			}
		}
		if(i == 3) {
			for(Player p :Rule.c.keySet()) {
				if(!Rule.buffmanager.OnBuffTime(p, "stun") && !Rule.buffmanager.OnBuffTime(p, "timestop")) {
					Map.playeTp(p);
				}
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg5"));
			}
		}
		
		if(i == 4) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg54"));
			}
			Map.spawn("food",15);
		}

		if(i == 5) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg53"));
			}
			Map.spawn("zombie",20);
		}
		
		if(i == 6) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg52"));
			}
			Map.spawn("sn",15);
		}
		
		if(i == 7) {
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg51"));
			}
			Map.spawn("bard",15);
		}
		
		if(i == 8) {
			Player pls = ARSystem.RandomPlayer();
			for(Player p :Rule.c.keySet()) {
				p.teleport(pls);
				ARSystem.giveBuff(p, new Silence(p), 40);
				ARSystem.giveBuff(p, new Nodamage(p), 40);
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg6"));
			}
		}
		
		if(i == 9) {
			Player pls = ARSystem.RandomPlayer();
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg7"));
			}
			ARSystem.Death(ARSystem.RandomPlayer(),ARSystem.RandomPlayer());
		}
		if(i == 10) {
			Player pls = ARSystem.RandomPlayer();
			for(Player p :Rule.c.keySet()) {
				AdvManager.set(p, 388, 0,  Main.GetText("main:msg2") +" "+ Main.GetText("main:msg7"));
			}
			ARSystem.GameStop();
		}
	}

}
