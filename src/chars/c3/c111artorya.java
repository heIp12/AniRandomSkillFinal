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

public class c111artorya extends c00main{
	int stack = 0;
	int sk1 = 0;
	boolean start = false;
	Entity target = null;
	
	boolean remove = false;
	
	float s3 = 1;
	
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c111artorya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 111;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c111s3");
		sk1 = 30;
		target = null;
		remove = false;
		if(s3 == -1) {
			s3 = 1+ stack*0.01f;
			stack = 0;
		}
		
		ARSystem.giveBuff(player, new Stun(player), 30);
		ARSystem.giveBuff(player, new Silence(player), 30);
		
		skill("c111_s3-1");
		delay(()->{
			if(target != null) {
				ARSystem.heal(player, 9999);
				player.teleport(ULocal.lookAt(player.getLocation(), target.getLocation()));
				skill("c111_s3-2");
				ARSystem.playSound((Entity)player, "c111s32");
			}
			delay(()->{
				s3 = 1;
			},10);
		},30);
		return true;
	}
	
	@Override
	public boolean skill2() {
		stack += 100;
		skill("c111_s2");
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c111s1");
		s3 = -1;
		return true;
	}

	public void skillsp() {
		Rule.playerinfo.get(player).tropy(111, 1);
		for(Player p : Rule.c.keySet()) {
			ARSystem.giveBuff(p, new TimeStop(p), 200);
			if(p != player) {
				Location loc = player.getLocation();
				loc.setPitch(0);
				loc = ULocal.offset(loc, new Vector(3,0,0));
				loc = ULocal.lookAt(loc, player.getLocation());
				p.teleport(loc);
			}
		}
		ARSystem.playSoundAll("c111sp2");
		skill("c111_sp");
		delay(()->{
			ARSystem.playSoundAll("c111sp");
			skill("c111_spl");
			delay(()->{
				Skill.win(player);
			},60);
		},100);
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			if(remove) {
				target.damage(16 * s3,player);
			} else {
				target.damage(8 * s3,player);
			}
		}
	}
	
	@Override
	public boolean firsttick() {
		if(start && sk1 <= 0) {
			if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF)) {
					if(stack > 20) {
						stack -=20;
						buff.setTime(0);
					}
				}
			}
		}
		
		return false;
	}
	@Override
	public boolean tick() {
		start = true;
		if(sk1 > 0) sk1--;
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c111:p")+ "] : &f" + stack +"");
		}
		return true;
	}

	
	@Override
	public boolean remove(Entity caster) {
		if(sk1 > 0) {
			target = caster;
			remove = true;
			return false;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
		} else {
			if(sk1 > 0) {
				target = e.getDamager();
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(e.getDamage() >= player.getMaxHealth()) {
				e.setDamage(player.getMaxHealth() - 1);
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
				if(Rule.c.get(e) instanceof c30siro) {
					is = "siro";
					break;
				}
				if(Rule.c.get(e) instanceof c60gil) {
					is = "gil";
					break;
				}
			}
		}
		
		if(is.equals("siro")) {
			ARSystem.playSound((Entity)player, "c111siro");
		} else if(is.equals("gil")) {
			ARSystem.playSound((Entity)player, "c111gil");
		} else {
			ARSystem.playSound((Entity)player, "c111db");
		}
		
		return true;
	}
}
