package util;

import java.util.ArrayList;
import java.util.List;

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
import com.nisovin.magicspells.util.compat.EventUtil;

import ars.Rule;
import event.Skill;

public class MagicSpellText extends InstantSpell implements TargetedLocationSpell{
	List<String> txt;
	int tick;
	int range = 0;
	
	public MagicSpellText(MagicConfig config, String spellName) {
		super(config, spellName);
		this.txt = getConfigStringList("text", new ArrayList<String>());
		this.tick = getConfigInt("tick", 20);
		this.range = (int)(getConfigFloat("range", 0) * 100);
	}

	@Override
	public PostCastAction castSpell(Player arg0, SpellCastState arg1, float arg2, String[] arg3) {
		String txt = this.txt.get(AMath.random(this.txt.size())-1);
		Holo.create(arg0.getLocation().add((range/2 - AMath.random(0, range))*0.01,(range/2 - AMath.random(0, range))*0.01,(range/2 - AMath.random(0, range))*0.01), txt ,tick, 
				new Vector(0,AMath.random(100)*0.001,0));
		return null;
	}

	@Override
	public boolean castAtLocation(Location arg0, float arg1) {
		String txt = this.txt.get(AMath.random(this.txt.size())-1);
		Holo.create(arg0.add((range/2 - AMath.random(0, range))*0.01,(range/2 - AMath.random(0, range))*0.01,(range/2 - AMath.random(0, range))*0.01), txt ,tick, new Vector(0,AMath.random(300)*0.001,0));
		return false;
	}

	@Override
	public boolean castAtLocation(Player arg0, Location arg1, float arg2) {
		String txt = this.txt.get(AMath.random(this.txt.size())-1);
		Holo.create(arg1.add((range/2 - AMath.random(0, range))*0.01,(range/2 - AMath.random(0, range))*0.01,(range/2 - AMath.random(0, range))*0.01), txt ,tick, new Vector(0,AMath.random(300)*0.001,0));
		return false;
	}
}
