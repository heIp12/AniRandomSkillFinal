package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Fascination;
import buff.PowerUp;
import buff.Rampage;
import mode.lobobuff.LoboBuffBase;
import util.AMath;
import util.Text;

public class RB_db011 extends LoboBuffBase{
	public RB_db011() {
		id = 11;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%88-Math.min(63,getlobo().cr.size()*3) == 0) {
			Player p = ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1]);
			ARSystem.playSoundAll("redshoo");
			ARSystem.giveBuff(p, new PowerUp(p), 400, 3);
			ARSystem.giveBuff(p, new Rampage(p), 400, 2);
			String name = p.getCustomName();
			p.setCustomName(Text.get("lobo:o31_t"));
			ARSystem.giveBuff(p, new Fascination(p, ((Player)Rule.c.keySet().toArray()[AMath.random(Rule.c.size())-1])), 400, 2);
			double hp = p.getMaxHealth();
			p.setMaxHealth(p.getMaxHealth()*5);
			p.setHealth(p.getHealth()*5);
			Rule.team.teamQuit("H", p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Rule.gamerule, ()->{
				p.setHealth(p.getHealth()/5);
				p.setMaxHealth(hp);
				Rule.team.teamJoin("H", p);
				p.setCustomName(name);
			},400);
		}
	}
}
