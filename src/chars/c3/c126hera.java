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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import ars.gui.G_Satory;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.NoHeal;
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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c126hera extends c00main{
	int p = 11;
	int p2 = 0;
	int pattantime = 0;
	int pattan = 0;
	public c126hera(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 126;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c126s"+AMath.random(3));
	}


	@Override
	public boolean skill1() {
		if(p2 <= 0) {
			skill("c126_s1");
			ARSystem.playSound((Entity)player, "c126s1");
			for(Entity e : ARSystem.box(player, new Vector(7,5,7), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				en.setNoDamageTicks(0);
				en.damage(3,player);
				Curse cs = new Curse(en);
				cs.setCaster(player);
				ARSystem.giveBuff(en, cs, 20 , 0.5);
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(p2 <= 0) {
			skill("c126_s2");
			ARSystem.playSound((Entity)player, "c126s2");
			for(Entity e : ARSystem.box(player, new Vector(7,5,7), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				ARSystem.giveBuff(en, new Stun(en), 60);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(p2 <= 0) {
			ARSystem.playSound((Entity)player, "c126s3");
			p2 = 60;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0) scoreBoardText.add("&c ["+Main.GetText("c126:ps")+ "] : &f"+p);
		if(p2 <= 0 && AMath.random(500) <= 12 - p && Rule.buffmanager.GetBuffTime(player, "stun") <= 0) {
			ARSystem.playSound((Entity)player, "c126s3");
			p2 = 20 + 8 * (12-p2);
		}
		if(p2 > 0) {
			p2--;
			ps2();
		}
		return true;
	}
	
	void ps() {
		p2 = 0;
		ARSystem.giveBuff(player, new Nodamage(player), 20);
		p--;
		ARSystem.playSound((Entity)player, "c126death");
		double size = player.getMaxHealth() - AMath.random(0, 2);
		if(size <=1) size = 1;
		player.setMaxHealth(size);
		ARSystem.giveBuff(player, new Stun(player), 80);
		ARSystem.giveBuff(player, new Silence(player), 80);
		ARSystem.overheal(player, hp);
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(p > 0) {
			ps();
			return false;
		}
		return super.remove(caster);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(p2 > 0){
				e.setDamage(e.getDamage() * 1.5);
			}
			
		} else {
			if(player.getHealth() - e.getDamage() < 1 && p > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				ps();
				return false;
			}
			if(p2 > 0){
				e.setDamage(e.getDamage() * 0.5);
			}
		}
		return true;
	}
	
	void ps2(){
		Entity target = ARSystem.boxSOne(player, new Vector(80,80,80), box.TARGET);
		if(target == null) pattan = 2;
		
		if(pattantime > 0) {
			pattantime--;
			if(pattan == 1) pattan1(target);
			if(pattan == 2) pattan2(target);
		} else {
			double range = target.getLocation().distance(player.getLocation());
			pattantime = AMath.random(20);
			if(range <= 14) pattan = 1;
			if(AMath.random(5) == 3) pattan = AMath.random(2);
		}
	}
	int delay = 0;
	
	public void pattan1(Entity target) {
		if(target != null) {
			if(delay <= 0) {
				Location loc = ULocal.lookAt(player.getLocation(), target.getLocation().clone());
				double range = target.getLocation().distance(player.getLocation());
				if(range < 6) {
					if(range < 4) {
						if(AMath.random(10) <= 2) {
							delay = 10;
							player.setVelocity(loc.clone().add(0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10)).getDirection().multiply(3).setY(1.5));
							if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(-4).setY(-0.5));
						} else {
							delay = 5;
							player.teleport(loc);
							ARSystem.playSound((Entity)player, "0sword",0.3f+AMath.random(1,6)*0.1f);
							skill("c126_p2_"+AMath.random(6));
						}
					} else {
						delay = 3;
						player.teleport(loc);
						player.setVelocity(loc.getDirection().multiply(-1).setY(0.2));
						if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(-1).setY(-0.5));
					}
				}
				else if(range > 8) {
					delay = 2;
					player.setVelocity(loc.clone().add(0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10)).getDirection().multiply(0.6).setY(0));
					if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(0.6).setY(-0.5));
				} else {
					delay = 10;
					player.teleport(loc);
					skill("c126_p");
				}
			}
		}
		delay--;
	}
	
	public void pattan2(Entity target) {
		if(delay <= 0) {
			if(target != null && AMath.random(3) == 2) {
				delay = 5;
				Location ll = player.getLocation();
				ll.setYaw(ll.getYaw() + 3 - AMath.random(6));
				Location loc = ULocal.lookAt(ll, target.getLocation().clone());
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(1).setY(0));
				if(AMath.random(3) == 2 && player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1));
			} else {
				delay = 5+AMath.random(5);
				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw() + 5 - AMath.random(10));
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(1).setY(0));
				if(player.getLocation().add(player.getLocation().getDirection()).getBlock().isEmpty()) {
					if(player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1.5));
					delay = 10;
				} else {
					if(AMath.random(3) == 2 && player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1));
					if(AMath.random(3) == 2 && !player.isOnGround()) player.setVelocity(loc.getDirection().multiply(3).setY(-2));
				}
			}
		}
		delay--;
	}
}
