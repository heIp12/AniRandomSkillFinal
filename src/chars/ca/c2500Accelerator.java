package chars.ca;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Barrier;
import buff.Buff;
import buff.Exposure;
import buff.Nodamage;
import buff.Reflect;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c2.c62shinon;
import chars.c2.c63micoto;
import event.Skill;
import manager.AdvManager;
import types.BuffType;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.Holo;
import util.MSUtil;
import util.Map;
import util.Text;
import util.ULocal;

public class c2500Accelerator extends c00main{
	int ps = 0;
	boolean fly = false;
	int s3 = 200;
	boolean sp = false;
	
	public c2500Accelerator(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2025;
		picksound = false;
		load();
		text();

		if(p != null) {
			((Barrier)Rule.buffmanager.selectBuff(player, "barrier")).SetEffect("c25_p");
		}
		ARSystem.playSoundAll("c2025select");
	}
	
	@Override
	public boolean skill1() {
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		skill("c1025_s11");
		skill("c1025_s12");
		skill("c1025_s13");
		skill("c1025_s14");
		skill("c1025_s15");
		delay(()->{
			player.teleport(ULocal.offset(player.getLocation(), new Vector(2,0,0)));
			ARSystem.playSound((Entity)player, "0explod" ,0.5f,0.5f);
			skill("c1025_s1a");
		},20);
		ARSystem.playSound((Entity)player, "c2025s1");
		return true;
	}
	
		
	
	@Override
	public boolean skill2() {
		Entity target = ARSystem.boxSOne(player, new Vector(6,6,6), box.TARGET);
		if(target != null) {
			ARSystem.playSound((Entity)player, "c2025s2");
			LivingEntity t = (LivingEntity)target;
			double damage = t.getMaxHealth() - t.getHealth();
			Location l = player.getLocation();
			l.setPitch(0);
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 20);
			ARSystem.giveBuff(t, new Stun(t), 40);
			ARSystem.giveBuff(t, new Silence(t), 40);
			for(int i = 0; i< 18; i++) {
				int j = i;
				delay(()->{
					player.teleport(l);
					l.setPitch(j*-2.5f);
					t.teleport(ULocal.offset(player.getLocation(), new Vector(2,0,0)));
					delay(()->{
						ARSystem.spellCast(player, target, "bload");
						t.setNoDamageTicks(0);
						t.damage(damage*0.15,player);
					},15);
				},i);
			}
		} else {
			cooldown[2] = 0;
			return false;
		}
		return true;
	}

	
	@Override
	public boolean skill3() {
		if(s3 > 0) {
			fly = !fly;
			player.setFlySpeed(0.15f);
			player.setAllowFlight(fly);
			skill("c25_ssp");
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c2025:t1")+ "]&f : " + AMath.round(s3*0.05,2)+ " / 10");
		}
		if(tk%2 == 0) {
			for(int i =0; i<10;i++) if(cooldown[i] > 0) cooldown[i] -= s3*0.001;
		}
		if(fly) {
			s3--;
			if(s3 < 0) {
				fly = false;
				MSUtil.buffoff(player, "c25_ssp");
				player.setFlySpeed(0.15f);
				player.setAllowFlight(false);
			}
		} else if(!BlockUtil.isAirbone(player.getLocation(), 1)) {
			s3+= skillmult + sskillmult;
			if(s3 > 200) s3 = 200;
		}
		
		if(tk%20 == 0) {
			Location l1 = player.getLocation();
			Location l2 = Map.getCenter();
			l1.setY(0);
			l2.setY(0);
			if(l1.distance(l2) <= 3 && skillCooldown(0) && sp) {
				sp = false;
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c2025sp");
				ARSystem.giveBuff(player, new Stun(player), 400);
				ARSystem.giveBuff(player, new Silence(player), 400);
				ARSystem.giveBuff(player, new Exposure(player), 400 , -3);
				for(int i =0; i<400; i++){
					delay(()->{
						ARSystem.spellLocCast(player,player.getLocation().clone().add(0,4,0), "c1025_sp1");
						for(Entity e : ARSystem.box(player, new Vector(1999,1999,1999), box.ALL)) {
							double power = e.getLocation().distance(player.getLocation());
							if(power <= 8) {
								power = 0.3;
							} else {
								power = 0.26 - Math.min(0.25,power*0.015);
							}
							Location l = e.getLocation().clone();
							Vector v = e.getVelocity();
							if(power >= 0.3) {
								v.add(ULocal.lookAt(l, player.getLocation().clone().add(0,4,0)).getDirection().multiply(power));
							} else {
								v.add(ULocal.lookAt(l, player.getLocation()).getDirection().multiply(power));
							}
							e.setVelocity(v);
						}
					},i);
				}
				delay(()->{
					ARSystem.playSoundAll("0explod2");
					ARSystem.spellLocCast(player,player.getLocation().clone().add(0,0.5,0), "c1025_sp2");
					for(Entity e : ARSystem.box(player, new Vector(1999,1999,1999), box.ALL)) {
						double power = e.getLocation().distance(player.getLocation().clone().add(0,4,0));
						if(power <= 3) {
							power = 100;
						} else {
							power = 100 - Math.max(Math.min(99,power*power),0);
						}
						((LivingEntity)e).setNoDamageTicks(0);
						((LivingEntity)e).damage(9*power,player);
					}
				},400);
			}
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(e.getDamage() > 50) {
				e.setDamage(0);
				e.setCancelled(true);
				LivingEntity en = (LivingEntity)e.getDamager();
				player.teleport(en);
				ARSystem.giveBuff(en, new Stun(en), 60);
				ARSystem.giveBuff(en, new Silence(en), 60);
				return false;
			} else {
				Rule.buffmanager.selectBuffValue(player, "barrier", 5);
			}
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == p && p != player) {
			if(Rule.c.get(p) != null && Rule.c.get(p).number == 3025 && BlockUtil.isAirbone(player.getLocation(), 2)) {
				ARSystem.playSoundAll("c2025e"+AMath.random(2));
				sp= true;
			} else {
				ARSystem.playSound((Player)p, "c2025kill");
			}
		}
	}
	
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c63micoto) {
					is = "micoto";
					break;
				}
			}
		}
		
		if(is.equals("micoto")) {
			ARSystem.playSound((Entity)player, "c25micoto");
		} else {
			ARSystem.playSound((Entity)player, "c25db");
		}
		
		return true;
	}
}
