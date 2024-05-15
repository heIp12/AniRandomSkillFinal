package buff;

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
	int lv = 0;
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
		timer++;
		if(timer > 20) {
			timer = 0;
			lv++;
		}
		if(lv > 5) {
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
			ARSystem.potion(target, 2, 10, lv);
			if(tick% Math.max(6-lv,1) == 0)target.setVelocity(new Vector(0,-lv*0.4,0));
		}
		return false;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		e.setDamage(e.getDamage() * Math.max(1-(0.1*lv),0));
		return super.onHit(e);
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(lv > 7) {
			e.setCancelled(true);
			return false;
		}
		return super.onSkill(e);
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(lv > 5) {
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
