package buff;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import types.BuffType;
import util.AMath;
import util.ULocal;

public class Fascination extends Buff{
	LivingEntity owner;
	
	public Fascination(LivingEntity target,LivingEntity owner) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.HEADCC);
		buffName = "fascination";
		color = "§d";
		order = 40;
		this.owner = owner;
	}
	
	public boolean onTicks() {
		if(tick > 0 && target != null) {
			Location loc = target.getLocation();
			loc = ULocal.lookAt(loc, owner.getLocation());
			float yaw = loc.getYaw() - target.getLocation().getYaw();
			float pitch = loc.getPitch() - target.getLocation().getPitch();
			
			if(target instanceof Player) {
				ARSystem.playerAddRotate((Player) target, yaw*0.2f, pitch*0.2f);
			}
			if(target.getLocation().distance(owner.getLocation()) > 2) {
				target.setVelocity(loc.getDirection().multiply(value));
			}
		}
		return false;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tick > 0) {
			e.setCancelled(true);
		}
		return false;
	}
}
