package util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.nisovin.magicspells.events.MagicSpellsEntityDamageByEntityEvent;
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.compat.EventUtil;

import ars.ARSystem;
import ars.Rule;
import event.Skill;

public class MagicSpellDamage extends InstantSpell implements TargetedEntitySpell{
	double damage;
	boolean remove;
    boolean fixed;
    boolean quit;

	public MagicSpellDamage(MagicConfig config, String spellName) {
		super(config, spellName);
		this.damage = getConfigDouble("damage", 1.0);
		this.remove = getConfigBoolean("remove", false);
		this.quit = getConfigBoolean("quit", false);
		this.fixed = getConfigBoolean("fixed", false);
	}

	@Override
	public boolean castAtEntity(LivingEntity arg0, float arg1) {
		return false;
	}

	@Override
	public boolean castAtEntity(Player arg0, LivingEntity arg1, float arg2) {
		if(quit) {
			Skill.quit(arg1);
		}
		if(fixed) {
			ARSystem.fixedDamage(arg1, arg0, damage);
		} else {
			if(remove && Rule.c.get(arg1) != null) {
				Skill.remove(arg1, arg0);
			} else {
				arg1.setNoDamageTicks(0);
				arg1.damage(damage,arg0);
			}
		}
		return true;
	}

	@Override
	public PostCastAction castSpell(Player arg0, SpellCastState arg1, float arg2, String[] arg3) {
		return null;
	}
}
