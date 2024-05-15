package chars.ca;

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
import buff.Airborne;
import buff.Buff;
import buff.Dancing;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import chars.c.c00main;
import event.Skill;
import types.BuffType;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c0600gura extends c00main{
	
	int pt = 0;
	int s2ct = 200;
	int s2c = 4;
	int s2t = 0;
	int s2 = 0;
	boolean s2n = false;
	
	Entity tg;
	int tgt = 0;
	
	int count = 0;
	
	@Override
	public void setStack(float f) {
		s2c = (int)f;
	}
	
	public c0600gura(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1006;
		load();
		text();
	}
	
	@Override
	public boolean skill1() {
		if(pt > 80) {
			cooldown[1] = 0;
			return false;
		}
		pt = 0;
		if(player.getLocation().getPitch() < -35) {
			skill("c1006_s1-1");
			ARSystem.playSound((Entity)player, "c1006s1u");
			if(BlockUtil.isAirbone(player.getLocation(), 1)) ARSystem.giveBuff(player, new Airborne(player), 12);
			
		} else if(player.getLocation().getPitch() > 50) {
			skill("c1006_s1-3");
			ARSystem.playSound((Entity)player, "c1006s1d");
			if(BlockUtil.isAirbone(player.getLocation(), 1)) ARSystem.giveBuff(player, new Airborne(player), 8);
			
		} else {
			skill("c1006_s1-2");
			ARSystem.playSound((Entity)player, "c1006s1m"+AMath.random(3));
			if(tg != null) {
				player.teleport(ULocal.lookAt(player.getLocation().clone(), tg.getLocation()));
			}
			player.setVelocity(player.getLocation().getDirection().multiply(1.6f));
			delay(()->{
				player.setVelocity(new Vector(0,0.2,0));
			},6);
			if(BlockUtil.isAirbone(player.getLocation(), 1)) delay(()->{ARSystem.giveBuff(player, new Airborne(player), 12);},8);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(pt > 80) {
			cooldown[2] = 0;
			return false;
		}
		pt = 0;
		if(s2 > 0 || s2c <= 0) {
			cooldown[2] = 0;
			return false;
		}
		s2c--;
		player.setVelocity(new Vector(0,0.1,0));
		player.teleport(player);
		player.setFallDistance(0);
		ARSystem.playSound((Entity)player, "c1006s2"+AMath.random(4));
		if(BlockUtil.isAirbone(player.getLocation(), 2)) {
			player.teleport(player.getLocation().clone().add(0,-1,0));
		}
		skill("c"+number+"_s2");
		s2 = 40;
		s2n = false;
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1006_s3");
		ARSystem.playSound((Entity)player, "c1006s3");
		if(s3 <= 0) s3 = -1;
		return true;
	}
	int s3 = 0;
	
	@Override
	public boolean tick() {
		if(s2 > 0) {
			s2--;
			player.setFallDistance(0);
			if(s2 > 0 && player.isSneaking()) {
				player.setSneaking(false);
				s2 = 0;
				s2n = true;
				skill("c1006_p");
			}
		}
		if(s2c < 4) {
			s2t++;
			if(pt > 80) s2t+=3;
			if(s2t >= s2ct) {
				s2t = 0;
				s2c++;
			}
		}
		if(s3 > 0) {
			s3--;
			if(s3 == 0) {
				skillmult-=2;
				s2ct = 200;
			}
		}
		if(pt <= 80) {
			if(!player.isOnGround()) pt = 0;
			pt++;
			if(pt > 80) {
				ARSystem.potion(player, 1, 100000, 4);
				ARSystem.potion(player, 14, 100000, 2);
				skill("c1006_p1");
				skill("c1006_pj");
			}
		} else {
			if(pt > 80 && player.isSneaking()) {
				player.removePotionEffect(PotionEffectType.SPEED);
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				player.setVelocity(new Vector(0,1.4f,0));
				skill("c1006_pe");
				skill("c1006_p2");
				skill("c1006_pb");
				ARSystem.playSound((Entity)player, "c1006p"+AMath.random(3));
				for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(3,player);
					en.teleport(en.getLocation().clone().add(0,1.5f,0));
					en.setVelocity(new Vector(0,1.45f,0));
					ARSystem.giveBuff(en, new Silence(en), 40);
				}
				pt = 0;
			}
		}
		if(tgt > 0) {
			tgt--;
			if(tgt <= 0) {
				tg = null;
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c1006:sk2")+ "] : " + s2c +" / 4");
		}
		
		if(count > 0 && !BlockUtil.isAirbone(player.getLocation(), 1)) {
			count = 0;
		}
		if(sts > 0) sts--;
		return true;
	}
	
	int sts = 0;
	@Override
	public boolean firsttick() {
		if(inGame) {
			if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
					if(buff.getTime() > 100 && sts <= 0) {
						sts = 200;
						delay(()->{ARSystem.playSound((Entity)player, "c1006stun"+AMath.random(2));},20);
					}
				}
			}
		}
		return super.firsttick();
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			s2 = 0;
			player.setFallDistance(0);
			target.setNoDamageTicks(0);
			target.damage(2,player);
			cooldown[1] = 0;
			Rule.buffmanager.selectBuffTime(target, "stun", 0);
			target.setVelocity(new Vector(0,1.1f,0));
			if(s2n) {
				ARSystem.giveBuff(target, new Silence(target), 40);
				ARSystem.giveBuff(target, new Stun(target), 30);
				ARSystem.giveBuff(player, new Airborne(player), 10);
				target.teleport(target.getLocation().clone().add(0,1.5,0));
			}
			player.setVelocity(new Vector(0,1.1f,0));
		}
		if(n.equals("2")) {
			if(s3 == -1) {
				s3 = 100;
				skillmult+=2;
				s2ct = 10;
			}
			if(BlockUtil.isAirbone(target.getLocation(), 2)) {
				ARSystem.giveBuff(target, new Silence(target), 80);
				target.teleport(target.getLocation().clone().add(0,3,0));
			} else {
				target.setVelocity(new Vector(0,1.75f,0));
			}
			if(pt > 80) {
				player.removePotionEffect(PotionEffectType.SPEED);
				player.removePotionEffect(PotionEffectType.INVISIBILITY);
				skill("c1006_pe");
			}
			target.setNoDamageTicks(0);
			target.damage(5,player);
		}
		if(n.equals("3")) {
			target.setVelocity(new Vector(0,0.5f,0));
			target.setNoDamageTicks(0);
			target.damage(0.6f,player);
		}
		if(n.equals("4")) {
			ARSystem.giveBuff(target, new Airborne(target), 10);
			target.setNoDamageTicks(0);
			target.damage(2,player);
		}
		if(n.equals("5")) {
			int fall = 0;
			Location loc = target.getLocation().clone();
			for(int i = 0; i< 50; i++) {
				loc = loc.add(0,-1,0);
				if(loc.getY() > 1 &&BlockUtil.isPathable(loc.getBlock()) || Map.loc_l.getY()-1 < loc.getY()) {
					fall++;
				} else {
					break;
				}
			}
			target.teleport(loc);
			target.setNoDamageTicks(0);
			target.damage(2+(fall*1.2),player);
		}
		if(n.equals("6")) {
			if(BlockUtil.isAirbone(target.getLocation(), 1)) {
				ARSystem.giveBuff(target, new Airborne(target), 10);
			} else {
				target.setVelocity(new Vector(0,0.55f,0));
			}
			if(pt > 80) {
				float size = 1;
				if(target.getHealth() - size > 1) {
					target.setHealth(target.getHealth() -size);
					s_damage+=size;
				} else {
					Skill.remove(target, player);
				}
				ARSystem.spellCast(player,target, "bload");
				delay(()->{pt=200;},0);
			} else {
				target.setNoDamageTicks(0);
				target.damage(1,player);
			}
			if(Rule.buffmanager.GetBuffValue(player, "barrier") < player.getMaxHealth()) {
				Rule.buffmanager.selectBuffAddValue(player, "barrier", 2);
			}
			
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 1 && pt > 80) {
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(isps) {
				e.setDamage(e.getDamage()*2);
			}
			pt = 0;
			tg = e.getEntity();
			tgt = 40;
			if(BlockUtil.isAirbone(player.getLocation(), 1)) {
				count++;
				if(count >= 11 && !isps) {
					spskillon();
					spskillen();
					ARSystem.playSoundAll("c1006sp");
					skill("c1006_sp");
					skill("c1006_sp");
				}
			} else {
				count = 0;
			}
		} else {
			if(pt > 80) {
				e.setDamage(e.getDamage()*0.4f);
			}
			if(e.getDamage() > 10 && player.getHealth() - e.getDamage() >= 1) {
				ARSystem.playSound((Entity)player,"c1006dmg");
			}
		}
		return true;
	}
	
	@Override
	public String getBgm() {
		return "c1006";
	}
}
