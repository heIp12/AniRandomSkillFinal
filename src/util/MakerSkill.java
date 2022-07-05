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

public class MakerSkill extends InstantSpell implements TargetedEntitySpell{
	private String name;

	
	public MakerSkill(MagicConfig config, String spellName) {
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
			
			boolean done = maker(player, target.getTarget());

			return PostCastAction.NO_MESSAGES;
		}
		return PostCastAction.HANDLE_NORMALLY;
	}
	
	private boolean maker(Player player, LivingEntity target) {
		if(Rule.c.get(player) != null) {
			Rule.c.get(player).makerSkill(target, name);
		}
		return true;
	}

	@Override
	public boolean castAtEntity(Player caster, LivingEntity target, float power) {
		if (!validTargetList.canTarget(caster, target)) return false;
		return maker(caster, target);
	}

	@Override
	public boolean castAtEntity(LivingEntity target, float power) {
		return false;
	}

}
