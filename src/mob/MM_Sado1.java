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

public class MM_Sado1 extends MobMMBase {
	boolean isDeath = false;
	LivingEntity owner;
	MLoboTomy lobo;
	int time = 0;
	
	public MM_Sado1(LivingEntity mob) {
		super(mob);
		if(!MM_WhiteNight.sado.contains(this)) MM_WhiteNight.sado.add(this);
		if(MM_WhiteNight.whitenight != null) {
			if(MM_WhiteNight.whitenight.isDead() || MM_WhiteNight.whitenight.getHealth() < 1) {
				MM_WhiteNight.whitenight = null;
			} else {
			owner = MM_WhiteNight.whitenight;
			MythicMobs.inst().getMobManager().getActiveMob(mob.getUniqueId()).get().setParent(MythicMobs.inst().getMobManager().getActiveMob(owner.getUniqueId()).get());
			}
		}
		isCooldown("remove", 10);
	}
	
	public void reSpawn() {
		isDeath = false;
		if(Rule.buffmanager.getBuffs(entity) != null) Rule.buffmanager.getBuffs(entity).clear();
		if(!getlobo().mobs.contains(entity)) getlobo().mobs.add(entity);
		addCooldown("remove", -10);
	}
	
	@Override
	public void onBuffRemove() {
		if(MM_WhiteNight.sado.contains(this)) MM_WhiteNight.sado.remove(this);
	}

	public void Death() {
		isDeath = false;
		Rule.buffmanager.getBuffs(entity).clear();
		ARSystem.giveBuff(entity, new TimeStop(entity), 60);
		delay(()->{
			Skill.quit(entity);
		},60);
	}
	
	@Override
	protected void onTick() {
		if(isDeath) {
			if(tick%20 == 0) Holo.create(entity.getLocation().clone().add(0,1,0), "§c§lDIE", 20, new Vector(0,0,0));
			return;
		}
		sadoTick();
	}
	
	protected void sadoTick() {
		for(Entity e : boxS(entity, new Vector(20,20,20), box.PLAYER)) {
			if(e.getLocation().distance(entity.getLocation()) > 12 || AMath.random(100) <= 3) {
				if(AMath.random(10) <= 1 && isCooldown("remove", 50)) {
					entity.teleport(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()));
					signalPart("attack", 40);
					ARSystem.giveBuff(entity, new Stun(entity), 100);
					ARSystem.giveBuff(entity, new Silence(entity), 100);
				}
			}
		}
		for(Entity e : boxS(entity, new Vector(2.5,2.5,2.5), box.TARGET)) {
			if(isCooldown("1", 0.5)) {
				ARSystem.giveBuff(entity, new Stun(entity), 10);
				ARSystem.giveBuff(entity, new Silence(entity), 10);
				
				signalPart("attack2", 10);
				
				LivingEntity en = (LivingEntity)e;
				en.setNoDamageTicks(0);
				en.damage(5,entity);
				ARSystem.spellCast(npc(), en, "sado_attacke");
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(isDeath) {
			e.setDamage(0);
			e.setCancelled(true);
			ARSystem.heal(entity, 10000);
			ARSystem.giveBuff(entity, new TimeStop(entity), 100000);
			ARSystem.giveBuff(entity, new Nodie(entity), 100000);
			return;
		}
		if(entity.getHealth() - e.getDamage() < 1 && MM_WhiteNight.whitenight != null) {
			e.setDamage(0);
			e.setCancelled(true);
			if(ARSystem.isGameMode("lobotomy") && getlobo().mobs.contains(entity)) {
				getlobo().mobs.remove(entity);
			}
			ARSystem.giveBuff(entity, new TimeStop(entity), 100000);
			ARSystem.giveBuff(entity, new Nodie(entity), 100000);
			ARSystem.heal(entity, 10000);
			isDeath = true;
		}
		
	}
	
	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity target) {
		if(isDeath) return;
	}
	
	public MLoboTomy getlobo() {
		if(lobo != null) return lobo;
		if(ARSystem.AniRandomSkill == null) return null;
		for(ModeBase m : ARSystem.AniRandomSkill.modes) {
			if(m instanceof MLoboTomy) {
				lobo = (MLoboTomy)m;
				return lobo;
			}
		}
		return null;
	}
}
