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

public class M_BarrarDrain extends MobBase {
	public M_BarrarDrain(LivingEntity mob) {
		super(mob);
		ARSystem.giveBuff(mob, new Barrier(mob), 10000, 3);
	}
	
	@Override
	protected void onTick() {
		for(Entity e : ARSystem.box(entity, new Vector(8,8,8), box.TARGET)) {
			if(e instanceof Player) {
				if(Rule.buffmanager.GetBuffValue((LivingEntity)e, "barrier") > 0) {
					float value = (float)Rule.buffmanager.GetBuffValue((LivingEntity)e, "barrier") * 0.05f;
					Rule.buffmanager.selectBuffAddValue((LivingEntity)e, "barrier", -value);
					Rule.buffmanager.selectBuffAddValue(entity, "barrier", value);
				}
			}
		}
	}
}
