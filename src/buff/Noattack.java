package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import types.BuffType;

public class Noattack extends Buff{
	
	public Noattack(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.CC);
		buffName = "noattack";
		color = "§c";
		onlyone = true;
	}

	@Override
	public boolean onAttack(EntityDamageByEntityEvent e){
		e.setDamage(0);
		e.setCancelled(true);
		return false;
	}
}
