package mob;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.NoCC;
import buff.Nodamage;
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class M_IIZEN extends MobBase {
	public M_IIZEN(LivingEntity mob) {
		super(mob);
	}


	@Override
	public void onHit(EntityDamageByEntityEvent e,LivingEntity attaker) {
		if(AMath.random(10) <= 1 && isCooldown("1", 3)) {
			ARSystem.playSound(entity, "c161s32", 1, 0.5f);
			ARSystem.playSound(entity, "0miss", 2, 0.2f);
			
			entity.damage(0.01,attaker);
			Holo.create(entity.getLocation(), "언제부터 내가 거기에 있었다고 생각한거지?",40,new Vector(0,0,0));
			Location loc = attaker.getLocation().clone();
			loc.setPitch(0);
			loc.setYaw(AMath.random(360));
			entity.teleport(ULocal.offset(loc, new Vector(AMath.random(6)+2,0,0)));
			e.setDamage(0);
			e.setCancelled(true);
		}
	}
}
