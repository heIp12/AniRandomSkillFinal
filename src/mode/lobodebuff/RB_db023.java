package mode.lobodebuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import manager.Bgm;
import mode.MLoboTomy;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Text;

public class RB_db023 extends LoboBuffBase{
	int delay = 10;
	public RB_db023() {
		id = 23;
		debuff = true;
		
		delay = 1;
		getlobo().nobgmList.add("m5");
		delay(()->{Bgm.setBgm("m5");},10);
	}
	
	@Override
	public void onTime(int time) {
		if(delay > 0) {
			delay--;
			return;
		}
		if(Bgm.bgmNameCode.equals("m5")) {
			if(time%3 == 0) {
				for(Player p : Rule.c.keySet()) {
					ARSystem.giveBuff(p, new Silence(p), 20, 0);
				}
			}
			if(time%4 == 0) {
				getlobo();
				for(LivingEntity e : MLoboTomy.mobs) {
					ARSystem.heal(e, e.getMaxHealth()*0.15 + 8);
					getlobo();
					ARSystem.potion(e, 22, 40, MLoboTomy.level/2);
				}
			}
		} else {
			getlobo().nobgmList.remove("m5");
			MLoboTomy.text(393,getName() + Text.get("lobo:ot2"));
			
			int size = MLoboTomy.debuff.size()-1;
			MLoboTomy.debuff.clear();
			int i = 1;
			while(Text.get("lobo:o"+i) != null) i++;
			for(int j = 0; j < size; j++) {
				int r = AMath.random(i);
				getlobo();
				while(r == id || MLoboTomy.isdebuff(r)) r = AMath.random(i);
				getlobo();
				MLoboTomy.adddebuff(""+r);
			}
			
		}
	}
	
	@Override
	public void onRemove() {

		delay(()->{
			MLoboTomy.debuff.remove(this);
		},5);
	}
}
