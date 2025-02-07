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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
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
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
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
import util.Text;

public class c117kanade extends c00main{
	Location loc;
	int plt = 0;
	int pt = 0;
	int i = 1;
	int sk3 = 0;
	int ps = 0;
	float adddamage = 1;
	
	
	int sk2 = 2;
	int sk2t = 0;
	
	int count = 0;
	
	@Override
	public void setStack(float f) {
		ps = (int)f;
		if(ps >= Math.max(3,count/2) && !isps) {
			spskillon();
			spskillen();
			skillmult += 0.5;
			Rule.buffmanager.selectBuffValue(player, "barrier", 10);
		}
	}
	
	public c117kanade(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 117;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c117s1");
		if(i >= 2 && player.isSneaking()) {
			skill("c117_s1-attack1");
			skill("c117_attack1");
			ARSystem.giveBuff(player, new Silence(player), 10);
			player.setVelocity(player.getLocation().getDirection().multiply(2.5).setY(0));
			cooldown[1] *= i*3;
			i = 1;
		} else {
			skill("c117_s1-attack"+i);
			skill("c117_attack"+i);
			ARSystem.giveBuff(player, new Silence(player), 10);
			
			if(i == 1) player.setVelocity(player.getLocation().getDirection().multiply(2.5).setY(0));
			if(i == 2) ARSystem.giveBuff(player, new Stun(player), 10);
			if(i == 3) player.setVelocity(new Vector(0,0.8,0));
			
			i++;
			if(i > 3) {
				cooldown[1] *= 10;
				i = 1;
			} else {
				cooldown[1] = (float) (setcooldown[1] * (skillmult+sskillmult));
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps && sk2 <= 0) {
			cooldown[2] = 0;
			return false;
		}
		ARSystem.playSound((Entity)player, "c117s2");
		if(isps) {
			sk2--;
			skill("c117_spawn2");
			skill("c117_s2");
			skill("c117_s22");
			sk2t = 0;
			cooldown[2] = 0.2f;
		} else {
			skill("c117_spawn");
			skill("c117_s2");
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c117s3");
		sk3 = 20;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.giveBuff(player, new Stun(player), 60);
		ARSystem.giveBuff(player, new Silence(player), 60);
		ARSystem.playSound((Entity)player, "c117s4");
		skill("c117_attack4");
		skill("c117_s4");
		return true;
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			target.setVelocity(new Vector(0,0.7,0));
		}
		if(n.equals("2")) {
			Location loc = target.getLocation();
			loc = ULocal.lookAt(loc, player.getLocation());
			target.setVelocity(target.getVelocity().add(loc.getDirection().multiply(-3).setY(0)));
			LivingEntity t = target;
			delay(()->{
				ARSystem.addBuff(t, new Stun(t), 20);
				ARSystem.addBuff(t, new Silence(t), 160);
			},10);
		}
	}
	
	@Override
	public boolean tick() {
		if(count == 0) count = Rule.c.keySet().size();
		double sk = skillmult + sskillmult;
		if(pt > 0) pt--;
		if(tk%20 == 0) {
			if(adddamage > 1)scoreBoardText.add("&c ["+Main.GetText("c117:p1")+ "] : &f" + AMath.round(adddamage*100,0) +"%");
			if(isps) {
				if(sk2 >= 2) {
					scoreBoardText.add("&c ["+Main.GetText("c117:sk2")+ "] : &f" + sk2 + " (" + AMath.round(7.0f - sk2t*0.05, 2) +")");
				} else {
					scoreBoardText.add("&c ["+Main.GetText("c117:sk2")+ "] : &f" + sk2 + " (" + AMath.round(30.0f - sk2t*0.05, 2) +")");
				}
			}
		}
		
		if(loc != null && ULocal.isEqual(player.getLocation(), loc)) {
			plt++;
		} else {
			loc = player.getLocation();
			plt = 0;
		}
		
		if(sk > 1.5) {
			adddamage += (sk - 1.5);
			sskillmult -= (sk - 1.5);
		}
		if(sk3 >0) sk3--;
		
		if(isps && sk2 < 3) {
			sk2t++;
			if(sk >= 2) {
				if(sk2t > 600) {
					sk2t = 0;
					sk2++;
				}
			} else {
				if(sk2t > 140) {
					sk2t = 0;
					sk2++;
				}
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player) {
			ps++;
			if(ps >= Math.max(3,count/2) && !isps && s_kill > 0 && skillCooldown(0)) {
				spskillon();
				spskillen();
				skillmult += 0.5;
				Rule.buffmanager.selectBuffValue(player, "barrier", 10);
			}
		}
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(sk3 > 0) {
			Rule.playerinfo.get(player).tropy(117, 1);
			sk3 = 0;
			cooldown[3] = 0;
			skill("c117_s3");
			player.teleport(ULocal.offset(player.getLocation(), new Vector(0,0,1)));
			ARSystem.heal(player, 6);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * adddamage);
		} else {
			if(plt > 3&& e.getDamager().getLocation().distance(player.getLocation()) > 5) {
				if(pt <= 0) {
					pt = 100;
					ARSystem.playSound((Entity)player, "c117p");
				}
				ARSystem.giveBuff(player, new Stun(player), 1);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(sk3 > 0) {
				sk3 = 0;
				cooldown[3] = 0;
				skill("c117_s3");
				player.teleport(ULocal.offset(player.getLocation(), new Vector(0,0,1)));
				e.setDamage(0);
				ARSystem.heal(player, 6);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
