package buff;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import types.BuffType;
import util.AMath;

public class Minecart extends Buff{
	Location loc;
	Vector vtr;
	int tk = 0;
	public Minecart(LivingEntity target,Vector vtr) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.DAMAGE);
		buffName = "minecart";
		color = "§a";
		onlyone = true;
		order = 40;
		this.vtr = vtr;
		loc = target.getLocation();
	}


	public boolean onTicks() {
		tk++;
		if(tk > 10) {
			tk = 0;
			ARSystem.playSound(target, "minecraft:entity.minecart.riding");
		}
		if(target.getLocation().distance(loc) > 3 && !target.getLocation().clone().add(0,-1,0).getBlock().isEmpty()) {
			tick = 0;
		} else {
			target.setVelocity(vtr);
			
			Location l = target.getLocation();
			boolean rep = false;
			if(target.getLocation().getY() < loc.getY()) {
				rep = true;
				l.setY(loc.getY());
			}
			if(vtr.getX() == 0&& target.getLocation().getBlockX() != loc.getBlockX()) {
				rep = true;
				l.setX(loc.getX());
			}
			if(vtr.getY() == 0 && target.getLocation().getBlockY() != loc.getBlockY()) {
				rep = true;
				l.setY(loc.getY());
			}
			
			if(rep) {
				l.add(vtr.clone().multiply(0.2));
				target.teleport(l);
			}
		}
		return false;
	}
}
