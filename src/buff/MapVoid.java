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
import manager.Holo;
import types.BuffType;
import util.AMath;

public class MapVoid extends Buff{
	Location loc;
	public MapVoid(LivingEntity target) {
		super(target);
		bufftype.add(BuffType.BUFF);
		buffName = "void";
		isText = false;
		isScore = false;
		color = "§f";
		order = 1;
	}

	public boolean onTicks() {
		if(Rule.c.get(target) != null) {
			Rule.c.get(target).maptick = 0;
		}
		return false;
	}
}
