package buff;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import net.citizensnpcs.api.npc.NPC;
import types.BuffType;
import util.AMath;
import util.BlockUtil;
import util.ULocal;

public class Follow extends Buff{
	LivingEntity owner;
	int jump = 0;
	float up = -0.3f;
	NPC npc;
	boolean tp = false;
	Location floc;
	int ftick = 0;
	
	public Follow(LivingEntity target,LivingEntity owner) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.HEADCC);
		buffName = "follow";
		color = "§d";
		order = 40;
		this.owner = owner;
	}
	public Follow(NPC target,LivingEntity owner, boolean b) {
		super((LivingEntity)target.getEntity());
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.HEADCC);
		buffName = "follow";
		color = "§d";
		order = 40;
		this.owner = owner;
		this.npc = target;
		tp = b;
		floc = this.target.getLocation();
	}
	
	
	public boolean onTicks() {
		if(target instanceof Player && ((Player)target).getGameMode() == GameMode.SPECTATOR) return false;
		if(tick > 0 && target != null) {
			Location loc = target.getLocation();
			loc = ULocal.lookAt(loc, owner.getLocation());

			if(value < 0) {
				loc.setYaw((loc.getYaw()+180)%360);
				loc.setPitch(loc.getPitch()*-1);
			}
			
			float yaw = loc.getYaw() - target.getLocation().getYaw();
			float pitch = loc.getPitch() - target.getLocation().getPitch();
			
			if(target instanceof Player) {
				ARSystem.playerAddRotate((Player) target, yaw*0.2f, pitch*0.2f);
			}
			if(jump > 0) {
				jump--;
				if(jump == 0) {
					up = -0.5f;
				}
			}
			if(target.getLocation().distance(owner.getLocation()) > 2 || value < 0) {
				target.setVelocity(loc.getDirection().multiply(Math.abs(value)).setY(up));
				if(target.isOnGround()) {
					if(!BlockUtil.isPathable(ULocal.offset(target.getLocation(), new Vector(0.5,0,0)).getBlock()) || target.getLocation().getPitch() < -75 || AMath.random(100) <= 5) {
						jump = 10;
						up = 0.05f;
					}
				}
			}
			if(npc != null) {
				Location lc = npc.getEntity().getLocation();
				lc = ULocal.lookAt(lc, owner.getLocation());
				npc.faceLocation(loc.clone().add(loc.getDirection().multiply(10)));
			}
			
			if(tp && floc.distance(target.getLocation()) <= 0.05 && target.getLocation().distance(owner.getLocation()) > 4) {
				ftick++;
				if(ftick > 15) {
					do {
						target.teleport(target.getLocation().clone().add(loc.getDirection()));
					} while (!BlockUtil.isPathable(target.getLocation().getBlock())); 
					ftick = 0;
				}
			} else {
				ftick = 0;
			}
			floc = target.getLocation();
		}
		return false;
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(tick > 0) {
			e.setCancelled(true);
		}
		return false;
	}
}
