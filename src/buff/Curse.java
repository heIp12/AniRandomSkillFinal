package buff;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import event.Skill;
import types.BuffType;
import util.AMath;
import util.Holo;

public class Curse extends Buff{
	LivingEntity caster;
	
	public Curse(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.DAMAGE);

		buffName = "curse";
		onlyone = false;
		color = "§7";
		isScore = true;
		isValueP = true;
	}

	public void setCaster(LivingEntity caster) {
		this.caster = caster;
	}
	
	public boolean onTicks() {
		if(AMath.random(40) == 1) {
			target.setVelocity(new Vector(0,0,0));
		}
		return false;
	}
	
	@Override
	public void last() {
		double h = target.getMaxHealth() - target.getHealth();
		
		target.setNoDamageTicks(0);
		target.damage(h*value,caster);
	}
}
