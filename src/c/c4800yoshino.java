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
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c4800yoshino extends c00main{
	boolean sk3 = false;
	
	public c4800yoshino(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1048;
		load();
		text();
		ARSystem.playSound(p, "c48sp");
	}
	

	@Override
	public boolean skill1() {
		skill("c1048_s1");
		ARSystem.playSound((Entity)player, "c48s3");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c1048_s2");
		ARSystem.giveBuff(player,new TimeStop(player), 60);
		ARSystem.heal(player, (player.getMaxHealth()-player.getHealth())*0.5);
		ARSystem.playSound((Entity)player, "c48select");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.giveBuff(player,new Stun(player), 20);
		sk3 = !sk3;
		if(sk3) {
			ARSystem.playSound((Entity)player, "c48s2");
		} else {
			ARSystem.giveBuff(player,new Stun(player), 100);
			ARSystem.giveBuff(player,new Silence(player), 100);
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(tk%1==0) {
			ARSystem.spellLocCast(player, Map.randomLoc(), "c1048p");
		}
		if(sk3) {
			ARSystem.addBuff(player,new Stun(player), 1);
			skill("c1048_s3");
		}
		return true;
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(target.getMaxHealth()/10,player);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.potion(target, 2, 100, 2);
			ARSystem.giveBuff(target,new Stun(target), 40);
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(0.05 + target.getMaxHealth()/1000,player);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 1.5);
		} else {
			e.setDamage(e.getDamage()*0.8);
		}
		return true;
	}
}
