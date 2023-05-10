package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
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

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.Stun;
import event.Skill;
import manager.AdvManager;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c40megumin extends c00main{
	int count = 5;
	double damage = 4;
	double range = 4;
	double sten = 0;
	
	float co[] = new float[4];
	
	int c = 0;
	public c40megumin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 40;
		load();
		text();
		for(int i = 0; i<4;i++) co[i] = setcooldown[i];
	}
	

	@Override
	public boolean skill1() {
		if(count > 0) {
			count--;
			sc();
			damage+=6;
			cooldown[1] = setcooldown[1];
			cooldown[2] = setcooldown[2];
			cooldown[3] = setcooldown[3];
			if(damage >= 30 && c == 5 &&skillCooldown(0)) {
				spskillon();
				spskillen();
				count+=5;
			}
			if(c > 5) damage*=1.75;
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	@Override
	public boolean skill2() {
		if(count > 0) {
			count--;
			sc();
			range+=5;
			cooldown[1] = setcooldown[1];
			cooldown[2] = setcooldown[2];
			cooldown[3] = setcooldown[3];
			if(c > 5) range*=1.75;
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(count > 0 && c < 5) {
			count--;
			sc();
			sten+=1;
			range+=2;
			damage+=3;
			cooldown[1] = setcooldown[1];
			cooldown[2] = setcooldown[2];
			cooldown[3] = setcooldown[3];
			for(int i=0;i<4;i++) setcooldown[i] -=1;
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	void sc(){
		c++;
		ARSystem.playSound((Entity)player,"c40e"+c);
		ARSystem.giveBuff(player, new Stun(player), 40);
		
		if(c <= 5) {
			skill("c40e1");
		} else {
			skill("c40e2");
		}
		if(c >= 10) {
			Rule.playerinfo.get(player).tropy(40,1);
		}
	}
	
	@Override
	public boolean skill4() {
		for(int i=0;i<4;i++) setcooldown[i] = co[i];
		cooldown[1] = setcooldown[1];
		cooldown[2] = setcooldown[2];
		cooldown[3] = setcooldown[3];
		
		count = 5;

		if(range > 20) {
			ARSystem.playSoundAll("c40s4");
		} else {
			ARSystem.playSound((Entity)player,"c40s4");
		}
		Location l = player.getLocation().clone().add(player.getLocation().clone().getDirection().multiply(range/2));
		
		ARSystem.spellLocCast(player, l, "c40_s4");
		if(c >= 2) delay(new Runnable()  {@Override public void run() { ARSystem.spellLocCast(player, l, "c40_s41"); }}, 10);
		if(c >= 4) delay(new Runnable()  {@Override public void run() { ARSystem.spellLocCast(player, l, "c40_s42"); }}, 20);
		if(c >= 5) delay(new Runnable()  {@Override public void run() { ARSystem.spellLocCast(player, l, "c40_s43"); }}, 30);
		if(c >= 7) delay(new Runnable()  {@Override public void run() { ARSystem.spellLocCast(player, l, "c40_s44"); }}, 40);
		if(c >= 10) delay(new Runnable()  {@Override public void run() { ARSystem.spellLocCast(player, l, "c40_s45"); }}, 50);
		
		delay(new Runnable() {
			double d = damage;
			double r = range;
			double cc = c;
			double st = sten;
			
			@Override
			public void run() {
				
				if(ARSystem.locEntity(l, new Vector(r,r,r),player) != null) {
					for(Entity e : ARSystem.locEntity(l, new Vector(r,r,r),player)) {
						if(cc > 5 && !ARSystem.isGameMode("lobotomy")) {
							if(((LivingEntity)e).getHealth() - d < 1) {
								Skill.remove(e, player);
							} else {
								((LivingEntity)e).damage(d,player);
							}
						} else {
							((LivingEntity)e).damage(d,player);
						}
					}
				}
				for(int i=0;i<d*5;i++) {
					l.getWorld().playSound(l, "c40e41" , (float) (r/4), 1);
					l.getWorld().playEffect(l.clone().add(r*0.5-AMath.random((int) r*10)*0.1,r*0.5-AMath.random((int) r*10)*0.1,r*0.5-AMath.random((int) r*10)*0.1), Effect.EXPLOSION_LARGE, 1);
				}
				ARSystem.giveBuff(player, new Stun(player), (int) ((cc*3 - (st*5))*20));
				ARSystem.giveBuff(player, new Silence(player),(int) ((cc*3 - (st*5))*20));

				if((cc*3 - (st*5))*20 > 0) ARSystem.playSound((Entity)player,"c40p");
			}
		}, 60);
		
		c = 0;
		damage = 4;
		range = 4;
		sten = 0;
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c40:t1")+ "] :" + damage);
			scoreBoardText.add("&e ["+Main.GetText("c40:t2")+ "] :" + range);
			if((c==5 && count > 0) || c>5) {
				scoreBoardText.add("&a ["+Main.GetText("c40:t3")+ "] :" + (10-c) + " / 10");
			} else {
				scoreBoardText.add("&a ["+Main.GetText("c40:t3")+ "] :" + (5-c) + " / 5");
			}
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage() * 2.5);
		} else {

		}
		return true;
	}
}
