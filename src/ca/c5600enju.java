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
import org.bukkit.command.ConsoleCommandSender;
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
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import c.c15tina;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.box;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c5600enju extends c00main{
	int p = 0;
	int count = 0;
	
	int lastskill = 1;
	
	double s3cooldown[] = {0,0,0};
	private int pt = 0;
	
	public c5600enju(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1056;
		load();
		text();
		ARSystem.playSound(player, "c56select");
	}
	

	@Override
	public boolean skill1() {
		if(isps) ARSystem.heal(player, 1);
		skill("c1056_s1");
		skill("c1056_b");
		ARSystem.playSound((Entity)player, "c56j1");
		lastskill = 1;
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps) ARSystem.heal(player, 1);
		skill("c1056_s2");
		skill("c1056_b");
		ARSystem.playSound((Entity)player, "c56j2");
		lastskill = 2;
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(isps) ARSystem.heal(player, 1);
		if(!player.isOnGround()) {
			skill("c1056_s3");
			ARSystem.playSound((Entity)player, "c56j3");
			delay(()->{
				ARSystem.playSound((Entity)player, "c56p3");
				skill("c1056_s32");
			},10);
			lastskill = 3;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(s3cooldown[lastskill-1] <= 0) {
			s3cooldown[lastskill-1] = 8;
			skill("c1056_s4"+lastskill);
			if(isps) ARSystem.heal(player, 1);
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(pt > 0) pt--;
		if(tk%20 == 0) {
			for(int i = 0; i < s3cooldown.length; i++) {
				scoreBoardText.add("&a ["+Main.GetText("c1056:sk3")+(i+1)+ "] : &7"+ s3cooldown[i]);
			}
		}
		player.setFallDistance(0);
		if(MSUtil.isbuff(player, "c1056_b")) {
			if(player.isOnGround()) {
				MSUtil.buffoff(player, "c1056_b");
			} else if(player.isSneaking()) {
				MSUtil.buffoff(player, "c1056_b");
				player.setSneaking(false);
				ARSystem.playSound((Entity)player, "c56p1");
				skill("c1056_p1");
			}
		}
		for(int i = 0; i < s3cooldown.length; i++) {
			if(s3cooldown[i] > 0) {
				s3cooldown[i] -= 0.05*(skillmult+sskillmult);
				s3cooldown[i] = AMath.round(s3cooldown[i],2); 
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(!isps && e == player) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c56sp");
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			if(isps) e.setDamage(e.getDamage()*1.5);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.7);
			if(!player.isOnGround()) {
				if(pt <= 0) {
					pt = 1;
					if(!ARSystem.gameMode2) {
						e.setDamage(e.getDamage()*0.7);
					} else {
						e.setDamage(e.getDamage()*0.4);
					}
				}
			}
			
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c15tina) {
					is = "tina";
					break;
				}

			}
		}
		
		if(is.equals("tina")) {
			ARSystem.playSound((Entity)player, "c56tina");
		} else {
			ARSystem.playSound((Entity)player, "c56db");
		}
		return true;
	}
}
