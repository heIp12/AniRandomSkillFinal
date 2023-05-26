package chars.c;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Silence;
import buff.TimeStop;
import event.Skill;
import types.box;
import util.AMath;
import util.Holo;
import util.MSUtil;

public class c05touma extends c00main{
	private int bf = 0;
	private double damage = 0;
	private int ctime;
	
	public c05touma(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 5;
		load();
		text();
	}
	@Override
	public void setStack(float f) {
		damage = f;
	}
	
	@Override
	public boolean skill1() {
		skill("c"+number+"_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c"+number+"_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c"+number+"_s3");
		return true;
	}
	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c [Damage]&f : " + damage);
		}
		if(ctime > 0) ctime--;
		
		if(tk% 10 == 0) {
			for(Entity e : ARSystem.box(player, new Vector(5, 5, 5), box.TARGET)) {
				if(e != null && Rule.c.get(e) != null && Rule.c.get(e).number == 25) {
					Skill.remove(e, player);
				}
			}
		}
		return true;
	}
	
	protected boolean skill0(EntityDamageByEntityEvent e) {
		double damage = e.getDamage();
		e.setCancelled(true);
		cooldown[0] = 15;
		ARSystem.giveBuff(player, new TimeStop(player), 400);
		ARSystem.giveBuff((LivingEntity) e.getDamager(), new TimeStop((LivingEntity) e.getDamager()), 400);
		Player p = ((Player)e.getEntity());
		p.sendTitle(" ",e.getDamage()*1+ "§a/§c" +(p.getMaxHealth()*0.3),0,10,10);
		skill("c"+number+"_p3");
		
		if(p.getMaxHealth() > p.getHealth() + e.getDamage()*bf) {
			p.setHealth(p.getHealth() + e.getDamage()*bf);
		} else {
			p.setHealth(p.getMaxHealth());
		}
		
		delay(new Runnable(){@Override public void run() { 
			((LivingEntity)e.getDamager()).setMaximumAir(1005);
			skill("c"+number+"_p");
		}}, 60);
		
		delay(new Runnable(){@Override public void run() {
			((LivingEntity)e.getDamager()).setMaximumAir(300);
			skill("c"+number+"_p4");
			Rule.buffmanager.selectBuffTime((LivingEntity) e.getDamager(), "timestop",0);
			Rule.buffmanager.selectBuffTime(player, "timestop",0);
			((LivingEntity)e.getDamager()).setNoDamageTicks(0);
		}}, 160);
		delay(new Runnable(){@Override public void run() {
			((LivingEntity)e.getDamager()).damage(damage*bf,player);
		}}, 161);
		return true;
	}

	protected boolean skill01(EntityDamageByEntityEvent e) {
		e.setCancelled(true);
		ARSystem.giveBuff(player, new TimeStop(player), 400);
		ARSystem.giveBuff((LivingEntity) e.getDamager(), new TimeStop((LivingEntity) e.getDamager()), 400);
		Player p = ((Player)e.getEntity());
		p.sendTitle(" ",e.getDamage()*1+ "§a/§c" +(p.getMaxHealth()*0.3),0,10,10);
		
		skill("c"+number+"_p3_1");
		
		p.setHealth(p.getMaxHealth());
		e.setDamage(0);
		delay(new Runnable(){@Override public void run() { 
			skill("c"+number+"_p4");
		}}, 60);
		delay(new Runnable(){@Override public void run() { 
			skill("c"+number+"_p4");
		}}, 160);
		delay(new Runnable(){@Override public void run() { 
			((LivingEntity)e.getDamager()).setMaximumAir(1005);
			skill("c"+number+"_p_2");
			skill("c"+number+"_spe");
		}}, 220);
		delay(new Runnable(){@Override public void run() { skill("c"+number+"_p4");}}, 228);
		delay(new Runnable(){@Override public void run() { skill("c"+number+"_p4");}}, 236);
		delay(new Runnable(){@Override public void run() { skill("c"+number+"_p4");}}, 244);
		delay(new Runnable(){@Override public void run() { skill("c"+number+"_p4");}}, 252);
		for(int i=0; i<15;i++) {
			delay(()-> {
				ARSystem.spellLocCast(p, p.getLocation().clone().add(new Vector(4-AMath.random(400)*0.02,2-AMath.random(400)*0.01,4-AMath.random(400)*0.02)), "c5_sp0");
				ARSystem.spellLocCast(p, p.getLocation().clone().add(new Vector(4-AMath.random(400)*0.02,2-AMath.random(400)*0.01,4-AMath.random(400)*0.02)), "c5_sp0");
				ARSystem.spellLocCast(p, p.getLocation().clone().add(new Vector(4-AMath.random(400)*0.02,2-AMath.random(400)*0.01,4-AMath.random(400)*0.02)), "c5_sp0");
			}, 255+i);
		}
		delay(new Runnable(){@Override public void run() { 
			((LivingEntity)e.getDamager()).setMaximumAir(300);
			skill("c"+number+"_p4");
			Rule.buffmanager.selectBuffTime((LivingEntity) e.getDamager(), "timestop",0);
			Rule.buffmanager.selectBuffTime(player, "timestop",0);
			Skill.remove(e.getDamager(), e.getEntity());
			for(Entity en : ARSystem.box(p, new Vector(6,3,6),box.TARGET)) {
				if(e.getDamager() != en) {
					Skill.remove(en, e.getEntity());
				}
			}
		}}, 260);
		return true;
	}

	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(MSUtil.isbuff(player, "c5_s1_d")) {
				MSUtil.buffoff(player, "c5_s1_d");
				e.setDamage(e.getDamage()+damage);
				ARSystem.giveBuff((LivingEntity) e.getEntity(), new Silence((LivingEntity) e.getEntity()), 80);
				skill("c"+number+"_p5");
			}
			if(e.getDamage() > 50) {
				Rule.playerinfo.get(player).tropy(5,1);
			}
		}
		if(!isAttack) {
			bf = 1;
			if(MSUtil.isbuff(player, "c5_s3_d")) {
				bf = 3;
			}
			damage = e.getDamage()*bf;
			if(((LivingEntity)e.getEntity()).getMaxHealth() <= e.getDamage()*bf) {
				if(skillCooldown(0)) {
					if(ARSystem.getPlayerCount() == 2) {
						s_score+=10000;
					}
					spskillon();
					spskillen();
					
					skill01(e);
					return false;
				}
			}
			else if(((LivingEntity)e.getEntity()).getMaxHealth()*0.3 <= e.getDamage()*bf){
				if(skillCooldown(0)) {
					skill0(e);
					return false;
				}
			}
		}
		if(e.getDamager() instanceof Player) {
			if(Rule.c.get(e.getDamager()) != null) {
				if(Rule.c.get(e.getDamager()).number == 25) {
					if(ctime <= 0) {
						skill("c5_p4");
						ctime = 8;
					}
					e.setCancelled(true);
					return false;
				}
			}
		}
		return true;
	}
}
