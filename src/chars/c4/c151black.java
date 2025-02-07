package chars.c4;

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
import buff.Boom;
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
import buffs.BuffBase;
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

public class c151black extends c00main{
	int stack = 0;
	LivingEntity en;
	int s1stack = 5;
	Location s2l;
	int s2 = 0;
	int p = 0;
	float heal = 0;
	
	float s4range = 0;
	float s4yaw = 0;
	float s4StartYaw = 0;
	
	@Override
	public void setStack(float f) {
		s1stack = (int)f;
	}
	
	public c151black(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 151;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(isps) {
			Vector vt = player.getLocation().getDirection().multiply(1.5f);
			if(vt.getY() > 2) vt.setY(2);
			player.setVelocity(vt);
			delay(()->{
				skill("c151_s1-2");
				ARSystem.playSound((Entity)player, "c151s1");
			},10);
			if(cooldown[1] < skillmult+sskillmult) {
				cooldown[1] = (int)(skillmult+sskillmult);
			}
		} else {
			skill("c151_s1");
			ARSystem.playSound((Entity)player, "c151s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps) {
			ARSystem.playSound((Entity)player, "c151s2");
			s2 = s1stack*4;
			s1stack = 5;
		} else {
			if(en == null) {
				cooldown[2] = 0;
				return false;
			}
			ARSystem.playSound((Entity)player, "c151s2");
			s2l = player.getLocation();
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 20);
			delay(()->{
				s2 = s1stack*4;
				s1stack = 5;
			},10);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c151_s3");
		return true;
	}

	@Override
	public boolean skill4() {
		if(s4yaw > 0 || en == null) {
			cooldown[4] = 0;
			return false;
		}
		s4range = (float)en.getLocation().distance(player.getLocation());
		s4StartYaw = ULocal.lookAt(en.getLocation().clone(), player.getLocation()).getYaw();
		s4yaw = 360;
		return true;
	}
	
	@Override
	public boolean skill5() {
		if(stack >= 100 && !isps && skillCooldown(0)) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c151sp");
			ARSystem.giveBuff(player, new TimeStop(player), 40);
			s2l = null;
			heal = 0;
			cooldown[0] = 0;
		} else if(isps){
			isps = false;
			ARSystem.playSound((Entity)player, "c151sp2");
			ARSystem.heal(player, heal);
			heal = 0;
			s2l = player.getLocation();
			cooldown[0] = setcooldown[0];
		}
		return super.skill4();
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			s1stack++;
			player.setVelocity(player.getLocation().getDirection().multiply(-1.3f));
			
			target.setNoDamageTicks(0);
			target.damage(2,player);
		}
		if(n.equals("2")) {
			Boom boom = new Boom(target,player);
			boom.setDamage(0.3f, true, "c151_s3e1", "c151_s3e2");
			ARSystem.giveBuff(target, boom, 200, 6);
		}
	}
	

	@Override
	public boolean tick() {
		if(en != null && (en.getLocation().distance(player.getLocation()) > 30 || en.isDead() || (en instanceof Player && ((Player)en).getGameMode() == GameMode.SPECTATOR))) en = null;
		if(s1stack >= 30) Rule.playerinfo.get(this.player).tropy(151,1);
		
		if(en != null && s4yaw > 0) {
			Location lc = en.getLocation().clone();
			lc.setYaw(s4StartYaw+s4yaw);
			lc.setPitch(0);
			lc = ULocal.offset(lc, new Vector(s4range,0,0));
			lc = ULocal.lookAt(lc, en.getLocation());
			player.teleport(lc);
			s4yaw-= 8-Math.min(7,0.2*s4range);
			if(player.isSneaking()) s4yaw = 0;
			ARSystem.spellCast(player,en, "c151_s4");
			if(s4yaw <= 0) 	{
				s4yaw = 0;
				player.setVelocity(player.getLocation().getDirection().multiply(s4range*0.2));
			}
		}
		
		if(tk%20 == 0) {
			if(en != null) scoreBoardText.add("&c [target] : "+ en.getName());
			scoreBoardText.add("&c ["+Main.GetText("c151:sk2")+ "] : "+ s1stack);
			scoreBoardText.add("&c ["+Main.GetText("c151:ps")+ "] : "+ stack);
			if(isps) scoreBoardText.add("&c [heal] : "+ AMath.round(heal, 2));
		}
		if(s2 > 0) {
			if(isps) {
				if(s2%2 == 0) {
					ARSystem.playSound((Entity)player, "0gun5", 2);
					Location lc = player.getLocation().clone();
					if(AMath.random(3) <= 2 && s4yaw <= 0) {
						lc.setPitch(lc.getPitch()+ AMath.random(20)-10);
						lc.setYaw(lc.getYaw()+ AMath.random(20)-10);
						ARSystem.spellLocCast(player, lc, "c151_s2-2");
					} else {
						ARSystem.spellLocCast(player, lc, "c151_s2-2");
					}
				}
			} else {
				if(en == null || player.isSneaking()) {
					s1stack += (s2-10)/4;
					s2 = 0;
				} else {
					if(s4yaw <= 0 && s2l != null) {
						ARSystem.giveBuff(player, new Silence(player), 5);
						player.teleport(ULocal.lookAt(s2l, en.getLocation()));
					}
					if(s2%4 == 0) {
						ARSystem.playSound((Entity)player, "0gun5", 1.6f);
						skill("c151_s2");
					}
				}
			}
			s2--;
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) cooldown[4] = 0;
		if(p.getLocation().distance(player.getLocation()) <= 25) {
			ARSystem.giveBuff(player, new Panic(player), 120);
		}
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "panic")) {
			buff(Rule.buffmanager.selectBuff(player, "panic"));
		}
		if(Rule.buffmanager.OnBuffTime(player, "rampage")) {
			buff(Rule.buffmanager.selectBuff(player, "rampage"));
		}
		if(Rule.buffmanager.OnBuffTime(player, "sleep")) {
			ARSystem.giveBuff(player, new Rampage(player), Rule.buffmanager.GetBuffTime(player, "sleep"));
			Rule.buffmanager.selectBuffTime(player, "sleep",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "fascination")) {
			ARSystem.giveBuff(player, new Rampage(player), Rule.buffmanager.GetBuffTime(player, "fascination"));
			Rule.buffmanager.selectBuffTime(player, "fascination",0);
		}
		return true;
	}
	
	public void buff(Buff buff) {
		buff.addTime(-3);
		for(Entity e : ARSystem.box(player, new Vector(8,8,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			if(buff instanceof Panic) {
				ARSystem.addBuff(en, new Panic(en), 3);
			} else {
				ARSystem.addBuff(en, new Rampage(en), 3);
			}
		}
		hp += 0.1;
		player.setMaxHealth(hp);
		ARSystem.heal(player, 0.1);
		skillmult += 0.01f;
		stack += 1;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			en = (LivingEntity)e.getEntity();
			e.setDamage(e.getDamage() * (1+(stack*0.008)));
			if(isps) heal += e.getDamage()*0.5f;
		} else {
			e.setDamage(e.getDamage() * Math.max(0.3,1-(stack*0.001)));
		}
		return true;
	}
	
}
