package c;

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
import buff.Airborne;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c44izuna extends c00main{
	int tick = 0;
	int count = 0;
	int tick2 = 0;
	int tick3 = 0;
	int ty = 0;

	boolean ps = false;

	boolean sn = false;
	
	int c = 0;
	private int s3;
	public c44izuna(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 44;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c44_s1_ia");
		ty++;
		if(ty >= 100) {
			Rule.playerinfo.get(player).tropy(44,1);
		}
		tick = 40;
		tick2 = 0;
		tick3 = 20;
		count = 5;
		return true;
	}
	@Override
	public boolean skill2() {
		if(player.getHealth() > 2) {
			player.setHealth(player.getHealth() - 1);
			tick3 = 20;
			tick2 = 10;
			tick = 0;
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.heal(player, 2);
		tick3 = 20;
		s3 = 20;
		skill("c44_s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(player.isOnGround()) {
			skill("c44_s4");
			tick3 = 20;
		} else {
			ARSystem.playSound((Entity)player,"c44s4");
			skill("c44_s4_a");
			tick3 = 20;
		}
		return true;
	}

	@Override
	public boolean tick() {
		
		if(s3 > 0) {
			if(ARSystem.box(player, new Vector(6, 6, 6),box.TARGET) != null && ARSystem.box(player, new Vector(6, 6, 6),box.TARGET).size() > 0) {
				s3 = 0;
				skill("c44_s3_a2");
			}
		}
		if(!ps && player.getHealth() <= 4) ps = true;
		if(ps && player.getHealth() == player.getMaxHealth() && !isps) {
			spskillon();
			spskillen();
			hp*=2;
			player.setMaxHealth(hp);
			player.setHealth(hp);
			setcooldown[1] *= 0.7f;
			setcooldown[2] *= 0.7f;
			setcooldown[3] *= 0.7f;
			setcooldown[4] *= 0.7f;
			skill("c44_sp");
		}
		if(player.isSneaking()) {
			if(tick2 > 0  && !sn) {
				ty++;
				if(ty >= 100) {
					Rule.playerinfo.get(player).tropy(44,1);
				}
				sn = true;
				skill("c44_s2");
				tick3 = 20;
				tick2 = 0;
			}
			if(tick > 0 && count > 0 && !sn) {
				sn = true;
				skill("c44_s1_ia");
				tick3 = 20;
				count--;
			}
		} else {
			sn = false;
		}
		
		if(tick3>0) tick3--;
		if(tick2>0) tick2--;
		if(tick>0) tick--;
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			ARSystem.giveBuff(player, new Airborne(player), 20);
			ARSystem.giveBuff((LivingEntity) e.getEntity(), new Airborne((LivingEntity) e.getEntity()), 20);
			if(e.getEntity() instanceof LivingEntity && !((LivingEntity)e.getEntity()).isOnGround()) {
				cooldown[3] -=1;
				cooldown[4] -=1;
			}
			double hp = ((LivingEntity)e.getEntity()).getHealth() / ((LivingEntity)e.getEntity()).getMaxHealth();
			e.setDamage(e.getDamage() + e.getDamage() * (1.0 - hp));
			if(hp <= 0.7 || isps) {
				ARSystem.heal(player, e.getDamage());
			}
			if(hp <= 0.4) {
				cooldown[1] -= setcooldown[1]*0.15;
				cooldown[2] -= setcooldown[2]*0.15;
				cooldown[3] -= setcooldown[3]*0.15;
				cooldown[4] -= setcooldown[4]*0.15;
			}
		} else {
			if(tick3 > 0) {
				if(isps) {
					e.setDamage(e.getDamage()*0.60);
				} else {
					e.setDamage(e.getDamage()*0.75);
				}
			}
		}
		return true;
	}
}
