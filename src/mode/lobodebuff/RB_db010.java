package mode.lobodebuff;

import mode.lobobuff.LoboBuffBase;
import util.Map;

public class RB_db010 extends LoboBuffBase{
	public RB_db010() {
		id = 10;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(stageTime()%39 == 0) {
			getlobo().etcmobs.add(Map.spawnMM("bunny", Map.randomLoc()));
		}
	}
}
