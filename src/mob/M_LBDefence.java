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
import util.ULocal;

public class M_LBDefence extends MobBase {
	boolean text = true;
	
	public M_LBDefence(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(text && Rule.c.get(attaker) != null) {
			text = false;
			Holo.create(entity.getLocation().clone().add(new Vector(10*0.1-0.5,0,10*0.1-0.5)), "§c- Buff : Defence -",100, new Vector(0,0.1,0));
		}
		if(e.getDamage() > entity.getMaxHealth() * 0.1 && AMath.random(10) <= 5) {
			e.setDamage(entity.getMaxHealth() * 0.1);
		}
		if(AMath.random(10) <= 3 && isCooldown("1", 15)) {
			float power =  (float) Math.max(entity.getMaxHealth()*0.1f,10);
			if(Rule.buffmanager.isBuff(entity, "barrier")) {
				Rule.buffmanager.selectBuffValue(entity, "barrier", power);
			} else {
				ARSystem.giveBuff(entity, new Barrier(entity), 200, power);
			}
		}
	}
}
