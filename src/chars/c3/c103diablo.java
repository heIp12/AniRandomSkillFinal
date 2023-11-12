package chars.c3;

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
import buff.PowerUp;
import buff.Rampage;
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
import util.Collision;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c103diablo extends c00main{
	boolean start = false;
	Player owner = player;
	int tr = 0;
	
	public c103diablo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 103;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		List<Entity> en = ARSystem.PlayerBeamBox(player, 20, 5, box.TARGET);
		if(en.size() > 0) {
			LivingEntity target = (LivingEntity) en.toArray()[0];
			if(target == owner) {
				cooldown[1] = 0;
				return true;
			}
			if(target.getHealth() < 2) {
				ARSystem.playSound((Entity)player, "c103s12");
				target.setMaxHealth(100);
				ARSystem.heal(target, 10000);
				ARSystem.giveBuff(target, new Rampage(target), 120000);
				ARSystem.giveBuff(target, new PowerUp(target), 120000,3);
				tr++;
				if(tr >=2)Rule.playerinfo.get(player).tropy(103, 1);
				
			} else {
				ARSystem.playSound((Entity)player, "c103s1");
				double p = 1.2 - (target.getHealth() / target.getMaxHealth());
				if(p > 1) p = 1;
				ARSystem.giveBuff(target, new Rampage(target), (int) ((200*p)));
				ARSystem.giveBuff(target, new PowerUp(target), (int) ((200*p)),3);
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> en = ARSystem.box(player, new Vector(12,12,12), box.TARGET);
		if(en.size() > 0) {
			ARSystem.playSound((Entity)player, "c103s2");
			for(Entity ent : en) {
				LivingEntity entity = (LivingEntity)ent;
				if(ent == owner) continue;
				
				float time = 0;
				if(Rule.buffmanager.getBuffs(entity) != null && Rule.buffmanager.getBuffs(entity).getBuff() != null) {
					for(Buff buff : Rule.buffmanager.getBuffs(entity).getBuff()) {
						if(buff.getTime() > 0) time += buff.getTime()*0.025f;
						buff.stop();
					}
				}
				if(time > 10) {
					time*=0.5f;
				}
				entity.setNoDamageTicks(0);
				entity.damage(time,player);
				ARSystem.giveBuff(entity, new Panic(entity), 100);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		int range = 18;
		if(player.isSneaking()) {
			cooldown[3] *= 0.33f;
			range = 6;
		}
		player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(range)).add(0,0.5,0));
		ARSystem.playSound((Entity)player, "c103db");
		ARSystem.playSound((Entity)player, "0miss");
		return true;
	}
	

	@Override
	public boolean tick() {
		start = true;
		if(tk%10 == 0) {
			List<Player> e = ARSystem.PlayerOnlyBeamBox(player, 50, 2, box.TARGET);
			if(e.size() > 0) {
				player.sendTitle(e.get(0).getName(), ""+ e.get(0).getHealth() + " / " + e.get(0).getMaxHealth(),0,20,0);
			}
			
			for(Player p : Rule.c.keySet()) {
				if(Rule.c.get(p).number%1000 == 3) {
					if(!isps) {
						spskillon();
						spskillen();
						ARSystem.playSound((Player)player, "c103rimuru");
						owner = p;
						player.setFlySpeed(0.1f);
						player.setAllowFlight(true);
					}
				}
			}
			if(isps && Rule.c.get(owner) == null) {
				hpCost(player.getHealth() * 0.9f, true);
				isps = false;
				player.setFlying(false);
				player.setAllowFlight(false);
				player.setFlySpeed(0.1f);
			}
			if(Rule.c.size() <= 2 && Rule.c.get(owner) != null) {
				Skill.quit(player);
			}
		}
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(start) {
			for(Buff buff : Rule.buffmanager.getBuffs(player).getBuff()) {
				buff.stop();
			}
		}
		return false;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(owner == e.getEntity()) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		} else {
			if(owner == e.getDamager()) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
}
