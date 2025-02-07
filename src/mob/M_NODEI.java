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
import buff.Nodie;
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class M_NODEI extends MobBase {
	public M_NODEI(LivingEntity mob) {
		super(mob);
	}

	
	@Override
	public void onHit(EntityDamageByEntityEvent e,LivingEntity attaker) {
		if(entity.getHealth() - e.getDamage() < 1 && isCooldown("1", 30)) {
			Holo.create(entity.getLocation(), "卍해!!",100,new Vector(0,0,0));
			ARSystem.giveBuff(entity, new Nodie(entity), 100);
			ARSystem.giveBuff(entity, new NoCC(entity), 100);
			e.setDamage(0);
			e.setCancelled(true);
		}
	}
}
