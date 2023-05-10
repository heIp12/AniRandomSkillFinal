package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c35miruk extends c00main{
	boolean s1 = false;
	int time = 0;
	int timer;
	int count = 0;
	Location loc = null;
	
	public c35miruk(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 35;
		load();
		text();
		loc = player.getLocation();
	}
	

	@Override
	public boolean skill1() {
		if(player.getMaxHealth() > 1 || isps) {
			s1 = !s1;
			if(s1) ARSystem.playSound((Entity)player, "c35s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(s1) {
			cooldown[2] = 0;
			return false;
		}
		skill("c35_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(s1) {
			cooldown[3] = 0;
			return false;
		}
		ARSystem.giveBuff(player, new Nodamage(player), 100);
		for(Entity e : ARSystem.box(player, new Vector(12,8,12), box.ALL)) {
			ARSystem.giveBuff((LivingEntity) e, new Nodamage((LivingEntity) e), 100);
		}
		skill("c35_s3");
		return true;
	}
	
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		boolean is = false;
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				String s = Main.GetText("c"+Rule.c.get(e).getCode()+":tag");
				if(s.indexOf("tg1") != -1) {
					is = true;
					break;
				}
			}
		}
		
		if(is) {
			ARSystem.playSound((Entity)player, "c35db2");
		} else {
			ARSystem.playSound((Entity)player, "c35db1");
		}
		
		return true;
	}
	
	@Override
	public boolean tick() {
		if(s1) {
			if(player.getMaxHealth() > 1 || isps) {
				if(!isps) {
					if(player.getHealth() <= 2) {
						if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 20) {
							spskillen();
							spskillon();
							player.setMaxHealth(30);
							player.setHealth(30);
							s1 = false;
						}
					}
					double hp = player.getHealth();
					player.setMaxHealth(player.getMaxHealth()-0.4);
					if(hp < player.getMaxHealth()) player.setHealth(hp);
				} else {
					ARSystem.giveBuff(player, new Stun(player), 10);
				}
				List<Entity> el = ARSystem.box(player, new Vector(1,1,1),box.TARGET);
				for(Entity e : el) {
					if(e != player) {
						if(!isps) { 
							e.teleport(e.getLocation().add(0,-100,0));
						} else {
							if(tk%4 == 1) {
								((LivingEntity)e).setNoDamageTicks(0);
								((LivingEntity)e).damage(1,player);
								count++;
								if(count >= 30) {
									Rule.playerinfo.get(player).tropy(35,1);
								}
							}
						}
					}
				}
				skill("c35_p");
			}
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			e.setDamage(e.getDamage()*0.8);
		}
		return true;
	}
}
