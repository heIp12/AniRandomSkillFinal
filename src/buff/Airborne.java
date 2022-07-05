package buff;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import event.Skill;
import manager.Holo;
import types.BuffType;
import util.AMath;

public class Airborne extends Buff{
	Location loc;
	public Airborne(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.CC);
		buffName = "airborne";
		color = "§f";
		onlyone = true;
		isText = true;
	}

	public boolean onTicks() {
		target.setVelocity(target.getVelocity().setY(0));
		return false;
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		target.setVelocity(target.getVelocity().setY(0));
		return false;
	}
}
