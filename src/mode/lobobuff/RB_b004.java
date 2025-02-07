package mode.lobobuff;

import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import util.AMath;
import util.Text;

public class RB_b004 extends LoboBuffBase{
	public RB_b004() {
		id = 4;
	}	
	
	@Override
	public void onPlayerChar(Player p) {
		if(p == lobo.bast) ARSystem.playSoundAll("promise");
		float rt = (AMath.random(0, 110)+40)*0.01f;
		Rule.c.get(p).frist_damage *= rt;
		p.sendTitle("§f"+Text.get("lobo:b4"), (int)(rt*100) + "%",0,40,50);
	}
}