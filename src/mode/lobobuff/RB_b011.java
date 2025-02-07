package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;

public class RB_b011 extends LoboBuffBase{
	public RB_b011() {
		id = 11;
	}
	@Override
	public void onTime(int time) {
		if(stageTime()%9 == 0) {
			Player t = null;
			float pw = 0;
			
			for(Player p : Rule.c.keySet()) {
				if(Rule.buffmanager.GetBuffTime(p, "panic") > pw) {
					t = p;
					pw = Rule.buffmanager.GetBuffTime(p, "panic");
				}
			}
			if(t != null) {
				ARSystem.playSound(t,"healbullet");
				Rule.buffmanager.selectBuffTime(t, "panic", 0);
				ARSystem.addBuff(t, new Nodamage(t), 40);
			}
		}
	}
}