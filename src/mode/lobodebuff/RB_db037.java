package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import manager.Bgm;
import mode.lobobuff.LoboBuffBase;
import types.box;
import util.AMath;
import util.Map;
import util.NpcPlayer;
import util.ULocal;

public class RB_db037 extends LoboBuffBase{
	public RB_db037() {
		id = 37;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%45 == 0) {
			Location l = Map.randomLoc();
			if(AMath.random(5) <= 1) {
				l.setY(56);
				l.setX(110);
			}
			if(Rule.c.size() > 0) {
				Location lc = ULocal.lookAt(l, ARSystem.RandomPlayer().getLocation());
				ARSystem.playSoundAll("magicalgirl2warp");
				for(int i =0; i<40; i++) {
					delay(()->{
						ARSystem.spellLocCast(NpcPlayer.npc(lc), lc, "magic2e");
					},i);
				}
				delay(()->{
					ARSystem.spellLocCast(NpcPlayer.npc(lc), lc, "magic2");
				},40);
			}
		}
	}
}
