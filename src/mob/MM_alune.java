package mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.Barrier;
import buff.Bload;
import buff.NoCC;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.skills.SkillCaster;
import io.lumine.xikage.mythicmobs.util.BlockUtil;
import manager.Bgm;
import mode.MLoboTomy;
import mode.ModeBase;
import types.box;
import util.AMath;
import util.Holo;
import util.Map;
import util.NpcPlayer;
import util.ULocal;

public class MM_alune extends MobMMBase {
	Location startloc;
	List<Location> hm = new ArrayList<>();
	double range = 3;
	int battle = 0;
	int headdelay = 0;
	
	public MM_alune(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	protected void onTick() {
		if(startloc == null) {
			if(entity.isOnGround()) {
				Location loc = Map.getCenter();
				loc.setY(entity.getLocation().getY());
				startloc = ULocal.lookAt(entity.getLocation().clone(),loc);
			}
			return;
		}
		if(tick%100 == 0) ARSystem.playSound(entity, "alitimer");
		
		headdelay--;
		if(AMath.random(200) <= 1 && headdelay <= 0) {
			headdelay = AMath.random(20)*20;
			signalPart("head"+AMath.random(3), headdelay);
			headdelay+=20;
		}
		entity.teleport(startloc);
		range = entity.getMaxHealth()*0.015f;
		int size = 0;
		size = (int)(range*2);
		
		if(tick%20 == 0) {
			for(int i = 0; i< size; i++) {
				Location loc = getRdLoc();
				if(hm.size() < range/4) {
					boolean is = true;
					if(loc.distance(entity.getLocation()) > 5) {
						for(Location l : hm) {
							if(l.distance(loc) < 5) {
								is = false;
							}
						}
						if(is) hm.add(loc);
					}
				}
				if(size-i >= 10 && AMath.random(20) <= 1) {
					i+= 8;
					ARSystem.spellLocCast(npc(), loc, "alune_e7");
					break;
				} else if(size-i >= 4 && AMath.random(10) <= 2) {
					i+= 3;
					ARSystem.spellLocCast(npc(), loc, "alune_e6");
					break;
				}
				ARSystem.spellLocCast(npc(), loc, "alune_e" + AMath.random(2));
				if(range > 20 && AMath.random(10) <= 3) {
					loc = getRdLoc();
					ARSystem.spellLocCast(npc(), loc, "alune_e"+(AMath.random(2)+3));
					loc.getWorld().playSound(loc,"alisk"+AMath.random(2), 1, AMath.random(10)*0.1f + 0.5f);
					for(Entity e : ARSystem.box(loc, entity, new Vector(3,3,3), box.TARGET)){
						LivingEntity en = (LivingEntity)e;
						en.damage(1 + entity.getMaxHealth()*0.005 ,entity);
						ARSystem.addBuff(en, new Panic(en), 20 + (int)(entity.getMaxHealth()*0.05));
						ARSystem.giveBuff(en, new ArmorUp(en), 20, -1);
					}
				}
			}
			
			if(tick%60 == 0) for(Location l : hm) ARSystem.spellLocCast(npc(), ULocal.lookAt(l.clone(), startloc), "alune_e3");
		}
		battle++;
		if(battle >= 60) {
			if(entity.getMaxHealth() > 2000) {
				entity.setMaxHealth(entity.getMaxHealth() + 0.25f);
				ARSystem.heal(entity, 0.25f);
			} else if(entity.getMaxHealth() > 750) {
				entity.setMaxHealth(entity.getMaxHealth() + 0.75f);
				ARSystem.heal(entity, 0.5f);
			} else  {
				entity.setMaxHealth(entity.getMaxHealth() + 2.5f);
				ARSystem.heal(entity, 2.5f);
			}
		} else {
			entity.setMaxHealth(entity.getMaxHealth() + 0.04f);
		}
		
		if(isCooldown("attack", 3)) {
			LivingEntity e = boxSOne(entity, new Vector(range,range,range), box.TARGET);
			if(e != null && e.getLocation().distance(entity.getLocation()) <= range) {
				ARSystem.playSound(e, "alidmg");
				double damage = 2+entity.getMaxHealth()*0.01;
				e.setNoDamageTicks(0);
				e.damage(damage,entity);
				ARSystem.spellLocCast(npc(), e.getLocation(), "alune_e4");
				ARSystem.giveBuff(e, new ArmorUp(e), 20, -0.2);
				if(damage > 10) {
					ARSystem.giveBuff(e, new Rampage(e), 10);
				}
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(attaker instanceof Player) {
			battle = 0;
		}
		e.setDamage(e.getDamage() * 0.6);
		if(e.getDamage() > 100) e.setDamage(100);
		if(e.getDamage() > entity.getMaxHealth()*0.2) e.setDamage(entity.getMaxHealth()*0.2);
	}
	
	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity target) {
		if(target instanceof Player) {
			battle = 0;
		}
	}
	
	@Override
	public void onKill(LivingEntity target) {
		Location loc = target.getLocation();
		while(BlockUtil.isPathable(loc.clone().add(0,-1,0).getBlock())) {
			loc.add(new Vector(0,-1,0));
			if(loc.getY() < 0) break;
		}
		if(loc.getY() < 0) {
			hm.add(getRdLoc());
		} else {
			hm.add(loc);
		}
		battle = 100;
		entity.setMaxHealth(entity.getMaxHealth() + 30f);
		ARSystem.heal(entity, 30f);
		
	}
	
	Location getRdLoc() {
		Location loc = startloc.clone();
		loc.setYaw(AMath.random(360));
		loc.setPitch(0);
		double a = AMath.random((int)(range*100))/100.0f;
		int rmt = 0;
		while(!Map.inMap(ULocal.offset(loc, new Vector(a,0,0)))) {
			loc.setYaw(AMath.random(360));
			a = AMath.random((int)(range*100))/100.0f;
			if(AMath.random(10) < 3) {
				a = AMath.random((int)(range*25))/100.0f + (range*0.75f);
			} else {
				if(a < 3 && AMath.random(10) < 7) continue;
			}
			Location l = ULocal.offset(loc, new Vector(a,0,0));
			while(BlockUtil.isPathable(l.clone().add(0,-1,0).getBlock())) {
				loc = loc.add(new Vector(0,-1,0));
				l = ULocal.offset(loc, new Vector(a,0,0));
				if(!Map.inMap(l) || l.getBlockY() < Map.loc_f.getBlockY()) {
					break;
				}
			}
			rmt++;
			if(rmt > 100) break;
		}
		return ULocal.offset(loc, new Vector(a,0,0));
	}
}
