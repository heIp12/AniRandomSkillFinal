package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Panic;

public class RB_b006 extends LoboBuffBase{
	public RB_b006() {
		id = 6;
	}	
	
	@Override
	public void onTime(int time) {
		if(stageTime()%5 == 0) {
			ARSystem.playSoundAll("theresia");
			for(Player p : Rule.c.keySet()) {
				if(stageTime()%50 == 0) {
					ARSystem.giveBuff(p, new Panic(p), 100);
				} else {
					if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
						Rule.buffmanager.selectBuffAddTime(p, "panic", -300);
					}
				}
			}
		}
	}
}