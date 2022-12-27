package c;

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
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c36kaneki extends c00main{
	int timer = 0;
	int vt = 0;
	int s1 = 1;
	int count;
	
	boolean sp = false;
	public c36kaneki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 36;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		if(s1==2) s1=1;
		if(s1==1) s1=2;
		skill("c36_s1");
		skill("c36_s0"+s1);
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c36_s2");
		cooldown[1] = 0;
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c36_s3");
		cooldown[1] = 0;
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c36_s4");
		cooldown[1] = 0;
		vt = 10;
		return true;
	}

	@Override
	public boolean tick() {
		if(vt > 0) {
			vt--;
			player.setVelocity(new Vector(0,0,0));
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player || ARSystem.gameMode == modes.LOBOTOMY) {
			player.setMaxHealth(player.getMaxHealth()+4);
			ARSystem.heal(player, 8);
			count++;
			if(count >= 2 && !isps) {
				spskillon();
				spskillen();
				setcooldown[1]-=1.5;
				setcooldown[2]-=1.5;
				setcooldown[3]-=1.5;
				setcooldown[4]-=1.5;
				skill("c36_sp");
			}
			if(count>=3) {
				Rule.playerinfo.get(player).tropy(36,1);
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				e.setDamage(e.getDamage() + 5);
				ARSystem.heal(player,e.getDamage()/3);
			}
		} else {

		}
		return true;
	}
}
