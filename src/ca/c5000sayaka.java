package ca;

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
import buff.Timeshock;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c5000sayaka extends c00main{

	public c5000sayaka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1050;
		load();
		text();
		ARSystem.playSound(p, "c50sp");
	}
	
	@Override
	public boolean skill1() {
		skill("c50_s1");
		spawn(player.getLocation());
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.potion(player, 1, 60, 1);
		skill("c50_s2");
		delay(()->{spawn(player.getLocation());},10);
		return true;
	}
	
	@Override
	public boolean skill3() {
		for(int i = 0; i<3;i++) {
			spawn(player.getLocation());
		}
		return true;
	}

	@Override
	protected boolean skill9() {
		player.getWorld().playSound(player.getLocation(), "c50db", 1, 1);
		return true;
	}
	@Override
	public boolean tick() {
		if(tk%5==0) {
			ARSystem.heal(player, 1);
		}
		return true;
	}
	
	public void spawn(Location loc) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_"+(AMath.random(3)+1)+" 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
		} else {
			if(!(e.getDamager() instanceof Player) && ARSystem.gameMode != modes.LOBOTOMY) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
}
