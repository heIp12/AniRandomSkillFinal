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
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c5500yaya extends c00main{
	boolean sk1a = false;
	int sk1 = 0;
	int sk2 = 0;
	int sk22 = 0;
	
	Vector sk2v;
	int sk2t = 0;
	List<LivingEntity> tg;
	
	LivingEntity target;
	float sp1 = 0;
	float sp2 = 0;
	int sp3 = 2;
	
	int sph = 0;
	
	public c5500yaya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1055;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		if(sk1 <= 0) {
			skill("c1055_s1");
			ARSystem.potion(player, 1, 200, 4);
			ARSystem.potion(player, 8, 200, 4);
			sk1 = 200;
			ARSystem.playSound((Entity)player, "c55s4");
			sk1a = true;
			cooldown[1] = 0;
		} else {
			ARSystem.playSound((Entity)player, "c55s1");
			cooldown[1] = setcooldown[1] - sk1*0.05f;
			player.setVelocity(new Vector(0,1.5,0));
			delay(()->{
				player.setVelocity(new Vector(0,-5,0));
				delay(()->{
					skill("c1055_s1-2");
					for(Entity e : ARSystem.box(player, new Vector(8,2,8),box.TARGET)) {
						LivingEntity en = (LivingEntity)e;
						en.setNoDamageTicks(0);
						en.damage(12,player);
					}
				},5);
			},10);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(sk2 <= 0) {
			sk2=100;
			skill("c1055_s2-1");
			ARSystem.playSound((Entity)player, "c55s3");
			for(Entity e : ARSystem.box(player, new Vector(8,8,8),box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				ARSystem.giveBuff(en, new Silence(en), 100);
				ARSystem.potion(en, 2, 60, 3);
				}
			cooldown[2] = 0;
		} else {
			skill("c1055_s2-2");
			cooldown[2] = setcooldown[2] - sk2*0.05f;
			ARSystem.playSound((Entity)player, "c55s1");
			sk2v = player.getLocation().getDirection();
			sk2t = 20;
			tg = new ArrayList<>();
		}
		return true;
	}


	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "silence")) {
			Rule.buffmanager.selectBuffTime(player, "silence",0);
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sp1 > 0 && sp2 > 0) {
			player.sendTitle("§e[ §c§l"+sp3+" §e]",""+ AMath.round(sp1*0.05, 2),0,20,0);
			sp1--;
			sp2--;
			if(sp1 <=0 || sp2 <= 0) {
				sp1 = 0;
				sp2 = 0;
				for(Player p : Bukkit.getOnlinePlayers()) p.stopSound("c55sp2");
			}
		}
		if(tk%20 == 0) {
			ARSystem.heal(player, 1);
		}
		if(sk1 > 0) {
			sk1--;
			if(sk1 <= 0) {
				sk1a = false;
				cooldown[1] = setcooldown[1] - 10;
			}
		}
		if(sk2 > 0) {
			sk2--;
			if(sk2 <= 0) {
				cooldown[1] = setcooldown[1] - 10;
			}
		}
		if(sk2t > 0) {
			sk2t--;
			player.setVelocity(sk2v);
			for(Entity e : ARSystem.box(player, new Vector(4,4,4),box.TARGET)) {
				if(!tg.contains(e)) {
					tg.add((LivingEntity)e);
				}
			}
			for(LivingEntity e : tg) {
				e.teleport(player);
			}
			if(sk2t <= 0) {
				skill("c1055_s2-3");
				for(Entity e : ARSystem.box(player, new Vector(8,4,8),box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(8,player);
				}
			}
		}
		return true;
	}
	
	
	@Override
	public boolean key(PlayerItemHeldEvent e) {
		if(sp1 > 0 && sp2 > 0) {
			int i = 1+e.getNewSlot();
			if(sp3 == i) {
				sp1 = 10;
				int o = sp3;
				while(o == sp3) sp3 = AMath.random(2);
				target.setNoDamageTicks(0);
				target.damage(1,player);
				sph++;
				Holo.create(target.getLocation(),"§c§l<< "+sph + " Hit!! >>", 100, new Vector(0, 0.01, 0));
				Location ploc = player.getLocation();
				Location loc = ULocal.lookAt(target.getLocation(),ploc);
				loc = loc.add(loc.getDirection().multiply(1.5));
				loc = ULocal.lookAt(loc, target.getLocation());
				player.teleport(loc);
				ARSystem.spellCast(player, target, "c1055_sp1");
				if(sph >= 20) {
					ARSystem.spellCast(player, target, "c1055_sp2");
					target.setNoDamageTicks(0);
					target.damage(1,player);
				}
				if(sph >= 50) {
					ARSystem.spellCast(player, target, "c1055_sp3");
					target.setNoDamageTicks(0);
					target.damage(3,player);
				}
				if(sph >= 111) {
					if(Rule.c.get(target) != null) Rule.c.put((Player)target, new c000humen((Player)target, plugin, null));
					Skill.remove(target, player);
					sp3 = 0;
				}
				ARSystem.giveBuff(target, new Silence(target), 5);
				ARSystem.giveBuff(target, new Noattack(target), 5);
				ARSystem.giveBuff(player, new Nodamage(player), 5);
				ARSystem.giveBuff(player, new Stun(player), 5);
				target.setVelocity(player.getLocation().getDirection().multiply(0.04));
			} else if(i == 8) {
				return false;
			} else {
				for(Player p : Bukkit.getOnlinePlayers()) p.stopSound("c55sp2");
				sp1 = 0;
				sp2 = 0;
			}
			return true;
		} else {
			return super.key(e);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk1 > 0 && sk2 > 0 && e.getDamage() <= 1&& skillCooldown(0)) {
				spskillon();
				spskillen();
				sph = 1;
				target = (LivingEntity)e.getEntity();
				sp1 = 10;
				sp2 = 200;
				sp3 = 1;
				target.setNoDamageTicks(0);
				target.damage(1,player);
				Holo.create(target.getLocation(),"§c"+sph + " Hit!!", 50, new Vector(0, 0.005, 0));
				ARSystem.playSoundAll("c55sp2");
			} else if((sp1 <= 0|| sp2 <= 0) && sk1a && e.getDamage() <= 1) {
				sk1a = false;
				e.setDamage(e.getDamage() + 8);
				ARSystem.giveBuff((LivingEntity)e.getEntity(), new Stun((LivingEntity)e.getEntity()), 60);
				ARSystem.spellCast(player, e.getEntity(), "c1055_s1-3");
			}
		} else {
			
		}
		return true;
	}
}
