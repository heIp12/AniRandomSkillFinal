package chars.ca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Exposure;
import buff.Nodamage;
import buff.Panic;
import buff.Stun;
import buff.Wound;
import chars.c.c00main;
import types.TargetMap;
import types.box;
import util.AMath;
import util.MSUtil;
import util.Map;
import util.ULocal;


public class c0900youmu extends c00main{	
	public class c09Count {
		c09Count(){
			timer = 12;
			count = 1;
		}
		public int timer = 12;
		public int count = 1;
	}
	
	Location loc;
	int ls = 0;
	
	int sk2 = 3;

	boolean ps = true;
	boolean sk3 = false;
	
	HashMap<Entity, c09Count> s3 = new HashMap<>();
	
	public c0900youmu(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1009;
		load();
		text();
		ARSystem.playSound(player, "c9select");
	}
	
	@Override
	public void setStack(float f) {
		sk2 = (int) f;
		if(sk2 == 999) {
			spskillon();
		}
	}
	
	
	@Override
	public boolean skill1() {
		sk2+=1;
		if(sk2 > 3) sk2 = 3;
		cooldown[2] = 0;
		loc = player.getLocation();
		ls = 1;
		skill("c1009_s1");
		if(cooldown[3] > 0) cooldown[3] -= 1;
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(isps && skillCooldown(0)) {
			isps = false;
			spskillen();
			if(player.isSneaking()) {
				player.setVelocity(player.getLocation().getDirection().multiply(1.2));
			} else {
				player.setVelocity(player.getLocation().getDirection().multiply(3));
			}
			ARSystem.giveBuff(player, new Nodamage(player), 20);
			ARSystem.playSoundAll("c9sp");
			delay(()->{
				Location loc = player.getLocation();
				skill("c1009_sp");
				player.setGameMode(GameMode.SPECTATOR);
				for(Entity e : ARSystem.box(player, new Vector(8,4,8), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					ARSystem.giveBuff(en, new Stun(en), 20);
					ARSystem.giveBuff(en, new Panic(en), 200);
					ARSystem.potion(en, 2, 200, 6);
				}
				delay(()->{
					player.teleport(loc);
					player.setGameMode(GameMode.ADVENTURE);
				},220);
			},10);
			return true;
		}
		if(sk2 > 0) {
			sk2--;
			loc = player.getLocation();
			ls = 2;

			skill("c1009_s2");
			if(cooldown[3] > 0) cooldown[3] -= 1;
			ARSystem.giveBuff(player, new Nodamage(player), 10);
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(loc == null) {
			cooldown[3] = 0;
			return false;
		}
		sk2+=1;
		if(sk2 > 3) sk2 = 3;
		cooldown[2] = 0;
		ARSystem.spellLocCast(player, loc, "c1009_s3-"+ls);
		return true;
	}
	
	
	@Override
	public boolean skill9(){
		player.getWorld().playSound(player.getLocation(), "c9db", 1, 1);
		return false;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player && !isps) {
			spskillon();
		}
	}

	@Override
	public boolean tick() {
		if(tk%20 ==0) {
			scoreBoardText.add("&c ["+Main.GetText("c9:sk2")+ "]&f : " + sk2 + " / 3");
		}
		ArrayList<Entity> r = new ArrayList<>();
		for(Entity v : s3.keySet()) {
			s3.get(v).timer--;
			if(s3.get(v).timer <= 0) {
				r.add(v);
			}
		}
		for(Entity en : r) {
			s3.remove(en);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			Location lc = ULocal.lookAt(player.getLocation().clone(), target.getLocation());
			target.setVelocity(lc.getDirection().multiply(1.6f));
		}
		if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(2,player);
			Location lc = ULocal.lookAt(player.getLocation().clone(), target.getLocation());
			target.setVelocity(lc.getDirection().multiply(2.2f));
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			if(player.getGameMode() != GameMode.SPECTATOR) {
				if(s3.get(target) != null) {
					s3.get(target).count++;
					if(s3.get(target).count >= 3) {
						s3.get(target).count = -999;
						s3.get(target).timer = 60;
						delay(()->{
							Location lc = target.getLocation().clone();
							ARSystem.spellLocCast(player, ULocal.lookAt(ULocal.offset(lc, new Vector(0,0,5)), lc), "c1009_s3-2");
							ARSystem.spellLocCast(player, ULocal.lookAt(ULocal.offset(lc, new Vector(0,0,-5)), lc), "c1009_s3-2");
							s3.get(target).count = -999;
						},5);
					}
				} else {
					s3.put(target, new c09Count());
				}
			}
		}
		if(!Rule.buffmanager.isBuff(target, "exposure")) {
			ARSystem.giveBuff(target, new Exposure(target) , 160, 0.5f);
		} else {
			Rule.buffmanager.selectBuffAddTime(target, "exposure", 20);
			Rule.buffmanager.selectBuffAddValue(target, "exposure", 0.5f);
		}
	}
	
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {

		}
		return true;
	}
}
