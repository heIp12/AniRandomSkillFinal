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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import c.c00main;
import c.c37subaru;
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

public class c54patel extends c00main{
	
	public c54patel(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 54;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c54_s1");
		ARSystem.playSound((Entity)player, "c54s1"+AMath.random(4));
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c54_s2");
		ARSystem.playSound((Entity)player, "c54s2"+AMath.random(3));
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c54_s3");
		ARSystem.playSound((Entity)player, "c54s3"+AMath.random(5));
		for(Entity e : ARSystem.box(player, new Vector(6,4,6),box.TARGET)){
			ARSystem.giveBuff((LivingEntity) e, new Panic((LivingEntity) e), 280);
			
			Wound w = new Wound((LivingEntity) e);
			w.setValue(1);
			w.setDelay(player,40,160);
			ARSystem.giveBuff((LivingEntity) e, w, 460);
		}
		return true;
	}
	
	
	@Override
	public boolean tick() {
		
		return true;
	}
	
	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(mycaster && ARSystem.isTarget(e.getTarget(),player)) {
			if(e.getSpell().getName().equals("c54_s1")) {
				Wound w = new Wound(e.getTarget());
				w.setValue(1);
				w.setDelay(player,40,40);
				ARSystem.giveBuff(e.getTarget(), w, 240);
			}
			if(e.getSpell().getName().equals("c54_s2")) {
				Wound w = new Wound(e.getTarget());
				w.setValue(1);
				w.setDelay(player,120,0);
				ARSystem.giveBuff(e.getTarget(), w, 2400);
			}
		}
	}
	
	@Override
	public void PlayerSpCast(Player p) {
		if(Rule.c.get(p) !=null && Rule.c.get(p) instanceof c37subaru) {
			ARSystem.giveBuff(player, new TimeStop(player), 180);
			delay(()->{
				ARSystem.playSoundAll("c54ev2");
			},160);
			delay(()->{
				Rule.playerinfo.get(player).tropy(54,1);
				Skill.death(player, p);
			},180);
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*6);
			ARSystem.heal(player, e.getDamage()*0.2);
			ARSystem.spellLocCast(player, e.getEntity().getLocation(), "c54_p");
		} else {
			if(player.getHealth() - e.getDamage() < 1 && Rule.buffmanager.OnBuffTime((LivingEntity) e.getDamager(), "panic")&&skillCooldown(0)) {
				death((Player)e.getDamager());
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(Rule.buffmanager.OnBuffTime((LivingEntity) caster, "panic") && skillCooldown(0)) {
			death((Player)caster);
			return false;
		}
		return true;
	}
	
	public void death(Player p){
		spskillen();
		spskillon();
		info();
		ARSystem.playSound((Entity)player, "c54sp");
		player.teleport(p);
		p.setGameMode(GameMode.SPECTATOR);
		Rule.c.remove(player);
		HashMap<Player, c00main> s = (HashMap<Player, c00main>) Rule.c.clone();
		Rule.c.put(player,s.get(p));
		Rule.c.remove(p);
		Rule.c.get(player).player = player;
		player.setMaxHealth(hp);
		player.setHealth(hp);
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		delay(()->{
			if(Rule.c.get(player) != null) {
				delay(()->{
					if(Rule.c.get(player) != null) {
						delay(()->{
							if(Rule.c.get(player) != null) {
								if(Rule.c.size() > 1) {
									HashMap<Player, c00main> ss = (HashMap<Player, c00main>) Rule.c.clone();
									Rule.c.put(p,ss.get(player));
									Rule.c.get(p).player = p;
									Skill.remove(player, p);
									p.setGameMode(GameMode.ADVENTURE);
									ARSystem.Stop();
								} else {
									ARSystem.Stop();
								}
							}
						},200);
					}
				},200);
			}
		},200);
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.TARGET);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c37subaru) {
					is = "subaru";
					break;
				}
			}
		}
		
		if(is.equals("subaru")) {
			ARSystem.playSound((Entity)player, "c54ev1");
			cooldown[9] = 10;
		} else {
			ARSystem.playSound((Entity)player, "c54db");
		}
		
		return true;
	}
}
