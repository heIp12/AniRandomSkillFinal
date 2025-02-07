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

public class MM_C extends MobMMBase {
	MLoboTomy lobo;
	int time = 0;
	
	public MM_C(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	protected void onTick() {
		time++;
		LivingEntity tg = null;
		if(time >= 140) {
			for(Entity e : ARSystem.boxS(entity, new Vector(20,20,20), box.ALL)) {
				if(e.getCustomName() != null && entity.getCustomName() != null && e.getCustomName().equals(entity.getCustomName())) {
					if(MythicMobs.inst().getMobManager().isActiveMob(entity.getUniqueId())) {
						ActiveMob am = MythicMobs.inst().getMobManager().getActiveMob(entity.getUniqueId()).get();
						if(am.hasTarget()) am.resetTarget();
						tg = (LivingEntity)e;
						setTarget((LivingEntity)e);
					}
				}
			}
			if(tg != null && !tg.isDead() && tg.getLocation().distance(entity.getLocation()) < 3) {
				double max = 0,min = 0;
				if(tg.getMaxHealth() > entity.getMaxHealth()) {
					max = tg.getMaxHealth();
					min = entity.getMaxHealth();
				} else {
					min = tg.getMaxHealth();
					max = entity.getMaxHealth();
				}
				time = 130;
				entity.setMaxHealth((int)(max + min*0.4f));
				ARSystem.heal(entity, entity.getMaxHealth());
				ARSystem.spellLocCast(npc(),tg.getLocation(), "bload");
				Skill.quit(tg);
				if(entity.getMaxHealth() >= 500) {
					Skill.quit(entity);
					LivingEntity en2 = Map.spawnMM("c2", entity.getLocation());
					if(getlobo() != null) getlobo().etcmobs.add(en2);
				}
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		time = 0;
		float dfc = 1;
		dfc -= Math.min(entity.getMaxHealth()*0.0008,0.65f);
		e.setDamage(e.getDamage() * dfc);
		
		if(entity.getMaxHealth() >= 200 && (entity.getHealth()- e.getDamage())/entity.getMaxHealth() < 0.5f && isCooldown("2", 100)) {
			e.setCancelled(true);
			LivingEntity en2 = Map.spawnMM("c", entity.getLocation());
			if(getlobo() != null) getlobo().etcmobs.add(en2);
			en2.setMaxHealth(entity.getMaxHealth()*0.5);
			en2.setHealth(entity.getMaxHealth()*0.5);
			en2.setVelocity(new Vector(0,0.5,0));

			entity.setMaxHealth(entity.getMaxHealth()*0.5);
			entity.setHealth(entity.getMaxHealth());
		}
	}
	
	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity target) {
		time = 0;
		if(isCooldown("1", 5, true)) {
			ARSystem.playSound(target, "0attack4", 0.2f);
			e.setDamage(e.getDamage() + entity.getMaxHealth()*0.04);
		}
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
