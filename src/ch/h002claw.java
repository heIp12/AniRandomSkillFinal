package ch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.MapVoid;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.Bgm;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Map;

public class h002claw extends c00main{
	int sk2 = 0;
	int sk3 = 0;
	List<LivingEntity> en;
	List<LivingEntity> target;
	
	public h002claw(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 998;
		load();
		text();
		if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[1] *= 0.3;
		if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[2] *= 0.5;
	}
	
	int skill1 = 0;
	int skill1_tick = 0;
	
	@Override
	public boolean skill1() {
		skill("claw_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "0select2", 0.1f);
		ARSystem.giveBuff(player, new Stun(player), 20);
		delay(()->{sk2 = 120;},20);
		
		en = new ArrayList<>();
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.giveBuff(player, new Stun(player), 160);
		ARSystem.giveBuff(player, new Silence(player), 160);
		sk3 = 160;
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			String s = Bgm.bgmcode;
			if(!s.equals("bc998") && Bgm.rep) {
				Bgm.setlockBgm("c998");
			}
		}
		if(sk2 > 0) {
			Location loc = player.getLocation();
			loc.setPitch(0);
			loc = loc.add(player.getLocation().getDirection().clone().multiply(2));
			if(!loc.clone().add(0,1,0).getBlock().isEmpty()) {
				sk2 = 0;
			} else {
				sk2--;
				player.setVelocity(player.getLocation().getDirection().setY(0.001).multiply(2));
				for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TARGET)) {
					if(!en.contains(e)) {
						en.add((LivingEntity) e);
						ARSystem.playSound(e, "0bload",(float) 0.2);
						ARSystem.spellCast(player, e, "claw_s2");
						((LivingEntity)e).setNoDamageTicks(0);
						((LivingEntity)e).damage(10,player);
					}
				}
			}
		}
		if(sk3 > 0) {
			sk3--;
			ARSystem.heal(player, 0.25);
			if(sk3%8==0) ARSystem.playSound((Entity)player, "0air",2);
		}
		
		if(player.getHealth()/ player.getMaxHealth() <= 0.05 && skillCooldown(0)){
			if(ARSystem.gameMode == modes.LOBOTOMY) {
				ARSystem.heal(player,40);
				spskillen();
				spskillon();
				Location loc = player.getLocation();
				loc.setPitch(25);
				player.teleport(loc);
				skill("claw_sp_ia1");
				ARSystem.addBuff(player, new TimeStop(player), 60);
				delay(()->{
					skill("claw_sp2");
					ARSystem.playSoundAll("0slash4");
					for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
						if(Rule.buffmanager.selectBuffType((LivingEntity) e, BuffType.HEADCC) != null) {
							for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
								buff.setTime(0);
							}
						}
						if(Rule.buffmanager.selectBuffType((LivingEntity) e, BuffType.CC) != null) {
							for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
								buff.setTime(0);
							}
						}
						((LivingEntity)e).setNoDamageTicks(0);
						((LivingEntity)e).damage(((LivingEntity)e).getMaxHealth()*0.4,player);
					}
				},61);
			} else {
				ARSystem.heal(player,40);
				target = new ArrayList<>();
				spskillen();
				spskillon();
				Location loc = player.getLocation();
				loc.setPitch(25);
				player.teleport(loc);
				skill("claw_sp_ia1");
				int time = Rule.c.size();
				
				ARSystem.addBuff(player, new TimeStop(player), 20+ time*4);
				delay(()->{
					int i = 0;
					for(Player p : Rule.c.keySet()) {
						i++;
						delay(()->{
							ARSystem.giveBuff(p, new Stun(p), 400);
							ARSystem.playSound((Entity)p, "0select2",2);
						},i*4);
					}
				},20);
				delay(()->{
					ARSystem.addBuff(player, new Silence(player), 200);
					ARSystem.addBuff(player, new Nodamage(player), 200);
					int i = 0;
					for(Player p : Rule.c.keySet()) {
						i++;
						delay(()->{
							for(LivingEntity e : target) {
								e.teleport(p);
								ARSystem.addBuff(e, new Silence(e), 20);
								ARSystem.addBuff(e, new Noattack(e), 20);
							}
							player.teleport(p);
							target.add(p);
							ARSystem.playSound((Entity)p, "0barray",2);
						},4*i);
					}
					delay(()->{
						for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.ALL)) {
							if(!target.contains(e)) {
								target.add((LivingEntity) e);
							}
						}
						for(int j =0; j<20;j++) {
							delay(()->{
								Map.Random(0,-1);
								Location locl = Map.randomLoc();
								player.teleport(locl);
								skill("claw_sp2");
								
								for(LivingEntity e : target) {
									e.teleport(locl.clone().add(new Vector(1.5-(AMath.random(30)*0.1),0,1.5-(AMath.random(30)*0.1))));
									ARSystem.addBuff(e, new Silence(e), 20);
									ARSystem.addBuff(e, new Noattack(e), 20);
									if(AMath.random(3) == 1 ) {
										ARSystem.playSoundAll("0katana2",(float) (1 + (AMath.random(10)*0.1)));
									} else if(AMath.random(2) == 1 ){
										ARSystem.playSoundAll("0katana",(float) (1 + (AMath.random(10)*0.1)));
									} else {
										ARSystem.playSoundAll("0katana6",(float) (1 + (AMath.random(10)*0.1)));
									}
								}
							},j*6);
						}
						int maps = Map.lastplay;
						if(ARSystem.gameMode == modes.LOBOTOMY) maps = 1004;
						int map = maps;
						delay(()->{
							Map.getMapinfo(map);
							Location locl = Map.randomLoc();
							
							player.teleport(locl);
							skill("claw_sp2");
	
							for(LivingEntity e : target) {
								if(e != player) {
									e.teleport(locl.clone().add(new Vector(1.5-(AMath.random(30)*0.1),0,1.5-(AMath.random(30)*0.1))));
									if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
										for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
											buff.setTime(0);
										}
									}
									if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
										for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
											buff.setTime(0);
										}
									}
									delay(()->{
										ARSystem.playSoundAll("0slash4");
										
										if(Rule.buffmanager.selectBuffType(e, BuffType.HEADCC) != null) {
											for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
												buff.setTime(0);
											}
										}
										if(Rule.buffmanager.selectBuffType(e, BuffType.CC) != null) {
											for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
												buff.setTime(0);
											}
										}
										e.setNoDamageTicks(0);
										e.damage(999,player);
									},5);
								}
							}
						},126);
					},4*time);
				},60+ time*4);
			}
		}
		
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*5);
			
			if((((LivingEntity)e.getEntity()).getHealth() - e.getDamage())/((LivingEntity)e.getEntity()).getMaxHealth() <= 0.1) {
				Skill.remove(e.getEntity(), player);
				ARSystem.playSound(e.getEntity(), "0slash3",(float) 0.2);
			}
		} else {
			if(e.getDamage()*0.7 > 5) {
				e.setDamage(5);
			} else {
				e.setDamage(e.getDamage()*0.7);
			}
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*0.5);
		}
		return true;
	}
}
