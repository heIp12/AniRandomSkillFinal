package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import types.BuffType;
import util.Holo;

public class NoCC extends Buff{
	
	public NoCC(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		buffName = "nocc";
		color = "§6";
		onlyone = true;
		order = 0;
	}

	@Override
	public boolean onTicks() {
		boolean rm = false;
		for(Buff bf : Rule.buffmanager.selectBuffType(target, BuffType.CC)) {
			if(bf.tick > 0) {
				bf.setTime(0);
				rm = true;
			}
		}
		for(Buff bf : Rule.buffmanager.selectBuffType(target, BuffType.HEADCC)) {
			if(bf.tick > 0) {
				bf.setTime(0);
				rm = true;
			}
		}
		if(rm) {
			Holo.create(target.getLocation(), color+"《강인함》",20, new Vector(0, 0.15f, 0));
		}
		return false;
	}
}
