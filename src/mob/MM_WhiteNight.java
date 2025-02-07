package mob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import com.google.common.cache.Cache;

import ars.ARSystem;
import ars.Rule;
import buff.Airborne;
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
import types.MobBuffs;
import types.box;
import util.AMath;
import util.Holo;
import util.Map;
import util.NpcPlayer;
import util.ULocal;

public class MM_WhiteNight extends MobMMBase {
	static public List<MM_Sado1> sado = new ArrayList<>();
	static public LivingEntity whitenight;
	public int phase = 0;
	public int nodamage = 0;
	boolean firstTick = true;
	
	public MM_WhiteNight(LivingEntity mob) {
		super(mob);
		sado = new ArrayList<>();
		whitenight = mob;
		for(MM_Sado1 sado : sado) {
			sado.owner = entity;
		}
	}
	void spawn(String s,Location l){
		LivingEntity e = (LivingEntity) Map.spawnMMOwner(s, l, entity.getUniqueId());
		if(ARSystem.isGameMode("lobotomy")) {
			MLoboTomy.mobs.add(e);
			e.setMaxHealth(e.getMaxHealth() * MLoboTomy.hpmult);
			e.setHealth(e.getMaxHealth());
			Rule.mobmanager.Add(new M_LBDefence2(e,9));
			
		}
	}
	@Override
	protected void onTick() {
		if(nodamage > 0) nodamage--;
		if(firstTick) {
			firstTick = false;
			entity.teleport(Map.getCenter());
			spawn("sado", entity.getLocation());
			spawn("sado2", entity.getLocation());
			spawn("sado3", entity.getLocation());
			setCooldown("skill2", 20);
			setCooldown("respawn", 30);
		}
		if(phase > 0) {
			LivingEntity tg = boxSOne(entity , new Vector(200,200,200), box.PLAYER);
			if(tg != null && isCooldown("sadoAttack", 2 - 0.4*phase) && AMath.random(100) <= 5*phase) {
				Location l = tg.getLocation().clone();
				l.setPitch(0);
				if(phase > 1 && AMath.random(10) <= 3) {
					ARSystem.spellLocCast(npc(), ULocal.offset(l, new Vector(0.5 + AMath.random(5)*0.5,0,0)), "backya_attack2");
				} else {
					ARSystem.spellLocCast(npc(), ULocal.offset(l, new Vector(0.5 + AMath.random(5)*0.5,0,0)), "backya_attack");
				}
			}
			
			if(isCooldown("skill1", 100 + AMath.random(50))) {
				Location l = entity.getLocation().clone();
				ARSystem.giveBuff(entity, new TimeStop(entity), 260);
				ARSystem.giveBuff(entity, new Silence(entity), 250);
				ARSystem.playSoundAll("by2");
				entity.teleport(Map.getCenter());
				ARSystem.spellLocCast(npc(), entity.getLocation(), "bye");
				
				for(int i =0; i<20; i++) {
					delay(()->{
						for(Player p : Rule.c.keySet()) {
							Location lc = p.getLocation().clone();
							lc.setPitch(0);
							if(AMath.random(3) <= 1) lc.setYaw(AMath.random(360));
							if(phase > 1) {
								ARSystem.spellLocCast(npc(), ULocal.offset(lc, new Vector(0.5 + AMath.random(10)*0.5,0,0)), "backya_attack2");
							} else {
								ARSystem.spellLocCast(npc(), ULocal.offset(lc, new Vector(0.5 + AMath.random(10)*0.5,0,0)), "backya_attack");
							}
						}
					},10*i+50);
				}
				delay(()->{
					entity.teleport(l);
				},260);
			}
		}
		if(isCooldown("skill2", 60 + AMath.random(60))) {
			ARSystem.playSoundAll("by4");
			ARSystem.spellLocCast(npc(), entity.getLocation(), "bye2");
			ARSystem.giveBuff(entity, new Stun(entity), 80);
			ARSystem.giveBuff(entity, new Silence(entity), 80);
			ARSystem.spellLocCast(npc(), entity.getLocation(), "backya_attack3e2");
			
			delay(()->{
				ARSystem.spellLocCast(npc(), entity.getLocation(), "backya_attack3");
				ARSystem.playSoundAll("by5");
				for(LivingEntity e : box(entity, new Vector(10,10,10), box.PLAYER)) {
					if(e.getLocation().distance(entity.getLocation()) <= 8) {
						delay(()->{
							e.damage(15,entity);
							e.setVelocity(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()).getDirection().multiply(5));
							ARSystem.giveBuff(e, new Airborne(e), 60);
							delay(()->{
								e.setVelocity(ULocal.lookAt(entity.getLocation().clone(), e.getLocation()).getDirection().multiply(50));
							},2);
						},13);
					}
				}
			},40);
		}
		
		
		if(AMath.random(100) <= 5) {
			List<MM_Sado1> ens = new ArrayList<MM_Sado1>();
			for(MM_Sado1 sd : sado) {
				if(sd != null && !sd.isDeath) ens.add(sd);
			}
			if(ens.size() > 0) {
				MM_Sado1 en = ens.get(AMath.random(ens.size())-1);
				Player p = ARSystem.RandomPlayer();
				en.setTarget(p);
				if(phase > 1 && AMath.random(3) == 1 && entity.getLocation().distance(en.entity.getLocation()) < 10 && p.getLocation().distance(en.entity.getLocation()) > 10) {
					Location lc = Map.randomLoc();
					for(int i = 0; i < 300; i++) {
						if(lc.distance(p.getLocation()) > 15) {
							lc = Map.randomLoc();
						}
					}
					en.entity.teleport(lc);
				}
			}
		}
		
