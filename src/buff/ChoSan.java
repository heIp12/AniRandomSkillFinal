package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import types.BuffType;
import util.AMath;
import util.Holo;

public class ChoSan extends Buff{
	String effect = "chosan";
	
	public ChoSan(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		bufftype.add(BuffType.POWERUP);
		buffName = "chosan";
		onlyone = true;
		alltime = true;
		isScore = false;
		color = "§f";
		order = 2;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		if(target.getNoDamageTicks() <= 0) {
			for(Player p : Rule.c.keySet()) {
				Rule.c.get(p).hpCost(e.getDamage() * 0.05, true);
			} 
			e.setDamage(e.getDamage() * 0.6); 
		}
		return false;
	}
}
