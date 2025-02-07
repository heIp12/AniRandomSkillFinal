package mode.lobodebuff;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import event.Skill;
import mode.lobobuff.LoboBuffBase;
import mode.lobobuff.RB_b046;
import util.Map;
import util.NpcPlayer;

public class RB_db022 extends LoboBuffBase{
	boolean remove = false;
	public RB_db022() {
		id = 22;
		debuff = true;
	}
	
	@Override
	public void onTime(int time) {
		if(time%3 == 0) {
			if(!remove) for(LoboBuffBase bf : getlobo().buff) if(bf instanceof RB_b046) remove = true;
			ARSystem.playSoundAll("bluestar");
			if(remove) {
				for(Player p : Rule.c.keySet()) {
					if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
						Rule.buffmanager.selectBuffTime(p, "panic", 0);
						ARSystem.heal(p, 4);
					}
					ARSystem.heal(p, 1);
				}
			} else {
				for(Player p : Rule.c.keySet()) {
					if(Rule.buffmanager.GetBuffTime(p, "panic") > 1000) {
						Remove(p);
					} else {
						p.setNoDamageTicks(0);
						if(Rule.buffmanager.GetBuffTime(p, "panic") > 0) {
							p.damage(5,NpcPlayer.npc(Map.randomLoc()));
						} else {
							p.damage(1,NpcPlayer.npc(Map.randomLoc()));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void onTick() {
		if(!remove) {
			for(Player p :Rule.c.keySet()) {
				if(p.getHealth()/p.getMaxHealth() < 0.1) {
					Remove(p);
				}
			}
		}
	}
	
	void Remove(Player p) {
		Rule.buffmanager.getBuffs(p).clear();
		ARSystem.heal(p, 10000);
		ARSystem.giveBuff(p, new Nodamage(p), 200);
		ARSystem.giveBuff(p, new Noattack(p), 200);
		ARSystem.giveBuff(p, new Silence(p), 200);
		ARSystem.giveBuff(p, new Stun(p), 200);
		ARSystem.spellLocCast(p, Map.getCenter(), "ratio100");
		Location l = Map.getCenter().add(0,-15,0);
		l.setY(100);
		delay(()->{
			ARSystem.spellLocCast(p, l, "ratio100");
			ARSystem.spellLocCast(NpcPlayer.npc(l), l.clone().add(0,2,0),"lobo_star");
			delay(()->{
				Skill.quit(p);
			},40);
		},60);
	}
}
