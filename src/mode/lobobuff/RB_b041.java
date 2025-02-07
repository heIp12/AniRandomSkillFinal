package mode.lobobuff;

import org.bukkit.entity.LivingEntity;

import ars.ARSystem;
import event.Skill;
import util.Map;
import util.NpcPlayer;

public class RB_b041 extends LoboBuffBase{
	public RB_b041() {
		id = 41;
	}
	
	@Override
	public void onTime(int time) {
		if(getlobo().level < 9) {
			for(LivingEntity e : getlobo().mobs) {
				if(e.getHealth()/e.getMaxHealth() <= 0.1 && !e.isDead()) {
					ARSystem.spellCast(NpcPlayer.npc(Map.getCenter()), e, "bload");
					Skill.quit(e);
				}
			}
		}
	}
}