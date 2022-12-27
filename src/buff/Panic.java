package buff;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import manager.Holo;
import types.BuffType;
import util.AMath;

public class Panic extends Buff{
	Location loc;
	int l = 1;
	public Panic(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.CC);
		buffName = "panic";
		onlyone = true;
		color = "§5";
	}

	@Override
	public boolean onMove(PlayerMoveEvent e) {
		if(target instanceof Player) {
			ARSystem.playerAddRotate((Player) target, AMath.random(5)*l, 0);
			if(target.getPotionEffect(PotionEffectType.BLINDNESS) == null) {
				ARSystem.potion(target, 15, 60, 20);
			}
		}
		return false;
	}
	
	@Override
	public boolean onTicks() {
		if(AMath.random(20) == 1) l*=-1;
		
		if(!(target instanceof Player)) {
			Location tg = target.getLocation();
			tg.setYaw(tg.getYaw()+(10*l));
			target.teleport(tg);
			if(tick %20== 1) {
				target.setVelocity(new Vector(0,0.2,0));
			}
		}
		return false;
	}
	
	
}
