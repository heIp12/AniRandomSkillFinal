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

import com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import event.Skill;
import manager.AdvManager;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c41zanitu extends c00main{
	Location loc = null;
	LivingEntity target;
	
	int c = 0;
	int c2 = 0;
	int c3 = 0;
	int ccount = 6;
	int brcount = 0;
	int spcount = 1;
	int s1 = 0;
	
	public c41zanitu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 41;
		load();
		text();

		loc = player.getLocation();
	}
	

	@Override
	public boolean skill1() {
		if(ccount > 0 && c3 > 0) {
			spcount = 1;
			ccount--;
			cooldown[1] = 0.4f;
			ARSystem.spellCast(player, target, "c41_s1_tg");
			delay(()->{s1 = 30;}, 20); 
		} else {
			if(c2 > 20) c2 = 0;
			if(target != null) {
				brcount++;
				if(brcount >= 10) {
					Rule.playerinfo.get(player).tropy(41,1);
				}
				ARSystem.spellCast(player, target, "c41_s1_tg");
				delay(()->{s1 = 30;}, 20); 
				if(!target.isOnGround()) {
					if(AMath.random(100) < 5*spcount) {
						spskillon();
						spskillen();
						c3 = 300;
						ccount = 5; 
						ARSystem.playSound((Player)player, "c41sp");
						cooldown[1] = 0;
					} else {
						spcount++;
					}
				}
			} else {
				cooldown[1] = 0;
			}
		}
		c = 0;
		target = null;
		player.removePotionEffect(PotionEffectType.BLINDNESS);
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c41_s2");
		cooldown[1] = 0;
		c2 = 20;
		return true;
	}
	
	@Override
	public boolean tick() {
		if(s1 > 0) {
			if(ARSystem.box(player, new Vector(6, 6, 6),box.TARGET) != null && ARSystem.box(player, new Vector(6, 6, 6),box.TARGET).size() > 0) {
				s1 = 0;
				skill("c41_s1_am");
			}
		}
		if(c3 > 0 && ccount > 0) {
			c3--;
			target = (LivingEntity) ARSystem.boxSOne(player, new Vector(20,15,20),box.TARGET);
			if(target != null) ARSystem.potion(target, 24, 2, 2);
		} else {
			if(c2 > 0) {
				c2--;
				ARSystem.potion(player, 15, 100, 1);
				target = (LivingEntity) ARSystem.boxSOne(player, new Vector(15,15,15),box.TARGET);
				if(target != null) ARSystem.potion(target, 24, 2, 2);
			} else {
				if(player.isSneaking() && player.getLocation().distance(loc) <= 0.01) {
					c+= (skillmult + sskillmult);
					if(c > 10) {
						ARSystem.potion(player, 15, 100, 1);
						target = (LivingEntity) ARSystem.boxSOne(player, new Vector(15,15,15),box.TARGET);
						if(target != null) ARSystem.potion(target, 24, 2, 2);
					}
				} else {
					if(c > 0) {
						c = 0;
						target = null;
						ARSystem.potion(player, 15, 0, 0);
					}
				}
			}
		}
		if(!Rule.buffmanager.OnBuffTime(player, "panic") && player.hasPotionEffect(PotionEffectType.BLINDNESS) && c<=0 && c2<=0) {
			player.removePotionEffect(PotionEffectType.BLINDNESS);
		}
		if(tk%20==0) {
			if(target != null) scoreBoardText.add("&c ["+Main.GetText("c41:ps")+ "] &a" + target.getName());
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c41:sk0")+ "] &f" + (spcount*5) +"%");
		}
		s1--;
		loc = player.getLocation();
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*4);
		} else {

		}
		return true;
	}
}
