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

public class c123rin extends c00main{
	int p = 0;
	int rep = 0;
	int s2 = 0;
	int s3 = 0;
	int kd = 0;
	Vector move;
	
	List<Location> death = new ArrayList<>();
	
	public c123rin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 123;
		load();
		text();
		c = this;
		if(ARSystem.getReadyPlayer().size() <= 3) death.add(Map.randomLoc());
		if(ARSystem.getReadyPlayer().size() <= 5) death.add(Map.randomLoc());
		if(ARSystem.getReadyPlayer().size() <= 10) death.add(Map.randomLoc());
		
	}
	@Override
	public void setStack(float f) {
		p = (int)f;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c123s1");
		if(isps) {
			skill("c123_sp");
		} else {
			skill("c123_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c123s2");
		s2 = 40 + p*20;
		skill("c123_pr");
		skill("c123_p");
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c123s3");
		s3 = 12 +(p*2);
		if(s3 > 40) s3 = 40;
		
		move = player.getLocation().getDirection().multiply(1);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(4+p,player);
			ARSystem.heal(player, (4+p)*0.6);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(0.5+p*0.5,player);
		}
		if(n.equals("3")) {
			s3 = 0;
			if(s2 > 0) {
				Wound wn = new Wound(target);
				wn.setEffect("c123_s2e");
				wn.setDelay(player, 20, 0);
				ARSystem.giveBuff(target, wn, s2 ,0.5+p*0.5);
				s2 = 0;
			}
			target.setNoDamageTicks(0);
			target.damage(5,player);
			player.setVelocity(new Vector(0,0,0));
			target.setVelocity(player.getLocation().getDirection().multiply(5));
			delay(()->{
				Location lc = target.getLocation().clone();
				delay(()->{
					if(lc.distance(target.getLocation()) < 0.5) {
						ARSystem.giveBuff(target, new Stun(target), 20 + p*10);
						ARSystem.giveBuff(target, new Silence(target), 20 + p*10);
					}
				},1);
			},5);
		}
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c123:p1")+ "] : &f" + p );
			List<Location> removelist = new ArrayList<>();
			for(Location loc : death) {
				scoreBoardText.add("&c ["+Main.GetText("c123:p1")+ "]  &e "+ AMath.round(player.getLocation().distance(loc),2)
				+"["+ULocal.getloc(player.getLocation(),loc)+"]");
				if(loc.distance(player.getLocation()) < 3) {
					removelist.add(loc);
				}
			}
			for(Location list : removelist) {
				death.remove(list);
				p++;
				player.setMaxHealth(player.getMaxHealth()+5);
				ARSystem.heal(player, 5);
				if(p >= 7 && !isps) {
					spskillon();
					spskillen();
					ARSystem.playSound((Entity)player, "c123sp");
				} else {
					ARSystem.playSound((Entity)player, "c123p"+AMath.random(3));
				}
			}
		}
		if(tk%10 == 0) {
			for(Location loc : death) {
				ARSystem.spellLocCast(player, loc, "c123_death");
			}
		}
		if(s3 > 0) {
			s3--;
			player.setVelocity(move);
			for(Entity e : ARSystem.locEntity(ULocal.offset(player.getLocation(), new Vector(2,0.5,0)), new Vector(3,3,3), player)) {
				makerSkill((LivingEntity)e,"3");
			}
		}
		if(s2 > 0) {
			s2--;
			if(s2%2 == 0) {
				skill("c123_s2");
			}
		}
		rep--;
		if(rep <= 0) {
			rep = 200;
			skill("c123_pr");
			skill("c123_p");
		}
		if(kd > 0) kd--;
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player) {
			death.add(p.getLocation());
			if(kd <= 0) {
				kd = 80;
				ARSystem.playSound(player, "c123p4");
			}
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

		}
		return true;
	}
}
