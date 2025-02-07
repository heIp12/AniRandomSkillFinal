package mode.lobobuff;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import addon.util.Holo;
import ars.ARSystem;
import event.Skill;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_b047 extends LoboBuffBase{
	LivingEntity en;
	public RB_b047() {
		id = 47;
	}
	
	
	@Override
	public LivingEntity onEntityCreate(String name, int size) {
		if(name.contains("by")) {
			this.en =  Map.spawnMM(name, Map.randomLoc());
			return this.en;
		}
		return null;
	}
	int t = 0;
	@Override
	public void onTick() {
		if(en != null && (en.isDead() || en.getHealth() < 1)) en = null;
		if(en != null) {
			t++;
			if(t%5 == 0) {
				Holo.create(en.getLocation().clone().add(AMath.random(10)*0.2-1,0,AMath.random(10)*0.2-1), "§b§l☠ RESIST 66",20, new Vector(0,0.2,0));
				ARSystem.spellLocCast(NpcPlayer.npc(en.getLocation()), en.getLocation().clone().add(AMath.random(10)*0.2-1,0,AMath.random(10)*0.2-1), "whilekill");
				en.damage(66,NpcPlayer.npc(en.getLocation()));
			}
		}
	}
}