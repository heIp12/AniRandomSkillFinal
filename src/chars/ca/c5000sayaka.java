package chars.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;

import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c5000sayaka extends c00main{
	int sk1 = 0;
	LivingEntity target;
	int sk3 = 0;
	
	public c5000sayaka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1050;
		load();
		text();
		ARSystem.playSound(p, "c50sp");
	}
	
	@Override
	public boolean skill1() {
		if(sk1 <= 0) {
			sk1 = 20;
			cooldown[1] = 0.3f;
			skill("c1050_s1");
		} else {
			sk1 = 0;
			skill("c1050_s1-2");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.potion(player, 1, 60, 1);
		player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0.2));
		for(int i = 0; i<2;i++) skill("c1050_p");
		skill("c1050_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		sk3 = 0;
		target = null;
		for(int i = 0; i<3;i++) skill("c1050_p");
		ARSystem.playSound((Entity)player, "c50s3");
		for(int i = 0; i<3;i++) {
			delay(()->{
				ARSystem.playSound((Entity)player, "0slash2",1.6f,0.1f);
				skill("c1050_s3");
				skill("c1050_s3e");
			},i*3);
		}
		return true;
	}

	@Override
	protected boolean skill9() {
		player.getWorld().playSound(player.getLocation(), "c50db", 1, 1);
		return true;
	}
	@Override
	public boolean tick() {
		if(tk%5==0 && AMath.random(100)<= 10) {
			skill("c1050_p");
		}
		if(sk1 > 0) {
			sk1--;
			if(sk1 == 0) cooldown[1] = setcooldown[1];
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(4,player);
			target.setVelocity(new Vector(0,0.8,0));
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(5,player);
			Location loc = target.getLocation();
			loc = ULocal.lookAt(loc, player.getLocation());
			target.setVelocity(target.getVelocity().add(loc.getDirection().multiply(-3)).setY(0.2));
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(5,player);
			if(target != this.target) {
				this.target = target;
				sk3 = 0;
			}
			sk3++;
			if(sk3 == 3) {
				player.teleport(target);
				skill("c1050_s3-2");
			}
		}
			
	}
	
	public void spawn(Location loc) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"mm m spawn c50_"+(AMath.random(3)+1)+" 1 "+loc.getWorld().getName()+","+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ());
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 1) {
				ARSystem.heal(player, e.getDamage());
			}
		} else {
			if(AMath.random(100) <= 20) {
				for(int i= AMath.random(5); i > 0; i--) {
					skill("c1050_p");
				}
			}
		}
		return true;
	}
	
}
