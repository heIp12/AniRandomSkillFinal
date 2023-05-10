package chars.c2;

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
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c100kuroko extends c00main{
	int ps = 0;
	int ps2 = 0;
	int count = 0;
	Entity killer = null;
	public c100kuroko(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 100;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c100s1");
		skill("c100_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c100s1");
		List<Entity> target = ARSystem.box(player, new Vector(2,2,2), box.ALL);
		if(target.size() <= 0) {
			cooldown[2] = 0;
			return true;
		}
		for(Entity e : target) {
			if(player.isSneaking()) {
				e.teleport(e.getLocation().add(player.getLocation().getDirection().multiply(-15)).add(0,0.5,0));
			} else {
				e.teleport(e.getLocation().add(player.getLocation().getDirection().multiply(15)).add(0,0.5,0));
			}
			e.setVelocity(new Vector(0,0,0));
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(killer != null) {
			player.teleport(killer);
			ARSystem.giveBuff((LivingEntity) killer, new Silence((LivingEntity) killer), 60);
			ARSystem.giveBuff((LivingEntity) killer, new Stun((LivingEntity) killer), 60);
			killer = null;
			ARSystem.playSound((Entity)player, "c100db");
			count++;
			if(count <= 3) Rule.playerinfo.get(player).tropy(100,1);
		} else {
			if(player.isSneaking()) {
				player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(-10)).add(0,0.5,0));
			} else {
				player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(10)).add(0,0.5,0));
			}
			ARSystem.playSound((Entity)player, "c100s2");
		}
		player.setVelocity(new Vector(0,0,0));
		player.setFallDistance(0);
		return true;
	}
	
	@Override
	public boolean skill4() {		
		LivingEntity target = null;
		Location loc = player.getLocation().clone();
		for(int i=0;i<27;i++) {
			loc.add(loc.getDirection());
			for (LivingEntity e : player.getWorld().getLivingEntities()) {
				if(e.getLocation().distance(loc) <= 6 && e != player) {
					if(ARSystem.isTarget(e, player, box.TARGET)) target = e;
				}
			}
			if(target != null) i = 30;
		}
		if(target != null) {
			ARSystem.playSound((Entity)player, "c100p");
			Location ploc = player.getLocation();
			loc = ULocal.lookAt(target.getLocation(),ploc);
			loc = loc.add(loc.getDirection().multiply(1.5));
			loc = ULocal.lookAt(loc, target.getLocation());
			player.teleport(loc);
			loc = target.getLocation();
			target.damage(3,player);
			LivingEntity e = target;
			if(Rule.buffmanager.OnBuffTime(e, "stun")) {
				Rule.buffmanager.selectBuffTime(e, "stun",0);
			}
			delay(()->{
				Location el = e.getLocation().clone();
				e.setVelocity(player.getLocation().getDirection().multiply(3.5));
				delay(()->{
					if(e.getLocation().distance(el) <= 7) {
						if(Rule.c.get(e) != null && Rule.c.get(e).s_kill >=2 && skillCooldown(0)) {
							spskillon();
							spskillen();
							Location l = ploc;
							ARSystem.playSound((Entity)player, "c100s1");
							e.teleport(l);
							ARSystem.giveBuff(e, new Stun(e), 200);
							ARSystem.giveBuff(e, new Silence(e), 200);
							ARSystem.giveBuff(e, new Noattack(e), 200);
							ARSystem.playSound((Entity)player, "c100sp");
							for(int i=0;i<10;i++) {
								delay(()->{
									ARSystem.playSound((Entity)player, "c100s2");
									Location eloc = e.getLocation();
									eloc.setYaw(AMath.random(360));
									eloc.setPitch(-10+AMath.random(20));
									eloc = ULocal.offset(eloc, new Vector(2,0,0));
									eloc = ULocal.lookAt(eloc, e.getLocation());
									player.teleport(eloc);
									e.setNoDamageTicks(0);
									e.damage(1,player);
									ARSystem.spellLocCast(player, e.getLocation(), "c100_sk0");
								},10 + i*4);
							}
							delay(()->{
								ARSystem.playSound((Entity)player, "c100s1");
								e.teleport(e.getLocation().add(0,2,0));
								Location eloc = e.getLocation();
								eloc = ULocal.offset(eloc, new Vector(-2,0,0));
								player.teleport(eloc);
								ARSystem.spellLocCast(player, e.getLocation(), "c100_sk01");
							},50);
							
							for(int i=0;i<3;i++) {
								delay(()->{
									ARSystem.playSound((Entity)player, "c100s2");
									Location eloc = e.getLocation();
									eloc.setYaw(AMath.random(360));
									eloc.setPitch(-10+AMath.random(20));
									eloc = ULocal.offset(eloc, new Vector(2,0,0));
									eloc = ULocal.lookAt(eloc, e.getLocation());
									e.teleport(eloc);
									player.teleport(ULocal.offset(eloc,new Vector(-1,0,0)));
									e.setNoDamageTicks(0);
									e.damage(3,player);
								},60 + i*5);
							}
							for(int i=0;i<2;i++) {
								delay(()->{
									ARSystem.playSound((Entity)player, "c100s1");
									Location eloc = e.getLocation();
									eloc.setYaw(AMath.random(360));
									eloc.setPitch(-10+AMath.random(20));
									eloc = ULocal.offset(eloc, new Vector(0,-4,0));
									eloc = ULocal.lookAt(eloc, e.getLocation());
									e.teleport(eloc);
									player.teleport(ULocal.offset(eloc,new Vector(-1,0,0)));
									e.setNoDamageTicks(0);
									e.damage(4,player);
								},75 + i*5);
							}
							delay(()->{
								ARSystem.playSound((Entity)player, "c100s2");
								player.teleport(ULocal.lookAt(e.getLocation().add(0,2,0),e.getLocation()));
								player.setFallDistance(0);
							},85);
							delay(()->{
								ARSystem.playSound((Entity)player, "c100s1");
								player.teleport(ULocal.lookAt(e.getLocation().add(0,2,0),e.getLocation()));
								ARSystem.spellLocCast(player, e.getLocation(), "c100_sk02");
								e.setNoDamageTicks(0);
								e.damage(22,player);
								player.setFallDistance(0);
							},100);
						} else {
							e.setNoDamageTicks(0);
							e.damage(6,player);
							ARSystem.giveBuff(e, new Stun(e), 60);
							ARSystem.giveBuff(e, new Silence(e), 60);
						}
					}
				},6);
			},3);
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			if(player.isSneaking()) {
				ARSystem.giveBuff(target, new Stun(target), 20);
				target.damage(1.5,player);
			} else {
				target.damage(3,player);
			}
		}
	}
	
	@Override
	public boolean tick() {
		if(ps > 0) {
			ps--;
		}
		if(ps2 > 0) {
			ps2--;
			if(ps2 == 0) killer = null;
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e != player && p != player) {
			killer = e;
			ps2 = 20;
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 1 && e.getDamage() > 0) {
				Location loc = e.getEntity().getLocation();
				loc.setPitch(AMath.random(180)-90);
				loc.setYaw(AMath.random(360));
				Location lc = ULocal.lookAt(ULocal.offset(loc, new Vector(2,0,0)),e.getEntity().getLocation());
				ARSystem.spellLocCast(player, lc, "c100_ps");
			}
		} else {
			if(e.getDamage() <= 1 && ps <= 0) {
				ps = 10;
				e.setDamage(0);
				e.setCancelled(true);
				player.teleport(ULocal.lookAt(e.getDamager().getLocation().add(AMath.random(3)-2,0,AMath.random(3)-2),e.getDamager().getLocation()));
				
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c63micoto) {
					is = "micoto";
					break;
				}

			}
		}
		
		if(is.equals("micoto")) {
			ARSystem.playSound((Entity)player, "c100m");
		} else {
			ARSystem.playSound((Entity)player, "c100db");
		}
		return true;
	}
}
