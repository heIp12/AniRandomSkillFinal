package buff;

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

public class Bubble extends Buff{
	public Vector Movement = new Vector(0,0.3,0);
	
	public Bubble(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.CC);
		buffName = "bubble";
		color = "§b";
		onlyone = true;
		isText = true;
	}

	public boolean onTicks() {
		if(tick%10 == 0) {
			if(target instanceof Player) {
				ARSystem.spellCast((Player) target, "bubble");
			} else {

				ARSystem.spellCast(ARSystem.RandomPlayer() ,target, "bubble2");
			}
		}
		target.setVelocity(Movement);
		return false;
	}
	
	
	@Override
	public boolean onMove(PlayerMoveEvent e) {
		target.setVelocity(target.getVelocity().setY(0));
		return false;
	}
}
