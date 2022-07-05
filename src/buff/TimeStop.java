package buff;

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

public class TimeStop extends Buff{
	Location loc;
	public TimeStop(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.STOP);
		bufftype.add(BuffType.HEADCC);
		buffName = "timestop";
		onlyone = true;
		color = "§4";
		order = 1;
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
		if(tick > 0) {
			e.setDamage(0);
			e.setCancelled(true);
		}
		return true;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if(tick > 0) {
			e.setDamage(0);
			e.setCancelled(true);
		}
		return true;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tick > 0) {
			e.setCancelled(true);
		}
		return false;
	}
}
