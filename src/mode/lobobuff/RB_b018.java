package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;

public class RB_b018 extends LoboBuffBase{
	public RB_b018() {
		id = 18;
		getlobo().mobCountValue += 0.3;
	}
	@Override
	public void onNextStage() {
		for(Player p : Rule.c.keySet()) {
			ARSystem.heal(p, p.getMaxHealth() * 0.5);
			ARSystem.giveBuff(p, new Nodamage(p), 200);
		}
	}
}