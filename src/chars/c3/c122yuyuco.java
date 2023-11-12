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
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c44izuna;
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

public class c122yuyuco extends c00main{
	int stack = 0;
	Location loc;
	Location locf;
	Location locl;
	
	int tick = 0;
	int pt = 0;
	int playerC = 0;
	List<Player> killList = new ArrayList<Player>();
	
	List<Entity> removes = new ArrayList<Entity>();
	
	int s1 = 3;
	
	int sptick = 0;
	@Override
	public void setStack(float f) {
		stack = (int)f;
		upgrad();
	}
	
	public c122yuyuco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 122;
		load();
		text();
		c = this;
		
		String map = Text.get("c122:map"+ Map.mapid);
		loc = Map.randomLoc();
		if(map == null) {
			Location locs = Map.getCenter().clone();
			locs.setY(0);
			Location eloc = loc.clone();
			eloc.setY(0);
			int size = 0;
			while(locs.distance(eloc) > 16 + (size*0.002)) {
				loc = Map.randomLoc();
				eloc = loc.clone();
				eloc.setY(0);
				size++;
			}
		} else {
			String[] str = map.split(",");
			loc.setX(Float.parseFloat(str[0]));
			loc.setY(Float.parseFloat(str[1]));
			loc.setZ(Float.parseFloat(str[2]));
		}
		
