package ca;

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

import Main.Main;
import ars.ARSystem;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import ars.Rule;
import event.Skill;
import types.modes;
import util.MSUtil;

public class c2600ribai extends c00main{
	int ticks = 0;
	int gas = 200;
	int maxgas = 300;
	int timer = 18;
	
	public c2600ribai(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1026;
		load();
		text();
		ARSystem.playSound(p, "c26select");
	}
	
	@Override
	public void setStack(float f) {
		gas = (int) f;
	}
	
	@Override
	public void info() {
		super.info();
		if(s_kill >= 3) {
			Rule.playerinfo.get(player).tropy(26,1);
		}
	}
	@Override
	public boolean skill1() {
		if(gas >= 20) {
			skill("c26_s1");
			gas-=20;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(gas >= 50) {
			if(isps) {
				skill("c1026_s2p");
			} else {
				skill("c1026_s2");
			}
			gas-=50;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(gas >= 10) {
			if(isps) {
				skill("c26_s3p");
			} else {
				skill("c26_s3");
			}
			gas-=10;
			if(ARSystem.gameMode == modes.ZOMBIE) {
				gas-=30;
			}
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		gas = maxgas;
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		skill("c26_s4");
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p,Entity e) {
		if(timer > 0 && !isps && p != player) {
			timer = 0;
			spskillon();
			spskillen();
			skillmult += 0.5;
			maxgas = 800;
			gas = 800;
			cooldown[1] = cooldown[2] = cooldown[3] = 0;
			ARSystem.giveBuff(player, new TimeStop(player), 40);
			skill("c26_sp");
			ARSystem.playSound((Entity)player, "c26sp");
		}
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			if(timer > 0) timer--;
			if(psopen && timer > 0) {
				scoreBoardText.add("&c ["+Main.GetText("c26:sk0")+ "]&f : "+ timer);
			}
			scoreBoardText.add("&c ["+Main.GetText("c26:t")+ "]&f : "+ gas+" / "+maxgas);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			double hp = ((LivingEntity)e.getEntity()).getMaxHealth();
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 2);
			e.setDamage(e.getDamage()+ e.getDamage()*0.05);
			if(hp>30) e.setDamage(e.getDamage()*2);
			
			if(hp>40) ARSystem.heal(player, e.getDamage());
			
			if(hp>50) {
				s_damage += e.getDamage();
				if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() > 1) {
					((LivingEntity)e.getEntity()).setHealth(((LivingEntity)e.getEntity()).getHealth() - e.getDamage());
				} else {
					Skill.remove(e.getEntity(), player);
				}
				e.setDamage(0);
			}
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage() * 0.7);
		}
		return true;
	}
}
