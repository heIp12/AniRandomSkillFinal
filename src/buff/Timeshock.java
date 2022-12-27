package buff;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import event.Skill;
import manager.Holo;
import types.BuffType;
import util.AMath;

public class Timeshock extends Buff{
	Location loc;
	HashMap<LivingEntity,Double> damage;
	
	public Timeshock(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.SILENCE);
		bufftype.add(BuffType.HEADCC);
		bufftype.add(BuffType.STUN);
		bufftype.add(BuffType.DAMAGE);
		buffName = "timeshock";
		onlyone = true;
		color = "§4";
	}

	public boolean onTicks() {
		if(!(target instanceof Player)) {
			if(loc != null) target.teleport(loc);
			loc = target.getLocation();
		}
		target.setVelocity(new Vector(0,0,0));
		return false;
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		e.setCancelled(true);
		return false;
	}
	
	@Override
	public boolean onAttack(EntityDamageByEntityEvent e) {
		return true;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tick > 0) {
			LivingEntity attaker = (LivingEntity) e.getDamager();
			if(!damage.containsKey(attaker)) {
				damage.put(attaker, 0.0);
			}
			damage.put(attaker, e.getDamage() + damage.get(attaker));
			e.setDamage(0);
			e.setCancelled(true);
		}
		return true;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tick > 0) e.setCancelled(true);
		return false;
	}
	
	@Override
	public void first() {
		damage = new HashMap<LivingEntity,Double>();
	}
	
	@Override
	public void last() {
		int i = 0;
		for(LivingEntity e : damage.keySet()) {
			i++;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				target.setNoDamageTicks(0);
				target.damage(damage.get(e),e);
			}, i*2);
		}
	}
}
