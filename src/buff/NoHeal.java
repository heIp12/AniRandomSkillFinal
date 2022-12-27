package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import types.BuffType;

public class NoHeal extends Buff{
	double minhp = 999;
	public NoHeal(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		buffName = "noheal";
		color = "§c";
		onlyone = true;
	}

	@Override
	public boolean onTicks() {
		if(target.getHealth() <= minhp) {
			minhp = target.getHealth();
		} else {
			 target.setHealth(minhp);
		}
		return false;
	}
	
}
