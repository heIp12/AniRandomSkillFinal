package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import types.BuffType;

public class Nodamage extends Buff{
	
	public Nodamage(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.GOD);
		bufftype.add(BuffType.POWERUP);
		buffName = "nodamage";
		color = "§a";
		onlyone = true;
	}

	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		e.setDamage(0);
		e.setCancelled(true);
		
		return false;
	}
}
