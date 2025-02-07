package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import types.BuffType;

public class Nodamage extends Buff{
	LivingEntity nodamage;
	
	public Nodamage(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.GOD);
		bufftype.add(BuffType.POWERUP);
		buffName = "nodamage";
		color = "§a";
		onlyone = true;
	}
	public Nodamage(LivingEntity target,LivingEntity damager) {
		super(target);
		nodamage = damager;
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.GOD);
		bufftype.add(BuffType.POWERUP);
		buffName = "nodamage";
		color = "§a";
		onlyone = true;
	}

	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		if(nodamage == null  || (nodamage != null && e.getDamager() == nodamage)) {
			e.setDamage(0);	
			e.setCancelled(true);
		}
		
		return false;
	}
}
