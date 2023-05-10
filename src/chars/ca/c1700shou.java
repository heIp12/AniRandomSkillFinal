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
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import types.box;

import util.AMath;
import util.ULocal;
import util.MSUtil;
import util.Text;

public class c1700shou extends c00main{
	float damage = 0;
	int tick = 0;
	int j = 0;
	
	public c1700shou(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1017;
		load();
		text();

		ARSystem.playSound(player,"c17fs");
	}
	
	@Override
	public boolean skill1() {
		List<Entity> targets = ARSystem.PlayerBeamBox(player, 100, 3, box.TARGET);
		double min = 1000;
		for(Entity e : targets) {
			if(e.getLocation().distance(player.getLocation()) < min) min = e.getLocation().distance(player.getLocation());
		}
		if(min > 35 && min < 999) {
			s0();
			return true;
		}
		if(min <= 35 && min > 14) {
			s1();
			return true;
		}
		if(min > 5 && min <= 14) {
			s2(min);
			return true;
		}
		if(min <= 5) {
			s3();
			return true;
		}
		
		Entity target = ARSystem.boxSOne(player, new Vector(6,6,6), box.TARGET);
		if(target != null) {
			s4(target);
			return true;
		}
		if(player.getHealth() < player.getMaxHealth()) {
			s5();
			return true;
		}
		s6();
		return true;
	}
	void s0(){ // 저격
		if(player.getHealth() <= player.getMaxHealth()/2) {
			s5();
			return;
		}
		skill("c1017_s0");
		cooldown[1] *= 12;
	}
	
	void s1(){ // 원거리
		if(player.getHealth() <= player.getMaxHealth()/2) {
			player.setVelocity(player.getLocation().getDirection().multiply(-1));
		}
		skill("c1017_s1");
		cooldown[1] *= 3;
	}
	void s2(double r){ // 돌진
		player.setFallDistance(0);
		if(player.getHealth() <= player.getMaxHealth()/4) {
			player.setVelocity(player.getLocation().getDirection().multiply(-0.5));
			s5();
			return;
		}
		player.setVelocity(player.getLocation().getDirection().multiply(0.5 + (r*0.2)));
		skill("c1017_s2");
	}
	void s3(){ // 연속공격
		player.setFallDistance(0);
		if(AMath.random(10) <= 5) {
			skill("c1017_s3");
			cooldown[1] *= 0.3;
		} else {
			skill("c1017_s3_1");
			cooldown[1] *= 0.5;
		}
	}
	void s4(Entity target){ // 서치 공격
		player.setFallDistance(0);
		Location loc = player.getLocation();
		loc = ULocal.lookAt(loc, target.getLocation());
		player.teleport(loc);
		
		if(player.getHealth() <= player.getMaxHealth()/4) {
			player.setVelocity(player.getLocation().getDirection().multiply(-1));
			s5();
			return;
		}
		if(player.isOnGround()) {
			player.setVelocity(player.getLocation().getDirection().multiply(1));
			skill("c1017_s4");
			cooldown[1] *= 0.3;
			delay(()->{
				Location locs = player.getLocation();
				locs = ULocal.lookAt(locs, target.getLocation());
				player.teleport(locs);
			},4);
		} else {
			skill("c1017_s4_1");
			cooldown[1] *= 0.6;	
		}
	}
	void s5(){ // 회복
		ARSystem.heal(player, 5);
		cooldown[1] *= 3;
		skill("c1017_s5");
	}
	void s6(){ // 이동
		Entity target = ARSystem.boxSOne(player, new Vector(5,10,5), box.TARGET);
		if(target != null) {
			s4(target);
			return;
		}
		player.setVelocity(player.getLocation().getDirection().multiply(2));
		if(!player.isOnGround()) {
			j++;
			cooldown[1] *= (4+j);
		} else {
			j = 0;
		}

		ARSystem.playSound((Entity)player,"c17j");
		skill("c1017_s6");
	}
	@Override
	public boolean tick() {
		if(tick > 0) {
			tick--;
			if(tick == 0) {
				damage = 0;
			}
		}
		return false;
	}
	
	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player,"c17db"+AMath.random(4));
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			tick = 20;
			damage += e.getDamage();
			if(damage > 30) {
				ARSystem.giveBuff(player, new TimeStop(player), 60);
				skill("c1017_p");
				ARSystem.playSound((Entity)player,"c17p");
				tick = 0;
				damage = 0;
				e.setCancelled(true);
				e.setDamage(0);
				return false;
			}
		}
		return true;
	}
}
