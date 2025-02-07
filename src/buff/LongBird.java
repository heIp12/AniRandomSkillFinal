package buff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import types.BuffType;

public class LongBird extends Buff{
	
	public LongBird(LivingEntity target, Player player) {
		super(target);
		bufftype.add(BuffType.DEBUFF);
		buffName = "eyes";
		color = "§f§l";
		onlyone = true;
		isText = true;
	}

	@Override
	public boolean onSkill(PlayerItemHeldEvent e) {
		if(e.getNewSlot()+1 != 8) {
			ARSystem.spellCast((Player) target, "simfan");
			ARSystem.playSound(target, "longbirdhanging");
			Skill.quit(target);
		}
		return false;
	}
}
