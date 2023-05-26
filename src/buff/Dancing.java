package buff;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import types.BuffType;
import util.AMath;

public class Dancing extends Buff{
	int y = -1;
	int p = -1;
	int ya = 3;
	int pa = 3;
	public Dancing(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.CC);
		buffName = "dancing";
		color = "§a";
		order = 40;
	}
	
	public boolean onTicks() {
		
		if(AMath.random(100) <= 5) {
			y = AMath.random(3)-2;
			ya = y*AMath.random(30);
		}
		if(AMath.random(100) <= 30) {
			p = AMath.random(3)-2;
			pa = p*AMath.random(10);
		}
		
		if(target instanceof Player) {
			ARSystem.playerAddRotate((Player) target, ya, pa);
		} else if(tick%20 == 0){
			target.setVelocity(new Vector(0,0.1,0));
		}
		
		return false;
	}
}
