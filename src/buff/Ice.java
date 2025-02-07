package buff;

import org.bukkit.GameMode;
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
import util.ULocal;

public class Ice extends Buff{
	Location loc;
	Player ctr;
	public Ice(LivingEntity target,Player caster) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.STUN);
		bufftype.add(BuffType.SILENCE);
		bufftype.add(BuffType.HEADCC);
		buffName = "ice";
		onlyone = true;
		color = "§b";
		ctr = caster;
	}

	public boolean onTicks() {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		if(!(target instanceof Player)) {
			if(loc != null) target.teleport(loc);
			loc = target.getLocation();
		}
		target.setVelocity(new Vector(0,0,0));
		if(tick%10 == 0) {
			ARSystem.spellCast(ctr, target, "ice");
		}
		return false;
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		loc = e.getFrom();
		if(!ULocal.isEqual(loc,e.getTo())) e.setCancelled(true);
		return false;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		e.setCancelled(true);
		return false;
	}
	
	@Override
	public boolean onHit(EntityDamageByEntityEvent e){
		tick -= e.getDamage()*10;
		return false;
	}
}
