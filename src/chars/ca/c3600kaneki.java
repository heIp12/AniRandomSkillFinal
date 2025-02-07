package chars.ca;

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
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c3600kaneki extends c00main{
	int s1t = 0;
	int s1 = 0;
	int t = 0;
	
	boolean sp = false;
	
	int luck = 10000;
	@Override
	public void setStack(float f) {
		luck = (int)f;
	}
	
	public c3600kaneki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1036;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		s1++;
		s1t = 60;
		if(s1 > 1)ARSystem.playSound((Entity)player, "c1036s1"+(s1-1));
		else ARSystem.playSound((Entity)player, "c1036s1");
		
		ARSystem.giveBuff(player, new Silence(player), 20);
		player.setVelocity(player.getLocation().getDirection().multiply(2));
		
		delay(()->{
			ARSystem.giveBuff(player, new Stun(player), 10);
			skill("c1036_s1");
			ARSystem.playSound((Entity)player, "c1036a",1,0.5f);
			for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				en.setNoDamageTicks(0);
				en.damage(4,player);
				if(isps) {
					en.setNoDamageTicks(0);
					en.damage(3,player);
				}
				if(s1 == 3) ARSystem.giveBuff(en, new Stun(en), 40);
				ARSystem.giveBuff(en, new Silence(en), 20);
				Location lc = player.getLocation();
				lc.setPitch(0);
				en.teleport(en.getLocation().clone().add(lc.getDirection().multiply(Math.max(8-player.getLocation().distance(en.getLocation()),1f))));
			}
		},10);
		if(s1 >= 3) {
			s1 = 0;
			s1t = 0;
			cooldown[1] = setcooldown[1]*7;
		}
		return true;
	}
	Location s2l = player.getLocation();
	@Override
	public boolean skill2() {
		ARSystem.giveBuff(player, new Silence(player), 50);
		s2l = player.getLocation();
		s2l.setPitch(-25);
		skill("c1036_s2");
		skill("c1036_s2m");
		if(isps) skill("c1036_s2d2");
		else skill("c1036_s2d");
		delay(()->{
			player.setVelocity(s2l.getDirection().multiply(1.5f));
		},31);
		ARSystem.playSound((Entity)player, "c1036s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.giveBuff(player, new Silence(player), 30);
		ARSystem.giveBuff(player, new Stun(player), 30);
		ARSystem.playSound((Entity)player, "c1036s3");
		skill("c1036_s3");
		cooldown[1] = 0;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.giveBuff(player, new Silence(player), 40);
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.playSound((Entity)player, "c1036s");
		skill("c1036_s4");
		return true;
	}

	@Override
	public boolean tick() {
		if(!isps && AMath.random(luck) == 1) {
			spskillon();
			spskillen();
			ARSystem.giveBuff(player, new Silence(player), 50);
			ARSystem.giveBuff(player, new Stun(player), 50);
			s1 = 0;
			s1t = 0;
			cooldown[1] = cooldown[2] = cooldown[3] = cooldown[4] = 0;
			ARSystem.spellCast(player,player, "bload");
			for(int i =0; i< 5; i++)delay(()->{ARSystem.spellCast(player,player, "bload");},20+i*4);
			ARSystem.playSoundAll("c1036s4");
			skill("c1036_sp");
		}
		if(s1t > 0) {
			s1t--;
			if(s1t <=0) {
				s1 = 0;
				cooldown[1] = setcooldown[1]*7;
			}
		}
		if(t <= 0) {
			t = 1;
			skill("c1036_p");
		}
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 20);
			ARSystem.giveBuff(target, new Silence(target), 20);
			ARSystem.playSound(target, "c1036a");
			for(int i =0; i < 10; i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					ARSystem.heal(player, 3f);
					target.damage(1f,player);
				},i*2);
			}
		}
		if(n.equals("2")) {
			ARSystem.giveBuff(target, new Stun(target), 4);
			target.setNoDamageTicks(0);
			target.damage(0.75,player);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) e.setDamage(e.getDamage() * 0.8f);
			ARSystem.spellCast(player, e.getEntity(), "bload2");
			ARSystem.heal(player,e.getDamage()/2);
		} else {
			if(Rule.buffmanager.selectBuffType(player, BuffType.SILENCE).size() > 0) {
				e.setDamage(e.getDamage()*0.2f);
			}
		}
		return true;
	}
}
