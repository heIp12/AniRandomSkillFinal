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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c64hisoka extends c00main{
	LivingEntity target1;
	LivingEntity target2;
	int tick = 0;
	int ty = 0;
	
	List<LivingEntity> target = new ArrayList<>();
	
	public c64hisoka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 64;
		load();
		text();
		c = this;
		target1 = target2 = player;
		if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[1] *= 0.4;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c64s1");
		skill("c64_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c64s2");
		skill("c64_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ty++;
		if(ty > 9) {
			Rule.playerinfo.get(player).tropy(64,1);
		}
		ARSystem.playSound((Entity)player, "c64s3");
		if(player.isSneaking()) {
			for(int i=0; i<20;i++) {
				delay(()->{
					target1.teleport(target1.getLocation().clone().add(target2.getLocation().clone().subtract(target1.getLocation()).multiply(0.1)));
				},i);
			}
			delay(()->{
				target1.teleport(target2.getLocation());
				if(target1 != player) {
					ARSystem.giveBuff(target1, new Stun(target1), 20);
					target1.damage(5,player);
				}
				if(target2 != player) {
					ARSystem.giveBuff(target2, new Stun(target2), 20);
					target2.damage(5,player);
				}
			},21);
		} else {
			for(int i=0; i<20;i++) {
				delay(()->{
					target2.teleport(target2.getLocation().clone().add(target1.getLocation().clone().subtract(target2.getLocation()).multiply(0.1)));
				},i);
			}
			delay(()->{
				target2.teleport(target1.getLocation());
				if(target1 != player) {
					ARSystem.giveBuff(target1, new Stun(target1), 20);
					target1.damage(5,player);
				}
				if(target2 != player) {
					ARSystem.giveBuff(target2, new Stun(target2), 20);
					target2.damage(5,player);
				}
			},21);
		}
		return true;
	}

	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c64s4");
		target2 = target1;
		target1 = player;
		ARSystem.heal(player, 2);
		if(ARSystem.gameMode == modes.LOBOTOMY) ARSystem.heal(player, 10);
		return true;
	}

	@Override
	public boolean tick() {
		tick++;
		if(isps) {
			if(tk%20 == 0 && tick%220 == 0) {
				cooldown[1] = cooldown[2] = cooldown[3] = cooldown[4] = 0;
			}
			if(tk%20 == 0) {
				for(Entity e : ARSystem.box(player, new Vector(12,12,12), box.TARGET)){
					if(!target.contains(e)) {
						target.add((LivingEntity) e);
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 1980);
					}
				}
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c64:ps")+ "] : 1 "+ target1.getName());
			scoreBoardText.add("&c ["+Main.GetText("c64:ps")+ "] : 2 "+ target2.getName());
			boolean s = true;
			for(Player p : Rule.c.keySet()) {
				if(p.getHealth()/p.getMaxHealth() > 0.5) {
					s = false;
				}
			}
		}
		
		if(!isps && tk%20 == 0) {
			for(LivingEntity e : target) {
				if(e.getHealth()/e.getMaxHealth() == 1 && Rule.c.get(e) != null) {
					spskillen();
					spskillon();
					ARSystem.playSound((Entity)player, "c64sp");
					target.clear();
					break;
				}
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(isps) {
			ARSystem.heal(player, 3);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
			if(target1 != e.getEntity() && e.getEntity() instanceof LivingEntity) {
				target2 = target1;
				target1 = (LivingEntity)e.getEntity();
			}
			if(isps) {
				ARSystem.addBuff((LivingEntity) e.getEntity(), new Panic((LivingEntity) e.getEntity()), 60);
			}
			if(!isps && ((LivingEntity)e.getEntity()).getHealth()/((LivingEntity)e.getEntity()).getMaxHealth() < 0.5) {
				target.add((LivingEntity) e.getEntity());
			}
		} else {
			if(Rule.buffmanager.isBuff((LivingEntity) e.getDamager(), "panic")) {
				e.setDamage(e.getDamage()*0.05);
			}
		}
		return true;
	}
	
}
