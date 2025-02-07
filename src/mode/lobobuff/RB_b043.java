package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Bload;
import buff.NoCC;

public class RB_b043 extends LoboBuffBase{
	public RB_b043() {
		id = 43;
	}
	
	@Override
	public void onTime(int time) {
		ARSystem.giveBuff(getlobo().bast, new NoCC(getlobo().bast), 100000);
		super.onTime(time);
	}
	
	@Override
	public void onPlayerChar(Player p) {
		if(p == getlobo().bast) {
			Rule.c.get(p).frist_damage *= 1.3;
			ARSystem.giveBuff(p, new NoCC(p), 100000);
			ARSystem.giveBuff(p, new Bload(p), 100000,0.15);
			for(int i =0; i <10; i++) {
				if(i == 1 || i == 7 || i == 9) Rule.c.get(p).setcooldown[i] *= 0.35;
				else Rule.c.get(p).setcooldown[i] = -100000;
			}
		}
	}
}