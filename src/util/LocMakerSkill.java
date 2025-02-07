package util;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.MagicSpellsEntityDamageByEntityEvent;
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedLocationSpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.TargetInfo;
import com.nisovin.magicspells.util.compat.EventUtil;

import ars.Rule;
import event.Skill;

public class LocMakerSkill extends InstantSpell implements TargetedEntitySpell, TargetedLocationSpell{
	private String name;

	
	public LocMakerSkill(MagicConfig config, String spellName) {
		super(config, spellName);
		
		name = getConfigString("name", "no");

	}

	@Override
	public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
		if (state == SpellCastState.NORMAL) {
			TargetInfo<LivingEntity> target = getTargetedEntity(player, power);
			if (target == null) {
				// Fail
				return PostCastAction.NO_MESSAGES;
			}
			
			boolean done = maker(player, player.getLocation());

			return PostCastAction.NO_MESSAGES;
		}
		return PostCastAction.HANDLE_NORMALLY;
	}
	
	private boolean maker(Player player, Location loc) {
		if(Rule.c.get(player) != null) {
			Rule.c.get(player).LocmakerSkill(loc,name);
		}
		return true;
	}

	@Override
	public boolean castAtEntity(Player caster, LivingEntity target, float power) {
		if (!validTargetList.canTarget(caster, target)) return false;
		return maker(caster, target.getLocation());
	}

	@Override
	public boolean castAtEntity(LivingEntity target, float power) {
		return false;
	}

	@Override
	public boolean castAtLocation(Location arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean castAtLocation(Player arg0, Location arg1, float arg2) {
		// TODO Auto-generated method stub
		return maker(arg0, arg1);
	}

}
