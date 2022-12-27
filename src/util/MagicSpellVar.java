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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;

public class MagicSpellVar extends InstantSpell implements TargetedEntitySpell{
	double number;
	boolean isCooldown;
	boolean add;
	int set;
    

	public MagicSpellVar(MagicConfig config, String spellName) {
		super(config, spellName);
		this.number = getConfigDouble("size", 1.0);
	    this.isCooldown = getConfigBoolean("iscooldown", true);
	    this.add = getConfigBoolean("isadd", true);
	    this.set = getConfigInt("set", 0);
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
		if(isCooldown) {
			if(Rule.c.get(e) != null) {
				if(add) {
					Rule.c.get(e).cooldown[set]+= number;
				} else {
					Rule.c.get(e).cooldown[set] = (float) number;
				}
			}
		} else {
			
			if(add) {
				if(set == 0) ARSystem.addBuff(e, new TimeStop(e), (int) (number*20), 0);
				if(set == 1) ARSystem.addBuff(e, new Silence(e), (int) (number*20), 0);
				if(set == 2) ARSystem.addBuff(e, new Stun(e), (int) (number*20), 0);
				if(set == 3) ARSystem.addBuff(e, new Nodamage(e), (int) (number*20), 0);
				if(set == 4) ARSystem.addBuff(e, new Noattack(e), (int) (number*20), 0);
				if(set == 5) ARSystem.addBuff(e, new Timeshock(e), (int) (number*20), 0);
				if(set == 6) ARSystem.addBuff(e, new Panic(e), (int) (number*20), 0);
				if(set == 7) ARSystem.addBuff(e, new Airborne(e), (int) (number*20), 0);
			} else {
				if(set == 0) ARSystem.giveBuff(e, new TimeStop(e), (int) (number*20));
				if(set == 1) ARSystem.giveBuff(e, new Silence(e), (int) (number*20));
				if(set == 2) ARSystem.giveBuff(e, new Stun(e), (int) (number*20));
				if(set == 3) ARSystem.giveBuff(e, new Nodamage(e), (int) (number*20));
				if(set == 4) ARSystem.giveBuff(e, new Noattack(e), (int) (number*20));
				if(set == 5) ARSystem.giveBuff(e, new Timeshock(e), (int) (number*20));
				if(set == 6) ARSystem.giveBuff(e, new Panic(e), (int) (number*20));
				if(set == 7) ARSystem.giveBuff(e, new Airborne(e), (int) (number*20));
			}
		}
	}
}
