package mode.lobodebuff;

import mode.lobobuff.LoboBuffBase;

public class RB_db012 extends LoboBuffBase{
	public RB_db012() {
		id = 12;
		debuff = true;
		getlobo().mobCountValue += 0.5;
	}
	
	@Override
	public void onRemove() {
		getlobo().mobCountValue -= 0.5;
	}
}
