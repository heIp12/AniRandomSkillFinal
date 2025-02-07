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
import io.lumine.xikage.mythicmobs.skills.SkillCaster;
import manager.Bgm;
import mode.MLoboTomy;
import mode.ModeBase;
import types.box;
import util.AMath;
import util.Holo;
import util.Map;
import util.NpcPlayer;
import util.ULocal;

public class MM_Co extends MobMMBase {
	MLoboTomy lobo;
	int time = 0;
	int time2 = 0;
	
	public MM_Co(LivingEntity mob) {
		super(mob);
	}
	
	LivingEntity food;
	@Override
	protected void onTick() {
		if(Rule.buffmanager.isBuff(entity, "panic")) Rule.buffmanager.selectBuffTime(entity, "panic", 0);
		time++;
		if(food == null || AMath.random(10) <= 3) {
			if(AMath.random(400) <= 1) ARSystem.giveBuff(entity, new Stun(entity), AMath.random(30)*6);
			for(Entity e : boxS(entity, new Vector(1.5,1.5,1.5), box.TARGET)) {
				if(isCooldown("1", 0.8)) {
					entity.teleport(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()));
					signalPart("move", 0);
					signalPart("attack", 15);
					ARSystem.playSound(entity, "noise", 0.25f);
					ARSystem.giveBuff(entity, new Stun(entity), 15);
					delay(()->{
						for(Entity e2 : boxS(entity,new Vector(3,3,3), box.TARGET)) {
							LivingEntity en = (LivingEntity)e2;
							en.setNoDamageTicks(0);
							en.damage(20,entity);
							ARSystem.addBuff(en, new Panic(en), 10);
						}
					},5);
				}
			}
		}
		if(food == null) {
			time2 = 0;
			for(Entity e : ARSystem.boxS(entity, new Vector(80,50,80), box.ALL)) {
				if(MythicMobs.inst().getMobManager().isActiveMob(e.getUniqueId())) {
					ActiveMob ams = MythicMobs.inst().getMobManager().getActiveMob(e.getUniqueId()).get();
					String targetType = ams.getType().getInternalName();
					if(targetType.contains("human")) {
						if(MythicMobs.inst().getMobManager().isActiveMob(entity.getUniqueId())) {
							ActiveMob am = MythicMobs.inst().getMobManager().getActiveMob(entity.getUniqueId()).get();
							if(((LivingEntity)e).getHealth()/((LivingEntity)e).getMaxHealth() < 0.1 || time > 160) {
								setTarget((LivingEntity)e);
								food = (LivingEntity)e;
							}
							
						}
					}
				}
			}
		}
		
		if(food == null || !food.isEmpty() || food.isDead() || food.getHealth() < 1 || time2 > 400) food = null;
		if(food != null && food.getLocation().distance(entity.getLocation()) < 4 &&  isCooldown("eat", 0.2,false)) {
			if(isCooldown("baby", 2, false) && food.getHealth()/food.getMaxHealth() <= 0.1) {
				Skill.quit(food);
				ARSystem.giveBuff(food, new Stun(food), 40);
				baby(food.getLocation(), 3);
				ARSystem.spellCast(npc(), food, "nul2_e");
				ARSystem.heal(entity, entity.getMaxHealth() * 0.1);
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		double dfc = Math.max(0.2,entity.getHealth()/entity.getMaxHealth() - 0.2f);
		e.setDamage(e.getDamage() - e.getDamage()*dfc);
		time = 0;
	}

	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity attaker) {
		time = 0;
		ARSystem.heal(entity,e.getDamage());
		if(AMath.random(50) <= 1) {
			e.setDamage(e.getDamage() + 30);
			ARSystem.giveBuff(attaker, new Stun(attaker), 40);

			int size = 1;
			if(attaker instanceof Player) {
				e.setDamage(e.getDamage() - 20);
				size = 3;
			}
			baby(attaker.getLocation(), size);
			ARSystem.spellCast(npc(), attaker, "nul2_e");
		}
	}
	
	@Override
	public void onKill(LivingEntity killer) {
		ARSystem.giveBuff(killer, new Stun(killer), 40);

		int size = 1;
		if(killer instanceof Player) size = 3;
		baby(killer.getLocation(), size);
		ARSystem.spellCast(npc(), killer, "nul2_e");
	}
	
	public void baby(Location loc,int size){
		signalPart("move", 0);
		signalPart("attack", 15);
		ARSystem.playSound(entity, "noise2");

		ActiveMob ams = MythicMobs.inst().getMobManager().getActiveMob(entity.getUniqueId()).get();
		
		for(int i = 0; i <size;i++) {
			LivingEntity en2 = Map.spawnMM("co2", loc);
			if(getlobo() != null) getlobo().etcmobs.add(en2);
			ActiveMob am = MythicMobs.inst().getMobManager().getActiveMob(en2.getUniqueId()).get();
			am.setOwner(entity.getUniqueId());
			if(AMath.random(3) <= 1) am.setParent(ams);
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
