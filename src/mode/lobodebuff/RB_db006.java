package mode.lobodebuff;

import ars.ARSystem;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Text;

public class RB_db006 extends LoboBuffBase{
	public RB_db006() {
		id = 6;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%120 == 0) {
			int level = getlobo().level;
			int count = getlobo().count;
			String txt = Text.get("lobo:"+level+"-"+(count-1));
			if(txt == null) txt = Text.get("lobo:"+level+"-"+count);
			String[] s = txt.split(",");
			String[] mob = s[AMath.random(s.length)-1].split(":");
			getlobo().spawn(mob[0] , Integer.parseInt(mob[1]),true);
			ARSystem.playSoundAll("babycry");
		}
	}
}
