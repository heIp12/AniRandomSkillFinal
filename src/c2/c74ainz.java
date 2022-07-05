package c2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
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
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Dancing;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c74ainz extends c00main{
	int p = 0;
	int p2 = 0;
	int sk3 = 0;
	int rep = 1;
	int size = 1;
	int tick = 0;
	
	Location floc;
	HashMap<LivingEntity,Integer> attack = new HashMap<>();
	List<Entity> target;
	
	public c74ainz(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 74;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		for(int k = 0; k<rep;k++) {
			target = new ArrayList<>();
			Location loc = player.getLocation().clone();
			loc.add(loc.getDirection());
			for(int i=0;i<30*(size*size);i++) {
				loc.add(loc.getDirection());
				if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
					i = 100;
				} else {
					for (LivingEntity e : player.getWorld().getLivingEntities()) {
						if(e.getLocation().distance(loc) <= 1*(size*size) && !target.contains(e)) {
							target.add(e);
							if(ARSystem.isTarget(e, player)) {
								double damage = 3;
								ARSystem.playSound((Entity)e, "0bload");
								s_damage += damage;
								if(e.getHealth() - damage > 1) {
									e.setHealth(e.getHealth()-damage);
								} else {
									Rule.buffmanager.selectBuffValue(e, "barrier",0);
									e.damage(damage);
								}
							}
						}
					}
				}
			}
		}
		ARSystem.playSound((Entity)player, "c74s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c74s2");
		for(int k = 0; k<rep;k++) {
			Location loc = player.getLocation().clone();
			for(int i =0; i<16*(size*size); i++) {
				if(loc.clone().add(0,1,0).getBlock().isEmpty()) {
					loc.add(loc.getDirection().multiply(1));
				} else {
					i = 999;
				}
			}
			player.teleport(loc);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c74s3");
		for(int k = 0; k<rep;k++) {
				int size = 10*(this.size*this.size*this.size*this.size);
				
				for(Entity e : ARSystem.box(player, new Vector(size,size,size), box.TARGET)) {
					if(attack.get(e) == null) {
						attack.put((LivingEntity) e, 0);
					}
					attack.put((LivingEntity) e, attack.get(e)+1);
					if(attack.get(e) == 1) {
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 100);
						ARSystem.addBuff((LivingEntity) e, new Silence((LivingEntity) e), 40);
					}
					if(attack.get(e) == 2) {
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 100);
						ARSystem.addBuff((LivingEntity) e, new Dancing((LivingEntity) e), 100);
						ARSystem.addBuff((LivingEntity) e, new Silence((LivingEntity) e), 60);
					}
					if(attack.get(e) == 3) {
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 160);
						ARSystem.addBuff((LivingEntity) e, new Dancing((LivingEntity) e), 160);
						ARSystem.addBuff((LivingEntity) e, new Silence((LivingEntity) e), 100);
					}
					if(attack.get(e) == 4) {
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 260);
						ARSystem.addBuff((LivingEntity) e, new Dancing((LivingEntity) e), 260);
						ARSystem.addBuff((LivingEntity) e, new Silence((LivingEntity) e), 260);
					}
					if(attack.get(e) == 5) {
						Skill.remove(e,player);
					}
				}

		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		sk3 = AMath.random(3);
		ARSystem.playSound((Entity)player, "c74s4"+sk3);
		rep = size = 1;
		if(sk3 == 1) rep = 2;
		if(sk3 == 2) rep = 3;
		if(sk3 == 3) size = 2;
		return true;
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(p >= 200) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(p >= 100 && Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
				if(buff.getName().equals("panic")) {
					buff.setTime(0);
				}
			}
		}
		if(floc == null && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() <  5 && p == 0) {
			floc = player.getLocation();
			ARSystem.playSound((Entity)player, "c74p");
		}
		if(floc != null && floc.distance(player.getLocation()) < 0.3) {
			p++;
			if(p==400) {
				skillmult+=0.3;
			}
			if(p >= 600) {
				Rule.playerinfo.get(player).tropy(74,1);
			}
		} else if(floc != null) {
			floc = null;
			player.stopSound("c74p");
		}
		if(p2 > 0) {
			p2--;
		}
		if(tick%100 == 0) {
			List<Entity> l = ARSystem.box(player, new Vector(999,999,999), box.TARGET);
			if(l.size() >= 40 && skillCooldown(0)) {
				ARSystem.addBuff(player, new TimeStop(player), 200);
				int size = 2 + l.size()/50;
				ARSystem.playSoundAll("c74sp");
				for(Entity e : l) {
					if(!(e instanceof Player)) {
						ARSystem.addBuff((LivingEntity) e, new TimeStop((LivingEntity) e), 100);
						delay(()->{
							e.remove();
						},140);
					} else {
						((LivingEntity) e).setHealth(((LivingEntity) e).getHealth()/2);
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 600);
						ARSystem.addBuff((LivingEntity) e, new Silence((LivingEntity) e), 200);
					}
				}
				delay(()->{
					for(int i=0; i<size; i++) {
						Entity target = ARSystem.RandomPlayer(player);
						Location loc = target.getLocation();
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn dango 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
					}
				},200);
			}
		}
		tick++;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(p>=500) {
				e.setDamage(e.getDamage()*2);
			}
		} else {
			if(p>=300 && e.getDamage() <= 3) {
				e.setDamage(0);
			}
			if(p2 <= 0 && p>=600 && e.getDamage() > 0) {
				p2 = 60;
				e.setDamage(0);
			}
		}
		return true;
	}
	
}
