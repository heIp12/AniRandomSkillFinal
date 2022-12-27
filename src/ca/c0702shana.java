package ca;

import org.bukkit.entity.Entity;
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
import buff.Silence;
import c.c00main;
import types.modes;
import util.AMath;
import util.MSUtil;

public class c0702shana extends c00main{
	boolean damage = false;
	
	public c0702shana(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2007;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound(player, "c2007s1");
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound(player, "c2007s2");
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound(player, "c2007s3");
		skill("c"+number+"_s3");
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%10 == 0 && player.getHealth() < player.getMaxHealth()) {
			double hp = player.getMaxHealth() - player.getHealth();
			hp = hp*0.2;
			if(hp < 0.5) hp = 0.5;
			ARSystem.heal(player, hp);
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && !isps && !damage) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c7select");
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				e.setDamage(e.getDamage() + (((LivingEntity)e.getEntity()).getHealth()*0.05*e.getDamage()));
			}
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 1.5);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.4);
			if(isps) e.setDamage(e.getDamage() * 0.7);
			damage = true;
		}
		return true;
	}
}
