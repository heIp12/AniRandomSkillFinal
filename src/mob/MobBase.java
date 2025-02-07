package mob;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ars.Rule;
import buff.Buff;
import types.BuffType;
import types.TargetMap;
import util.NpcPlayer;

public class MobBase {
	int tick = 0;
	LivingEntity entity;
	HashMap<Runnable,Double> delayEvent = new HashMap<>();
	TargetMap<String, Double> cooldown = new TargetMap<>();
	
	public MobBase(LivingEntity mob){
		entity = mob;
	}

	public void tick() {
		tick++;
		
		ArrayList<Runnable> removes = new ArrayList<>();
		for(Runnable run : delayEvent.keySet()) {
			delayEvent.put(run, delayEvent.get(run)-1);
			if(delayEvent.get(run) <= 0) removes.add(run);
		}
		for(Runnable run : removes) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, run);
			delayEvent.remove(run);
		}
		cooldown.addAll(-0.05);
		cooldown.removes();
		onTick();
	}
	
	protected void onTick() {}
	public void onAttack(EntityDamageByEntityEvent e,LivingEntity target) {}
	public void onHit(EntityDamageByEntityEvent e,LivingEntity attaker) {}
	public void onDeath(LivingEntity killer) {}
	public void onKill(LivingEntity target) {}
	public void onBuffRemove() {}
	
	public void delay(Runnable event, int delay) {
		delayEvent.put(event, (double)delay);
	}
	
	public boolean isCooldown(String name,double d) {
		return isCooldown(name, d, true);
	}
	public boolean isCooldown(String name,double value,boolean stunNotCast) {
		if(!cooldown.get().containsKey(name)) cooldown.add(name, 0);
		if(cooldown.get(name) <= 0) {
			if(!stunNotCast || (!isSilence() && stunNotCast)) {
				cooldown.add(name, value);
				return true;
			}
		}
		return false;
	}
	public boolean setCooldown(String name,double value) {
		if(!cooldown.get().containsKey(name)) cooldown.add(name, 0);
		if(cooldown.get(name) <= 0) {
			cooldown.set(name, value);
		}
		return false;
	}
	public boolean addCooldown(String name,double value) {
		if(!cooldown.get().containsKey(name)) cooldown.add(name, 0);
		if(cooldown.get(name) <= 0) {
			cooldown.add(name, value);
		}
		return false;
	}
	public boolean isBuff(String n) {
		return Rule.buffmanager.isBuff(entity, n);
	}
	public boolean isSilence() {
		if(Rule.buffmanager.getBuffs(entity) == null || Rule.buffmanager.getBuffs(entity).getBuff() == null) return false;
		for(Buff b : Rule.buffmanager.getBuffs(entity).getBuff()) {
			if(b.istype(BuffType.SILENCE) || b.istype(BuffType.STOP)) {
				return true;
			}
		}
		return false;
	}
	
	public Player npc() {
		return NpcPlayer.npc(entity.getLocation());
	}
}
