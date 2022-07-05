package c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import event.Skill;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c29guren extends c00main{
	int ticks = 0;
	int mana = 1865;
	int type = 1;
	int cast = 0;
	boolean arukana = false;
	int count = 0;
	
	public c29guren(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 29;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		mana = (int) f;
	}
	
	@Override
	public boolean skill1() {
		if(arukana) {
			cooldown[1] = 0;
			return false;
		}
		if(mana >= 500) {
			mana-=500;
			ARSystem.giveBuff(player, new Stun(player), 80);
			ARSystem.giveBuff(player, new Silence(player), 80);
			skill("c29_s1");
		}
		return true;
	}
	@Override
	public boolean skill2() {
		type++;
		if(type == 4) {
			type = 1;
		}
		player.sendTitle(Main.GetText("c29:t"+type), Main.GetText("c29:t1") + " " + Main.GetText("c29:t2") + " " + Main.GetText("c29:t3"));
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(arukana) {
			arukana = false;
		} else {
			arukana = true;
			skill("c29_s3");
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(arukana) {
			cooldown[4] = 0;
			return false;
		}
		if(mana >= 800 -(skillmult+sskillmult)) {
			if(ARSystem.gameMode != modes.LOBOTOMY) {
				ARSystem.giveBuff(player, new Stun(player), (int) ((4 - (skillmult+sskillmult))*20));
				ARSystem.giveBuff(player, new Silence(player), (int) ((4 - (skillmult+sskillmult))*20));
			}
			mana-=800-(skillmult+sskillmult);
			cast++;
			if(cast == 7) {
				spskillon();
			}
			if(cast > 7&&!spben) {
				spskillen();
				cast = 0;
				mana = 0;
				player.setHealth(1);
				if(ARSystem.gameMode == modes.LOBOTOMY) ARSystem.heal(player, 1000);
				ARSystem.playSound((Entity)player,"c29sp8");
				skill("c29_sp");
			}
			else {
				ARSystem.playSound((Entity)player,"c29sp"+cast);
			}
			skill("c29_s4");
		}
		return true;
	}
	
	

	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c29:ps")+ "]&f : "+ mana + "/1865");
			
		}
		if(ticks%40==0) {
			if(ARSystem.gameMode == modes.LOBOTOMY) mana+=200;
			if(mana < 1865) {
				if(!ARSystem.gameMode2) mana += 182 *(skillmult+sskillmult);
				
				mana += 121 *(skillmult+sskillmult);
			}
			if(mana > 1865) {
				mana = 1865;
			}
		}
		
		if(arukana && mana > 10) {
			mana -= 10;
			count++;
			if(count >= 400) {
				Rule.playerinfo.get(player).tropy(29,1);
			}
		} else if(arukana) {
			arukana = false;
		}
		
		if(arukana) {
			for(Entity e : ARSystem.box(player, new Vector(10, 8, 10),box.TARGET)) {
				ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 4);
			}
		}
		ticks++;
		
		ticks %= 40;
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(type == 1) {
				e.setDamage(e.getDamage() + 1);
				ARSystem.playSound(e.getEntity(),"0attack");
				Vector lasts = AMath.Vector(player.getLocation().clone().toVector().subtract(e.getEntity().getLocation().toVector()).normalize());
				delay(new Runnable() { Vector last = lasts; @Override public void run() {((LivingEntity)e.getEntity()).setVelocity(last.multiply(-2).setY(0));}}, 1);
				
				
			}
			if(type == 2) {
				ARSystem.playSound(e.getEntity(),"0attack4");
				e.setDamage(e.getDamage() + 3);
				ARSystem.spellCast(player, e.getEntity(), "c29_s2_1");
			}
			if(type == 3) {
				e.setDamage(e.getDamage() + 1);
				ARSystem.playSound(e.getEntity(),"0attack3");
				if(((LivingEntity)e.getEntity()).getNoDamageTicks() < 6) ((LivingEntity)e.getEntity()).setNoDamageTicks(0);
				delay(new Runnable() { @Override public void run() {((LivingEntity)e.getEntity()).setVelocity(new Vector(0, 0.5, 0));}}, 1);
			}
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*0.5);
		}
		return true;
	}
}
