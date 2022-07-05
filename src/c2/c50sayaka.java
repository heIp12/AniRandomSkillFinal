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

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c50sayaka extends c00main{
	int tick = 0;
	boolean ps = false;
	float damage = 0;
	private int ti = 0;

	public c50sayaka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 50;
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		damage = f;
	}

	@Override
	public boolean skill1() {
		skill("c50_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.potion(player, 1, 60, 1);
		skill("c50_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.heal(player, 2);
		skill("c50_s3");
		return true;
	}
	

	@Override
	public boolean tick() {
		tick++;
		if(tick%10==0) {
			ARSystem.heal(player, 1);
		}
		if(tk%20==0 && psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c50:sk0")+ "] : "+ damage +"/70");
		}
		if(damage >= 70 && !isps && ARSystem.gameMode != modes.LOBOTOMY) {
			spskillon();
			spskillen();
			Rule.playerinfo.get(player).tropy(50,1);
			ARSystem.playSound((Entity)player, "c50sp",(float) 1);
			ARSystem.giveBuff(player, new TimeStop(player), 120);
			delay(new Runnable() {
				@Override
				public void run() {
					player.setGameMode(GameMode.SPECTATOR);
					player.setMaxHealth(1000);
					player.setHealth(1000);
					Location locs = player.getLocation();
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_1 1 "+locs.getWorld().getName()+","+locs.getBlockX()+","+locs.getBlockY()+","+locs.getBlockZ());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_3 3 "+locs.getWorld().getName()+","+locs.getBlockX()+","+locs.getBlockY()+","+locs.getBlockZ());
					for(int i = 0; i < 10; i++) {
						Location loc = Map.randomLoc();
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_3 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
					}
					for(int i = 0; i < 40; i++) {
						Location loc = Map.randomLoc();
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_4 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
					}
					for(int i = 0; i < Rule.c.size()*2; i++) {
						Location loc = Map.randomLoc();
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_2 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
					}
				}
			},100);
		}
		if(tick%10==0) {
			if(isps) ti ++;
			
			if(isps && ti > 10) {
				boolean is = true;
				for(LivingEntity e : player.getWorld().getLivingEntities()) {
					if(e.getCustomName() != null ) {
						if(e.getCustomName().equals("Oktavia Von Seckendorff")) {
							is = false;
						}
					}
				}
				if(is) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m killall");
					Skill.remove(player, player);
				}
			}
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
		} else {

		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		damage += e.getDamage();
		return super.damage(e);
	}
}
