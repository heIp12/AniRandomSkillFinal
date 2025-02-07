package util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.MagicConfig;

import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
import buff.Exposure;
import buff.Ice;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;

public class Coin extends InstantSpell implements TargetedEntitySpell{
	int coin;

	public Coin(MagicConfig config, String spellName) {
		super(config, spellName);
	    this.coin = getConfigInt("coin", 100);
	}

	@Override
	public boolean castAtEntity(LivingEntity arg0, float arg1) {
		
		set(arg0);
		return true;
	}

	@Override
	public boolean castAtEntity(Player arg0, LivingEntity arg1, float arg2) {
		set(arg1);
		return true;
	}

	@Override
	public PostCastAction castSpell(Player arg0, SpellCastState arg1, float arg2, String[] arg3) {
		set(arg0);
		return null;
	}
	
	public void set(LivingEntity e) {
		if(Rule.c.get(e) != null) {
			if(coin >= 1000) {
				ARSystem.playSound(e,"0money2");
			} else {
				ARSystem.playSound(e,"0money1");
			}
			Rule.playerinfo.get(e).gold += coin;
		}
	}
}
