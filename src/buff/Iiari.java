package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import ars.ARSystem;
import ars.Rule;
import types.BuffType;

public class Iiari extends Buff{
	Player p;
	
	public Iiari(LivingEntity target, Player player) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		buffName = "iiari";
		color = "§c";
		onlyone = true;
		isText = false;
		p = player;
	}

	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(e.getNewSlot()+1 != 8) {
			Rule.c.get(e.getPlayer()).cooldown[e.getNewSlot()+1] = 120;
			((Player)target).performCommand("c c78_e1");
			ARSystem.playSound(target, "c78s1");
			e.getPlayer().damage(Rule.c.get(e.getPlayer()).setcooldown[e.getNewSlot()+1]/2,p);
			e.setCancelled(true);
			tick = 0;
		}
		return false;
	}
}
