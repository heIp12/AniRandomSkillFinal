package mob;

import org.bukkit.Bukkit;
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
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import manager.Bgm;
import mode.MLoboTomy;
import mode.ModeBase;
import types.box;
import util.AMath;
import util.Holo;
import util.Map;
import util.NpcPlayer;
import util.ULocal;

public class MM_C2 extends MobMMBase {
	MLoboTomy lobo;
	int time = 0;
	
	public MM_C2(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	protected void onTick() {
		time++;
		if(AMath.random(20) <= 1 && isCooldown("1", 1.5)) {
			LivingEntity tg = boxSOne(entity, new Vector(10,10,10), box.TARGET);
			if(AMath.random(5) <= 1 && tg != null) {
				ARSystem.spellLocCast(npc(), ULocal.lookAt(entity.getLocation().clone(), tg.getLocation()), "c1s");
			} else {
				Location l = entity.getLocation().clone();
				l.setYaw(AMath.random(360));
				l.setPitch(AMath.random(360));
				ARSystem.spellLocCast(npc(), l, "c1s");
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		e.setDamage(e.getDamage() * 0.3);
		if(isCooldown("2", 10)) {
			if(e.getDamage() > entity.getMaxHealth() * 0.1) {
				e.setDamage(entity.getMaxHealth() * 0.1);
			}
		}
	}

	
}
