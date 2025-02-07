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
import buff.Nodie;
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

public class MM_Sado3 extends MM_Sado1 {
	
	public MM_Sado3(LivingEntity mob) {
		super(mob);
	}

	@Override
	protected void sadoTick() {
		if(owner != null && tick%10 == 0) {
			LivingEntity tg = boxSOne(owner, new Vector(20,5,20), box.TARGET);
			setTarget(tg);
		}
		for(Entity e : boxS(entity, new Vector(20,20,20), box.PLAYER)) {
			if(e.getLocation().distance(entity.getLocation()) > 12 || AMath.random(100) <= 3) {
				if(AMath.random(30) <= 1 && isCooldown("remove", 15)) {
					entity.teleport(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()));
					signalPart("attack", 40);
					ARSystem.giveBuff(entity, new Stun(entity), 40);
					ARSystem.giveBuff(entity, new Silence(entity), 40);
				}
			}
		}
		for(Entity e : boxS(entity, new Vector(3,3,3), box.TARGET)) {
			if(isCooldown("1", 0.8)) {
				ARSystem.giveBuff(entity, new Stun(entity), 10);
				ARSystem.giveBuff(entity, new Silence(entity), 10);
				
				signalPart("attack2", 10);
				
				LivingEntity en = (LivingEntity)e;
				if(Rule.buffmanager.getBuffs(en) != null) Rule.buffmanager.getBuffs(en).buffClear();
				en.setNoDamageTicks(0);
				en.damage(10 + en.getMaxHealth()*0.1,entity);
				ARSystem.spellCast(npc(), en, "sado_attacke");
			}
		}
	}
}
