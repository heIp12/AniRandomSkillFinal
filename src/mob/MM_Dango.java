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
import types.box;
import util.AMath;
import util.Holo;
import util.NpcPlayer;
import util.ULocal;

public class MM_Dango extends MobMMBase {
	int level = 1;
	int pluslv = 0;
	double hp = 0;
	double firsthp = 0;
	
	public MM_Dango(LivingEntity mob) {
		super(mob);
	}
	
	LivingEntity food;
	@Override
	protected void onTick() {
		if(hp == 0) {
			hp = entity.getMaxHealth();
			firsthp = hp;
		}
		if(level == 1) {
			for(Entity e : ARSystem.PlayerBeamV(entity, 3, 2, box.TARGET)) {
				if(isCooldown("1", 1.5)) {
					signal("attack", 1);
					ARSystem.giveBuff(entity, new Stun(entity), 10);
					delay(()->{
						for(Entity e2 : boxS(entity,new Vector(3,3,3), box.TARGET)) {
							LivingEntity en = (LivingEntity)e2;
							en.setNoDamageTicks(0);
							en.damage(10,entity);
							ARSystem.spellCast(npc(), en, "dango_attack1");
						}
					},5);
				}
			}
		} else if(level == 2) {
			boolean attack = false;
			for(Entity e : ARSystem.boxS(entity,new Vector(8,5,8), box.TARGET)) {
				if(isCooldown("1", 5)) {
					signal("attack", 1);
					ARSystem.giveBuff(entity, new Stun(entity), 40);
					for(int i = 0; i < 10 ;i++) {
						delay(()->{
							for(Entity e2 : boxS(entity,new Vector(8,5,8), box.TARGET)) {
								LivingEntity en = (LivingEntity)e2;
								en.setNoDamageTicks(0);
								en.damage(5,entity);
							}
						},4*i);
					}
				}
			}

		} else if(level == 3) {
			for(Entity e : ARSystem.PlayerBeamV(entity, 6, 3, box.TARGET))  {
				if(isCooldown("1", 5)) {
					signal("attack", 1);
					ARSystem.giveBuff(entity, new Stun(entity), 40);
				}
			}
		}
	
		if(food == null) {
			for(Entity e : ARSystem.boxS(entity, new Vector(50,50,50), box.ALL)) {
				if(MythicMobs.inst().getMobManager().isActiveMob(e.getUniqueId())) {
					ActiveMob ams = MythicMobs.inst().getMobManager().getActiveMob(e.getUniqueId()).get();
					String targetType = ams.getType().getInternalName();
					if(targetType.contains("human")) {
						LivingEntity en = (LivingEntity)e;
						if(en.getHealth()/en.getMaxHealth() <= 0.1) {
							food = en;
							setTarget(food);
						} else if(MythicMobs.inst().getMobManager().isActiveMob(entity.getUniqueId())) {
							if(entity.getHealth()/entity.getMaxHealth() < 0.5f) {
								ActiveMob am = MythicMobs.inst().getMobManager().getActiveMob(entity.getUniqueId()).get();
								setTarget((LivingEntity)e);
							}
						}
					}
				}
			}
		}
		if(food == null || !food.isEmpty() || food.isDead() || food.getHealth() < 1) food = null;
		if(food != null && food.getLocation().distance(entity.getLocation()) < 3 &&  isCooldown("eat", 0.2,false)) {
			if(level < 3 && isCooldown("up", 1.5, false)) {
				signal("attack", 20);
				ARSystem.giveBuff(entity, new Stun(entity), 20);
				Skill.quit(food);
				LivingEntity tg = food;
				ARSystem.spellCast(npc(), tg, "bload");
				delay(()->{
					ARSystem.spellCast(npc(), tg, "bload");
					signalPart("update", 2);
					ARSystem.playSound(entity,"dau");
				},20);
				entity.setMaxHealth(entity.getMaxHealth() + hp);
				ARSystem.heal(entity, hp*1.5f);
				level++;
			} else {
				hp += firsthp*0.1;
				entity.setMaxHealth(entity.getMaxHealth() + (firsthp*0.1*level));
				Skill.quit(food);
				ARSystem.spellCast(npc(), food, "bload");
				ARSystem.heal(entity, hp*1);
				pluslv++;
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		e.setDamage(e.getDamage() - e.getDamage()*Math.min(0.8,pluslv*0.08));
		if(level > 1 && entity.getMaxHealth()-hp > entity.getHealth() - e.getDamage()) {
			entity.setMaxHealth(entity.getMaxHealth()- hp);
			entity.setHealth(entity.getMaxHealth());
			signalPart("down", 2);
			ARSystem.playSound(entity,"dad");
			level--;
			e.setDamage(0);
			e.setCancelled(true);
		}
	}
	
	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity target) {
		if(!(target instanceof Player)) {
			e.setDamage(e.getDamage() * 2);
			if(entity.getHealth()/entity.getMaxHealth() <= 0.5) {
				e.setDamage(e.getDamage() * 2.5f);
			}
		}
	}
	
	@Override
	public void onKill(LivingEntity killer) {
		if(killer instanceof Player) {
			signal("attack", 20);
			ARSystem.giveBuff(entity, new Stun(entity), 20);
			LivingEntity tg = killer;
			ARSystem.spellCast(npc(), tg, "bload");
			delay(()->{
				ARSystem.spellCast(npc(), tg, "bload");
				signalPart("update", 2);
				ARSystem.playSound(entity, "dau");
			},20);
			entity.setMaxHealth(entity.getMaxHealth() + hp);
			ARSystem.heal(entity, hp*1.5f);
			level++;
		}
	}
}
