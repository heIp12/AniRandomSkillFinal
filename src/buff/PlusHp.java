package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import event.Skill;
import types.BuffType;
import util.AMath;
import util.Holo;

public class PlusHp extends Buff{

	public PlusHp(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.HEAL);
		bufftype.add(BuffType.POWERUP);
		buffName = "plushp";
		onlyone = true;
		alltime = true;
		isScore = true;
		color = "§c";
		order = 1000;
	}

	@Override
	public boolean onTicks() {
		value = AMath.round(value, 2);
		if(target.getHealth() < target.getMaxHealth() && value > 0) {
			double hps = target.getMaxHealth()- target.getHealth();
			if(value > hps) {
				value-=hps;
				target.setHealth(target.getMaxHealth());
			} else {
				value = 0;
				target.setHealth(target.getHealth()+hps);
			}
		}
		return false;
	}
}
