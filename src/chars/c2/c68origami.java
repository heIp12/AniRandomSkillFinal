package chars.c2;

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
import buff.Wound;
import chars.c.c00main;
import chars.c.c20kurumi;
import chars.c.c48yoshino;
import chars.ca.c2000kurumi;
import chars.ca.c4800yoshino;
import chars.ca.c6800origami;
import chars.ch.h001chruno;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c68origami extends c00main{
	boolean ps = false;
	int count = 0;
	int pt = 0;
	
	public c68origami(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 68;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(ps) {
			ARSystem.playSound((Entity)player, "c68s");
			ARSystem.addBuff(player, new Stun(player), 20);
			for(int i =0; i < AMath.random(5)+5;i++) {
				Location loc = player.getLocation();
				loc.setPitch(0);
				if(i%2 == 0) {
					loc = ULocal.offset(loc, new Vector(-1.2+(i/2)*0.35,1.3-(i/2)*0.1,0.2+(i/2)*0.4));
				} else {
					loc = ULocal.offset(loc, new Vector(-1.2+(i/2)*0.35,1.3-(i/2)*0.1,-0.2-((i/2)*0.4)));
				}
				ARSystem.spellLocCast(player, loc, "c68_s1");
				
			}
			delay(()->{
				ARSystem.playSound((Entity)player, "c68s1");
				skill("c68_s12");
			},10);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(ps) {
			ARSystem.playSound((Entity)player, "c68s");
			ARSystem.addBuff(player, new Stun(player), 20);
			skill("c68_s2");
			delay(()->{ARSystem.playSound((Entity)player, "c68s2");},10);
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(ps) {
			ARSystem.playSound((Entity)player, "c68s3");
			skill("c68_s3");
			Location loc = player.getLocation().clone();
			for(int i =0; i<10; i++) {
				if(loc.clone().add(0,1,0).getBlock().isEmpty()) {
					loc.add(loc.getDirection().multiply(1));
				} else {
					i = 10;
				}
			}
			player.teleport(loc);
			skill("c68_s3");
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(pt>0) pt--;
		if(tk%20 == 0) {

		}
		return true;
	}
	

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(!ps) {
				count++;
				if(count>=2) {
					Rule.playerinfo.get(player).tropy(68,1);
					
					List<Entity> el = ARSystem.box(player, new Vector(999,999,999),box.ALL);
					boolean jr = false;
					for(Entity ee : el) {
						if(Rule.c.get(ee) != null) {
							if(Rule.c.get(ee) instanceof c6800origami) {
								jr = true;
								break;
							}
							if(Rule.c.get(ee) instanceof c48yoshino) {
								jr = true;
								break;
							}
							if(Rule.c.get(ee) instanceof c4800yoshino) {
								jr = true;
								break;
							}
							if(Rule.c.get(ee) instanceof c20kurumi) {
								jr = true;
								break;
							}
							if(Rule.c.get(ee) instanceof c2000kurumi) {
								jr = true;
								break;
							}
							if(Rule.c.get(ee) instanceof h001chruno) {
								jr = true;
								break;
							}
						}
					}
					if(jr && AMath.random(0,10) <= 5) {
						Rule.c.put(player, new c6800origami(player,Rule.gamerule, this));
					} else {
						ps = true;
						spskillen();
						spskillon();
						ARSystem.playSound((Entity)player, "c68sp");
						ARSystem.giveBuff(player, new Nodamage(player), 60);
						player.setMaxHealth(18);
						player.setHealth(18);
						e.setDamage(0);
					}
				}
			}
			if(ps && pt <= 0) {
				pt = 30;
				e.setDamage(0);
				e.setCancelled(true);
				if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
					for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
						buff.setTime(0);
					}
				}
				if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
					for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
						buff.setTime(0);
					}
				}
				Location loc = player.getLocation().clone().add(3-AMath.random(6),3-AMath.random(6),3-AMath.random(6));
				int i = 0;
				while(!loc.getBlock().isEmpty() || !loc.clone().add(0,1,0).getBlock().isEmpty()) {
					loc = player.getLocation().clone().add(3-AMath.random(6),3-AMath.random(6),3-AMath.random(6));
					if(i > 100) break;
					i++;
				}
				skill("c68_s3");
				ARSystem.playSound((Entity)player, "c68s3");
				player.teleport(loc);
				skill("c68_s3");
			}
		}
		return true;
	}
	
}
