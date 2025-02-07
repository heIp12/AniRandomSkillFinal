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

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import types.BuffType;
import util.AMath;
import util.Holo;

public class Boom extends Buff{
	Player caster;
	String cast;
	String cast2;
	boolean onlymy = true;
	float pp = 0;
	
	public Boom(LivingEntity target,Player caster) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.DAMAGE);

		buffName = "boom";
		onlyone = false;
		color = "§c";
		isScore = true;
		this.caster = caster;
	}

	public void setDamage(float pp,boolean onlymy,String effect,String lasteffect) {
		this.pp = pp;
		this.onlymy = onlymy;
		cast = effect;
		cast2 = lasteffect;
	}
	
	public boolean onTicks() {
		if(tick2%2 == 0) {
			ARSystem.spellCast(caster, target, cast);
		}
		return false;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e) {
		if((onlymy && e.getDamager() == caster) || !onlymy) {
			tick -= (int)e.getDamage()*5;
			if(pp > 0) value += e.getDamage()*pp;
		}
		return super.onHitNext(e);
	}
	
	@Override
	public void last() {
		float damage = (float) value;
		target.setNoDamageTicks(0);
		target.damage(damage,caster);
		ARSystem.spellCast(caster, target, cast2);
	}
}
