package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.NoCC;

public class RB_b033 extends LoboBuffBase{
	public RB_b033() {
		id = 33;
	}	
	
	@Override
	public void onTime(int time) {
		if(time%5 == 0) {

			Player t=null;double h = 2;
			for(Player pl : Rule.c.keySet()) {
				if(pl.getHealth()/pl.getMaxHealth() < h) {
					t = pl;
					h = pl.getHealth()/pl.getMaxHealth();
				}
			}
			ARSystem.addBuff(t, new NoCC(t), 60);
		}
	}
}