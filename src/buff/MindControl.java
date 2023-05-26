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
import util.BlockUtil;
import util.Holo;
import util.ULocal;

public class MindControl extends Buff{
	Location loc;
	int l = 1;
	LivingEntity owner;
	
	LivingEntity targets;
	public MindControl(LivingEntity target,LivingEntity owner) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.HEADCC);
		buffName = "mindcontrol";
		onlyone = true;
		color = "§7§l";
		this.owner = owner;
	}

	@Override
	public boolean onMove(PlayerMoveEvent e) {

		return false;
	}
	
	@Override
	public boolean onTicks() {
		Entity t = ARSystem.boxRandom(target, new Vector(14,14,14), box.ALL);
		if(t != owner) targets = (LivingEntity)t;
		if(targets != null) {
			Location look = ULocal.lookAt(target.getLocation(), targets.getLocation());
			if(target.getLocation().distance(targets.getLocation()) < 3) {
				if(tick%5 == 0) {
					targets.setNoDamageTicks(0);
					targets.damage(1,target);
					ARSystem.giveBuff(targets, new Stun(targets), 10);
				}
			} else {
				if(target instanceof Player) {
					ARSystem.playerRotate((Player)target, look.getYaw(), look.getPitch());
					target.setVelocity(target.getLocation().getDirection().setY(-0.3).multiply(1));
					if(!BlockUtil.isPathable(target.getLocation().clone().add(target.getVelocity()).getBlock())) {
						target.setVelocity(target.getVelocity().setY(1));
					}
				} else {
					target.setVelocity(look.getDirection().setY(0).multiply(0.45));
				}
			}
		} else {
			if(tick%20 == 0) {
				ARSystem.spellCast((Player)owner, target, "bload");
				ARSystem.giveBuff(target, new Stun(target), 10);
				target.setNoDamageTicks(0);
				target.damage(1,target);
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
