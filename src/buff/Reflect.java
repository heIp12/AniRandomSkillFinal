package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import manager.Holo;
import types.BuffType;
import util.AMath;

public class Reflect extends Buff{
	String effect = "barrier";
	String targetEffect = "";
	boolean nodamage = true;
	
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
		return true;
	}
	public void SetEffect(String s) {
		effect = s;
	}

	public void SetTargetEffect(String s) {
		targetEffect = s;
	}
	public void SetNoDamage(boolean b) {
		nodamage = b;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		if(target.getNoDamageTicks() <= 0) {
			 if(value > 0) {
				if(target instanceof Player) {
					((Player)target).performCommand("c "+effect);
				}
				Holo.create(target.getLocation(),"§f§l☇ "+ AMath.round(e.getDamage()*value,1),20,new Vector(AMath.random(5)*0.02,0.1,AMath.random(5)*0.02));
				
				((LivingEntity)e.getDamager()).damage(e.getDamage()*value);
				if(!targetEffect.equals("") && target instanceof Player) ARSystem.spellLocCast((Player) target, e.getDamager().getLocation(), targetEffect);
				if(nodamage) {
					e.setDamage(0);
					e.setCancelled(true);
				} else {
					e.setDamage(e.getDamage() - (e.getDamage() * value));
				}
				return false;
			}

		} else {
			e.setDamage(0);
		}
		return false;
	}
}
