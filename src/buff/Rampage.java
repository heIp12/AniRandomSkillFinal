package buff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
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
import types.BuffType;
import types.box;
import util.AMath;
import util.Holo;
import util.ULocal;

public class Rampage extends Buff{
	Location loc;
	int l = 1;
	public Rampage(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.HEADCC);
		buffName = "rampage";
		onlyone = true;
		color = "§f§l";
	}

	@Override
	public boolean onMove(PlayerMoveEvent e) {

		return false;
	}
	
	@Override
	public boolean onTicks() {
		Entity t = ARSystem.boxSOne(target, new Vector(14,14,14), box.ALL);
		if(t != null) {
			Location look = ULocal.lookAt(target.getLocation(), t.getLocation());
			if(t.getLocation().distance(target.getLocation()) < 3) {
				if(ARSystem.isGameMode("lobotomy") || tick%10 == 0) {
					((LivingEntity)t).damage(1,target);
					Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{((LivingEntity)t).damage(1,target);});
				}
			} else {
				if(target instanceof Player) {
					ARSystem.playerRotate((Player)target, look.getYaw(), look.getPitch());
					target.setVelocity(target.getLocation().getDirection().setY(0).multiply(0.45));
				} else {
					target.setVelocity(look.getDirection().setY(0).multiply(0.45));
				}
			}
		} else {
			if(target instanceof Player) {
				ARSystem.playerRotate((Player)target, target.getLocation().getYaw()+10, 0);
				target.setVelocity(target.getLocation().getDirection().setY(0).multiply(0.1));
			} else {
				Location loc = target.getLocation();
				loc.setYaw(loc.getYaw() + 20);
				target.setVelocity(loc.getDirection().setY(0).multiply(0.2));
			}
		}
		return false;
	}
	
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		e.setCancelled(true);
		return false;
	}
}
