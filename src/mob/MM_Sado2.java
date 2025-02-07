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
import buff.Minecart;
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

public class MM_Sado2 extends MM_Sado1 {
	
	public MM_Sado2(LivingEntity mob) {
		super(mob);
	}

	@Override
	protected void sadoTick() {
		for(Entity e : boxS(entity, new Vector(20,20,20), box.PLAYER)) {
			int rd = 1;
			if(e.getLocation().distance(entity.getLocation()) > 12) rd = 10;
			if(AMath.random(300) <= rd && isCooldown("remove", 40)) {
				entity.teleport(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()));
				signalPart("move", 0);
				signalPart("attack", 80);
				ARSystem.giveBuff(entity, new Stun(entity), 30);
				ARSystem.giveBuff(entity, new Silence(entity), 160);
				Vector v = entity.getLocation().getDirection();
				Location l = ULocal.lookAt(entity.getLocation().clone(),e.getLocation());
				for(int i =0;i<50;i++) {
					int j = i;
					delay(()->{entity.teleport(l.clone().add(v.clone().multiply(j*1.5)));},j + 30);
				}
				delay(()->{
					entity.teleport(l);
				},81);
			}
		}
		for(Entity e : boxS(entity, new Vector(2,2,2), box.TARGET)) {
			if(isCooldown("1", 0.5)) {
				ARSystem.giveBuff(entity, new Stun(entity), 10);
				ARSystem.giveBuff(entity, new Silence(entity), 10);
				
				signalPart("attack2", 10);
				ARSystem.spellLocCast(npc(), entity.getLocation(), "sado2_attacke");
				

				for(Entity e2 : boxS(entity, new Vector(5,5,5), box.TARGET)) {
					LivingEntity en = (LivingEntity)e2;
					en.setNoDamageTicks(0);
					en.damage(3 + en.getHealth()* 0.3,entity);
				}
			}
		}
	}
}
