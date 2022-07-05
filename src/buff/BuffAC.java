package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import manager.Holo;
import util.AMath;

public class BuffAC extends Buff{

	public BuffAC(LivingEntity target) {
		super(target);
		buffName = "buffac";
		onlyone = true;
		alltime = true;
		isValueP = true;
		order = 0;
	}

}
