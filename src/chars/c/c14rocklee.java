package chars.c;

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

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c14rocklee extends c00main{
	Location loc;
	int sk2 = 0;
	boolean sk1 = false;
	float sk3 = 1;
	
	public c14rocklee(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 14;
		load();
		text();
		if(p != null) {
			player.getLocation();
			MSUtil.setvar(player, "c14", 0);
		}
	}

	@Override
	public boolean skill1() {
		loc = player.getLocation();
		ARSystem.playSound((Entity)player, "c14s1");
		skill("c14_s1");
		skill("c14_move");
		sk1 = true;
		ARSystem.spellLocCast(player, player.getLocation().clone().add(player.getLocation().getDirection().multiply(8*sk3)), "c14_move");
		delay(()->{
			if(sk2 <= 0) {
				ARSystem.spellLocCast(player, loc, "c14_move");
			}
			sk1 = false;
		},10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		LivingEntity t = (LivingEntity)ARSystem.boxSOne(player, new Vector(3,2,3), box.TARGET);
		if(t != null) {
			if(!isps) ARSystem.playSound((Entity)player, "c14s2");
			if(isps) ARSystem.playSound((Entity)player, "c14s3");
			ARSystem.giveBuff(t, new Silence(t), 40);
			ARSystem.giveBuff(player, new Silence(player), 40);
			ARSystem.giveBuff(t, new Stun(t), 40);
			ARSystem.giveBuff(player, new Stun(player), 40);
			int count = 10;
			if(sk1) {
				cooldown[2] *= 1.5f;
				count = 20;
			}
			count*=sk3;
			sk2 = 40;
			for(int i = 0; i< count; i++) {
				int j = i;
				LivingEntity target = t;
				Location loc = target.getLocation().clone();
				tpsdelay(()->{
					Location l = loc.clone().add(0,j*1,0);
					l.setYaw(j*72);
					l.setPitch(0);
					Location l2 = l.clone();
					l.setPitch(-90);
					target.teleport(l);
					ARSystem.spellCast(player, target, "c14_d");
					l2 = ULocal.offset(l2, new Vector(1,0,0));
					l2 = ULocal.lookAt(l2, target.getLocation());
					player.teleport(l2);
					target.setVelocity(new Vector(0,0.1,0));
					player.setVelocity(new Vector(0,0.1,0));
					player.setFallDistance(0);
				},j);
			}
			
			Location loc = t.getLocation().clone().add(0,count,0);
			int fall = 0;
			for(int i = 0; i< 50; i++) {
				int j = i;
				LivingEntity target = t;
				Location local = loc.clone().add(0,-i,0);
				if(local.getY() > 1 &&BlockUtil.isPathable(local.getBlock()) || Map.loc_l.getY()-1 < local.getY()) {
					fall++;
					tpsdelay(()->{
						ARSystem.giveBuff(t, new Silence(t), 4);
						ARSystem.giveBuff(player, new Silence(player), 4);
						ARSystem.giveBuff(t, new Stun(t), 4);
						ARSystem.giveBuff(player, new Stun(player), 4);
						
						Location l = local.clone();
						l.setYaw(j*72);
						l.setPitch(0);
						Location l2 = l.clone();
						l.setPitch(90);
						target.teleport(l);
						ARSystem.spellCast(player, target, "c14_d");
						l2 = ULocal.offset(l2, new Vector(1,0,0));
						l2 = ULocal.lookAt(l2, target.getLocation());
						player.teleport(l2);
						player.setFallDistance(0);
						if(isps) {
							ARSystem.playSound(t, "0attack4",0.8f);
							ARSystem.spellLocCast(player,ULocal.offset(l.clone(), new Vector(0,0,2)), "c14_1e");
							target.setNoDamageTicks(0);
							target.damage(0.5,player);
						}
					},count+j);
				} else {
					break;
				}
			}
			float damage = fall*0.5f;
			delay(()->{
				t.setNoDamageTicks(0);
				if(!isps) t.damage(damage,player);
				if(isps) t.damage(damage*1.5f,player);
				ARSystem.playSound(t, "0explod",0.8f);
				ARSystem.spellCast(player, t, "c14_d2");
			},count+fall+2);
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sk3 == 1.5) {
			sk3 = 1;
			player.removePotionEffect(PotionEffectType.SPEED);
			skillmult -= 0.5;
		} else {
			sk3 = 1.5f;
			if(!isps) ARSystem.potion(player, 1, 40000, 2);
			if(isps) ARSystem.potion(player, 1, 40000, 4);
			skillmult += 0.5;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			int count = 5;
			if(isps) count = 10;
			for(int i =0;i<count;i++) {
				delay(()->{
					ARSystem.playSound(target, "c14a",0.7f);
					ARSystem.spellCast(player,target, "c14_d");
					target.setNoDamageTicks(0);
					target.damage(1,player);
				},i*2);
			}
		}
	}

	@Override
	public boolean tick() {
		if(sk2 > 0) sk2--;
		
		if(sk3 >= 1.5f) {
			if(tk%20 == 0 && !isps) {
				if(player.getHealth() - 0.5 <1) {
					Rule.playerinfo.get(player).tropy(14, 1);
				}
				hpCost(0.5, true);
				if(AMath.random(100) <= 1) {
					spskillen();
					spskillon();
					skill("c14_sp");
				}
			} else if(tk%20==0){
				ARSystem.heal(player, 1);
			}
			if(tk%2 == 0) skill("c14_s3");
		}
		
		return false;
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk3 >= 1.5f) {
				e.setDamage(e.getDamage()*0.8f);
			}
		} else {
			if(player.getHealth() >= 5 && player.getHealth() - e.getDamage() < 1) {
				player.setHealth(1);
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
}
