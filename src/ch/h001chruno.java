package ch;

import java.util.HashMap;

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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import c.c00main;
import event.Skill;
import manager.Bgm;
import types.box;
import types.modes;
import util.MSUtil;

public class h001chruno extends c00main{
	int ticks = 0;
	HashMap<Entity,Integer> fear = new HashMap<Entity,Integer>();
	Entity witch = null;
	
	public h001chruno(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 999;
		load();
		text();
		
		ARSystem.playSound((Entity)player, "c_chrunoselect");
	}
	
	int skill1 = 0;
	int skill1_tick = 0;
	
	@Override
	public boolean skill1() {
		skill1++;
		skill1_tick = 60;
		if(isps) {
			skill("chruno_s01-"+skill1);
			if(skill1 < 3) {
				cooldown[1] = 0.2f;
				if(ARSystem.gameMode == modes.LOBOTOMY) cooldown[1] = 0.1f;
				ARSystem.playSound((Entity)player, "c_chrunos3");
			} else {
				skill1 = 0;
				ARSystem.playSound((Entity)player, "c_chrunos4");
			}
		} else {		
			skill("chruno_s1-"+skill1);
			if(skill1 < 3) {
				cooldown[1] = 0.5f;
				ARSystem.playSound((Entity)player, "c_chrunos1");
			} else {
				skill1 = 0;
				ARSystem.playSound((Entity)player, "c_chrunos2");
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c_chrunos8");
		skill("chruno_s2");
		int count = 0;
		for(Entity e : ARSystem.box((Entity)player, new Vector(6,2,6),box.TARGET)) {
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(2,player);
			ARSystem.giveBuff(((LivingEntity)e), new Stun(((LivingEntity)e)), 60);
			ARSystem.giveBuff(((LivingEntity)e), new Silence(((LivingEntity)e)), 60);
			count++;
		}
		if(count >= 2 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c_chrunos6");
			skill("chruno");
			if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[1]*= 0.5f;
			if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[2]*= 0.5f;
			if(ARSystem.gameMode == modes.LOBOTOMY) setcooldown[3]*= 0.5f;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c_chrunos7");
		skill("chruno_s3");
		Rule.buffmanager.selectBuffValue(player, "barrier",8);

		if(isps) {
			ARSystem.giveBuff(player, new Nodamage(player), 50);
		}
		return true;
	}
	
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c_chrunos5");
		skill("chruno_s4");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			String s = Bgm.bgmcode;
			if(!s.equals("bc999") && Bgm.rep) {
				Bgm.setBgm("c999");
			}
		}
		if(skill1_tick > 0) {
			skill1_tick--;
			if(skill1_tick == 0) {
				skill1 = 0;
				cooldown[1] = setcooldown[1];
			}
		}
		
		return true;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c_chrunodb");
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() >= 2) {
				if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 3);
				LivingEntity entity = (LivingEntity) e.getEntity();
				if(entity.getPotionEffect(PotionEffectType.SLOW) != null && entity.getPotionEffect(PotionEffectType.SLOW).getDuration() > 0) {
					if(entity != null && !entity.isDead()) {
						entity.setNoDamageTicks(0);
						entity.damage(1,player);
						if(ARSystem.gameMode == modes.LOBOTOMY) {
							entity.setNoDamageTicks(0);
							entity.damage(1,player);
							entity.setNoDamageTicks(0);
							entity.damage(1,player);
						}
					}
				}
				ARSystem.potion(entity, 2, 20, 1);
			}
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.4);
		}
		return true;
	}
}
