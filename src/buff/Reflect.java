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

public class Reflect extends Buff{
	String effect = "barrier";
	String targetEffect = "";
	boolean nodamage = true;
	int delay = 0;
	int maxdelay = 20;
	
	public Reflect(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.GARD);
		bufftype.add(BuffType.POWERUP);
		buffName = "reflect";
		isScore = true;
		isText = false;
		color = "§f";
		order = 80;
		isValueP = true;
	}

	@Override
	public boolean onTicks() {
		value = AMath.round(value, 2);
		if(delay > 0) delay--;
		return true;
	}
	public void SetEffect(String s) {
		effect = s;
	}
	
	public void setDelay(int delay) {
		maxdelay = delay;
	}

	public void SetTargetEffect(String s) {
		targetEffect = s;
	}
	public void SetNoDamage(boolean b) {
		nodamage = b;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		if(delay <= 0) {
			delay = maxdelay;
			 if(value > 0) {
				if(target instanceof Player) {
					((Player)target).performCommand("c "+effect);
				}
				Holo.create(target.getLocation(),"§f§l☇ "+ AMath.round(e.getDamage()*value,1),20,new Vector(AMath.random(5)*0.02,0.1,AMath.random(5)*0.02));
				((LivingEntity)e.getDamager()).setNoDamageTicks(0);
				((LivingEntity)e.getDamager()).damage(e.getDamage()*value,target);
				if(!targetEffect.equals("") && target instanceof Player) ARSystem.spellLocCast((Player) target, e.getDamager().getLocation(), targetEffect);
				if(nodamage) {
					e.setDamage(0);
					e.setCancelled(true);
				} else {
					e.setDamage(e.getDamage() - (e.getDamage() * value));
				}
				return false;
			}

		}
		return true;
		
	}
}
