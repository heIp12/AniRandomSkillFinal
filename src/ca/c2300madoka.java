package ca;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Buff;
import buff.Nodamage;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import util.AMath;
import util.MSUtil;

public class c2300madoka extends c00main{
	int ticks = 0;
	int stack = 0;
	double damage = 6.0;
	boolean start = false;

	
	public c2300madoka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1023;
		damage = 6.0;
		load();
		text();
		Rule.playerinfo.get(p).tropy(23, 2);
	}
	
	@Override
	public void setStack(float f) {
		damage = (int) f;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSoundAll("c1023s1");
		for(Player p : Rule.c.keySet()) {
			p.setMaxHealth(p.getHealth() + (p.getMaxHealth() - p.getHealth())/2);
			p.setHealth(p.getMaxHealth());
			if(p.getHealth() <= damage) {
				delect(p);
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSoundAll("c1023s2");
		for(Player p : Rule.c.keySet()) {
			for(int i=0;i<10;i++) Rule.c.get(p).cooldown[i] = 5;
			if(p.getHealth() <= damage) {
				delect(p);
			}
			p.setMaxHealth(p.getMaxHealth()-1);
			for(Buff buff : Rule.buffmanager.getBuffs(p).getBuff()) {
				if(buff.istype(BuffType.DEBUFF)) {
					buff.setTime(0);
				}
			}
		}
		cooldown[2] = setcooldown[2];
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(start) {
			for(Buff buff : Rule.buffmanager.getBuffs(player).getBuff()) {
				if(buff.getTime() > 0) damage+= buff.getTime() * 0.01f;
				if(buff.getValue() > 0) damage+= buff.getValue() * 0.01f;
				buff.setTime(0);
			}
		}
		return false;
	}
	
	public void delect(LivingEntity e) {
		ARSystem.playSound((Entity)e, "c1023p1");
		ARSystem.spellCast(player, e, "c1023_p");
		ARSystem.giveBuff(e, new TimeStop(e), 220);
		delay(()->{
			ARSystem.playSound((Entity)e, "c1023p3");
		},80);
		delay(()->{
			Skill.quit(e);
		},220);
	}
	
	@Override
	public boolean remove(Entity caster) {
		Holo.create(player.getLocation(),"§d§l☬ "+Main.GetText("c1023:p"),40,new Vector(0,0.05,0));
		return false;
	}
	
	@Override
	public boolean tick() {
		if(ticks > 0) ticks--;
		if(!start) start = true;
		if(tk%20 == 0) {
			if(Math.round((0.2*damage)*100) >= 50) {
				Rule.playerinfo.get(player).tropy(23,1);
			}
		}
		scoreBoardText.add("&c ["+Main.GetText("c1023:ps")+ "]&f : Quit Hp| "+ Math.round((damage)));
		
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 200 && !isps) {
			spskillon();
			spskillen();
			s_score+=1000000;
			for(Player e : Rule.c.keySet()) {
				ARSystem.playSoundAll("c1023p1");
				delay(()->{
					ARSystem.playSoundAll("c1023p2");
				},80);
				
				if(e != player) {
					ARSystem.spellCast(player, e, "c1023_p");
					ARSystem.giveBuff(e, new TimeStop(e), 220);
					delay(()->{
						Skill.quit(e);
					},220);
				}
			}
		}
		return true;
	}
	@Override
	public boolean damage(EntityDamageEvent e) {
		Holo.create(player.getLocation(),"§d§l☬ "+Main.GetText("c1023:p"),10,new Vector(0,0.05,0));
		e.setDamage(0);
		e.setCancelled(true);
		return true;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(((LivingEntity)e.getEntity()).getHealth() <= damage && e.getEntity() instanceof LivingEntity) {
				delect((LivingEntity) e.getEntity());
			}
			e.setDamage(0);
		} else {
			damage+= e.getDamage()*0.2f;
		}
		e.setCancelled(true);
		return true;
	}
}
