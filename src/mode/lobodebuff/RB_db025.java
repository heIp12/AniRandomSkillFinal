package mode.lobodebuff;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import ars.ARSystem;
import ars.Rule;
import buff.Reflect;
import mode.lobobuff.LoboBuffBase;
import util.NpcPlayer;

public class RB_db025 extends LoboBuffBase{
	public RB_db025() {
		id = 25;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%14 == 0) {
			ARSystem.playSoundAll("blackswan");
			for(LivingEntity e : getlobo().mobs) {
				ARSystem.spellLocCast(NpcPlayer.npc(e.getLocation()), e.getLocation(), "blackswan");
				Bukkit.getScheduler().scheduleAsyncDelayedTask(Rule.gamerule, ()->{
					ARSystem.giveBuff(e, new Reflect(e), 40, 2);
				},20);
			}
		}
	}
}
