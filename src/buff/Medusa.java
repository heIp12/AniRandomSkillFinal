package buff;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import types.BuffType;
import util.AMath;
import util.Holo;
import util.ULocal;

public class Medusa extends Buff{
	Location loc;
	
	int timer = 0;
	public Medusa(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.STUN);
		bufftype.add(BuffType.HEADCC);
		buffName = "medusa";
		onlyone = true;
		color = "§b";
	}

	public boolean onTicks() {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		if(timer%2 == 0 && target instanceof Player) {
			ARSystem.spellCast((Player)target, "medusa");
		}
		timer++;
		if(timer > 20) {
			timer = 0;
			value++;
			if(value > 20) {
				value = 20;
			}
		}
		if(value > 5) {
			if(!(target instanceof Player)) {
				if(loc != null) {
					Location l1 = loc.clone();
					Location l2 = target.getLocation().clone();
					l1.setY(0);
					l2.setY(0);
					l1.setY(0);
					l2.setY(0);
					
					if(!ULocal.isEqual(l1,l2) || loc.getY() <= target.getLocation().getY())
					target.teleport(loc);
				}
				loc = target.getLocation();
			}
		} else {
			ARSystem.potion(target, 2, 10, (int)value);
			if(tick% Math.max(6-value,1) == 0)target.setVelocity(new Vector(0,-value*0.4,0));
		}
		return false;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * Math.max(1-(0.05*value),0));
		return super.onHit(e);
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(value > 7) {
			e.setCancelled(true);
			return false;
		}
		return super.onSkill(e);
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(value > 5) {
			loc = e.getFrom();
			Location l1 = loc.clone();
			Location l2 = e.getTo().clone();
			l1.setY(0);
			l2.setY(0);
			
			if(!ULocal.isEqual(l1,l2) || loc.getY() <= e.getTo().getY() ) e.setCancelled(true);
		}
		return false;
	}
}
