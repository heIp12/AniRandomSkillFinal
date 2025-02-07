package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import event.Skill;

public class RB_b036 extends LoboBuffBase{
	public RB_b036() {
		id = 36;
	}
	
	@Override
	public void onTime(int time) {
		for(Player p : Rule.c.keySet()) {
			for(LivingEntity e : getlobo().vilager) {
				if(e.getLocation().distance(p.getLocation()) < 3 && e.getHealth()/e.getMaxHealth() <= 0.1) {
					getlobo().delay(()->{
						ARSystem.heal(p, 20);
						Rule.c.get(p).frist_defence *= 0.9;
					},40);
					ARSystem.spellCast(p, e, "bload");
					Skill.quit(e);
					for(int i = 0; i<10; i++) ARSystem.spellLocCast(p, e.getLocation(), "cbamp");
				}
			}
		}
	}
}