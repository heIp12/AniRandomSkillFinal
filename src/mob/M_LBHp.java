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

public class M_LBHp extends MobBase {
	boolean text = true;
	
	public M_LBHp(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	protected void onTick() {
		if(tick%4 == 0) {
			ARSystem.heal(entity, Math.max(1, entity.getMaxHealth()*0.01));
			if(entity.getHealth()/entity.getMaxHealth() <= 0.5) {
				if(isCooldown("1", 20)) ARSystem.heal(entity, Math.max(10, entity.getMaxHealth()*0.3));
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(text && Rule.c.get(attaker) != null) {
			text = false;
			Holo.create(entity.getLocation().clone().add(new Vector(10*0.1-0.5,0,10*0.1-0.5)), "§c- Buff : Shell -", 100, new Vector(0,0.1,0));
		}
	}
}
