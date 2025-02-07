package mob;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.ArmorUp;
import buff.Barrier;
import buff.Bload;
import buff.NoCC;
import buff.Nodamage;
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import types.box;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class M_LBDefence2 extends MobBase {
	int level;
	public M_LBDefence2(LivingEntity mob,int level) {
		super(mob);
		this.level = level;
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(level >= 9) {
			e.setDamage(e.getDamage() * 0.3f);
		} else {
			e.setDamage(e.getDamage() - e.getDamage()*level*0.06);
		}
		
		if(level > 6) {
			if(AMath.random(20) <= level && entity.getHealth() - e.getDamage() < 1 && isCooldown("2", 1)) {
				e.setDamage(0);
				e.setCancelled(true);
				entity.setHealth(1);
				Holo.create(entity.getLocation(), Text.get("lobo:mb1"), 40, new Vector(0,0,0));
			}
		}
	}
}
