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
import buff.Airborne;
import buff.ArmorUp;
import buff.Barrier;
import buff.Bload;
import buff.NoCC;
import buff.NoHeal;
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
import mode.MKagerou;
import mode.MLoboTomy;
import mode.ModeBase;
import types.box;
import util.AMath;
import util.Holo;
import util.Map;
import util.NpcPlayer;
import util.Text;
import util.ULocal;

public class MM_Kuroha extends MobMMBase {
	Location lc;
	LivingEntity e = null;
	
	public MM_Kuroha(LivingEntity mob,Location lc) {
		super(mob);
		this.lc = lc;
		ARSystem.playSoundAll("kurohaspawn");
	}
	
	@Override
	protected void onTick() {
		if(lc == null) lc = entity.getLocation();
		
		if(e != null && AMath.random(30) < 4 && lc.distance(e.getLocation()) <= 17 && isCooldown("jumpAttack", 8)) {
			entity.teleport(e.getLocation().clone().add(0,8,0));
			ARSystem.giveBuff(entity, new Silence(entity), 40);
			ARSystem.giveBuff(entity, new Stun(entity), 3);
			ARSystem.playSound(entity, "kurohas1",1,2f);
			delay(()->{
				entity.setVelocity(new Vector(0,-8,0));
				delay(()->{
					ARSystem.playSound(entity, "0explod");
					for(LivingEntity en : box(entity, new Vector(5,5,5), box.TARGET)) {
						en.setVelocity(new Vector(0,3,0));
						en.setNoDamageTicks(0);
						en.damage(25,entity);
						ARSystem.giveBuff(entity, new Stun(entity), 25);
						ARSystem.giveBuff(en, new NoHeal(en), 200);
					}
				},15);
			},5);
		}
		
		if(AMath.random(100) < 8 && isCooldown("skill2", 12+AMath.random(48))) {
			ARSystem.giveBuff(entity, new Stun(entity), 80);
			ARSystem.giveBuff(entity, new Silence(entity), 80);
			ARSystem.playSound(entity, "kurohas2",1,2f);
			
			delay(()->{
				ARSystem.spellLocCast(npc(), entity.getLocation(), "backya_attack3");
				for(LivingEntity e : box(entity, new Vector(9,9,9), box.PLAYER)) {
					if(e.getLocation().distance(entity.getLocation()) <= 8) {
						delay(()->{
							ARSystem.giveBuff(entity, new Silence(entity), 300);
							e.damage(10,entity);
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
		
		if(lc.distance(entity.getLocation()) > 17) {
			if(lc.distance(entity.getLocation()) > 19) {
				entity.teleport(lc);
			} else {
				entity.setVelocity(ULocal.lookAt(entity.getLocation().clone(), lc).getDirection().multiply(2));
			}
		}
	}
	
	@Override
	public void onHit(EntityDamageByEntityEvent e, LivingEntity attaker) {
		e.setDamage(e.getDamage()*0.3f);
		if(attaker.getLocation().distance(lc) > 15) {
			e.setDamage(e.getDamage()* 0.05);
			Holo.create(ULocal.offset(attaker.getLocation(), new Vector(3,0,0)), "Ranged Defense", 60 , new Vector(0,0.1,0));
		} else {
			this.e = attaker;
		}
	}
	
	@Override
	public void onAttack(EntityDamageByEntityEvent e, LivingEntity target) {
		e.setDamage(e.getDamage() + target.getMaxHealth()*0.15f);
	}
	
	@Override
	public void onDeath(LivingEntity killer) {
		ARSystem.playSoundAll("kurohadeath");
		MKagerou.npcid = -1;
		Bukkit.broadcastMessage("§a§l[ARSystem] §c§l" + Text.get("kage:killcenter").replace("{name}", Text.get("kage:c11")).replace("{team}", killer.getName()));
		if(killer instanceof Player && Rule.team.isTeam((Player)killer)) {
			Rule.playerinfo.get(killer).gold += 3000;
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(Rule.team.isTeam(p, (Player)killer)) {
					Rule.playerinfo.get(p).gold += 2000;
					ARSystem.addItem(p, 100110);
				}
			}
		}
	}
}
