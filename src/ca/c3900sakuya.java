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
import buff.Silence;
import buff.Stun;
import buff.Timeshock;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c3900sakuya extends c00main{
	int s1 = 0;
	int s2 = 0;
	public c3900sakuya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1039;
		load();
		text();
		ARSystem.playSound(player, "c39select");
	}
	

	@Override
	public boolean skill1() {
		s1++;
		if(s1 == 1) {
			skill("c1039_a1");
			ARSystem.playSound((Entity)player, "0sword");
			ARSystem.playSound((Entity)player, "c39a3");
		}
		if(s1 == 2) {
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,0.2)), "c1039_a2");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,0)), "c1039_a2");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,-0.2)), "c1039_a2");
			ARSystem.playSound((Entity)player, "0sword");
			ARSystem.playSound((Entity)player, "c39a3");
		}
		if(s1 == 3) {
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,-0.2)), "c1039_a2");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,0)), "c1039_a1");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,0.2)), "c1039_a2");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,-0.4)), "c1039_a1");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(0,0,0.4)), "c1039_a1");
			ARSystem.playSound((Entity)player, "0sword2");
			ARSystem.playSound((Entity)player, "c39a");
			s1 = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		s2 = 30;
		ARSystem.playSound((Entity)player, "c39s3");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1039_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c1039_s4");
		ARSystem.playSound((Entity)player, "c39s4");
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.playSound((Entity)player, "c39sp");
			ARSystem.giveBuff(target, new Timeshock(target), 180);
		}
	}
	@Override
	public boolean tick() {
		if(tk%2== 0 && s2 > 0) {
			s2--;
			ARSystem.playSound((Entity)player, "0sword");
			ARSystem.spellLocCast(player, Local.offset(player.getLocation(), new Vector(-0.5+AMath.random(10)*0.1,0.5+AMath.random(10)*0.1,-0.5+AMath.random(10)*0.1)), "c1039_a"+ AMath.random(2));
			ARSystem.addBuff(player, new Silence(player), 2);
			ARSystem.addBuff(player, new Stun(player), 2);
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(AMath.random(100) <= 20) {
				skill("c1039_p");
				ARSystem.playSound((Entity)player, "0slash2");
			}
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*5);
		} else {

		}
		return true;
	}
}
