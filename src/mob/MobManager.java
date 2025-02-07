package mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import ars.Rule;

public class MobManager {
	List<MobBase> monster = new ArrayList<>();
	List<MobBase> removes = new ArrayList<>();
	
	public MobManager(){
		monster = new ArrayList<>();
	}
	public boolean contains(Entity e) {
		if(e == null) return false;
		boolean iscon = false;
		for(MobBase m : monster) {
			if(m == null || m.entity == null || e == null) {
				continue;
			} else if(m.entity == e) {
				iscon = true;
				break;
			}
		}
		return iscon;
	}
	public void Add(MobBase e) {
		monster.add(e);
	}
	
	public void Reset() {
		monster.clear();
		removes.clear();
	}
	
	public void onTick(){
		for(MobBase mob : monster) {
			if(mob != null && mob.entity != null && mob.entity instanceof LivingEntity) {
				LivingEntity e = mob.entity; 
				if(mob == null || e == null || e.isDead() || !e.isEmpty() || e.getHealth() < 1) {
					removes.add(mob);
				} else if(mob instanceof Player && Rule.c.get(mob) == null) {
					removes.add(mob);
				}
			} else {
				removes.add(mob);
			}
		}
		for(MobBase mob : removes) {
			if(mob != null) {
				try {
					mob.onBuffRemove();
					monster.remove(mob);
				}catch(Exception e) {
					
				}
			}
		}
		try {
			if(monster.contains(null)) monster.remove(null);
		} catch(Exception e) {}
		for(MobBase mob : monster) if(mob != null) mob.tick();
	}
	
	public void onKill(LivingEntity target,LivingEntity killer){
		for(MobBase mob : monster) if(mob != null && mob.entity != null && killer == mob.entity) mob.onKill(target);
	}
	public void onDeath(LivingEntity target,LivingEntity death){
		for(MobBase mob : monster) if(mob != null && mob.entity != null && death == mob.entity) mob.onDeath(target);
	}
	
	public void onAttack(EntityDamageByEntityEvent e){
		for(MobBase mob : monster) {
			if(mob != null && mob.entity != null) {
				if(e.getDamager() == mob.entity && e.getEntity() instanceof LivingEntity) mob.onAttack(e,(LivingEntity)e.getEntity());
			}
		}
	}
	public void onHit(EntityDamageByEntityEvent e){
		for(MobBase mob : monster) {
			if(mob != null && mob.entity != null) {
				if(e.getEntity() == mob.entity &&e.getDamager() instanceof LivingEntity) mob.onHit(e,(LivingEntity)e.getDamager());
			}
		}
	}
}
