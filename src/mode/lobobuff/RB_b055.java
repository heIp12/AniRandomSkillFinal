package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Bload;
import buff.NoCC;

public class RB_b055 extends LoboBuffBase{
	public RB_b055() {
		id = 55;
	}
	
	@Override
	public void onNextStage() {
		for(int i =1; i<3; i++)
		delay(()->{
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new NoCC(p), 40);
			}
		},100*i);
	}
}