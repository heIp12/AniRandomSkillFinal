package event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import manager.AdvManager;
import manager.Holo;
import util.AMath;

public class Skill {
	static public void remove(Entity entity,Entity player) {
		if(Rule.c.get(player) == null) return;
		Holo.create(entity.getLocation(),"§c§l<< Delect >>",100,new Vector(0,0.01,0));
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
}
