package c;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import event.Skill;
import manager.Holo;
import util.AMath;
import util.MSUtil;

public class c03remuru extends c00main{
	int ticks = 0;
	int s2 = 0;
	
	public c03remuru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 3;
		Rule.buffmanager.selectBuffValue(player, "plushp",10);
		load();
		text();
	}
	
	@Override
	public void setStack(float f) {
		Rule.buffmanager.selectBuffValue(player, "plushp",f);
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c3a");
		
		if(Rule.buffmanager.GetBuffValue(player, "plushp") > 100) {
			cooldown[1] *= 0.5;
		}
		if(Rule.buffmanager.GetBuffValue(player, "plushp")< 50) {
			skill("c"+number+"_s1_e1");
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") < 100) {
			skill("c"+number+"_s1_e2");
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") < 250) {
			skill("c"+number+"_s1_e3");
		} else if(Rule.buffmanager.GetBuffValue(player, "plushp") < 500) {
			skill("c"+number+"_s1_e4");
		} else {
			skill("c"+number+"_s1_e4");
			delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_e3");}}, 5);
			delay(new Runnable() { @Override public void run() {skill("c"+number+"_s1_e2");}}, 10);
		}
		
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c3d");
		s2 = 80;
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}

	
	public boolean skill0(EntityDamageByEntityEvent e) {
		if(skillCooldown(0)) {
			skill("c"+number+"_sps1");
			Rule.buffmanager.selectBuffValue(player, "plushp",0);
			s_score+= 50000000;
			delay(new Runnable(){@Override public void run() {
				ARSystem.giveBuff(player, new TimeStop(player), 1200);
				}}, 20);
			delay(new Runnable(){@Override public void run() { skill("c"+number+"_sps2");}}, 120);
			delay(new Runnable(){@Override public void run() { skill("c"+number+"_spe_3");}}, 340);
			delay(new Runnable(){@Override public void run() { skill("c"+number+"_spe_4");}}, 580);
			delay(new Runnable(){@Override public void run() { skill("c"+number+"_spe_4");}}, 602);
			delay(()->{ skill("c"+number+"_spe_4");}, 624);
			delay(()->{ skill("c"+number+"_sps3");}, 840);
			delay(()->{  skill("c"+number+"_sp_a");}, 1080);
			 
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		ticks++;
		if(s2>0)s2--;

		if(MSUtil.isbuff(player, "c3_s3") && player.isSneaking()) {
			player.setVelocity(new Vector(0,0.01,0));
		}
		if(MSUtil.isbuff(player, "c3_s3") && !player.isSneaking()) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.5));
		}
		
		ticks%=20;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(Rule.buffmanager.GetBuffValue(player, "plushp") >= 1000 && !spben) {
			spskillon();
			spskillen();
			skill0(e);
		}
		if(isAttack) {
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() < 1) {
				boolean hs = false;

				if(e.getEntity() instanceof Player && ((Player)e.getEntity()).getGameMode() == GameMode.SPECTATOR) {
					hs = true;
				}
				if(Rule.buffmanager.OnBuffTime(player, "timestop")) {
					hs = true;
				}
				if(Rule.buffmanager.OnBuffTime(player, "nodamage")) {
					hs = true;
				}
				

				if(!hs) {
					player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp")+"(+"+((LivingEntity)e.getEntity()).getMaxHealth()/2+")",0,40,20);
					Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) (((LivingEntity)e.getEntity()).getMaxHealth()/2));
					Skill.remove(e.getEntity(), e.getDamager());
					ARSystem.playSound((Entity)player, "c3s2");
					e.setDamage(0);
				}
			}
		}
		if(!isAttack) {
			if(s2 > 0) {
				ARSystem.giveBuff(player, new Nodamage(player), 2);
				skill("c3_sound3");
				player.sendTitle("","HP: "+Rule.buffmanager.GetBuffValue(player, "plushp")+"(+"+e.getDamage()+")",0,30,10);
				Rule.buffmanager.selectBuffAddValue(player, "plushp",(float) e.getDamage());
				if(e.getDamage() >= 100) {
					Rule.playerinfo.get(player).tropy(3,1);
				}
				player.setNoDamageTicks(20);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			} else if(Rule.buffmanager.OnBuffValue(player, "plushp")) {
				ARSystem.playSound((Entity)player, "c3s1");
			}
		}
		return true;
	}
}