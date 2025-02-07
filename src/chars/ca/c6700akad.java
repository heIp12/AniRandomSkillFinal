package chars.ca;

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
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c6700akad extends c00main{
	int s1 = 0;
	int s3 = 0;
	int sp = 0;
	
	public c6700akad(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1067;
		load();
		text();
		c = this;
	}
	
	@Override
	public void setStack(float f) {
		sp = (int)f;
		if(!isps && sp >= 5) {
			ARSystem.giveBuff(player, new TimeStop(player), 280);
			ARSystem.playSoundAll("c67sp");
			spskillon();
			spskillen();
		}
	}

	@Override
	public boolean skill1() {
		s1++;
		if(s1%2==1) {
			ARSystem.playerAddRotate(player,0,(float) -12);
			ARSystem.playSound((Entity)player, "c67s1",0.7f);
			skill("c1067_s1");
			cooldown[1] *= 0.33;
		} else {
			ARSystem.playerAddRotate(player,0,(float) -3);
			ARSystem.playSound((Entity)player, "c67s1");
			skill("c1067_s12");
			
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps) {
			ARSystem.playSound((Entity)player, "c1067s2");
			ARSystem.giveBuff(player, new Stun(player), 40);
			ARSystem.giveBuff(player, new Silence(player), 40);
			for(int i = 0; i<10; i++) {
				delay(()->{
					if(AMath.random(3) == 1)
						skill("c1067_s2");
					else if(AMath.random(2) == 1)
						skill("c1067_sp1");
					else 
						skill("c1067_sp2");
				},i*3);
			}
			delay(()->{
				skill("c1067_sp3");
			},35);
		} else {
			ARSystem.playSound((Entity)player, "c67s4");
			skill("c1067_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c67s3");
		s3 = 40;
		player.setGameMode(GameMode.SPECTATOR);
		player.setFlySpeed(0.1f);
		return true;
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(target.getHealth() * 0.3,player);
			
			Location loc = target.getLocation();
			loc = ULocal.lookAt(loc, player.getLocation());
			target.setVelocity(loc.getDirection().multiply(-0.5));
			delay(()->{
				ARSystem.giveBuff(target, new Stun(target), 10);
			},2);
			
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(6,player);
			ARSystem.giveBuff(target, new NoHeal(target), 100);
			
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			if(isps) {
				target.damage(2,player);
			} else {
				target.damage(8,player);
			}
			ARSystem.giveBuff(target, new Silence(target), 40);
		}
		if(n.equals("4")) {
			target.setNoDamageTicks(0);
			target.damage(14,player);
			ARSystem.giveBuff(target, new Silence(target), 40);
		}
		if(isps) {
			for(int i = 0; i<3; i++) {
				delay(()->{
					if(target.getMaxHealth() >= 1.5 && !(target instanceof Player && Rule.c.get(target) == null)) {
						ARSystem.spellLocCast(player, target.getLocation(), "c1067_sp4");
						target.setMaxHealth(target.getMaxHealth()-0.5);
						player.setMaxHealth(player.getMaxHealth()+0.5);
						hp = (float)player.getMaxHealth();
						ARSystem.overheal(player, 0.5);
					}
				},20+20*i);
			}
		}
	}

	
	@Override
	public boolean tick() {
		if(s3 > 0) {
			s3--;
			skill("c1067_s3");
			ARSystem.heal(player, player.getMaxHealth()*0.1);
			if(s3 <= 0) {
				player.setGameMode(GameMode.ADVENTURE);
			}
		}
		if(tk%3 == 0) {
			Entity e = ARSystem.boxSOne(player, new Vector(6,6,6), box.TARGET);
			if(e != null) {
				LivingEntity en = (LivingEntity)e;
				if(en.getHealth() <= 4 && !en.isDead()) {
					bampheal(en);
					Skill.death(en, player);
					frist_damage*= 1.1;
					frist_defence*= 0.9;
					setStack(sp+1);
				}
			}
		}
		if(ARSystem.AniRandomSkill != null && tk%20==0 && ARSystem.AniRandomSkill.time%30 == 0 && ARSystem.AniRandomSkill.time > 10 ) {
				ARSystem.playSound((Entity)player, "c67p");
				setcooldown[1]-=0.1;
				setcooldown[2]-=0.1;
				player.setMaxHealth(player.getMaxHealth()+5);
				ARSystem.heal(player, 5);
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("main:damage")+ "] : "+ AMath.round(frist_damage*100,0) +"%");
			scoreBoardText.add("&c ["+Main.GetText("main:defence")+ "] : "+ AMath.round(frist_defence*100,0) +"%");
		}
		return true;
	}
	public void bampheal(Entity e) {
		LivingEntity en = (LivingEntity)e;
		double hp = en.getMaxHealth();
		delay(()->{
			if((en instanceof Player && (Rule.c.get(en) == null || ((Player)en).getGameMode() != GameMode.ADVENTURE)) || !(en instanceof Player) && (en.isDead() || en.getHealth() < 1)) {
				ARSystem.spellCast(player, en, "bload");
				for(int i = 0; i<10; i++) ARSystem.spellLocCast(player, en.getLocation(), "cbamp");
				delay(()->{
					ARSystem.overheal(player, hp*2);
				},40);
			}
		},0);
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			bampheal((LivingEntity)e.getEntity());
		} else {
			
		}
		return true;
	}

	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player,"c67db");
		return true;
	}
}
