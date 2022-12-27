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
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.MapType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c97sorao extends c00main{
	Location loc;
	int pc = 0;
	int tp = 0;
	
	public c97sorao(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 97;
		load();
		text();
		c = this;
	}
	
	@Override
	public void setStack(float f) {
		tp = pc = (int) f;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "0gun2");
		skill("c97_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c97s2");
		skill("c97_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c97s3");
		skill("c97_s3");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			float damage = 2;
			if(MSUtil.isbuff(player, "c97_s2")) {
				damage = 4;
				if(Rule.c.get(target) != null) {
					String s = Main.GetText("c"+Rule.c.get(target).getCode()+":tag");
					if(s.indexOf("tg4") == -1) {
						damage = 6;
					}
				}
				if(ARSystem.gameMode == modes.LOBOTOMY) damage*=10;
				
				if(target.getHealth()- damage >= 1) {
					s_damage+=damage;
					ARSystem.giveBuff(target, new Stun(target), 10);
					target.setHealth(target.getHealth() - damage);
				} else {
					Skill.death(target, player);
				}
			} else {
				if(ARSystem.gameMode == modes.LOBOTOMY) damage *= 5;
				target.damage(damage,player);
			}
		}
		if(n.equals("2")) {
			ARSystem.giveBuff(target, new Rampage(target), 100);
		}
	}
	
	@Override
	public boolean tick() {
		if(pc > 0) pc--;
		if(loc == null) loc = player.getLocation();
		if(AMath.random(1000) <= 1) {
			player.teleport(Map.randomLoc());
		}
		if(loc.distance(player.getLocation()) >= 6 && pc <= 0) {
			pc = 10;
			skillmult += 0.1;
			Rule.buffmanager.selectBuffValue(player, "barrier", 10 );
			ARSystem.heal(player, 5);
			tp++;
		}
		
		if(tk%20 == 0) {
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c97:sk0")+ "] : "+ tp +" / 30");
		}
		
		if(!isps && tp > 29) {
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c97sp1");
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new Silence(p), 140);
				ARSystem.giveBuff(p, new Nodamage(p), 140);
				ARSystem.giveBuff(p, new Stun(p), 140);
				delay(()->{ARSystem.potion(p, 15, 80, 80);},100);
			}
			delay(()->{
				WinEvent event = new WinEvent(player);
				Bukkit.getPluginManager().callEvent(event);
				if(!event.isCancelled()) {
					Rule.playerinfo.get(player).tropy(97,1);
					Map.mapType = MapType.NORMAL;
					ARSystem.heal(player,100);
					Bgm.setForceBgm("c97");
					for(Player p : Bukkit.getOnlinePlayers()) {
						Map.getMapinfo(1007);
						p.teleport(new Location(p.getWorld(),73,2,-46,-90,0));
					}
					delay(()->{
						ARSystem.giveBuff(player, new Silence(player), 20000);
						ARSystem.giveBuff(player, new Nodamage(player), 20000);
					},20);
					for(Player p : Rule.c.keySet()) {
						if(p != player) {
							Rule.c.put(p, new c000humen(p, plugin, null));
						}
					}
					delay(()->{
						for(int i = 0; i<4;i++) {
							Location loc = Map.getCenter();
							loc.setZ(-43.0 - AMath.random(40)*0.1);
							loc.setX(75+ i);
							Map.spawn("monkey", loc, 1);
						}
						for(int i = 0; i<50;i++) {
							int x = i;
							delay(()->{
								Location loc = Map.getCenter();
								loc.setZ(-43.0 - AMath.random(40)*0.1);
								loc.setX(73+ x*1.5);
								Map.spawn("monkey", loc, 1);
							},200+i*10);
						}
					},100);
				}
			},140);
		}
		
		if(MSUtil.isbuff(player, "c97_s2")) {
			Location loc = player.getLocation().clone();
			for(int i=0;i<27;i++) {
				loc.add(loc.getDirection());
				for (LivingEntity e : player.getWorld().getLivingEntities()) {
					if(e != player && e instanceof Player) {
						if(e.hasPotionEffect(PotionEffectType.INVISIBILITY)) e.removePotionEffect(PotionEffectType.INVISIBILITY);
						for(Player p : Bukkit.getOnlinePlayers()) {
							p.showPlayer((Player)e);
						}
					}
				}
			}
		}
		
		loc = player.getLocation();
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*0.6);
			if(AMath.random(10) <= 1) {
				player.teleport(Map.randomLoc());
			}
		}
		return true;
	}
}
