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
import buff.Airborne;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c32sanji extends c00main{
	int timer = 0;
	int vt = 0;
	float cooldownc[] = new float[8];
	int count;
	int damage =0;

	String skill = "";
	
	boolean sp = false;
	public c32sanji(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 32;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		ARSystem.giveBuff(player, new Airborne(player), 14);
		if(skill.equals("")) {
			cooldownc[0] = cooldown[1];
			skill("c32_s1");
			player.setVelocity(player.getLocation().getDirection().multiply(1));
			count++;
		}
		else if(skill.equals("s3")) {
			cooldownc[2] = 3;
			cooldown[1] = 3;
			skill("c32_s3_1");
			player.setVelocity(player.getLocation().getDirection().multiply(0.4));
			count++;
		}
		else if(skill.equals("s4")) {
			cooldownc[5] = 5;
			cooldown[1] = 5;
			skill("c32_s4_1");
			count++;
		}
		return true;
	}
	@Override
	public boolean skill2() {
		ARSystem.giveBuff(player, new Airborne(player), 14);
		if(skill.equals("")) {
			cooldownc[1] = cooldown[2];
			skill("c32_s2");
			ARSystem.giveBuff(player, new Airborne(player), 60);
			count++;
			
		}
		else if(skill.equals("s3")) {
			cooldownc[3] = 3;
			cooldown[2] = 3;
			skill("c32_s3_2");
			player.setVelocity(player.getLocation().getDirection().multiply(0.4));
			count++;
		}
		else if(skill.equals("s4")) {
			cooldownc[6] = 5;
			cooldown[1] = 5;
			skill("c32_s4_2");
			count++;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(skill.equals("")) {
			skill = "s3";
			cooldown[1] = cooldownc[2];
			cooldown[2] = cooldownc[3];
			cooldown[4] = cooldownc[4];
		}
		else if(skill.equals("s3")){
			skill = "";
			cooldown[1] = cooldownc[0];
			cooldown[2] = cooldownc[1];
			cooldown[3] = 0;
			cooldown[4] = 0;
		}
		else if(skill.equals("s4")) {
			ARSystem.giveBuff(player, new Airborne(player), 14);
			cooldownc[7] = 5;
			cooldown[3] = 5;
			skill("c32_s4_3");
			count++;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(skill.equals("")) {
			skill = "s4";
			cooldown[1] = cooldownc[5];
			cooldown[2] = cooldownc[6];
			cooldown[3] = cooldownc[7];
		}
		else if(skill.equals("s4")){
			skill = "";
			cooldown[1] = cooldownc[0];
			cooldown[2] = cooldownc[1];
			cooldown[3] = 0;
			cooldown[4] = 0;
		}
		else if(skill.equals("s3")) {
			ARSystem.giveBuff(player, new Airborne(player), 14);
			cooldownc[4] = 3;
			cooldown[4] = 3;
			skill("c32_s3_3");
			player.setVelocity(player.getLocation().getDirection().multiply(0.4));
			count++;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(isps) {
			skill("c32_sp");
		}
		if(!spben && !isps && ARSystem.AniRandomSkill !=null && ARSystem.AniRandomSkill.time <= 20 && count > 30) {
			spskillon();
			spskillen();
			skill("c32_e");
		}

		if(tk%20==0) {
			if(skill.equals("s3")) {
				scoreBoardText.add("&c "+Main.GetText("c32:t")+ "&f : "+Main.GetText("c32:sk3"));
			} else if(skill.equals("s4")) {
				scoreBoardText.add("&c "+Main.GetText("c32:t")+ "&f : "+Main.GetText("c32:sk4"));
			} else {
				scoreBoardText.add("&c "+Main.GetText("c32:t")+ "&f : "+Main.GetText("c32:t2"));
			}
		}
		if(tk%20==0 && psopen && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 20) {
			scoreBoardText.add("&c "+Main.GetText("c32:sk0")+ "&f : " + count + "/ 30");
		}
		if(timer%2==0) {
			for(int i = 0; i < cooldownc.length; i++) {
				if(cooldownc[i] > 0) {
					cooldownc[i] -= 0.1*(skillmult+sskillmult);
					if(cooldownc[i] < 0) {
						cooldownc[i] = 0;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				e.setDamage(e.getDamage()+1);
			}
			float damage = 1;
			if(!e.getDamager().isOnGround()) damage += 0.25;
			if(!e.getEntity().isOnGround()) damage += 0.25;
			e.setDamage(e.getDamage()*damage);
			this.damage+= e.getDamage();
			if(this.damage >= 50) {
				Rule.playerinfo.get(player).tropy(32,1);
			}
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
		} else {

		}
		return true;
	}
}
