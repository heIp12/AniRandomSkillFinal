package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import types.BuffType;
import util.Holo;

public class Nodie extends Buff{
	
	public Nodie(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.GOD);
		buffName = "nodie";
		color = "§f§l";
		onlyone = true;
		if(value < 1) value = 1;  
	}

	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		if(target.getHealth() - e.getDamage() <= value) {
			target.setHealth(1);
			e.setDamage(0);
			e.setCancelled(true);
			Holo.create(target.getLocation(), "§f§l※No Die※", 20, new Vector(0,0.1,0));
		}
		return false;
	}
	
	@Override
	public boolean onHitNext(EntityDamageByEntityEvent e){
		if(target.getHealth() - e.getDamage() <= value) {
			target.setHealth(1);
			e.setDamage(0);
			e.setCancelled(true);
			Holo.create(target.getLocation(), "§f§l※No Die※", 20, new Vector(0,0.1,0));
		}
		return false;
	}
	
	@Override
	public boolean onRemove() {
		Holo.create(target.getLocation(), "§f§l※No Die※", 20, new Vector(0,0.1,0));
		return false;
	}
}
