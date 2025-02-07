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
import buff.Airborne;
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
import event.Skill;
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

public class c7202plan extends c00main{	
	int p = 0;
	int s1 = 0;
	
	public c7202plan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2072;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c1072select");
	}


	@Override
	public boolean skill1() {
		s1++;
		if(s1 == 1) {
			ARSystem.playSound((Entity)player, "c72s11");
			player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(0.75));
			skill("c2072_s1-1");
			delay(()->{
				ARSystem.addBuff(player, new Airborne(player), 40);
			},10);
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(6,8,6), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(3,player);
					en.setVelocity(new Vector(0,0.8,0));
					delay(()->{
						ARSystem.addBuff(en, new Airborne(en), 40);
					},10);
					makerSkill(en, "1");
				}
			},10);
		}
		if(s1 == 2) {
			ARSystem.playSound((Entity)player, "c72s12");
			player.setVelocity(player.getLocation().getDirection().multiply(0.7).setY(0));
			skill("c2072_s1-2");
			delay(()->{
				ARSystem.addBuff(player, new Airborne(player), 20);
			},10);
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(4,player);
					makerSkill(en, "1");
					ARSystem.addBuff(en, new Airborne(en), 20);
				}
			},4);
		}
		if(s1 == 3) {
			ARSystem.playSound((Entity)player, "c72s22");
			player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(-2));
			skill("c2072_s1-3");
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(6,8,6), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(5,player);
					Rule.buffmanager.selectBuffTime(en, "stun", 0);
					Rule.buffmanager.selectBuffTime(en, "airborne", 0);
					delay(()->{
						en.setVelocity(player.getLocation().getDirection().multiply(2).setY(-1.5));
					},2);
					makerSkill(en, "1");
				}
			},10);
			cooldown[1] *= 12;
			s1 = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c1072db");
		for(int i =0;i<3;i++) delay(()->{skill("c2072_s2");},i*5);
		for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			for(int i =0;i<3;i++) {
				delay(()->{
					ARSystem.overheal(player, en.getHealth()*0.1);
					en.setNoDamageTicks(0);
					en.damage(en.getHealth()*0.1,player);
					en.setVelocity(ULocal.lookAt(en.getLocation(), player.getLocation()).getDirection().multiply(0.8));
				},5*i);
				delay(()->{
					ARSystem.giveBuff(en, new Fascination(en,player), 60, -0.12);
				},17);
			}
		}
		return true;
	}

	LivingEntity s3;
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1072s1");
		player.setGameMode(GameMode.SPECTATOR);
		s3 = null;
		Location l = player.getLocation();
		skill("c2072_s3");
		delay(()->{
			if(s3 != null) {
				ARSystem.spellCast(player, s3, "c2072_s3-2");
				ARSystem.playSound((Entity)player, "c72s3");
				ARSystem.giveBuff(s3, new Stun(s3), 55);
				ARSystem.giveBuff(s3, new Silence(s3), 55);
				delay(()->{
					s3.setNoDamageTicks(0);
					s3.damage(3,player);
					Location lc = s3.getLocation().clone().add(0,3.5,0); 
					for(int i =0; i<40; i++) delay(()->{s3.teleport(lc);},i);
				},15);
				for(int i =0; i<8; i++) {
					delay(()->{
						s3.setNoDamageTicks(0);
						s3.damage(1,player);
					},40+1*i);
				}
				delay(()->{
					ARSystem.playSound((Entity)player, "c72sp1");
					s3.setNoDamageTicks(0);
					s3.damage(5,player);
					s3.setVelocity(new Vector(AMath.random(10)*0.2 - 1,-2,AMath.random(10)*0.2 - 1));
				},60);
				delay(()->{
					player.teleport(l);
					player.setGameMode(GameMode.ADVENTURE);
				},70);
			} else {
				player.setGameMode(GameMode.ADVENTURE);
			}
		},20);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			delay(()->{
				double h = target.getMaxHealth()-target.getHealth();
				target.setMaxHealth(target.getMaxHealth()-(h*0.2));
				ARSystem.spellCast(player, target, "c2072_p");
			},2);
		}
		if(n.equals("3")) {
			s3 = target;
			ARSystem.giveBuff(target, new Stun(target), 50);
		}
	}
	
	@Override
	public boolean tick() {
		if(!isps && Rule.buffmanager.GetBuffValue(player, "plushp") > 100) {
			Rule.buffmanager.selectBuffValue(player, "plushp", 0);
			spskillon();
			spskillen();
			ARSystem.playSound(player, "c72select");
		}
		if(isps) {
			for(Player p : Rule.c.keySet()) {
				if(p == player) continue;
				ARSystem.giveBuff(p, new NoHeal(p), 2);
				double h = p.getMaxHealth()-p.getHealth();
				if(h > 0) {
					p.setMaxHealth(p.getMaxHealth()-h);
					player.setMaxHealth(player.getMaxHealth()+h);
					hp = (float)player.getMaxHealth();
					ARSystem.overheal(player, h);
					ARSystem.spellCast(player, p, "c2072_p");
				}
			}
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
			bampheal(e.getEntity());
			p++;
			if(p >= 3) {
				p = 0;
				e.setDamage(e.getDamage()*1.5f);
				ARSystem.overheal(player, e.getDamage());
			}
		} else {
			if(e.getDamager().getLocation().distance(player.getLocation()) > 10) {
				e.setDamage(0);
				ARSystem.playSound((Entity)player, "0miss");
			}
		}
		return true;
	}


	@Override
	public boolean skill9(){
		ARSystem.playSound((Entity)player,"c72db");
		return true;
	}
}
