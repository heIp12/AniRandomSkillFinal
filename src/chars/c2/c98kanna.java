package chars.c2;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import event.Skill;

import util.AMath;

public class c98kanna extends c00main{
	LivingEntity target = null;
	int sk1 = 0;
	float light = 0;
	int count = 0;
	
	public c98kanna(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 98;
		load();
		text();
		c = this;
		if(ARSystem.isGameMode("kanna")) setcooldown[3] = 10000;
		
	}

	@Override
	public boolean skill1() {
		if(target == null) {
			ARSystem.playSound((Entity)player, "c98s1");
			player.setVelocity(player.getLocation().getDirection().multiply(0.5));
			skill("c98_s1");
			sk1 = 200;
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(target != null) {
			target.setNoDamageTicks(0);
			int damage = 3;
			
			if(ARSystem.isGameMode("lobotomy")) {
				damage = 10;
			}
			
			if(target.getHealth()-damage < 1) {
				count++;
				if(count >= 2) Rule.playerinfo.get(player).tropy(98,1);
				Skill.remove(target, player);
				if(!ARSystem.isGameMode("kanna"))  ARSystem.heal(player, 5);
				light += target.getHealth();
				target = null;
				cooldown[1] = sk1*0.05f;
				sk1 = 0;
			} else {
				target.damage(damage, player);
			}

			ARSystem.playSound((Entity)player, "c98s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c98s3");
		ARSystem.giveBuff(player, new Stun(player), 60);
		light *= 0.8;
		delay(()->{
			skill("c98_s3");
		},60);
		if(ARSystem.isGameMode("lobotomy")) light *= 0.2;
		return true;
	}
	
	public void te() {
		if(target.hasPotionEffect(PotionEffectType.INVISIBILITY) && (target instanceof Player || ARSystem.isGameMode("kanna"))) target.removePotionEffect(PotionEffectType.INVISIBILITY);
		if(Rule.buffmanager.OnBuffTime(target, "silence")) {
			Rule.buffmanager.selectBuffTime(target, "silence",0);
		}
		if(Rule.buffmanager.OnBuffTime(target, "stun")) {
			Rule.buffmanager.selectBuffTime(target, "stun",0);
		}
		if(Rule.buffmanager.OnBuffTime(target, "noattack")) {
			Rule.buffmanager.selectBuffTime(target, "noattack",0);
		}
		delay(()->{
			if(target != null) {
				target.teleport(player);
				target.setVelocity(player.getLocation().getDirection().multiply(2));
				target = null;
			}
		},1);
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			float kill = 14;
			if(ARSystem.isGameMode("lobotomy")) kill = 30;
			if(!(target instanceof Player) && target.getHealth() <= kill) {
				light += target.getHealth();
				Skill.remove(target, player);
				if(!ARSystem.isGameMode("kanna")) ARSystem.heal(player, 5);
				cooldown[1] = 0.5f;
				return;
			}
			if(this.target == null) {
				this.target = target;
				ARSystem.potion(target, 14, 200, 1);
				ARSystem.giveBuff(target, new Silence(target), 200);
				ARSystem.giveBuff(target, new Stun(target), 200);
				if(!ARSystem.isGameMode("kanna")) ARSystem.giveBuff(target, new Noattack(target), 200);
				cooldown[1] = 300;
			}
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(light*0.25,player);
		}
	}
	
	@Override
	public boolean tick() {
		if(ARSystem.isGameMode("kanna")) {
			light = 0;
			if(cooldown[1] > 1) {
				cooldown[1] = 1;
			}
		}
		if(target != null && player.isSneaking()) {
			te();
			cooldown[1] = 10 - sk1*0.05f;
			if(cooldown[1] <= 5) cooldown[1] = 5;
			
		}
		if(tk%20 == 0) {
			if(light >= 10) {
				int lv = 0;
				if(light >= 30) lv = 1;
				if(light >= 60) lv = 2;
				if(light >= 90) lv = 3;
				if(light >= 300) lv = 5;
				if(light >= 1000) lv = 10;
				ARSystem.potion(player, 1, 40, lv);
			}
		}
		if(target != null && sk1 > 0) {
			target.teleport(player.getLocation().add(0,2,0));
			target.setVelocity(new Vector(0, 0, 0));
			if(tk%20 == 0) {
				float damage = 0.5f;
				if(ARSystem.isGameMode("lobotomy")) damage = 3;
				if(target.getHealth()-damage < 1) {
					count++;
					if(count >= 2) Rule.playerinfo.get(player).tropy(98,1);
					Skill.remove(target, player);
					if(!ARSystem.isGameMode("kanna"))  ARSystem.heal(player, 5);
					light += target.getHealth();
					target = null;
					cooldown[1] = sk1*0.05f;
					sk1 = 0;
				} else {
					target.setNoDamageTicks(0);
					target.damage(damage,player);
				}
			}
			sk1--;
		}
		if(sk1 <= 0 && target != null) {
			te();
			cooldown[1] = 10;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c98:ps")+"] : " + light);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) {
				e.setDamage(e.getDamage()*2);
				light += e.getDamage() * 0.1;
			} else {
				light += e.getDamage();
			}
		} else {
			if(Rule.c.get(e.getDamager()) != null) {
				int code = Rule.c.get(e.getDamager()).number;
				if(code == 63 || code == 109) {
					light += e.getDamage()*2;
					e.setDamage(e.getDamage()* 0.1);
				}
			}
			double damage = e.getDamage()*0.9;
			if(light >= damage) {
				light -= damage;
				e.setDamage(e.getDamage() * 0.1);
			} else {
				e.setDamage(e.getDamage() - light);
				light = 0;
			}
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(ARSystem.isGameMode("kanna")) {
			e.setDamage(e.getDamage() * (AMath.random(1,500) * 0.1));
		}
		return true;
	}
}
