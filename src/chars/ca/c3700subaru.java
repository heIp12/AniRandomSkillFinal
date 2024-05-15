package chars.ca;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Noattack;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import types.box;
import util.AMath;
import util.ULocal;

public class c3700subaru extends c00main{
	Location loc;
	boolean ps = true;
	
	int replay = 1;
	int tick = 0;

	public c3700subaru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1037;
		load();
		text();
		loc = player.getLocation();
	}
	

	@Override
	public void setStack(float f) {
		replay = (int) f;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c1037s1");
		for(int i=0;i<replay*3;i++) {
			for(Entity e : ARSystem.box(player, new Vector(8,8,8),box.TARGET)) {
				if(Rule.c.get(e) != null) {
					delay(new Runnable() {
						Entity es = e;
						@Override
						public void run() {
							int j = AMath.random(4);
							if(j==1) ((LivingEntity)es).damage(2,es);
							if(j==2) {
								Location l;
								l = es.getLocation();
								Player e2 = ARSystem.RandomPlayer();
								es.teleport(e2);
								e2.teleport(l);
							}
							if(j==3) {
								if(Rule.c.get(es) != null){
									Rule.c.get(es).cooldown[0]+=1;
									Rule.c.get(es).cooldown[1]+=1;
									Rule.c.get(es).cooldown[2]+=1;
									Rule.c.get(es).cooldown[3]+=1;
									Rule.c.get(es).cooldown[4]+=1;
								}
							}
							if(j==4) {
								ARSystem.addBuff((LivingEntity) es, new Stun((LivingEntity) es), 20);
								ARSystem.addBuff((LivingEntity) es, new Noattack((LivingEntity) es), 20);
								ARSystem.addBuff((LivingEntity) es, new Silence((LivingEntity) es), 20);
							}
						}
					}, 60+(i*1));
				} else {
					delay(new Runnable() {
						Entity es = e;
						@Override
						public void run() {
							((LivingEntity)es).damage(2,es);
						}
					},60+(i*1));
				}
			}
		}
		return true;
	}
	@Override
	public boolean skill2() {
		if(AMath.random(2) == 2) {
			ARSystem.playSound((Entity)player, "c1037s2");
		} else {
			ARSystem.playSound((Entity)player, "c1037s22");
		}
		skill("c37_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1037s3");
		skill("c1037_s3");
		tick = 60;
		
		
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c37:t1")+ "]&f : "+ replay);
		}
		if(replay > 30) {
			replay = 30;
			ARSystem.giveBuff(player, new TimeStop(player), 60);
		}
		if(isps) {
			for(Player p : Rule.c.keySet()) {
				if(p != player) {
					Location r = ULocal.lookAt(p.getLocation(), player.getLocation());
					ARSystem.playerRotate(p, r.getYaw(), r.getPitch());
				}
			}
		}
		if(isps && tk%20==0) {
			for(Player p : Rule.c.keySet()) {
				if(p != player) {
					p.damage(1,player);
					ARSystem.spellCast(player, p, "look2");
				}
			}
		}
		
		if(ARSystem.AniRandomSkill != null && ps && ARSystem.AniRandomSkill.time >= 100) {
			ps = false;
			player.setMaxHealth(player.getMaxHealth()+(replay*3));
			player.setHealth(player.getMaxHealth());
			if(replay >= 8) {
				s_score -= 500;
				spskillen();
				spskillon();
				ARSystem.giveBuff(player, new TimeStop(player), 160);
				ARSystem.playSound((Entity)player, "c1037sp");
				delay(new Runnable() {
					
					@Override
					public void run() {
						s_score += 10000500;
					}
				},160);
			}
		}
		if(tick > 0) {
			tick--;
		}
		
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) {
			p.setMaxHealth(p.getMaxHealth()+4);
			ARSystem.heal(p, 8);
		}
	}
	
	public void ps() {
		replay++;
		ARSystem.giveBuff(player, new Nodamage(player), 10);
		ARSystem.giveBuff(player, new Silence(player), 10);
		player.teleport(loc);
		player.setHealth(hp);
		skill("c1037_p");
		if(replay >= 6) {
			Rule.playerinfo.get(player).tropy(37,1);
		}
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 100) {
			ps();
			return false;
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(tick > 0) {
				e.setDamage(e.getDamage());
			}
			if(player.getHealth()- e.getDamage() < 1 && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 100) {
				e.setDamage(0);
				ps();
				return false;
			}
		}
		return true;
	}
}