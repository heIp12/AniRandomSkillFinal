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
import buff.Cindaella;
import buff.Curse;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c67akad extends c00main{
	int p = 0;
	int sk3 = 0;
	double sp = 1;
	double damage = 0.10;
	boolean sk4 = false;
	int stk = 0;
	LivingEntity lasttarget = null;
	float cc = 0;
	
	public c67akad(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 67;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c67s1");
		ARSystem.playerAddRotate(player,0,(float) -4);
		skill("c67_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playerAddRotate(player,0,(float) -10);
		ARSystem.playSound((Entity)player, "c67s1");
		skill("c67_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c67s3");
		ARSystem.giveBuff(player, new Nodamage(player), 20);
		ARSystem.potion(player, 14, 20, 0);
		sk3 = 20;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c67s4");
		sk4 = true;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			if(ARSystem.gameMode == modes.LOBOTOMY && target.getMaxHealth() > 500) {
				target.damage(2 + (target.getMaxHealth() - target.getHealth()) * (damage/5),player);
			} else {
				target.damage(2 + (target.getMaxHealth() - target.getHealth()) * damage,player);
			}
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			if(ARSystem.gameMode == modes.LOBOTOMY && target.getMaxHealth() > 500) {
				target.damage(2 + target.getHealth() *  (damage/5),player);
			} else {
				target.damage(2 + target.getHealth() * damage,player);
				Location loc = target.getLocation();
				loc = Local.lookAt(loc, player.getLocation());
				target.setVelocity(loc.getDirection().multiply(-0.5));
			}
		}
	}

	
	@Override
	public boolean tick() {
		if(stk>0) stk--;
		if(sk3>0) {
			sk3--;
			skill("c67_s3");
			player.setVelocity(player.getLocation().getDirection().setY(0).multiply(0.3));
		}
		if((sk4||isps) && tk%10 == 0) {
			if(player.getHealth() < player.getMaxHealth()) {
				ARSystem.heal(player, 1);
				cc++;
				if(cc >= 100) Rule.playerinfo.get(player).tropy(67,1);
			} else {
				sk4 = false;
			}
		}
		if(ARSystem.AniRandomSkill != null && tk%20==0 && ARSystem.AniRandomSkill.time%30 == 0 && ARSystem.AniRandomSkill.time > 10 && p < 4) {
			p++;
			if(p == 1) {
				damage+=0.05;
				skillmult += 0.2;
			}
			if(p == 2) {
				damage+=0.08;
				skillmult += 0.25;
			}
			if(p == 3) {
				damage+=0.12;
				skillmult += 0.3;
			}
			if(p == 4) {
				spskillen();
				spskillon();
				ARSystem.playSound((Entity)player,"c67sp");
			} else {
				ARSystem.playSound((Entity)player, "c67p"+p);
				cooldown[1]-=0.3;
				cooldown[2]-=0.3;
				player.setMaxHealth(player.getMaxHealth()+5);
				ARSystem.heal(player, 5);
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c67:sk1")+ "] : "+ AMath.round(damage*100,0) +"%");
			scoreBoardText.add("&c ["+Main.GetText("c67:sk2")+ "] : "+ AMath.round(damage*100,0) +"%");
			if(isps) {
				scoreBoardText.add("&c ["+Main.GetText("c67:sk0")+ "] : "+ AMath.round(sp*100,0) +"%");
			}
		}
		return true;
	}
	

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps && lasttarget != e.getEntity()) {
				lasttarget = (LivingEntity) e.getEntity();
				e.setDamage(e.getDamage() * 2);
				ARSystem.addBuff(lasttarget, new Panic(lasttarget), (int) (e.getDamage()*20));
				ARSystem.spellCast(player, lasttarget, "c67_p");
				ARSystem.playSound(lasttarget, "0bload");
				if(lasttarget.getHealth()- e.getDamage() < 1) {
					ARSystem.overheal(player, lasttarget.getMaxHealth());
					delay(()->{
						((LivingEntity)ARSystem.boxSOne(player, new Vector(12,8,12), box.TARGET)).damage(lasttarget.getMaxHealth()*2,player);
					},3);
				}
				if(Rule.buffmanager.GetBuffValue(player, "plushp") >= 300) {
					Rule.playerinfo.get(player).tropy(67,1);
				}
			}
		} else {
			if(e.getEntity() == player && sk3 > 0) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
	
}
