package mode.lobodebuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.LongBird;
import mode.lobobuff.LoboBuffBase;
import mode.lobobuff.RB_b046;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db029 extends LoboBuffBase{
	public RB_db029() {
		id = 29;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(stageTime()%44 == 0) {
			ARSystem.playSoundAll("longbirdon");
			delay(()->{
				ARSystem.playSoundAll("longbirdstun");
				for(Player e : Rule.c.keySet()) {
					ARSystem.giveBuff(e, new LongBird(e, NpcPlayer.npc(e.getLocation())), 60);
				}
			},60);
		}
	}
}
