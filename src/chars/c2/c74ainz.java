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
import buff.Dancing;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
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

public class c74ainz extends c00main{
	int sk1 = 0;
	
	Location floc;
	HashMap<LivingEntity,Integer> attack = new HashMap<>();
	List<Entity> target;
	
	public c74ainz(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 74;
		load();
		text();
		c = this;
		if(ARSystem.isGameMode("lobotomy")) setcooldown[1] *= 0.3;
	}

	@Override
	public boolean skill1() {
		ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(), new Vector(1,0,0)), "c74_s1");
		ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(), new Vector(1,0,1)), "c74_s1");
		ARSystem.spellLocCast(player, ULocal.offset(player.getLocation(), new Vector(1,0,-1)), "c74_s1");
		ARSystem.playSound((Entity)player, "c74s1");
		sk1++;
		if(sk1 > 10) Rule.playerinfo.get(player).tropy(74,1);
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c74s2");
			Location loc = player.getLocation().clone();
			for(int i =0; i<8; i++) {
				if(loc.clone().add(0,1,0).getBlock().isEmpty()) {
					loc.add(loc.getDirection().multiply(1));
				} else {
					i = 999;
				}
			}
			player.teleport(loc);
		
		return true;
	}
	
	@Override
	public boolean skill3() {
		Entity en = ARSystem.boxSOne(player, new Vector(2,2,2), box.TARGET);
		if(en != null) {
			ARSystem.playSound((Entity)player, "c74s3");
			for(int i=0; i<5;i++)ARSystem.spellLocCast(player, en.getLocation(), "c74_s2");
			ARSystem.playSound(en, "0bload");
			if(((LivingEntity)en).getHealth() <= 14) {
				Skill.remove(en, player);
			} else {
				((LivingEntity)en).setHealth(((LivingEntity)en).getHealth() * 0.2f);
			}
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	
	@Override
	public boolean remove(Entity caster) {
		return false;
	}
	int tick = 0;
	@Override
	public boolean tick() {
		if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
				buff.setTime(0);
			}
		}
		
		
		if(tick%100 == 0) {
			List<Entity> l = ARSystem.box(player, new Vector(999,999,999), box.TARGET);
			if(l.size() >= 40 && skillCooldown(0)) {
				ARSystem.addBuff(player, new TimeStop(player), 200);
				int size = 2 + l.size()/50;
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c74sp");
				for(Entity e : l) {
					if(!(e instanceof Player)) {
						ARSystem.addBuff((LivingEntity) e, new TimeStop((LivingEntity) e), 100);
						delay(()->{
							e.remove();
						},140);
					} else {
						((LivingEntity) e).setHealth(((LivingEntity) e).getHealth()/2);
						ARSystem.addBuff((LivingEntity) e, new Panic((LivingEntity) e), 600);
						ARSystem.addBuff((LivingEntity) e, new Silence((LivingEntity) e), 200);
					}
				}
				delay(()->{
					Rule.playerinfo.get(player).tropy(74,1);
					for(int i=0; i<size; i++) {
						Entity target = ARSystem.RandomPlayer(player);
						Location loc = target.getLocation();
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn dango 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
					}
				},200);
			}
		}
		tick++;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
		} else {
			if(e.getDamage() <= 3) e.setDamage(0);
		}
		return true;
	}
	
}
