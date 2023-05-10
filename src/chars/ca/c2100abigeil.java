package chars.ca;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c2100abigeil extends c00main{
	int ticks = 0;
	HashMap<Entity,Integer> fear = new HashMap<Entity,Integer>();
	Entity witch = null;
	
	public c2100abigeil(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1021;
		load();
		text();
		
		skill("c21_b2");
		repset();
	}
	public void passive() {
		if(ARSystem.isGameMode("lobotomy")) {
			skillmult += 0.01;
			player.setMaxHealth(player.getMaxHealth()+1);
		} else {
			skillmult += 0.05;
			player.setMaxHealth(player.getMaxHealth()+2);
		}
	}
	@Override
	public boolean skill1() {
		passive();
		ARSystem.playSound((Entity)player, "c1021s1");
		ARSystem.heal(player, player.getMaxHealth()*0.3);
		ARSystem.addBuff(player,new Nodamage(player), 60);
		ARSystem.addBuff(player,new Silence(player), 60);
		
		for(Entity e : ARSystem.box(player, new Vector(8,6,8), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			addFear(e, 6);
			ARSystem.addBuff(en,new Stun(en), 60);
			Location loc = en.getLocation();
			loc.add(0,1,0);
			loc.setPitch(0);
			loc.setYaw(120);
			ARSystem.spellLocCast(player, loc, "c1021_s1_p");
			loc.setYaw(240);
			ARSystem.spellLocCast(player, loc, "c1021_s1_p");
			loc.setYaw(0);
			ARSystem.spellLocCast(player, loc, "c1021_s1_p");
			delay(()->{
				en.damage(player.getMaxHealth()*0.1,player);
				ARSystem.addBuff(en,new Panic(en), 120);
			},20);
		}
		for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TEAM)) {
			LivingEntity en = (LivingEntity)e;
			ARSystem.heal(en, player.getMaxHealth()*0.3);
			ARSystem.spellCast(player, en, "c21_s");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		passive();
		ARSystem.playSoundAll("c1021s2");
		for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
			LivingEntity en = (LivingEntity)e;
			addFear(e, 9);
			ARSystem.addBuff(en,new Panic(en), 180);
			ARSystem.addBuff(en,new Rampage(en), 180);
		}
		for(int i=0;i<20;i++) {
			ARSystem.spellLocCast(player, Map.randomLoc(), "c1021_s2_p");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		passive();
		ARSystem.addBuff(player,new Nodamage(player), 40);
		ARSystem.addBuff(player,new Silence(player), 40);
		ARSystem.addBuff(player,new Stun(player), 40);
		ARSystem.playSoundAll("c1021s3");
		ARSystem.spellLocCast(player, player.getLocation().add(0,0.5,0),"c1021_s3_p");
		ARSystem.spellLocCast(player, player.getLocation().add(0,0.5,0),"c1021_s3_p2");
		for(int i=0;i<6;i++) {
			delay(()->{
				ARSystem.spellLocCast(player, player.getLocation().add(1 - (AMath.random(20)*0.1),(AMath.random(40)*0.1),1 - (AMath.random(20)*0.1)), "c1021_s3_p3");
			},3+i*3);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			addFear(target, 5);
			ARSystem.addBuff(target,new Panic(target), 100);
		}
		if(n.equals("2")) {
			if(Rule.c.get(target) != null) Rule.c.put((Player) target, new c000humen((Player) target, plugin, null));
			Skill.remove(target, player);
		}
	}
	
	public void sp(Entity e) {
		spskillon();
		spskillen();
		
		ARSystem.spellCast(player, e, "look1");
		ARSystem.spellCast(player, e, "look2");

		ARSystem.giveBuff((LivingEntity) e, new Noattack((LivingEntity) e), 460);
		ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 460);
		ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 460);
		
		ARSystem.giveBuff(player, new TimeStop(player), 460);
		skill("c21_b2");
		ARSystem.playSound((Entity)player,"c1021sp");
		skill("c1021_spd");
		delay(()->{
			skill("c1021_spd2");
		},40);
		delay(()->{
			skill("c1021_spd3");
		},90);
		delay(()->{
			skill("c1021_spd4");
			skill("c21_b3");
		},130);
		delay(()->{
			skill("c1021_spd5");
		},190);
		delay(()->{
			skill("c21_b3");
			Location loc = player.getLocation().add(0,1,0);
			ARSystem.spellLocCast(player,ULocal.offset(loc,new Vector(-1,0,0)),"c1021_sp_e");
			loc.setYaw(loc.getYaw()-22.5f);
			ARSystem.spellLocCast(player,ULocal.offset(loc,new Vector(-0.5,-0.5,1.5)),"c1021_sp_e");
			loc.setYaw(loc.getYaw()-22.5f);
			ARSystem.spellLocCast(player,ULocal.offset(loc,new Vector(0,-1,3)),"c1021_sp_e");
			loc.setYaw(loc.getYaw()+67.5f);
			ARSystem.spellLocCast(player,ULocal.offset(loc,new Vector(-0.5,-0.5,-1.5)),"c1021_sp_e");
			loc.setYaw(loc.getYaw()+22.5f);
			ARSystem.spellLocCast(player,ULocal.offset(loc,new Vector(0,-1,-3)),"c1021_sp_e");
		},230);
		delay(()->{
			skill("c1021_spe2");
		},270);
		delay(()->{
			
		},290);
		delay(()->{
			
		},330);
		delay(()->{
			
		},380);
		delay(()->{
			for(int i = 0; i<20;i++) {
				delay(()->{
					ARSystem.spellLocCast(player, player.getLocation().add(2 - (AMath.random(40)*0.1),(AMath.random(40)*0.1),2 - (AMath.random(40)*0.1)), "c1021_s3_p3");
					ARSystem.spellLocCast(player, player.getLocation().add(2 - (AMath.random(40)*0.1),(AMath.random(40)*0.1),2 - (AMath.random(40)*0.1)), "c1021_s3_p3");
				},i);
			}
		},420);
		delay(()->{
			skill("c21_sp2e81");
			skill("c21_b2");
		},450);
	}
	
	@Override
	public boolean tick() {
		if(psopen && tk%20 == 0){
			for(Entity p : fear.keySet()) {
				if(fear.get(p) > 0) {
					scoreBoardText.add("&c ["+Main.GetText("c1021:sk0")+ "] : "+p.getName() +":"+ fear.get(p));
				}
				if(p.isDead() || p instanceof Player && ((Player) p).getGameMode() != GameMode.ADVENTURE) {
					fear.put(p, 0);
				}
			}
		}
		return true;
	}
	
	public void addFear(Entity e,int i) {
		if(e instanceof LivingEntity) {
			if(fear.get(e) == null) {
				fear.put(e,0);
			} else {
				fear.put(e, fear.get(e)+i);
				if(fear.get(e) >= 70 && !ARSystem.isGameMode("lobotomy")) {
					fear.put(e,0);
					if(skillCooldown(0)) sp(e);
				}
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
