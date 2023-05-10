package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import types.BuffType;
import util.AMath;
import util.Holo;

public class PowerUp extends Buff{

	public PowerUp(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.POWERUP);
		buffName = "powerup";
		isScore = true;
		isText = false;
		color = "§f";
		order = 80;
		isValueP = true;
		onlyone = true;
	}


	@Override
	public boolean onAttack(EntityDamageByEntityEvent e){
		e.setDamage(e.getDamage() + e.getDamage()*value);
		return false;
	}
}
