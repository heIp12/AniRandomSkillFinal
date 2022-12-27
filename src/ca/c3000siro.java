package ca;

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
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c3000siro extends c00main{
	int stack = 0;
	int tick = 4;
	int tick2 = 0;
	int tick3 = 0;
	int time = 0;
	int damage = 3;
	double mul = 1.2;
	
	HashMap<Entity,Integer> dot;
	
	int jin = 0;
	
	boolean sp = false;
	public c3000siro(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1030;
		load();
		text();
	}

	
	@Override
	public boolean skill1() {
		skill("c1030_s1");
		st(AMath.random(4));
		return true;
	}
	
	@Override
	public boolean skill2() {
		tick2 = 120;
		skill("c1030_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1030_s3");
		cooldown[1] = cooldown[2] = 0;
		return true;
	}
	
	public void st(int i) {
		if(stack+i < 0) i = -stack;
		if(stack+i > 11) i = 11-stack;
		
		if(i > 0) {
			for(int j = 0; j < i;j++) {
				skill("c1030_si"+ (1+stack++));
			}
			if(AMath.random(100) <= 20) {
				jin++;
				damage++;
			}
		} else {
			for(int j = 0; j < -i;j++) {
				skill("c1030_stop"+ (stack--));
			}
		}
	}
	
	public void sp() {
		ARSystem.giveBuff(player, new TimeStop(player), 300);
		ARSystem.giveBuff(player, new Stun(player), 20);
		for(Entity e : ARSystem.box(player, new Vector(15,5,15),box.TARGET)) {
			ARSystem.giveBuff((LivingEntity) e, new TimeStop((LivingEntity) e), 300);
		}
		st(-11);
		skill("c1030_sp");
		delay(new Runnable() {
			public void run() {
				dot = new HashMap<Entity,Integer>();
				for(Entity e : ARSystem.box(player, new Vector(15,15,15),box.TARGET)) {
					if((e instanceof LivingEntity)) {
						LivingEntity entity = (LivingEntity) e;
						entity.damage(entity.getMaxHealth()/2,player);
						dot.put(entity, 10);
					}
				}
				st(11);
			}
		}, 320);
	}

	public boolean tick() {
		if(tk%4==0 && player.isSneaking() && stack > 0) {
			if(cooldown[3] > 0) {
				skill("c1030_pp1");
			} else {
				skill("c1030_pp0");
			}
			skill("c1030_p"+AMath.random(3));
			ARSystem.playSound((Entity)player,"0katana"+(AMath.random(3)+1));
			st(-1);
		}
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c1030:ps")+ "]&f : "+ stack + "/ 11");
			scoreBoardText.add("&c ["+Main.GetText("c1030:t6")+ "]&f : "+ (30+5*stack) + "% &cx"+mul);
			if(jin > 0) {
				scoreBoardText.add("&c ["+Main.GetText("c1030:t7")+ "]&f : "+ jin);
			}

		}
		if(tick2>0) {
			mul = 2;
		} else {
			mul = 1.2;
		}
		
		if(time%60 ==0 && dot != null && !dot.isEmpty()) {
			for(Entity e : dot.keySet()) {
				if(dot.get(e) > 0) {
					dot.put(e, dot.get(e)-1);
					((LivingEntity)e).damage(2,player);
				}
			}
		}
		time++;
		time=time%60;
		if(tick>0) tick--;
		if(tick2>0) tick2--;
		return true;
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			double damage = this.damage;
			if(AMath.random(100) < 30 + (5*stack)) {
				damage*=mul;
				if(AMath.random(100) < 5 * jin) {
					if(skillCooldown(0)) {
						spskillon();
						spskillen();
						sp();
					}
				}
			}
			target.setNoDamageTicks(0);
			target.damage(damage,player);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
			
		} else {

		}
		return true;
	}
}
