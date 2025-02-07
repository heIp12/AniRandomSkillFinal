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

public class M_LBMaxHp extends MobBase {
	boolean text = true;
	public M_LBMaxHp(LivingEntity mob) {
		super(mob);
		
		if(AMath.random(10) <= 1) {
			if(AMath.random(10) <= 1) {
				mob.setMaxHealth(mob.getMaxHealth()* 5);
				mob.setHealth(mob.getMaxHealth());
			} else {
				mob.setMaxHealth(mob.getMaxHealth()* 2);
				mob.setHealth(mob.getMaxHealth());
			}
		} else {
			mob.setMaxHealth(mob.getMaxHealth()* 1.5);
			mob.setHealth(mob.getMaxHealth());
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(text && Rule.c.get(attaker) != null) {
			text = false;
			Holo.create(entity.getLocation().clone().add(new Vector(10*0.1-0.5,0,10*0.1-0.5)), "§c- Buff : MaxHp -",100, new Vector(0,0.1,0));
		}
	}
}
