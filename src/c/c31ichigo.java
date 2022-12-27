package c;

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
import buff.TimeStop;
import event.Skill;
import manager.AdvManager;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c31ichigo extends c00main{
	boolean passive = true;
	int timer = 0;
	double damage;
	int count;

	LivingEntity Entity = null;
	
	boolean sp = false;
	public c31ichigo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 31;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c31_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.TARGET);
		Player p = null;
		String is = "";
		player.setFallDistance(0);
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c22byakuya) {
					is = "bykuya";
					p = (Player) e;
					break;
				}
			}
		}
		if(is.equals("bykuya")) {
			cooldown[2] = 0.1f;
			Location loc = p.getLocation();
			loc = Local.offset(loc,new Vector(-1,0,0));
			player.teleport(loc);
			ARSystem.playSound((Entity)player, "0miss");
		} else {
			skill("c31_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c31_s3");
		return true;
	}

	@Override
	public boolean tick() {
		if(timer > 0) {
			timer--;
			if(timer%2==0) {
				List<Entity> entity = ARSystem.box(player,new Vector(4,4,4),box.TARGET);
				if(entity.isEmpty()) {
					ARSystem.spellCast(player, ARSystem.boxSOne(player, new Vector(200,200,200),box.TARGET), "c31_p10");
				} else {
					skill("c31_p2");
				}
				if(timer == 0) {
					Rule.buffmanager.selectBuffTime(player, "nodamage",0);
					Skill.death(player, Entity);
				}
			}
		}
		if(tk%20==0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c31:sk0")+ "]&f" + damage);
		}
		return true;
	}
	
	void sp(Double d){
		damage+=d;
		if(damage >= 30 && !isps) {
			ARSystem.playSound((Entity)player,"c31sp");
			spskillen();
			spskillon();
			setcooldown[2] = 0;
			setcooldown[1] *= 0.5;
			skill("c31_e");
		}
	}
	
	@Override
	public void PlayerDeath(Player p, org.bukkit.entity.Entity e) {
		if(e == player && !passive) {
			count++;
			if(count >= 2) {
				Rule.playerinfo.get(player).tropy(31,1);
			}
		}
		if(e == player && timer > 0) {
			timer = 100;
			ARSystem.giveBuff(player, new Silence(player), 100);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.potion(player, 18, 60, 10);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				e.setDamage(e.getDamage()+2);
				delay(new Runnable() {
					@Override
					public void run() {
						((LivingEntity)e.getEntity()).setNoDamageTicks(0);
					}
				}, 1);
			}
			if(passive) sp(e.getDamage());
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.5);
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1 && passive) {
				passive = false;
				Entity = (LivingEntity) e.getDamager();
				ARSystem.giveBuff(player, new TimeStop(player), 20);
				timer = 100;
				ARSystem.giveBuff(player, new Silence(player), 100);
				ARSystem.giveBuff(player, new Nodamage(player), 100);
				skill("c31_p0");
				ARSystem.potion((LivingEntity) e.getEntity(), 18, 60, 10);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			else if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() <= 0){
				sp(e.getDamage());
			}
			if(timer > 0) {
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