		if(isCooldown("respawn", 60 - phase*10)) {
			ARSystem.playSoundAll("by1");
			for(MM_Sado1 sado : sado) {
				if(sado != null) {
					ARSystem.heal(sado.entity, 10000);
					sado.reSpawn();
					if(sado.entity.getLocation().distance(entity.getLocation()) > 20) {
						sado.entity.teleport(whitenight.getLocation().clone().add(AMath.random(20)-10,0,AMath.random(20)-10));
					}
				}
			}
			for(Player p : Rule.c.keySet()) {
				p.setNoDamageTicks(0);
				p.damage(2, entity);
			}
		}
		
		if(isCooldown("flysado", 3)) {
	 		for(MM_Sado1 e : sado) {
	 			if(AMath.random(3) <= 1) {
					if(e != null && e.entity != null && !e.entity.isDead()) {
						Entity en = e.entity;
						if(en.getLocation().distance(entity.getLocation()) <= 8) {
							if(e.isDeath) {
								en.teleport(en.getLocation().clone().add(ULocal.lookAt(entity.getLocation().clone(), en.getLocation()).getDirection().multiply(1).setY(0)));
							} else {
								en.setVelocity(ULocal.lookAt(entity.getLocation().clone(), en.getLocation()).getDirection().multiply(8).setY(1));
							}
						}
					}
	 			}
			}
		}
		
		try {
			List<MM_Sado1> removes = new ArrayList<>();
	 		for(MM_Sado1 e : sado) {
				if(e == null || e.entity == null || e.entity.isDead()) {
					sado.add(e);
				}
			}
	 		for(MM_Sado1 e : removes) sado.remove(e);
	 		removes.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		if(nodamage > 0 || !(e.getDamager() instanceof Player)) {
			e.setDamage(0);
			e.setCancelled(true);
			return;
		}
		if(attaker.getLocation().distance(entity.getLocation()) < 5) {
			addCooldown("skill2", phase*3);
		}
		e.setDamage(e.getDamage() - (e.getDamage()*(0.2+phase*0.1)));
		
		if(e.getDamage() > entity.getMaxHealth() * 0.1) {
			ARSystem.giveBuff(entity, new Nodamage(entity), 20);
			e.setDamage(entity.getMaxHealth() * 0.1);
		}
		if(entity.getHealth() - e.getDamage() < 1) {
			if(phase <= 2) {
				phase++;
				nodamage = 60;
				if(Rule.buffmanager.getBuffs(entity) != null) Rule.buffmanager.getBuffs(entity).clear();
				ARSystem.heal(entity, 100000);
				entity.setMaxHealth(entity.getMaxHealth() * 1.2f);
				entity.setHealth(entity.getMaxHealth());
				ARSystem.giveBuff(entity, new TimeStop(entity), 60);
				delay(()->{
					ARSystem.heal(entity, 100000);
				},20);
				if(phase == 1) {
					spawn("sado", Map.randomLoc());
					spawn("sado", Map.randomLoc());
					spawn("sado", entity.getLocation());
					ARSystem.playSoundAll("by3", 1.4f);
					setCooldown("respawn", 5);
				} else if(phase == 2) {
					spawn("sado3", Map.randomLoc());
					spawn("sado3", Map.randomLoc());
					spawn("sado3", entity.getLocation());
					ARSystem.playSoundAll("by3", 1.7f);
					setCooldown("respawn", 20);
				} else if(phase == 3) {
					spawn("sado2", entity.getLocation());
					spawn("sado2", entity.getLocation());
					ARSystem.playSoundAll("by3", 1.2f);
					setCooldown("respawn", 0);
				}
				return;
			}
			for(MM_Sado1 sado : sado) {
				delay(()->{
					if(sado != null) sado.Death();
				},1);
			}
			sado.clear();
			ARSystem.playSoundAll("by3", 0.7f);
			ARSystem.giveBuff(entity, new TimeStop(entity), 100);
			ARSystem.giveBuff(entity, new Nodie(entity), 100);
			ARSystem.heal(entity, 100000);
			e.setDamage(0);
			e.setCancelled(true);
			
			for(int i =0; i<100;i++) {
				delay(()->{
					entity.teleport(entity.getLocation().clone().add(0,0.05,0));
				},i);
			}
			delay(()->{
				Skill.quit(entity);
			},100);
		}
	}
}
