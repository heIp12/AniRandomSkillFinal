package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import manager.Holo;
import types.BuffType;
import util.AMath;

public class Barrier extends Buff{
	String effect = "barrier";
	
	public Barrier(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.GOD);
		bufftype.add(BuffType.POWERUP);
		buffName = "barrier";
		onlyone = true;
		alltime = true;
		isScore = true;
		color = "§e";
		order = 1000;
	}

	@Override
	public boolean onTicks() {
		value = AMath.round(value, 2);
		return true;
	}
	public void SetEffect(String s) {
		effect = s;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		if(target.getNoDamageTicks() <= 0) {
			 if(value > 0) {
				if(target instanceof Player) {
					((Player)target).performCommand("c "+effect);
				}
				Holo.create(target.getLocation(),"§e§l☇ "+ (Math.round(e.getFinalDamage()*100)/100.0f),20,new Vector(AMath.random(5)*0.02,0.1,AMath.random(5)*0.02));
				value =  value - e.getFinalDamage();
				if(value < 0) {
					e.setDamage(value*-1);
					value = 0;
					return true;
				} else {
					e.setDamage(0);
					target.setNoDamageTicks(20);
				}
				return true;
			}

		} else {
			e.setDamage(0);
		}
		return false;
	}
}
