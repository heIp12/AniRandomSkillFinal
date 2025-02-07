package buff;

import org.bukkit.GameMode;
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
import util.ULocal;

public class Stun extends Buff{
	Location loc;
	public Stun(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.STUN);
		bufftype.add(BuffType.HEADCC);
		buffName = "stun";
		onlyone = true;
		color = "§b";
	}

	public boolean onTicks() {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		if(tick > 0) {
			if(!(target instanceof Player)) {
				if(loc != null) target.teleport(loc);
				loc = target.getLocation();
			}
			target.setVelocity(new Vector(0,0,0));
		}
		return false;
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		if(tick > 0) {
			loc = e.getFrom();
			if(!ULocal.isEqual(loc,e.getTo())) e.setCancelled(true);
		}
		return false;
	}
}
