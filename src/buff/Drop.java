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
import util.BlockUtil;

public class Drop extends Buff{
	Location loc;
	int tk = 0;
	public Drop(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.DAMAGE);
		buffName = "drio";
		color = "§6";
		onlyone = true;
		order = 40;
		loc = target.getLocation().clone();
		for(int i =0; i<50; i++) {
			if(BlockUtil.isAirbone(loc, 1) || loc.getBlock().isEmpty()) {
				loc.add(new Vector(0,-0.2f,0));
			} else {
				break;
			}
		}
		target.teleport(loc);
	}


	public boolean onTicks() {
		tk++;
		if(loc.getBlock().isEmpty()) {
			tick = 0;
		} else {
			loc.add(new Vector(0,-0.1f,0));
			target.teleport(loc);
		}
		return false;
	}
}
