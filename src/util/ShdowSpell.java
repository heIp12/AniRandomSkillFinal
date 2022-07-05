package util;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.MagicSpellsEntityDamageByEntityEvent;
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.TargetInfo;
import com.nisovin.magicspells.util.compat.EventUtil;

import ars.Rule;
import event.Skill;

public class ShdowSpell extends InstantSpell implements TargetedEntitySpell{
	private String strNoLandingSpot;
	private double distance;

	private float yaw;
	private float pitch;
	Vector relativeOffset;
	
	public ShdowSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		
		strNoLandingSpot = getConfigString("str-no-landing-spot", "Cannot shadowstep there.");
		distance = getConfigDouble("distance", -1);

		pitch = getConfigFloat("pitch", 0);
		yaw = getConfigFloat("yaw", 0);
		relativeOffset = getConfigVector("relative-offset", "-1,0,0");
		if (distance != -1) relativeOffset.setX(distance);
	}

	@Override
	public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
		if (state == SpellCastState.NORMAL) {
			TargetInfo<LivingEntity> target = getTargetedEntity(player, power);
			if (target == null) {
				// Fail
				return PostCastAction.NO_MESSAGES;
			}
			
			boolean done = shadowstep(player, target.getTarget());

			return PostCastAction.NO_MESSAGES;
		}
		return PostCastAction.HANDLE_NORMALLY;
	}
	
	private boolean shadowstep(Player player, LivingEntity target) {
		// Get landing location
		Location targetLoc = target.getLocation().clone();
		targetLoc.setPitch(0);

		Vector startDir = targetLoc.getDirection().setY(0).normalize();
		Vector horizOffset = new Vector(-startDir.getZ(), 0, startDir.getX()).normalize();

		targetLoc.add(horizOffset.multiply(relativeOffset.getZ())).getBlock().getLocation();
		targetLoc.add(targetLoc.getDirection().setY(0).multiply(relativeOffset.getX()));
		targetLoc.setY(targetLoc.getY() + relativeOffset.getY());

		targetLoc.setPitch(pitch);
		targetLoc.setYaw(targetLoc.getYaw() + yaw);

		// Ok
		playSpellEffects(player.getLocation(), targetLoc);
		player.teleport(targetLoc);

		return true;
	}

	@Override
	public boolean castAtEntity(Player caster, LivingEntity target, float power) {
		if (!validTargetList.canTarget(caster, target)) return false;
		return shadowstep(caster, target);
	}

	@Override
	public boolean castAtEntity(LivingEntity target, float power) {
		return false;
	}

}
