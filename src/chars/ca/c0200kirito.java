package chars.ca;

import java.sql.Time;
import java.util.Timer;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;

import util.AMath;
import util.ULocal;
import util.MSUtil;

public class c0200kirito extends c00main{
	int bullet = 8;
	int sk3 = 0;
	int sk1 = 0;
	LivingEntity target = null;
	int count = 0;
	float ps = 0;
	
	Location sk3loc = null;
	
	@Override
	public void setStack(float f) {
		ps = f;
	}
	
	public c0200kirito(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1002;
		load();
		text();
		ARSystem.playSound(player, "c2select");
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		if(sk1 < 10) sk1 = 10;
		if(ps >= 10) {
			cooldown[1]*=0.5;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(bullet > 0) {
			bullet--;
			skill("c"+number+"_s2");
			ARSystem.playSound((Entity)player, "c2a");
		} else {
			ARSystem.playSound((Entity)player, "c47s42",(float) 0.5);
			bullet = 8;
			if(ps < 20) {
				cooldown[2] = 8;
			} else {
				cooldown[2] = 4;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(ps < 30) {
			ARSystem.playSound((Entity)player, "c47s2",(float) 1);
			player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0));
			ARSystem.giveBuff(player, new Nodamage(player), 10);
			if(sk3 <= 0){
				sk3 = 1;
				cooldown[3] = 0;
				delay(()->{
					cooldown[3] = setcooldown[3]-1;
					sk3 = 0;
				},20);
			}
		} else {
			ARSystem.playSound((Entity)player, "c2002heal",(float) 1);
			sk1 = 20;
			sk3 = 20;
			sk3loc = player.getLocation();
			cooldown[3]*=0.5;
		}
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.addBuff(target, new Stun(target), 4);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			if(target == this.target && count >= 6 && skillCooldown(0)) {
				spskillen();
				spskillon();
				ARSystem.playSound(target, "c2002sp2");
				ARSystem.addBuff(player, new TimeStop(player), 20);
				ARSystem.addBuff(player, new Nodamage(player), 60);
				for(int i = 0; i<50; i++) {
					delay(()->{
						skill("c1002_sp2");
						ARSystem.spellCast(player, target, "c1002_s1_ia");
						ARSystem.spellCast(player, target, "c1002_spd");
					},20 + i);
				}
			}
		}
	}
	@Override
	public boolean skill4() {
		if(ps < 50) {
			ARSystem.giveBuff(player, new Stun(player), 10);
			ARSystem.giveBuff(player, new Silence(player), 10);
			ARSystem.playSound((Entity)player, "c2d");
			delay(()->{
				player.setVelocity(player.getLocation().getDirection().multiply(3).setY(0.2));
				ARSystem.playSound((Entity)player, "c2a2");
			},10);
			delay(()->{
				ARSystem.playSound((Entity)player, "c2002sp3");
			},20);
			for(int i=0;i<30;i++) {
				delay(()->{
					ARSystem.giveBuff(player, new Stun(player), 4);
					skill("c1002_s4");
				},20+i);
			}
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(3).setY(0.2));
			ARSystem.playSound((Entity)player, "c2a2");
			delay(()->{
				ARSystem.playSound((Entity)player, "c2002sp3");
			},10);
			for(int i=0;i<30;i++) {
				delay(()->{
					ARSystem.giveBuff(player, new Nodamage(player), 2);
					ARSystem.giveBuff(player, new Stun(player), 2);
					skill("c1002_s4");
					skill("c1002_s4");
					skill("c1002_s4");
				},10+i);
			}
		}
		return true;
	}
	@Override
	public boolean tick() {
		if(sk1 > 0) sk1--;
		if(ps >=20 && sk3 > 0) {
			sk3--;
			player.setVelocity(sk3loc.getDirection().multiply(1.2).setY(0));
			if(sk3%2==0) {
				Location loc = player.getLocation();
				loc.setYaw(sk3loc.getYaw()-10+AMath.random(20));
				loc.setPitch(-10 + AMath.random(20));
				ARSystem.spellLocCast(player, ULocal.offset(loc, new Vector(2,0,0)), "c1002_s2_b");
				ARSystem.playSound((Entity)player, "0gun2",1.4f);
			}
			if(sk3%2==1) {
				skill("c1002_s3_1");
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c1002:ps")+ "] : "+ ps);
			scoreBoardText.add("&c ["+Main.GetText("c1002:sk2")+ "] : "+ bullet);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getEntity().getLocation().distance(player.getLocation()) <= 6) {
				e.setDamage(e.getDamage()*1.5);
			}
			if(target == e.getEntity()) {
				count++;
			} else {
				target = (LivingEntity) e.getEntity();
			}
			((LivingEntity)e.getEntity()).setVelocity(new Vector(0,0,0));
		} else {
			if(sk1 > 0 && e.getDamager().getLocation().distance(player.getLocation()) > 4) {
				ps+= e.getDamage();
				ARSystem.playSound((Entity)player, "c2g");
				e.setDamage(0);
				return false;
			}
			if(e.getDamager().getLocation().distance(player.getLocation()) > 12) {
				e.setDamage(e.getDamage()*0.5);
			}
			
			if(player.getHealth() - e.getDamage() <=1 && ps >= 30) {
				ps-=30;
				ARSystem.playSound((Entity)player, "c2d");
				ARSystem.giveBuff(player, new Nodamage(player), 60);
				ARSystem.heal(player, 10000);
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
}
