package buff;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import chars.c.c00main;
import chars.ch.e002rain;
import types.BuffType;
import util.AMath;
import util.GetChar;
import util.Holo;

public class Fix extends Buff{
	String effect = "fix";
	c00main c;
	public boolean stop = false;
	
	public Fix(LivingEntity target) {
		super(target);
		buffName = "fix";
		onlyone = true;
		alltime = true;
		isScore = true;
		color = "§e";
		order = 1000;
		c = Rule.c.get(target);
	}

	@Override
	public boolean onTicks() {
		if(target == null || !Bukkit.getOnlinePlayers().contains(target)) {
			target = null;
			stop = true;
		}
		return true;
	}
	
	@Override
	public void last() {
		if(ARSystem.AniRandomSkill != null && target != null && !stop) {
			stop = true;
			Rule.buffmanager.getBuffs(target).removeBuff(this);
			Rule.buffmanager.getBuffs(target).run();
			Rule.c.put((Player)target, new e002rain((Player)target, Rule.gamerule, c));
			((e002rain)Rule.c.get(target)).l(14);
		}
	}
	
}
