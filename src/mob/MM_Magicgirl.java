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

public class MM_Magicgirl extends MobMMBase {
	int tp = 1;
	int nobattle = 0;
	
	public MM_Magicgirl(LivingEntity mob) {
		super(mob);
	}
	
	@Override
	protected void onTick() {
		MythicMobs.inst().getMobManager().getActiveMob(entity.getUniqueId()).get().resetTarget();
		LivingEntity e = boxSOne(entity, new Vector(8,8,8), box.TARGET);
		if(e != null && AMath.random(100) <= 3 && isCooldown("1", 15)) {
			entity.teleport(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()));
			ARSystem.giveBuff(entity, new Stun(entity), 200);
			ARSystem.giveBuff(entity, new Silence(entity), 200);
			ARSystem.giveBuff(entity, new Nodamage(entity,addon.util.NpcPlayer.npc(e.getLocation())), 240);
			signal("attack",1);
			delay(()->{
				signal("st",1);
				ARSystem.giveBuff(entity, new Stun(entity), 120);
				ARSystem.giveBuff(entity, new Silence(entity), 120);
			},150);
			nobattle = 0;
		}

		e = boxRandom(entity, new Vector(80,80,80), box.TARGET);
		
		if(e != null &&AMath.random(1000) <= tp && isCooldown("2", 30)) {
			tp = 1;
			ARSystem.spellLocCast(npc(), entity.getLocation(), "magic1_tp");
			for(LivingEntity en : boxS(entity, new Vector(8,8,8), box.TARGET)){
				en.setNoDamageTicks(0);
				en.damage(10,entity);
				ARSystem.addBuff(en, new Panic(en), 100);
			}
			
			ARSystem.giveBuff(entity, new Stun(entity), 4);
			ARSystem.giveBuff(entity, new Silence(entity), 20);
			

			Location lc = e.getLocation().clone();
			ARSystem.spellLocCast(npc(), lc, "magic1_tp");
			delay(()->{
				entity.teleport(lc);
				for(LivingEntity en : boxS(entity, new Vector(8,8,8), box.TARGET)){
					en.setNoDamageTicks(0);
					en.damage(10,entity);
					ARSystem.addBuff(en, new Panic(en), 100);
				}
				ARSystem.giveBuff(entity, new  Stun(entity), 10);
			},6);
			
		}
		
		nobattle++;
		if(nobattle > 120 && nobattle%10 == 0) {
			tp++;
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		tp++;
		if(isCooldown("3", 5, false)) {
			if(e.getDamage() > entity.getMaxHealth()*0.1) {
				e.setDamage(entity.getMaxHealth()*0.1);
				ARSystem.giveBuff(entity, new ArmorUp(entity), 50 , 0.75);
			}
		}
	}
	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity target) {
		nobattle = 0;
	}
}
