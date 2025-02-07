package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Fascination;
import buff.Follow;
import buff.PowerUp;
import buff.Rampage;
import event.Skill;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class RB_db033 extends LoboBuffBase{
	Player p;
	Location loc;
	int timer = 0;
	
	public RB_db033(){
		id = 33;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%123-Math.min(33,getlobo().cr.size()*5) == 0) {
			p = ARSystem.RandomPlayer();
			loc = Map.randomLoc();
			timer = 14;
			ARSystem.potion(p, 1, 280, 1);
			ARSystem.playSoundAll("galaxycry");
			ARSystem.spellLocCast(p, loc, "spaceloc");
			p.teleport(ULocal.lookAt(p.getLocation().clone(), loc));
		}
		if(timer > 0) {
			for(int i =0; i<20; i++) {
				delay(()->{
					if(p != null) {
						ARSystem.spellCast(p,p, "space");
						ARSystem.spellLocCast(p,ULocal.lookAt(p.getLocation().clone(), loc), "space2");
					}
				},i);
			}
			timer--;
			if(Rule.c.get(p) == null) {
				p = null;
				loc = null;
				timer = 0;
				ARSystem.playSoundAll("galaxy");
				for(Player entity : Bukkit.getOnlinePlayers()) entity.stopSound("galaxycry");
			} else if(p.getLocation().distance(loc) < 5) {
				ARSystem.heal(p, 1000);
				Rule.buffmanager.selectBuffTime(p, "panic", 0);
				p = null;
				loc = null;
				timer = 0;
				ARSystem.playSoundAll("galaxy");
				for(Player entity : Bukkit.getOnlinePlayers()) entity.stopSound("galaxycry");
			} else if(timer == 0) {
				Skill.remove(p, p);
			}
		}
	}
}
