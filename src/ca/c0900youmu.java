package ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import c.c00main;
import types.modes;
import util.AMath;
import util.MSUtil;
import util.Map;

public class c0900youmu extends c00main{
	Location loc;
	int sk3count = 3;
	int tick = 0;
	int ticks = 0;

	boolean ps = true;
	boolean sk3 = false;
	
	public c0900youmu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1009;
		load();
		text();
		ARSystem.playSound(player, "c9select");
	}
	
	@Override
	public void setStack(float f) {
		sk3count = (int) f;
	}
	
	@Override
	public void info() {
		super.info();
		if(ARSystem.AniRandomSkill != null && s_kill == ARSystem.AniRandomSkill.player-1) {
			Rule.playerinfo.get(player).tropy(9,1);
		}
	}
	
	@Override
	public boolean skill1() {
		if(isps) skill("c"+number+"_s3");
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps) skill("c"+number+"_s3");
		cooldown[2] = 0;
		if(sk3count == 3) ticks = 100;

		if(sk3count > 0) {
			sk3count--;
			skill("c"+number+"_s2");
		}
		if(sk3count == 0){
			sk3count--;
			skill("c"+number+"_s2");
			sk3count = 3;
			cooldown[2] = setcooldown[2];
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}
	
	
	@Override
	protected boolean skill9() {
		player.getWorld().playSound(player.getLocation(), "c9db", 1, 1);
		return false;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player&& !isps) {
			spskillon();
			spskillen();
		}
	}

	@Override
	public boolean tick() {
		tick++;
		if(ticks > 0) {
			ticks--;
			if(ticks==0 && sk3count != 3) {
				sk3count = 3;
				cooldown[2] = setcooldown[2];
			}
		}
		
		if(tk%20 ==0) {
			scoreBoardText.add("&c ["+Main.GetText("c9:sk2")+ "]&f : " + sk3count);
		}

		tick%=200;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 6);
		} else {
			if(player.getHealth() - e.getDamage() <= 0 && ps) {
				player.setHealth(6);
				ps = false;
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				Map.playeTp(player);
				return false;
			}
		}
		return true;
	}
}
