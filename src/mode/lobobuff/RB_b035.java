package mode.lobobuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import types.box;

public class RB_b035 extends LoboBuffBase{
	List<Player> p = new ArrayList<>();
	public RB_b035() {
		id = 35;
	}
	
	@Override
	public void onNextStage() {
		p.clear();
	}
	
	@Override
	public void onTick() {
		for(Player p : Rule.c.keySet()) {
			if(!this.p.contains(p) && p.getHealth()/p.getMaxHealth() < 0.5) {
				this.p.add(p);
				ARSystem.playSound(p, "entity.wolf.growl", 0.7f);
				ARSystem.giveBuff(p, new Nodamage(p), 20);
				for(Entity e : ARSystem.box(p, new Vector(8,8,8), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(15,p);
				}
				p.setGameMode(GameMode.SPECTATOR);
				getlobo().delay(()->{
					p.setGameMode(GameMode.ADVENTURE);
				},100);
			}
		}
	}
}