		locf = loc.clone().add(-8,-20,-8);
		locl = loc.clone().add(8,20,8);
		ARSystem.spellLocCast(p,loc, "c122_p1");
	}
	
	void upgrad() {
		if(isps) return;
		skill("c122_pr");
		if(stack >= Math.max(playerC*1.5, 15)) {
			if(!isps) {
				Bgm.setForceBgm("c122");
				skill0();
			}
		}
		else if(stack >= 10) {
			ARSystem.spellLocCast(player, loc, "c122_p5");
		} else if(stack >= 7) {
			ARSystem.spellLocCast(player, loc, "c122_p4");
		} else if(stack >= 4) {
			ARSystem.spellLocCast(player, loc, "c122_p3");
		} else if(stack >= 1) {
			ARSystem.spellLocCast(player, loc, "c122_p2");
		} else {
			ARSystem.spellLocCast(player, loc, "c122_p1");
		}

		
	}
	
	public void remove(LivingEntity e) {
		if(Rule.buffmanager.GetBuffTime(player, "timestop") > 0) return;
		if(stack >= 10 && e instanceof Player && !killList.contains(e)) {
			killList.add((Player)e);
		}
		if(!removes.contains(e)) {
			removes.add(e);
			Skill.remove(e, player);

			ARSystem.spellLocCast(player, e.getLocation(), "c122_e");
			delay(()->{ARSystem.spellLocCast(player, loc.clone().add(0,1,0), "c122_pl");},2);
			delay(()->{
				stack++;
				ARSystem.heal(player, 8);
				upgrad();
			},60);
		}
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		float damage = 1;
		
		if(n.equals("1")) damage = 1;
		if(n.equals("2")) damage = 2;
		if(playerC >= 10) {
			damage *= (1.0 + stack*0.08);
		} else {
			damage *= (1.0 + stack*0.25);
		}
		if(stack >= 15) damage = 20;
		
		LivingEntity en = target;
		if(en.getHealth() - damage <= 1) {
			
			if(en instanceof Player) {
				remove(en);
			} else {
				Skill.remove(en, player);
				ARSystem.spellLocCast(player, target.getLocation(), "c122_e");
				delay(()->{ARSystem.spellLocCast(player, loc.clone().add(0,1,0), "c122_pl");},2);
				delay(()->{
					stack++;
					ARSystem.heal(player, 8);
					upgrad();
				},60);
			}
		} else {
			en.setHealth(en.getHealth() - damage);
			s_damage += damage;
		}
	}

	@Override
	public boolean skill1() {
		float r = 90/s1;
		
		int color = AMath.random(2);
		for(int i =0;i<s1; i++) {
			Location l = player.getLocation();
			l.setYaw(-r*(s1/2) + i*r + l.getYaw() + r/4);
			ARSystem.spellLocCast(player, l, "c122_s1-"+color);
		}
		ARSystem.playSound((Entity)player, "c122s1"+ (s1-1)/2);
		
		s1+=2;
		if(s1 >= 11) s1 = 3;
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c122s2");
		for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
			Location loc = e.getLocation();
			loc = ULocal.lookAt(loc, player.getLocation());
			e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(-2)).setY(0.2));
		}
		skill("c122_s2");
		delay(()->{
			skill("c122_s2-2");
		},5);
		delay(()->{
			skill("c122_s2");
		},10);
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.giveBuff(player, new Stun(player), 100);
		ARSystem.giveBuff(player, new Silence(player), 100);
		ARSystem.playSound((Entity)player, "c122s3");
		skill("c122_s3");
		return true;
	}

	
	public boolean skill0(){
		spskillon();
		spskillen();
		ARSystem.playSoundAll("c122sp");
		skill("c122_pr");
		ARSystem.spellLocCast(player,loc,"c122_sp");
		player.setGameMode(GameMode.SPECTATOR);
		sptick = 1320;
		
		for(Player p : Rule.c.keySet()) {
			if(p.getLocation().distance(loc) > 17) {
				Location locs = ULocal.lookAt(loc, p.getLocation());
				locs.add(locs.getDirection().multiply(15));
				
				Vector l1 = new Vector(loc.getX(),loc.getY(),loc.getZ());
				Vector l2 = new Vector(locs.getX(),locs.getY(),locs.getZ());
				Vector l3 = new Vector(p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
				Vector l4 = ULocal.closestPoint(l1,l2,l3); 
				locs.setX(l4.getX());
				locs.setY(l4.getY());
				locs.setZ(l4.getZ());
				p.teleport(locs);
			}
			ARSystem.giveBuff(p, new TimeStop(p), 140);
			ARSystem.giveBuff(p, new Nodamage(player), sptick);
			ARSystem.giveBuff(p, new Silence(player), sptick);
			ARSystem.giveBuff(p, new Noattack(player), sptick);
		}
		ARSystem.giveBuff(player, new TimeStop(player), 200);
		
		return false;
	}
	
	@Override
	public boolean tick() {
		if(playerC == 0) playerC = Rule.c.size();
		if(tk%20 == 0) scoreBoardText.add("&c ["+Main.GetText("c122:p1")+ "] : &f" + stack);
		if(tk%5 == 0) {
			for(Player p : killList) {
				if(Rule.c.get(p) != null) {
					ARSystem.playSound(p, "c122remove");
					Skill.death(p, player);
					delay(()->{
						if(Rule.c.get(p) != null) {
							Skill.remove(p, player);
						}
					},4);
				}
			}
		}
		if(!isps) {
			if(!ULocal.BoxIn(locf, locl, player.getLocation())) {
				player.teleport(ULocal.BoxNear(locf, locl, player.getLocation()));
				pt+=3;
				if(pt > 10) {
					player.teleport(loc);
				}
			} else {
				if(pt > 0) pt -= 1;
			}
			if(tick%100 == 0) {
				upgrad();
			}
			tick++;
			
		}
		if(isps) {
			if(sptick == 1320) {
				ARSystem.spellLocCast(player, loc, "c122_p5");
				ARSystem.spellLocCast(player, loc, "c122_p6");
			}
			sptick--;
			for(Player p : Rule.c.keySet()) {
				if(p != player && p.getLocation().distance(loc) > 17) {
					Location locs = ULocal.lookAt(loc, p.getLocation());
					locs.add(locs.getDirection().multiply(15));
					
					Vector l1 = new Vector(loc.getX(),loc.getY(),loc.getZ());
					Vector l2 = new Vector(locs.getX(),locs.getY(),locs.getZ());
					Vector l3 = new Vector(p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
					Vector l4 = ULocal.closestPoint(l1,l2,l3); 
					locs.setX(l4.getX());
					locs.setY(l4.getY());
					locs.setZ(l4.getZ());
					p.teleport(locs);
				}
			}
			int rd = AMath.random(10);
			if(sptick < 800) rd = AMath.random(7);
			if(sptick < 400) rd = AMath.random(5);
			if(sptick < 200) rd = 2;
			
			int pd = 30;
			if(sptick < 1000) pd = 20;
			if(sptick < 700) pd = 15;
			if(sptick < 400) pd = 10;
			
			if(sptick%40 == 0 && rd <= 2) {
				Location locs = loc.clone();
				if(AMath.random(4) <= 2) locs.setPitch(locs.getPitch()+90);
				ARSystem.spellLocCast(player, locs, "c122_sp-"+AMath.random(3));
			}
			else if(sptick%pd == 0) {
				int max = 3;
				for(Entity e : ARSystem.locEntity(loc, new Vector(15,15,15), player)) {
					Location loc = this.loc.clone();
					loc = ULocal.lookAt(loc, e.getLocation());
					ARSystem.spellLocCast(player, loc, "c122_sp1");
					max--;
					if(max <= 0) break;
				}
			}
			if(sptick <= 0) {
				Skill.death(player, player);
			}
			if(sptick%400 == 0) {
				ARSystem.spellLocCast(player, loc, "c122_sp-"+(AMath.random(2)+3));
			}
		}
		if(!Map.inMap(loc)) {
			loc = Map.randomLoc();
			locf = loc.clone().add(-8,-20,-8);
			locl = loc.clone().add(8,20,8);
			ARSystem.spellLocCast(player ,loc, "c122_p1");
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p !=player && p.getLocation().distance(loc) < 15) {
			ARSystem.spellLocCast(player, p.getLocation(), "c122_e");
			delay(()->{ARSystem.spellLocCast(player, loc.clone().add(0,1,0), "c122_pl");},2);
			delay(()->{
				stack++;
				ARSystem.heal(player, 8);
				upgrad();
			},60);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * (1.0 + stack*0.12));
			LivingEntity en = (LivingEntity) e.getEntity();
			if(en.getHealth() - e.getDamage() <= 1) {
				if(e.getEntity() instanceof Player) {
					remove(en);
				} else {
					ARSystem.spellLocCast(player, en.getLocation(), "c122_e");
					delay(()->{ARSystem.spellLocCast(player, loc.clone().add(0,1,0), "c122_pl");},2);
					delay(()->{
						stack++;
						ARSystem.heal(player, 8);
						upgrad();
					},60);
					Skill.remove(en, player);
				}
			} else {
				en.setHealth(en.getHealth() - e.getDamage());
				s_damage += e.getDamage();
			}
			e.setDamage(0);
			e.setCancelled(true);
			return false;
		} else {
			if(Rule.c.get(e.getDamager()) != null) {
				if(Rule.c.get(e.getDamager()).number%1000 == 9) {
					e.setDamage(0);
					e.setCancelled(true);
					return false;
				}
			}
			e.setDamage(e.getDamage() * (1.0 - Math.min(0.4,stack*0.06)));
		}
		return true;
	}
	@Override
	protected boolean skill9() {
		for(c00main m : Rule.c.values()) {
			if(m.number%1000 == 9 && m.player.getLocation().distance(player.getLocation()) > 10) {
				ARSystem.playSound((Entity)player, "c122youmu1");
				return true;
			}
		}
		
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c09youmu) {
					is = "yo";
					break;
				}
			}
		}
		if(is.equals("yo")) {
			ARSystem.playSound((Entity)player, "c122youmu2");
		} else {
			ARSystem.playSound((Entity)player, "c122db");
		}
		
		return true;
	}
}
