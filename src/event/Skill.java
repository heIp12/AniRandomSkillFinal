package event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Timeshock;
import chars.c3.c101aris;
import chars.ca.c2400sinobu;
import chars.ca.c3002siro;
import chars.ca.c5600enju;
import chars.ca.c8400subi;
import manager.AdvManager;
import manager.BuffManager;
import util.GetChar;
import util.Holo;
import util.MSUtil;
import util.Map;

public class Skill {
	static public void remove(Entity entity,Entity player) {
		if(Rule.c.get(player) == null) return;
		Holo.create(entity.getLocation(),"§c§l<< Delete >>",100,new Vector(0,0.01,0));
		if(entity instanceof Player) {
			if(Rule.c.get(entity) != null) {
				if(Rule.c.get(entity).remove(player)) {
					
					for(Player p : Bukkit.getOnlinePlayers()) {
						AdvManager.set(p, 258, 0 , "§f§l"+player.getName() +" >>§4§l delete §f§l " + entity.getName());
					}
					Rule.c.get(player).kill();
					Rule.playerinfo.get(player).addcradit(5,entity.getName()+" "+Main.GetText("main:msg104"));
					ARSystem.Death((Player)entity,player);
				}
			}
		} else {
			if(((LivingEntity)entity).getMaxHealth() > 500) {
				((LivingEntity)entity).damage(((LivingEntity)entity).getMaxHealth()/2.5 + 250);
			} else {
				((LivingEntity)entity).setNoDamageTicks(0);
				((LivingEntity)entity).damage(((LivingEntity)entity).getMaxHealth());
			}
		}
	}
	
	static public void death(Entity entity,Entity player) {
		Holo.create(entity.getLocation(),"§6§l<< Kill >>",100,new Vector(0,0.01,0));
		if(entity instanceof Player) {
			if(Rule.c.get(player) != null) {
				Rule.c.get(player).kill();
				Rule.playerinfo.get(player).addcradit(5,entity.getName()+" "+Main.GetText("main:msg104"));
			}
			for(Player p : Bukkit.getOnlinePlayers()) {
				AdvManager.set(p, 267, 0 , "§f§l"+player.getName() +" >>§c§l Kill §f§l " + entity.getName());
			}
			ARSystem.Death((Player)entity,player);
		} else {
			((LivingEntity)entity).setHealth(0);
		}
	}

	public static void quit(LivingEntity entity) {
		Holo.create(entity.getLocation(),"§d§l<< Quit >>",100,new Vector(0,0.01,0));
		if(Rule.c.get(entity) != null) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				AdvManager.set(p, 258, 0 , "§d§l Quit §f§l " + entity.getName());
			}
			ARSystem.Death((Player)entity,entity);
		} else {
			if(!(entity instanceof Player)) entity.setHealth(0);
		}
	}

	public static void win(Player player) {
		int number = Rule.c.get(player).getCode();
		Rule.Var.addInt(player.getName()+".c"+(number%1000)+"Win",1);
		Rule.Var.addInt("ARSystem.c"+(number%1000)+"Win",1);
		String name = "§e§l[No."+(number%1000)+"]§b"+Main.GetText("c"+number+":name1")+" "+Main.GetText("c"+number+":name2");
		win(player.getName());
	}
	
	public static void win(String str) {
		ARSystem.RandomPlayer().performCommand("c removeall");
		if(Rule.buffmanager != null) Rule.buffmanager.clear();
		Rule.buffmanager = new BuffManager();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle("§a§l『§e§lVictory§a§l』",str ,40,20,40);
			MSUtil.resetbuff(p);
			p.setGameMode(GameMode.ADVENTURE);
			p.setMaxHealth(20);
			p.setHealth(20);
		}
		ARSystem.playSoundAll("0win");
		ARSystem.killall();
		ARSystem.AniRandomSkill = null;
		Rule.c.clear();
		Map.loby();
		if(aliveblock.Main.Aliveblock != null) {
			for(ABlock block : aliveblock.Main.Aliveblock) {
				block.removeBlock(5);
			}
			aliveblock.Main.Aliveblock.clear();
		}
	}

	public static void TimeLoop(LivingEntity target) {
		if(Rule.c.get(target) == null) ARSystem.giveBuff(target, new Timeshock(target), 200);
		int number = Rule.c.get(target).number;
		if(number == 1084) return;
		if(number == 1056) return;
		if(number == 1024) return;
		if(number == 2030) return;
		
		if(number == 30) {
			Rule.c.put((Player) target, new c3002siro((Player) target, Rule.gamerule, null));
			return;
		}
		if(number == 84) {
			Rule.c.put((Player) target, new c8400subi((Player) target, Rule.gamerule, null));
			return;
		}
		if(number == 24) {
			Rule.c.put((Player) target, new c2400sinobu((Player) target, Rule.gamerule, null));
		}
		if(number == 56) {
			Rule.c.put((Player) target, new c5600enju((Player) target, Rule.gamerule, null));
		}
		if(number > 999) {
			Rule.c.put((Player) target, GetChar.get((Player) target, Rule.gamerule, ""+(number%1000)));
		} else {
			for(int i = 0; i < 10; i++) Rule.c.get(target).cooldown[i] = 3;
		}
		
	}
}
