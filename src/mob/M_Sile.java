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
import buff.NoCC;
import buff.Nodamage;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import types.box;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class M_Sile extends MobBase {
	int r2 = 0;
	public M_Sile(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(attaker == entity) r2++;
	}

	@Override
	protected void onTick() {
		if(AMath.random(100) <= r2) {
			Entity tg = ARSystem.boxSPlayerOne(entity, new Vector(10,5,10), box.TARGET);
			if(tg != null && isCooldown("2",10)) {
				ARSystem.playSound(entity, "c7s");
				ARSystem.giveBuff((LivingEntity)tg, new Silence((LivingEntity)tg), 100);
			}
		}
	}
}
