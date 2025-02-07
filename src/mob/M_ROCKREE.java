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
import buff.TimeStop;
import event.Skill;
import manager.Bgm;
import types.box;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class M_ROCKREE extends MobBase {
	int r2 = 0;
	public M_ROCKREE(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		r2++;
	}

	@Override
	protected void onTick() {
		if(AMath.random(160) <= r2) {
			Entity tg = ARSystem.boxSPlayerOne(entity, new Vector(12,5,12), box.TARGET);
			if(tg != null && isCooldown("2",30)) {
				Holo.create(entity.getLocation(), "코노하센푸!!",60,new Vector(0,0,0));
				LivingEntity target = (LivingEntity)tg;
				ARSystem.playSound(entity, "c14s1");
				r2 = 1;
				for(int i =0; i<10; i++) {
					delay(()->{
						Location loc = target.getLocation().clone();
						loc.setPitch(0);
						loc.setYaw(AMath.random(360));
						entity.teleport(ULocal.offset(loc, new Vector(2,0,0)));
						ARSystem.playSound(entity, "0attack3", 1.4f, 0.3f);
						
						target.setNoDamageTicks(0);
						target.damage(1,entity);
					},i);
				}
			}
		}
	}
}
