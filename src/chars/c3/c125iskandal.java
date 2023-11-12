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
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Style;
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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import chars.c2.c88week;
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
import util.ItemCreate;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c125iskandal extends c00main{
	Horse horse;
	Player uma;
	int s2 = 0;
	float speed = 0;

	TargetMap<LivingEntity, Double> p = new TargetMap<>();
	TargetMap<Double, Double> time = new TargetMap<>();
	TargetMap<LivingEntity, Double> s2t = new TargetMap<>();
	
	Location loc;
	boolean sp = false;
	
	public c125iskandal(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 125;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c125s1"+AMath.random(3));
		skill("c125_s1");
		for(Entity e : ARSystem.box(player, new Vector(7,5,7), box.TARGET)) {
			LivingEntity en = (LivingEntity) e;
			en.setNoDamageTicks(0);
			en.damage(2,player);
		}
		
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c125s2");
		if(uma != null) {
			uma = null;
			((c88week)Rule.c.get(uma)).addspeed -= 5;
			uma.setPassenger(null);
		}
		
		for(Entity e : ARSystem.box(player, new Vector(12,8,12), box.ALL)) {
			if(Rule.c.get(e) instanceof c88week) {
				uma = (Player)e;
				uma.addPassenger(player);
				((c88week)Rule.c.get(e)).addspeed += 5;
				p.add(uma, 40);
			}
		}
		if(uma == null) {
			if(horse != null) horse.remove();
			
			horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
			horse.setAge(30000);
			horse.setStyle(Style.BLACK_DOTS);
			horse.setOwner(player);
			horse.addPassenger(player);
			horse.getInventory().setItem(0, ItemCreate.Item(329));
			horse.getInventory().setItem(1, ItemCreate.Item(418));
		}
		s2 = 200;
		return true;
	}
	
	
	public void sp() {
		player.performCommand("tm anitext all TITLE true 20 c125:sk0/c125:t1");
		spskillon();
		spskillen();
		
		isps = true;
		if(ARSystem.AniRandomSkill != null) ARSystem.AniRandomSkill.modes.clear();
		ARSystem.playSoundAll("c125sp");
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getGameMode() != GameMode.SPECTATOR) {
				ARSystem.giveBuff(p, new TimeStop(p), 300);
				ARSystem.giveBuff(p, new Silence(p), 200);
				ARSystem.giveBuff(p, new Stun(p), 100);
			}
		}
		ARSystem.giveBuff(player, new Nodamage(player), 200);
		delay(()->{
			Map.getMapinfo(1001);

			Location ploc = Map.getCenter();
			ploc.setY(31);
			ploc.setYaw(0);
			ploc.setPitch(0);
			player.teleport(ploc);
			player.setMaxHealth(player.getMaxHealth() * 2);
			ARSystem.heal(player, 999);
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p != player) {
					Location loc = ploc.clone();
					loc = ULocal.offset(loc, new Vector(6+AMath.random(2), 0, 5-(AMath.random(100)*0.1)));
					loc = ULocal.lookAt(loc, ploc);
					p.teleport(loc);
				}
			}
			
			cooldown[2] = 6;
			if(horse != null) horse.remove();
			
			horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
			horse.setAge(30000);
			horse.setStyle(Style.BLACK_DOTS);
			horse.setOwner(player);
			horse.addPassenger(player);
			horse.getInventory().setItem(0, ItemCreate.Item(329));
			horse.getInventory().setItem(1, ItemCreate.Item(418));
			ARSystem.giveBuff(horse, new TimeStop(horse), 100);
			s2 = 100;
			
			delay(()->{
				for(int j = 0; j < 5; j++) {
					int jj = j;
					delay(()->{
						for(int i=0; i<21; i++) {
							Location loc = player.getLocation();
							loc.setPitch(0);
							loc = ULocal.offset(loc, new Vector(-(jj+1) + (10-AMath.random(20))*0.02f , 0, (10-i)).multiply(3));
							ARSystem.spellLocCast(player, loc, "c125_sp1");
						}
					},5*j);
				}
				
				delay(()->{
					ARSystem.playSoundAll("c125sp2");
				},160);
				
				delay(()->{
					sp = true;
				},300);
			},20);
		},120);
	}

	@Override
	public boolean tick() {
		
		if(tk%20 == 0 && Rule.c.size() >= 2) {
			boolean isTeam = false;
			for(Player p : Rule.c.keySet()) {
				if(p != player && Rule.team.isTeam(player,p)) {
					isTeam = true;
					break;
				}
				for(Player pl : Rule.c.keySet()) {
					if(pl != player && p != player) {
						if(!Rule.team.isTeam(p,pl)) {
							isTeam = true;
							break;
						}
					}
				}
			}
			if(!isTeam && skillCooldown(0)) {
				sp();
			}
		}
		if(loc != null) {
			time.add(loc.distance(player.getLocation()), 1);
			speed = 0;
			for(Double db : time.get().keySet()) speed+=db;
		}
		if(tk%2 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(12,5,12), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				if(p.get(en) == null || p.get(en) <= 0) {
					p.add(en, 20);
					ARSystem.giveBuff(en, new Panic(en), 60);
				}
			}
		}
		if(tk%20 == 0) scoreBoardText.add("&c ["+Main.GetText("c125:p1")+ "] : &f"+ AMath.round(speed, 2));
		if(s2 > 0) {
			s2--;
			if(uma == null) horse.addPotionEffect(PotionEffectType.SPEED.createEffect(20,(int)(speed/3.5)));
			if(s2 == 0 || (uma == null && horse.getPassenger() == null)) {
				s2 = 0;
				if(horse != null) horse.remove();
				uma.removePassenger(player);
				((c88week)Rule.c.get(uma)).addspeed -= 5;
				uma = null;
				ARSystem.giveBuff(player, new Nodamage(player), 60);
			} else {
				for(Entity e : ARSystem.box(player, new Vector(4,5,4), box.TARGET)) {
					LivingEntity en = (LivingEntity) e;
					if(s2t.get(en) == null || s2t.get(en) <= 0) {
						s2t.add(en, 4);
						en.setNoDamageTicks(0);
						en.damage(3,player);
					}
				}
			}
		}
		
		
		for(Double db : time.get().keySet()) {
			time.add(db, -0.05);
			if(time.get(db) <= 0) {
				time.removeAdd(db);
			}
		}
		for(LivingEntity db : s2t.get().keySet()) {
			s2t.add(db, -0.05);
			if(s2t.get(db) <= 0) {
				s2t.removeAdd(db);
			}
		}
		for(LivingEntity db : p.get().keySet()) {
			p.add(db, -0.05);
			if(p.get(db) <= 0) {
				p.removeAdd(db);
			}
		}
		if(sp && (tk%10 == 1 || (tk%2 == 1 && s2 > 0))) {
			ARSystem.spellLocCast(player, Map.randomLoc(), "c125_sp4");
		}
		time.removes();
		s2t.removes();
		p.removes();
		if(player != null) loc = player.getLocation();
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(e.getEntity() == uma || e.getDamager() == uma) e.setDamage(0);
		
		if(isAttack) {
			e.setDamage(e.getDamage() + (e.getDamage()* (speed*0.1)));
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c60gil) {
					is = "g";
					break;
				}
				if(Rule.c.get(e) instanceof c111artorya) {
					is = "s";
					break;
				}
			}
		}

		if(is.equals("g")) {
			ARSystem.playSound((Entity)player, "c125gil");
		} else if(is.equals("s")) {
			ARSystem.playSound((Entity)player, "c125saber");
		} else {
			ARSystem.playSound((Entity)player, "c125db");
		}
		
		return true;
	}
}
