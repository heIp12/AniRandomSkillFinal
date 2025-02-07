package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Fascination;
import buff.Follow;
import buff.PowerUp;
import buff.Rampage;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.NpcPlayer;
import util.Text;

public class RB_db032 extends LoboBuffBase{
	Player p;
	LivingEntity p2;
	
	public RB_db032() {
		id = 32;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%44-Math.min(33,getlobo().cr.size()*5) == 0) {
			p = ARSystem.RandomPlayer();
			if(Rule.c.size() > 1) {
				p2 = ARSystem.RandomPlayer(p);
			} else {
				p2 = getlobo().mobs.get(AMath.random(getlobo().mobs.size())-1);
			}
			if(getlobo().cr.size() == 1) {
				ARSystem.giveBuff(p, new Follow(p, p2), 40, 0.5);	
			} else if(getlobo().cr.size() <= 2) {
				ARSystem.giveBuff(p, new Follow(p, p2), 140, 0.22);	
			} else if(getlobo().cr.size() > 5) {
				ARSystem.giveBuff(p, new Follow(p, p2), 200, 0.4);	
			} else  {
				ARSystem.giveBuff(p, new Follow(p, p2), 160, 0.25);
			}
		}
		if(Rule.buffmanager.isBuff(p, "follow")) {
			if(p.getLocation().distance(p2.getLocation()) <= 2) {
				ARSystem.playSoundAll("happyteddy");
				Rule.buffmanager.selectBuffValue(p, "barrier", 0);
				Rule.buffmanager.selectBuffValue(p2, "barrier", 0);
				Rule.buffmanager.selectBuffValue(p, "plushp", 0);
				Rule.buffmanager.selectBuffValue(p2, "plushp", 0);
				
				if(!Rule.c.get(p).hpCost(15, false)) p.setHealth(1);
				if(Rule.c.get(p2) != null) {
					if(!Rule.c.get(p2).hpCost(15, false)) {
						p2.setHealth(1);
					}
				} else {
					p2.damage(15,NpcPlayer.npc(p.getLocation()));
				}
				Rule.buffmanager.selectBuffTime(p, "follow", 0);
				ARSystem.spellCast(p, "lobo_teddy");
			}
		} else {
			p = null;
			p2 = null;
		}
	}
}
