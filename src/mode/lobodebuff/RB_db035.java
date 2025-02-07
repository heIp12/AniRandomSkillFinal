package mode.lobodebuff;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import event.Skill;
import mode.lobobuff.LoboBuffBase;
import types.TargetMap;
import types.box;
import util.AMath;
import util.Map;
import util.NpcPlayer;

public class RB_db035 extends LoboBuffBase{
	TargetMap<LivingEntity, Double> tg = new TargetMap<>();
	TargetMap<Location,Double> loc = new TargetMap<>();
	int pj = 0;
	
	public RB_db035() {
		id = 35;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%80 == 0) {
			pj = 20;
			ARSystem.playSoundAll("prince1");
		} else {
			pj--;
		}
		
		if(pj > 0) {
			if(time%3 == 0) {
				for(int i = 0; i < AMath.random(8); i++) {
					Location loc = Map.randomLoc();
					ARSystem.spellLocCast(NpcPlayer.npc(loc), loc, "babyking");
					this.loc.add(loc, 1.75);
				}
				for(LivingEntity e : tg.get().keySet()) {
					if(AMath.random(100) <= Math.max(5,tg.get(e)*10)) {
						if(e instanceof Player && ((Player) e).getGameMode() != GameMode.SPECTATOR) {
							Location loc = e.getLocation().clone();
							ARSystem.spellLocCast(NpcPlayer.npc(loc), loc, "babyking");
							this.loc.add(loc.clone(), 1.75);
						}
					}
				}
			}
		}

		for(LivingEntity e : tg.get().keySet()) {
			if(Rule.c.get(e) != null) {
				Rule.c.get(e).hpCost(tg.get(e), true);
			}
		}
	}
	
	@Override
	public void onTick() {
		loc.addAll(-0.05);
		for(Location lc : loc.get().keySet()) {
			if(loc.get(lc) != null && loc.get(lc) <= 0) {
				for(Entity en : ARSystem.box(lc, NpcPlayer.npc(lc), new Vector(8,8,8), box.ALL)){
					if(Rule.c.get(en) != null) {
						Player p = (Player)en;
						tg.add(p, 0.3f);
						if(tg.get(p) >= 6) {
							ARSystem.spellLocCast(NpcPlayer.npc(p.getLocation()), p.getLocation(), "babyking2");
							for(Entity en2 : ARSystem.box(p.getLocation(), NpcPlayer.npc(p.getLocation()), new Vector(10,10,10), box.ALL)){
								if(Rule.c.get(en2) != null) {
									tg.add((LivingEntity)en2, 1.5f);
									((Player)en2).sendTitle("§9§l[Damage]", "§f"+AMath.round(tg.get((Player)en2),2) +" §c(s)",10,10,10);
								}
							}
							Skill.remove(p, p);
						} else {
							p.sendTitle("§9§l[Damage]", "§f"+AMath.round(tg.get(p),2) +" §c(s)",10,10,10);
						}
					}
				}
			}
		}
		
		loc.removes();
	}
}
