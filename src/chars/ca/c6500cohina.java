package chars.ca;

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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Nodie;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c6500cohina extends c00main{
	int sk2 = 0;
	HashMap<LivingEntity,Location> targets = new HashMap<>();
	int sk4 = 0;
	
	public c6500cohina(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1065;
		load();
		text();
		c = this;
		ARSystem.playSound(p, "c65select");
	}


	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c65s1");
		skill("c1065_s1");
		for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(2,player);
		}
		delay(()->{player.setVelocity(player.getLocation().getDirection().setY(0).multiply(1.5));},2);
		delay(()->{
			skill("c1065_s1");
			for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
				player.setVelocity(new Vector(0,0,0));
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(3,player);
			}
		},6);
		if(isps) {
			delay(()->{player.setVelocity(player.getLocation().getDirection().setY(0).multiply(1.5));},10);
			delay(()->{
				skill("c1065_s1");
				for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
					player.setVelocity(new Vector(0,0,0));
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(3,player);
				}
			},14);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.giveBuff(player, new Stun(player), 60);
		ARSystem.giveBuff(player, new Silence(player), 60);
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.giveBuff(player, new Noattack(player), 30);
		targets.clear();
		sk2 = 40;
		ARSystem.playSound((Entity)player, "c65s2");
		for(Entity e : ARSystem.box(player, new Vector(13,5,13), box.TARGET)) {
			if(e instanceof Player) {
				((Player) e).sendTitle("Don't move", "");
			}
		}
		
		return true;
	}
	
	int s3 = 0;
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c65s4");
		player.setVelocity(player.getLocation().getDirection().multiply(2));
		targets.clear();
		delay(()->{
			s3 = 20;
			player.setGameMode(GameMode.SPECTATOR);
		},5);
		if(isps) {
			delay(()->{
				s3 = 10;
				player.setGameMode(GameMode.SPECTATOR);
			},s3+10);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(!targets.containsKey(target)) {
				targets.put(target, target.getLocation());
				target.damage(6,player);
			}
		}
	}

	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c65sp");
		ARSystem.giveBuff(player, new TimeStop(player), 40);
		sk4 = 100;
		if(isps) sk4 = 150;
		ARSystem.giveBuff(player, new Silence(player), sk4+20);
		
		skill("c1065_p2");
		return true;
	}

	@Override
	public boolean tick() {
		if(!isps && player.getHealth() / player.getMaxHealth() < 0.25f) {
			spskillon();
			spskillen();
			
		}
		if(s3 > 0) {
			s3--;
			if(player.isSneaking()) s3 = 0;
			if(s3 <= 0) {
				player.setGameMode(GameMode.ADVENTURE);
				skill("c1065_s3");
				player.setVelocity(player.getLocation().getDirection().multiply(2));
				ARSystem.playSound((Entity)player, "c65s3", 0.7f);
				delay(()->{
					for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
						if(e.getLocation().distance(player.getLocation()) <= 6) {
							((LivingEntity)e).setNoDamageTicks(0);
							ARSystem.spellCast(player, e, "0bload");
							((LivingEntity)e).damage(6,player);
						}
					}
				},10);
			}
		}
		if(sk2>0) {
			sk2--;
			if(sk2 == 20) {
				skill("c1065_sk2p");
				for(Entity e : ARSystem.box(player, new Vector(13,5,13), box.TARGET)) {
					targets.put((LivingEntity) e, e.getLocation());
				}
			}
			if(sk2 <= 20 && sk2 > 0) {
				for(LivingEntity e : targets.keySet()) {
					Location lc = targets.get(e);
					Location lcc = e.getLocation();
					if(lc.getY() > lcc.getY() || Math.abs(lc.getX()-lcc.getX()) > 0.1 || Math.abs(lc.getZ()-lcc.getZ()) > 0.1 || Math.abs(lc.getYaw()-lcc.getYaw()) > 1 || Math.abs(lc.getPitch()-lcc.getPitch()) > 1) {
						ARSystem.giveBuff(e, new Stun(e), 40);
						ARSystem.giveBuff(e, new Silence(e), 40);
						ARSystem.giveBuff(e, new Nodamage(e), 40);
					}
				}
			}
			if(sk2==0) {
				int i = 0;
				skill("c1065_p");
				for(LivingEntity e : targets.keySet()) {
					Location lc = targets.get(e);
					Location lcc = e.getLocation();
					if(lc.getY() > lcc.getY() || Math.abs(lc.getX()-lcc.getX()) > 0.1 || Math.abs(lc.getZ()-lcc.getZ()) > 0.1 || Math.abs(lc.getYaw()-lcc.getYaw()) > 1 || Math.abs(lc.getPitch()-lcc.getPitch()) > 1) {
						int size = i*10;
						ARSystem.giveBuff(e, new Stun(e), size);
						ARSystem.giveBuff(e, new Silence(e), size);
						ARSystem.giveBuff(e, new Nodamage(e), size);

						Rule.buffmanager.selectBuffAddTime(player, "nodamage", 10);
						Rule.buffmanager.selectBuffAddTime(player, "silence", 10);
						
						delay(()->{
							LivingEntity target = e;
							ARSystem.spellCast(player, target, "c1065_rt");
							delay(()->{
								ARSystem.spellCast(player, "c1065_s2e");
								ARSystem.playSound(target, "c65s3");

								Rule.buffmanager.selectBuffTime(target, "nodamage",0);
								target.setNoDamageTicks(0);
								target.damage(target.getHealth()*0.7,player);
							},10);
						},10*i);
						
						if(isps) {
							delay(()->{
								Rule.buffmanager.selectBuffAddTime(player, "nodamage", 10);
								Rule.buffmanager.selectBuffAddTime(player, "silence", 10);
								LivingEntity target = e;
								ARSystem.spellCast(player, target, "c1065_rt");
								delay(()->{
									ARSystem.spellCast(player, "c1065_s2e");
									ARSystem.playSound(target, "c65s3");

									Rule.buffmanager.selectBuffTime(target, "nodamage",0);
									target.setNoDamageTicks(0);
									target.damage(target.getHealth()*0.7,player);
								},10);
							},10*i + targets.size()*10 + 10);
						}
						i++;
					}
				}
				delay(()->{
					skill("c1065_p");
				},targets.keySet().size()*10);
			}
		}
		if(sk4 > 0) {
			sk4--;
			if(sk4%2== 1 || sk4 < 20) {
				ARSystem.giveBuff(player, new Nodie(player), 10);
				Location loc = player.getLocation();
				if(AMath.random(10) < 4) {
					loc.setYaw(AMath.random(360));
				} else {
					Entity e = ARSystem.boxSOne(player, new Vector(15,6,15), box.TARGET);
					if(e != null) {
						loc = ULocal.lookAt(loc, e.getLocation());
					} else {
						loc.setYaw(AMath.random(360));
					}
				}
				loc.setPitch(0);
				player.teleport(loc);
				if(AMath.random(10) <= 7) {
					player.setVelocity(player.getLocation().getDirection().multiply(4).setY(0));
					ARSystem.playSound((Entity)player, "c65s3", 1.5f);
				} else {
					player.setVelocity(player.getLocation().getDirection().multiply(2.5f));
					ARSystem.playSound((Entity)player, "c65s3", 1.8f);
				}
				skill("c1065_s4");
				for(Entity e : ARSystem.box(player, new Vector(5,4,5), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(2,player);
				}
			}
		}
		return true;
	}
	

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			ARSystem.heal(player, e.getDamage() * 0.33);
			LivingEntity tg = (LivingEntity) e.getEntity();
			if(tg.getHealth() - e.getDamage() < 1) {
				ARSystem.heal(player, tg.getMaxHealth()/2);
			}
		} else {
			if(isps) {
				((LivingEntity)e.getDamager()).damage(e.getDamage()*0.2,player);
				e.setDamage(e.getDamage()*0.8f);
			}
		}
		return true;
	}
}
