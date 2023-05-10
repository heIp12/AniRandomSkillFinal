package buff;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import ars.Rule;
import event.Skill;
import types.BuffType;
import util.AMath;
import util.Holo;

public class Silence extends Buff{
	Location loc;
	public Silence(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		bufftype.add(BuffType.SILENCE);
		bufftype.add(BuffType.CC);
		buffName = "silence";
		onlyone = true;
		color = "§8";
	}
	
	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		e.setCancelled(true);
		return false;
	}
}
