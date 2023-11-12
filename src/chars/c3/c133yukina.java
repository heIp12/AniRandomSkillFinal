package chars.c3;

import java.util.List;

import org.bukkit.GameMode;
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
import buff.NoHeal;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c.c02kirito;
import event.Skill;
import types.box;

import util.AMath;
import util.Holo;
import util.ULocal;

public class c133yukina extends c00main{
	int s2 = 0;
	int s3 = 0;
	int s3t = 0;
	
	int s1 = 0;
	LivingEntity tg;
	
	@Override
	public void setStack(float f) {
		s3 = (int)f;
	}
	
	public c133yukina(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 133;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		skill("c133_p");
		ARSystem.playSound((Entity)player, "c133s1"+AMath.random(6));
		return true;
	}
	
	@Override
	public boolean skill2() {
		cooldown[1] = 0;
		s2count = 0;
		Entity en = ARSystem.boxSOne(player, new Vector(8, 6, 8), box.TARGET);
		if(en == null) {
			cooldown[2] = 0;
			return true;
		}
		ARSystem.playSound((Entity)player, "c133s17");
		attack((LivingEntity)en);
		return true;
	}
	
	int s2count = 0;
	
	public void attack(LivingEntity e) {
		s2count++;
		int mt = 20;
		Location loc = e.getLocation();
		loc = ULocal.lookAt(loc, player.getLocation());
		if(s2count > 19) {
			mt=1;
			player.teleport(ULocal.lookAt(e.getLocation(), e.getLocation()));
			e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(-0.3f)));
		} else if(s2count > 9) {
			mt=2;
			ARSystem.spellCast(player, e, "c133_sk24");
			e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(-0.3f)));
		} else if(s2count > 4) {
			mt=5;
			ARSystem.spellCast(player, e, "c133_sk23");
			e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(-0.2f)));
		} else if(s2count > 1) {
			mt=10;
			ARSystem.spellCast(player, e, "c133_sk22");
		} else {
			ARSystem.spellCast(player, e, "c133_sk21");
		}

		ARSystem.giveBuff(player, new Silence(player), mt+1);
		ARSystem.giveBuff(player, new Nodamage(player), mt+1);
		delay(()->{
			e.setNoDamageTicks(0);
			if(s2count >= 100) {
				Skill.remove(e, player);
				ARSystem.playSound((Entity)player, "0katana", 2f);
				Holo.create(player.getLocation(), "§4Hit MAX!!",10+s2count,new Vector(0, 0.07, 0));
			} else  if(s2count >= 20) {
				makerSkill(e, "1");
				makerSkill(e, "2");
				ARSystem.playSound((Entity)player, "0katana", 2f);
				Holo.create(player.getLocation(), "§4"+s2count+" Hit!!!",10+s2count,new Vector(0, 0.07, 0));
			} else if(s2count >= 10) {
				makerSkill(e, "2");
				ARSystem.playSound((Entity)player, "0katana", 2f);
				Holo.create(player.getLocation(), "§4"+s2count+" Hit!!!",10+s2count,new Vector(0, 0.07, 0));
			} else if(s2count >= 5) {
				e.damage(3,player);	
				ARSystem.playSound((Entity)player, "0katana", 1.2f);
				Holo.create(player.getLocation(), "§6"+s2count+" Hit!!",10+s2count,new Vector(0, 0.1, 0));
			} else {
				e.damage(2,player);	
				ARSystem.playSound((Entity)player, "0katana", 0.8f);
				Holo.create(player.getLocation(), "§e"+s2count+" Hit!",10+s2count,new Vector(0, 0.2, 0));
			}
			for(Entity et : ARSystem.box(player, new Vector(11, 10, 11), box.TARGET)) {
				LivingEntity en = (LivingEntity)et;
				if(en != e && en.getHealth() >= 1 && !en.isDead()) {
					if(en instanceof Player && ((Player) en).getGameMode() == GameMode.SPECTATOR) {
						break;
					}
					attack(en);
					break;
				}
			}
		},Math.max(mt-1, 0));
	}
	
	
	@Override
	public boolean skill3() {
		if(s3 > 0) {
			s3--;
			cooldown[1] = 0;
			
			player.setVelocity(player.getLocation().getDirection().multiply(1.2).setY(0));
			if(AMath.random(3) <= 1&& s2 <= 0) ARSystem.playSound((Entity)player, "c133s3");
			skill("c133_s3");
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		cooldown[1] = 0;
		if(s2 <= 0) ARSystem.playSound((Entity)player, "c133s2");
		s2 = 120;
		for(int i=0; i<6; i++) {
			delay(()->{
				skill("c133_s2");
				skill("removeall");
			},i*20);
		}
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 1 && tk%10 == 0 && !isps) {
			boolean pp = true;
			if(Rule.c.size() < 2) pp = false;
			if(pp) {
				for(Player p : Rule.c.keySet()) {
					if(p != player && Rule.buffmanager.GetBuffTime(p, "nodamage") <= 0 && Rule.buffmanager.GetBuffTime(p, "timestop") <= 0) {
						pp = false;
					}
				}
			}
			if(pp) {
				ARSystem.playSoundAll("c133sp");
				spskillon();
				spskillen();
				skill("c133_sp");
				player.setMaxHealth(60);
				player.setHealth(60);
				ARSystem.giveBuff(player, new TimeStop(player), 140);
				for(int i=0; i<3; i++) {
					if(setcooldown[i] > 1.5f) setcooldown[i] -= 1.5f;
				}
			}
		}
		return super.firsttick();
	}
	
	public boolean tick() {
		if(s2 > 0) {
			s2--;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c133:sk3")+ "] : "+ s3);
			if(tg != null) scoreBoardText.add("&c ["+Main.GetText("c133:sk1")+ "] : "+ tg.getName() +" " + s1 + " / 6");
		}
		if(s3 < 5) {
			if(s3t%100 == 0) {
				s3t = 0;
				s3++;
			}
		}
		s3t+= sskillmult + skillmult;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		ARSystem.giveBuff(target, new NoHeal(target), 60);
		if(n.equals("1")) {
			if(Rule.buffmanager.GetBuffTime(target, "nodamage") > 0 || Rule.buffmanager.GetBuffTime(target, "timestop") > 0) {
				Rule.buffmanager.selectBuffTime(target, "nodamage", 0);
				ARSystem.spellCast(player, target, "c133_p2");
				target.setNoDamageTicks(0);
				float damage = 15;
				if(target.getHealth() - damage >= 1) {
					target.setHealth(target.getHealth() - damage);
				} else {
					Skill.remove(target, player);
				}
			} else {
				target.setNoDamageTicks(0);
				target.damage(3,player);
			}
		}
		if(n.equals("2")) {
			float damage = 1;
			if(isps) damage*=2;
			if(tg != null) {
				if(tg == target) {
					s1++;
					if(s1 >= 6) {
						s1 = 0;
						skill("c133_s1");
					}
				}
			}
				
			tg = target;
			if(Rule.buffmanager.GetBuffTime(target, "nodamage") > 0) {
				Rule.buffmanager.selectBuffTime(target, "nodamage", 0);
				target.setNoDamageTicks(0);
				damage *= 5;
				if(target.getHealth() - damage >= 1) {
					target.setHealth(target.getHealth() - damage);
				} else {
					Skill.remove(target, player);
				}
			} else {
				target.setNoDamageTicks(0);
				target.damage(damage,player);
			}
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			ARSystem.giveBuff((LivingEntity)e.getEntity(), new NoHeal((LivingEntity)e.getEntity()), 60);
			if(isps) {
				if(e.getEntity().getLocation().distance(e.getDamager().getLocation()) > 8) {
					e.setDamage(0);
					e.setCancelled(true);
				}
				if(Rule.buffmanager.GetBuffTime(player, "nodamage") > 0) {
					e.setDamage(0);
					e.setCancelled(true);
				}
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c02kirito) {
					is = "kirito";
					break;
				}
			}
		}
		
		if(is.equals("kirito")) {
			ARSystem.playSound((Entity)player, "c133kirito");
		} else {
			ARSystem.playSound((Entity)player, "c133db");
		}
		
		return true;
	}
}
