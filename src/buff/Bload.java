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

public class Bload extends Buff{

	public Bload(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.POWERUP);
		buffName = "bload";
		isScore = true;
		isText = false;
		color = "§c";
		order = 1000;
		isValueP = true;
		onlyone = true;
	}


	@Override
	public boolean onAttack(EntityDamageByEntityEvent e){
		ARSystem.heal(target, e.getDamage()*value);
		if(!(target instanceof Player)) {
			Holo.create(target.getLocation().clone().add(0.5-AMath.random(10)*0.1,0.5,AMath.random(10)*0.1-AMath.random(10)*0.1),"§a§l✞ "+ (Math.round((e.getDamage()*value)*100)/100.0),10,new Vector(0,-0.01,0));
		}
		return false;
	}
}
