package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;

public class RB_b103 extends LoboBuffBase{
	public RB_b103() {
		id = 103;
	}
	
	@Override
	public void onNextStage() {
		for(Player p : Rule.c.keySet()) {
			ARSystem.overheal(p, 20);
			Rule.c.get(p).frist_damage += 0.25f;
			Rule.c.get(p).frist_defence *= 0.1f;
		}
	}
}