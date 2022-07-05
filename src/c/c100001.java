package c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import manager.AdvManager;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c100001 extends c00main{
	public c100001(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 100001;
		load();
		text();
		
		Rule.team.teamJoin("buri", player);
	}
	

	@Override
	public boolean skill1() {
		ARSystem.giveBuff(player, new Silence(player), 10);
		ARSystem.giveBuff(player, new Stun(player), 10);
		skill("c100001_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c100001_s2");
		ARSystem.potion(player, 1, 10, 10);
		ARSystem.potion(player, 8, 5, 5);
		return true;
	}

	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			delay(new Runnable() {
				public void run() {
					Rule.c.put(p, new c100001(p, plugin, null));
					
					boolean all = true;
					for(Player play :Rule.c.keySet()) {
						if(Rule.c.get(play).getCode() != 100001) {
							all = false;
						}
					}
					if(all) {
						for(Player play : Bukkit.getOnlinePlayers()) {
							Rule.playerinfo.get(play).addcradit(8,Main.GetText("main:msg103"));
							play.sendTitle(" Win ", Main.GetText("main:msg42"),40,20,40);
						}
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								ARSystem.GameStop();
							}
						}, 40);
					}
				}
			}, 20);
		}
	}
	
	public void ps(){
		ARSystem.giveBuff(player, new TimeStop(player), 100);
		Rule.buffmanager.selectBuffTime(player, "silence",0);
		Rule.buffmanager.selectBuffTime(player, "stun",0);
		skillmult += 0.1;
		Rule.buffmanager.selectBuffAddValue(player, "buffac",0.3f);
		skill("c100001_p");
		player.setMaxHealth(player.getMaxHealth()+4);
		player.setHealth(player.getMaxHealth());
	}

	@Override
	public boolean remove(Entity caster) {
		if(ARSystem.AniRandomSkill != null) {
			ps();
			return false;
		}
		return true;
	}

	public double c() {
		int i = 0;
		int j = 0;
		double db = 0;
		
		for(Player play :Rule.c.keySet()) {
			if(Rule.c.get(play).getCode() == 100001) {
				i++;
			}
		}
		db = Rule.c.size()/(i*2);
		if(db > 5) db = 5;
		if(db < 0.4) db = 0.4;
		return db;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * c());
		} else {
			if(player.getHealth()- e.getDamage() < 1 && ARSystem.AniRandomSkill != null) {
				e.setCancelled(true);
				ps();
				return false;
			}
		}
		return true;
	}
}
