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

public class Airborne extends Buff{
	double y;
	public Airborne(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.CC);
		buffName = "airborne";
		color = "§f";
		onlyone = true;
		isText = true;
		y = target.getLocation().getY();
	}

	public boolean onTicks() {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		target.setVelocity(target.getVelocity().setY(0));
		if(target.getLocation().getY() < y) {
			target.setVelocity(target.getVelocity().setY(0.5));
		}
		if(y > target.getLocation().getY() + 1) {
			y = target.getLocation().getY();
		}
		return false;
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		target.setVelocity(target.getVelocity().setY(0));
		return false;
	}
}
