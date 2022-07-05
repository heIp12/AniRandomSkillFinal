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
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c76naohumi extends c00main{
	int damage = 0;
	
	public c76naohumi(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 76;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(isps) player.damage(player.getMaxHealth()*0.04);
		skill("c76_s1");
		if(isps) skill("c76_sp");
		ARSystem.playSound((Entity)player, "c76s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps) player.damage(player.getMaxHealth()*0.04);
		ARSystem.playSound((Entity)player, "c76s2");
		if(player.isSneaking()) {
			for(int i=0;i<12;i++) ARSystem.spellLocCast(player, player.getLocation(), "c76_s2_e");
			ARSystem.giveBuff(player,new TimeStop(player), 100);
		} else {
			skill("c76_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(isps) player.damage(player.getMaxHealth()*0.04);
		ARSystem.playSound((Entity)player, "c76s3");
		ARSystem.giveBuff(player,new Stun(player), 60);
		skill("c76_s3");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("2")) {
			target.damage(damage,player);
		}
		if(n.equals("1")) {
			ARSystem.giveBuff(target,new Stun(target), 100);
			ARSystem.giveBuff(target,new Silence(target), 100);
			for(int i=0;i<12;i++) ARSystem.spellLocCast(player, target.getLocation(), "c76_s2_e");
		}
	}
	

	@Override
	public boolean tick() {
		if(ARSystem.gameMode == modes.LOBOTOMY) {
			if(damage > 100) damage = 100;
		}
		
		if(isps && AMath.random(600) == 6) {
			ARSystem.giveBuff(player, new Panic(player), 100);
		}
		if(tk%20==0 && psopen) scoreBoardText.add("&c ["+Main.GetText("c76:sk0")+ "] : "+ damage+" / 30");
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) e.setDamage(e.getDamage()*3);
			
		} else {
			boolean d = false;
			Location loc = player.getLocation().clone();
			loc.add(loc.getDirection().multiply(2.6));
			for(int i=0;i<100;i++) {
				loc.add(loc.getDirection());
				for (LivingEntity en : player.getWorld().getLivingEntities()) {
					if(en.getLocation().distance(loc) <= 3+(i/2)) {
						if(e.getDamager() == en) {
							d = true;
						}
					}
				}
				
			}
			if(d) {
				if(ARSystem.gameMode == modes.LOBOTOMY) {
					e.setDamage(e.getDamage()*0.2);
				} else {
					e.setDamage(e.getDamage()*0.5);
				}
				if(isps) skill("c76_p2");
				if(!isps) skill("c76_p1");
			}
			damage++;
			if(damage > 100) Rule.playerinfo.get(player).tropy(76,1);
			if(damage >= 30 && !isps) {
				spskillon();
				spskillen();
				ARSystem.playSound((Entity)player, "c76sp");
			}
		}
		return true;
	}
	
}
