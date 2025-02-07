package mode.lobodebuff;

import ars.ARSystem;
import mode.lobobuff.LoboBuffBase;
import util.Map;
import util.NpcPlayer;

public class RB_db020 extends LoboBuffBase{
	public RB_db020() {
		id = 20;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%8 == 0) {
			ARSystem.spellLocCast(NpcPlayer.npc(Map.randomLoc()), Map.randomLoc(), "apple");
		}
	}
}
