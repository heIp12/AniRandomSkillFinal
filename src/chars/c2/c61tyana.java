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
import com.nisovin.magicspells.variables.meta.NoDamageTicksVariable;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c61tyana extends c00main{
	int mana = 0;
	float speed = 0.01f;
	boolean fly = false;
	int p = 2;
	
	public c61tyana(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 61;
		load();
		text();
		c = this;
		mana = 90;
		if(ARSystem.isGameMode("lobotomy")) this.p = 20;
	}
	
	@Override
	public void setStack(float f) {
		mana = (int) f;
	}
	@Override
	public boolean skill1() {
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 30 && s_kill >= 2 && skillCooldown(0)) {
			skill0();
		} else {
			if(mana > 8) {
				mana-=8;
				skill("c61_s1");
				ARSystem.playSound((Entity)player, "c61s1");
				ARSystem.playerAddRotate(player,0,(float) -40);
			} else {
				cooldown[1] = 0;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(mana > 14) {
			mana-=14;
			skill("c61_s2");
			ARSystem.playSound((Entity)player, "c61s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		fly = !fly;
		if(fly) speed = 0.1f;
		
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(mana > 5 && fly) {
			mana-=5;
			player.setVelocity(new Vector(3-AMath.random(6),3-AMath.random(6),3-AMath.random(6)).multiply(10));
			speed = 0.6f;
			ARSystem.addBuff(player, new Nodamage(player), 6);
		} else {
			cooldown[4] = 0;
		}
		return true;
	}
	
	
	public boolean skill0() {
		spskillen();
		spskillon();
		fly = false;
		ARSystem.playSoundAll("c61sp1");
		Location loc = player.getLocation().clone().add(0,0.4,0);
		
		ARSystem.addBuff(player, new TimeStop(player), 440);
		delay(()->{
			loc.setPitch(20);
			ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(-1.5,0,-1)), "c61_sp");
		},20);
		delay(()->{
			Location locs = ULocal.offset(loc, new Vector(-3,0.2,-3));
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,-1)), "c61_sp");
		},30);
		delay(()->{
			Location locs = ULocal.offset(loc, new Vector(-3,0.2,3));
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,-1)), "c61_sp");
		},30);
		
		Location loco = ULocal.offset(loc, new Vector(-5,1,9));
		delay(()->{
			loco.setPitch(20);
			ARSystem.spellLocCast(player, ULocal.offset(loco, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(loco, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(loco, new Vector(-1.5,0,-1)), "c61_sp");
		},40);
		delay(()->{
			Location locs = ULocal.offset(loco, new Vector(-3,0.2,-3));
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,-1)), "c61_sp");
		},40);
		delay(()->{
			Location locs = ULocal.offset(loco, new Vector(-3,0.2,3));
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,-1)), "c61_sp");
		},40);
		
		Location locz = ULocal.offset(loc, new Vector(-5,1,-9));
		delay(()->{
			locz.setPitch(20);
			ARSystem.spellLocCast(player, ULocal.offset(locz, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locz, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locz, new Vector(-1.5,0,-1)), "c61_sp");
		},40);
		delay(()->{
			Location locs = ULocal.offset(locz, new Vector(-3,0.2,-3));
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,-1)), "c61_sp");
		},40);
		delay(()->{
			Location locs = ULocal.offset(locz, new Vector(-3,0.2,3));
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(0,0,0)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,1)), "c61_sp");
			ARSystem.spellLocCast(player, ULocal.offset(locs, new Vector(-1.5,0,-1)), "c61_sp");
		},40);
		delay(()->{
			ARSystem.playSoundAll("c61sp2");
		},180);
		delay(()->{
			skill("c61_sp0");
			ARSystem.addBuff(player, new Stun(player), 100);
			ARSystem.addBuff(player, new Silence(player), 100);
			ARSystem.addBuff(player, new Nodamage(player), 100);
		},440);
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(fly) {
			if(fly && mana <= 0) {
				fly = false;
				return false;
			}
			player.setFallDistance(0);
			if(tk%20==0) mana--;
			if(player.isSneaking()) {
				player.setVelocity(new Vector(0,0.01,0));
				speed = 0.01f;
			} else {
				player.setVelocity(player.getLocation().getDirection().multiply(speed));
				speed+=speed*0.03;
				if(speed > 1.3) {
					speed = 1.3f;
				}
			}
		}
		if(tk%20 == 0) {
			if(ARSystem.isGameMode("lobotomy") && AMath.random(2) == 1) mana+=1;
			scoreBoardText.add("&c ["+Main.GetText("c61:ps")+ "] : "+ mana);
			scoreBoardText.add("&c [speed] : "+ AMath.round(speed,2));
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(s_kill > 3) {
			Rule.playerinfo.get(player).tropy(61,1);
		}
		if(e == player) {
			mana += 45;
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
		} else {
			if(p > 0) {
				p--;
				e.setDamage(0);
				e.setCancelled(true);
				skill("barrier");
			}
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.6);
		}
		return true;
	}
}
