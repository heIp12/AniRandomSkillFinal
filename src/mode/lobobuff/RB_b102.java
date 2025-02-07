package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import util.NpcPlayer;

public class RB_b102 extends LoboBuffBase{
	public RB_b102() {
		id = 102;
	}
	
	@Override
	public void onTime(int time) {
		for(LivingEntity e : getlobo().mobs) {
			e.setNoDamageTicks(0);
			e.damage(3,NpcPlayer.npc(e.getLocation()));
		}
		for(LivingEntity e : getlobo().etcmobs) {
			e.setNoDamageTicks(0);
			e.damage(3,NpcPlayer.npc(e.getLocation()));
		}
	}
}