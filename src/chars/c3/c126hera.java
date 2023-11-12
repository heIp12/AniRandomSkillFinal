package chars.c3;

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
import buff.Curse;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import types.box;

import util.AMath;
import util.ULocal;

public class c126hera extends c00main{
	int p = 11;
	int p2 = 0;
	int pattantime = 0;
	int pattan = 0;
	
	Location loc;
	int sp = 0;
	
	public c126hera(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 126;
		load();
		text();
		c = this;
		if(p != null) ARSystem.playSound(player, "c126s"+AMath.random(3));
	}

	@Override
	public void setStack(float f) {
		// TODO Auto-generated method stub
		p = (int)f;
	}
	@Override
	public boolean skill1() {
		if(p <= 4 && skillCooldown(0)) {
			cooldown[1] = 0;
			spskillon();
			spskillen();
			p2 = 0;
			ARSystem.playSound((Player)player, "c126sp");
			skill("c126_s2");
			ARSystem.playSound((Entity)player, "c126s2");
			for(Entity e : ARSystem.box(player, new Vector(16,5,16), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				ARSystem.giveBuff(en, new Stun(en), 120);
			}
			
			ARSystem.giveBuff(player, new TimeStop(player), 120);
			ARSystem.giveBuff(player, new Nodamage(player), 160);
			ARSystem.giveBuff(player, new Silence(player), 160);
			ARSystem.potion(player, 1, 160, 4);
			loc = player.getLocation();
			sp = 140;
			return true;
		}
		if(p2 <= 0) {
			skill("c126_s1");
			ARSystem.playSound((Entity)player, "c126s1");
			for(Entity e : ARSystem.box(player, new Vector(7,5,7), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				en.setNoDamageTicks(0);
				en.damage(3,player);
				Curse cs = new Curse(en);
				cs.setCaster(player);
				ARSystem.giveBuff(en, cs, 20 , 0.5);
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(p2 <= 0) {
			skill("c126_s2");
			ARSystem.playSound((Entity)player, "c126s2");
			for(Entity e : ARSystem.box(player, new Vector(7,5,7), box.TARGET)) {
				LivingEntity en = (LivingEntity) e;
				ARSystem.giveBuff(en, new Stun(en), 60);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(p2 <= 0) {
			ARSystem.playSound((Entity)player, "c126s3");
			p2 = 60;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0) scoreBoardText.add("&c ["+Main.GetText("c126:ps")+ "] : &f"+p);
		if(p2 <= 0 && Rule.buffmanager.GetBuffTime(player, "stun") <= 0 && sp <= 0 && AMath.random(1200) <= 12 - p) {
			ARSystem.playSound((Entity)player, "c126s3");
			p2 = 20 + 8 * (12-p2);
		}
		if(p2 > 0) {
			p2--;
			ps2();
		}
		if(sp > 0) {
			ARSystem.playerRotate(player, loc.getYaw(), loc.getPitch());
			if(sp > 100) {
				if(sp%20 == 0) skill("c126_sp1");
			}
			else if(sp > 80) {
				if(sp%10 == 0) skill("c126_sp1");
			}
			else if(sp > 60) {
				if(sp%5 == 0) skill("c126_sp1");
			}
			else if(sp > 40) {
				if(sp%2 == 0) skill("c126_sp1");
			}
			else if(sp > 20) {
				skill("c126_sp1");
			}
			if(sp == 10) {
				skill("c126_sp2");
				skill("c126_sp3");
				skill("c126_sp4");
			}
			sp--;
		}
		return true;
	}
	
	void ps(Entity e) {
		
		p2 = 0;
		ARSystem.giveBuff(player, new Nodamage(player), 5);
		p--;
		ARSystem.playSound((Entity)player, "c126death");
		double size = 0;
		
		if(player.getMaxHealth() <= 14 && AMath.random(10) <= 7) {
			size = player.getMaxHealth() - AMath.random(0, 1);
		} else {
			size = player.getMaxHealth() - AMath.random(0, 2);
		}
		
		if(size <=1) size = 1;
		player.setMaxHealth(size);
		ARSystem.giveBuff(player, new Stun(player), 80);
		ARSystem.giveBuff(player, new Silence(player), 100);
		ARSystem.heal(player, hp);
	}
	
	@Override
	public boolean remove(Entity caster) {
		if(p > 0) {
			ps(caster);
			return false;
		}
		return super.remove(caster);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sp > 0) {
				((LivingEntity)e.getEntity()).setVelocity(new Vector(0,-0.1,0));
				ARSystem.giveBuff((LivingEntity) e.getEntity(), new Stun((LivingEntity) e.getEntity()), 1);
			}
		} else {
			if(player.getHealth() - e.getDamage() < 1 && p > 0) {
				e.setDamage(0);
				e.setCancelled(true);
				ps(e.getDamager());
				return false;
			}
			if(p2 > 0){
				e.setDamage(e.getDamage() * 0.5);
			}
		}
		return true;
	}
	
	void ps2(){
		Entity target = ARSystem.boxSOne(player, new Vector(80,80,80), box.TARGET);
		if(target == null) pattan = 2;
		
		if(pattantime > 0) {
			pattantime--;
			if(pattan == 1) pattan1(target);
			if(pattan == 2) pattan2(target);
		} else {
			double range = target.getLocation().distance(player.getLocation());
			pattantime = AMath.random(20);
			if(range <= 14) pattan = 1;
			if(AMath.random(5) == 3) pattan = AMath.random(2);
		}
	}
	int delay = 0;
	
	public void pattan1(Entity target) {
		if(target != null) {
			if(delay <= 0) {
				Location loc = ULocal.lookAt(player.getLocation(), target.getLocation().clone());
				double range = target.getLocation().distance(player.getLocation());
				if(range < 6) {
					if(range < 4) {
						if(AMath.random(10) <= 2) {
							delay = 10;
							player.setVelocity(loc.clone().add(0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10)).getDirection().multiply(3).setY(1.5));
							if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(-4).setY(-0.5));
						} else {
							delay = 5;
							player.teleport(loc);
							ARSystem.playSound((Entity)player, "0sword",0.3f+AMath.random(1,6)*0.1f);
							skill("c126_p2_"+AMath.random(6));
						}
					} else {
						delay = 3;
						player.teleport(loc);
						player.setVelocity(loc.getDirection().multiply(-1).setY(0.2));
						if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(-1).setY(-0.5));
					}
				}
				else if(range > 8) {
					delay = 2;
					player.setVelocity(loc.clone().add(0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10),0.5-0.1*AMath.random(10)).getDirection().multiply(0.6).setY(0));
					if(!player.isOnGround()) player.setVelocity(loc.getDirection().multiply(0.6).setY(-0.5));
				} else {
					delay = 10;
					player.teleport(loc);
					skill("c126_p");
				}
			}
		}
		delay--;
	}
	
	public void pattan2(Entity target) {
		if(delay <= 0) {
			if(target != null && AMath.random(3) == 2) {
				delay = 5;
				Location ll = player.getLocation();
				ll.setYaw(ll.getYaw() + 3 - AMath.random(6));
				Location loc = ULocal.lookAt(ll, target.getLocation().clone());
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(1).setY(0));
				if(AMath.random(3) == 2 && player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1));
			} else {
				delay = 5+AMath.random(5);
				Location loc = player.getLocation();
				loc.setYaw(loc.getYaw() + 5 - AMath.random(10));
				player.teleport(loc);
				player.setVelocity(loc.getDirection().multiply(1).setY(0));
				if(player.getLocation().add(player.getLocation().getDirection()).getBlock().isEmpty()) {
					if(player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1.5));
					delay = 10;
				} else {
					if(AMath.random(3) == 2 && player.isOnGround()) player.setVelocity(loc.getDirection().multiply(1).setY(1));
					if(AMath.random(3) == 2 && !player.isOnGround()) player.setVelocity(loc.getDirection().multiply(3).setY(-2));
				}
			}
		}
		delay--;
	}
}
