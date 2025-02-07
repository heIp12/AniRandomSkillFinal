package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Stun;
import mode.lobobuff.LoboBuffBase;
import mode.lobobuff.RB_b046;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db028 extends LoboBuffBase{
	int cooldown = 0;
	LivingEntity tg;
	public RB_db028() {
		id = 28;
		debuff = true;
	}
	
	
	@Override
	public void onTime(int time) {
		if(tg != null && AMath.random(10) <= 1 && cooldown < 0) {
			tg = null;
			cooldown = 15;
		}
		if(time%5 == 0 && tg != null && AMath.random(10) <= 3 && cooldown < 0) {
			ARSystem.playSound(tg, "magicalgirl3gift", 1, 0.3f);
			for(int i =0; i<20; i++) {
				delay(()->{
					if(tg!=null) ARSystem.spellLocCast(NpcPlayer.npc(tg.getLocation()), tg.getLocation(), "lobo_magic3");
				},i);
			}
			cooldown = 15;
		}
		
		if(cooldown <= 0 && AMath.random(8) <= 1) {
			tg = getlobo().mobs.get(AMath.random(getlobo().mobs.size())-1);
			cooldown = 15;
		}
		cooldown--;
	}
	
	@Override
	public void onEntityDie(LivingEntity en, LivingEntity killer) {
		if(tg == en) {
			tg = null;
			cooldown = 10;
			for(int i =0;i<14;i++) {
				delay(()->{
					Location loc = killer.getLocation();
					loc.setYaw(AMath.random(360));
					loc.setPitch(AMath.random(360));
					delay(()->{
						killer.setNoDamageTicks(0);
						killer.damage(1,NpcPlayer.npc(killer.getLocation()));
					},3);
					ARSystem.spellLocCast(NpcPlayer.npc(killer.getLocation()),loc, "magicalgirl3atk");
					ARSystem.playSound(killer, "magicalgirl3atk", 1, 0.3f);
					ARSystem.giveBuff(killer, new Stun(killer), 20);
				},i);
			}
		}
	}
}
