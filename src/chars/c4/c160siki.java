package chars.c4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.events.SpellTargetEvent;

import Main.Main;
import aliveblock.ABlock;
import ars.ARSystem;
import ars.Rule;
import buff.Boom;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import buffs.BuffBase;
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
import io.lumine.xikage.mythicmobs.util.BlockUtil;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c160siki extends c00main{
	int maxp = 100;
	int p = 100;
	int s1 = 0;
	int s11 = 0;
	int s3 = 1;
	int s3t = 0;
	int s4up = 0;
	boolean s4 = false;
	LivingEntity s2target;
	LivingEntity s4target;
	int yaw = 0;
	int power = 0;
	
	@Override
	public void setStack(float f) {
		maxp = p = 100+((int)f*20);
	}
	
	public c160siki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 160;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		s1++;
		s11 = 0;
		if(s1 < 3) {
			ARSystem.playSound((Entity)player, "c160s1");
			skill("c160_s1");
			player.setVelocity(player.getLocation().getDirection().multiply(0.4f));
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(3,3,3), box.TARGET)){
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(2,player);
				}
			},2);
		} else {
			ARSystem.playSound((Entity)player, "c160s1",1.05f);
			skill("c160_s1-2");
			player.setVelocity(player.getLocation().getDirection().multiply(0.75f));
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(3,3,3), box.TARGET)){
					LivingEntity en = (LivingEntity)e;
					en.setNoDamageTicks(0);
					en.damage(3,player);
				}
			},2);
			s1 = 0;
			cooldown[1] = 8;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c160s2");
		s2target = null;
		skill("c160_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(player.isSneaking()) {
			s3++;
			if(s3 > 4) s3 = 1;
			player.sendTitle("§c§l[§e"+Text.get("c160:t"+s3)+"§c§l]", Text.get("c160:t"+s3+"_lore"),0,40,0);
			cooldown[3] = 0;
		} else {
			if(s3 == 1) {cooldown[3] = 7; s3t = 40;}
			if(s3 == 2) {cooldown[3] = 6; s3t = 20;}
			if(s3 == 3) {cooldown[3] = 5; s3t = 10;}
			if(s3 == 4) {cooldown[3] = 1; s3t = 6;}
		}
		return true;
	}

	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "silence") && cooldown[3] <= 0) {
			Rule.buffmanager.selectBuffTime(player, "silence",0);
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		s4 = !s4;
		if(s4) {
			ARSystem.playSound((Entity)player, "c160s4");
			ARSystem.playSound((Entity)player, "0barray",1.2f);
			skill("c160_s4");
		} else {
			cooldown[4] = 5;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			s2target = target;
		}
	}

	void passive(LivingEntity target) {
		Location local = ULocal.lookAt(ULocal.offset(target.getLocation(), new Vector(-1,1,0)), target.getLocation());
		ARSystem.spellLocCast(player, local, "c160_s2-eft");
		ARSystem.playSound((Entity)player, "c160s22");
		player.teleport(local);
		ARSystem.spellCast(player, target, "c160_s2-eft2");
		ARSystem.giveBuff(target, new Stun(target), 20);
		target.setNoDamageTicks(0);
		target.damage(5,player);
		if(power > 0) power +=1;
		if(power > 5) power = 5;
	}
	
	@Override
	public void LocmakerSkill(Location loc, String name) {
		if(name.equals("air")) {
			if(s2target != null && !Rule.buffmanager.isBuff(player, "timestop")) passive(s2target);
		}
		if(name.equals("drop")) {
			s1 = 0;
			cooldown[1] = 0;
			cooldown[2] = 0;
		}
	}
	
	@Override
	public boolean tick() {
		if(!isBattle()) {
			if(tk%4==0 && p < maxp) p++;
		} else {
			if(tk%10==0) p--;
		}
		
		if(p <= 0) {
			ARSystem.giveBuff(player, new Panic(player), 10);
			if(AMath.random(8) <= 1) {
				int i = AMath.random(4);
				if(cooldown[i] <= 0) {
					cooldown[i] = setcooldown[i];
					if(i == 1) skill1();
					else if(i == 2) skill2();
					else if(i == 3) skill3();
					else if(i == 4) skill4();
				}
			}
		}
		
		if(s3t > 0) {
			s3t--;
			if(s3 == 4 && s3t <= 0) {
				cooldown[3] = 30;
			}
		}
		
		if(s4up > 0) s4up--;
		
		
		s11++;
		if(s11 == 100) {
			s1 = 0;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c160:ps")+ "] : "+ p +" / " + maxp);
			if(s1 > 0 && s11 < 100)scoreBoardText.add("&c ["+Main.GetText("c160:sk1")+ "] : "+ AMath.round((100-s11)*0.05,2));
			if(s4up > 0)scoreBoardText.add("&c ["+Main.GetText("c160:t4")+ "] : "+ AMath.round(s4up*0.05,2));
		}
		
		//직사의마안
		if(AMath.random(100) <= 5) {
			yaw += 5;
		} else if(AMath.random(100) <= 5) {
			yaw -= 5;
		}
		power = 0;
		
		if(s4) {
			if(tk%2==0) p--;
			if(s4up > 0) power +=1;
		
			Entity e = ARSystem.boxSOne(player, new Vector(8,8,8), box.TARGET);
			if(e != null) s4target = (LivingEntity)e;
			if(s4target == null) return false;
			
			double myyaw = ULocal.lookAt(player.getLocation().clone(), s4target.getLocation()).getYaw();
			double lyaw = Math.abs(angle(myyaw,yaw));
			
			float power2 = 1;
			power2 = Math.max(1,maxp*0.06f);
			if(lyaw < 0.5 * power2) power+=1;
			if(lyaw < 3 * power2) power+=1;
			if(lyaw < 10 * power2) power+=1;
			if(lyaw < 35 * power2) power+=1;
			if(lyaw < 100 * power2) power+=1;
			if(power > 5) power = 0;
			if(power > 0) {
				String s = "§m----------------------------";
				if(power >= 5) s = s.replace("-", "≡");
				else if(power >= 3) s = s.replace("-", "=");
				if(power == 1) s= "§e"+s;
				if(power == 2) s= "§6"+s;
				if(power == 3) s= "§c"+s;
				if(power == 4) s= "§4"+s;
				if(power == 5) s= "§4§l"+s;
				player.sendTitle(s, "§c§l》§e"+s4target.getName()+"§c§l《",0,3,0);
			}
			ARSystem.spellLocCast(player, player.getLocation(), "c160_s4-eft");
		}
		return true;
	}
	
    double angle(double angle1, double angle2) {
        angle1 = angle1 % 360;
        angle2 = angle2 % 360;

        double difference = angle2 - angle1;

        difference = (difference + 180) % 360 - 180;

        return difference;
    }
    
    @Override
    public void PlayerDeath(Player player, Entity e) {
		if(e == this.player) {
			ARSystem.heal(this.player, (maxp-p)/8);
			maxp+=20;
			p = maxp;
			if(s_kill >= 3) Rule.playerinfo.get(this.player).tropy(160,1);
		}
    }
    
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			s4target = (LivingEntity)e.getEntity();
			if(p <= 0) {
				e.setDamage(e.getDamage()*0.7);
			}
			if(power >= 5 && skillCooldown(0)) {
				spskillon();
				spskillen();
				ARSystem.playSoundAll("c160sp");
				LivingEntity target = s4target;
				ARSystem.giveBuff(target, new TimeStop(target), 300);
				ARSystem.giveBuff(player, new TimeStop(player), 300);
				Location loc2 = ULocal.lookAt(target.getLocation().clone(), player.getLocation());
				loc2.setPitch(0);
				target.teleport(loc2);
				player.teleport(ULocal.lookAt(ULocal.offset(loc2, new Vector(8,0,0)), target.getLocation()));
				
				skill("c160_spp");
				delay(()->{
					ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation().clone(), target.getLocation()).add(0,-0.7,0), "c160_sp");
					for(int i = 0; i<50; i++) {
						delay(()->{skill("c160_spspawn");},i);
					}
				},40);
				delay(()->{
					ARSystem.spellCast(player, target, "c160_sp_la");
				},60);
				delay(()->{
					ARSystem.spellLocCast(player, ULocal.lookAt(ULocal.offset(loc2, new Vector(-2,0,0)), target.getLocation()), "c160_move");
				},70);
				
				delay(()->{
					player.teleport(ULocal.lookAt(ULocal.offset(loc2, new Vector(-2,0,0)), target.getLocation()));
					delay(()->{
						skill("c160_sp_l");
						ARSystem.spellCast(player, target, "c160_sp_la");
					},20);
					delay(()->{
						skill("c160_sp_l2");
						ARSystem.spellCast(player, target, "c160_sp_la");
					},30);
				},100);
				
				delay(()->{
					ARSystem.giveBuff(player, new Nodamage(player), 100);
					ARSystem.giveBuff(player, new TimeStop(player), 0);
					delay(()->{
						player.setVelocity(ULocal.lookAt(player.getLocation().clone(), target.getLocation()).getDirection().multiply(-1).setY(0.5));
					},2);
				},200);
				
				delay(()->{
					for(int i =0; i<5; i++) ARSystem.spellLocCast(player, target.getLocation(), "bload");
					Skill.remove(target, player);
					skill("removemyall");
				},210);
			} else if(power >= 1 && s2target != null) {
				double damage = e.getDamage();
				e.setDamage(e.getDamage() + (s4target.getMaxHealth()*0.01*damage));
				if(power >= 2) e.setDamage(e.getDamage() + (s4target.getHealth()*0.05 * damage));
				if(power >= 3) ARSystem.addBuff(s2target, new Silence(s2target), 6);
				if(power >= 4) {
					ARSystem.fixedDamage(s2target, player, e.getDamage()*0.5f);
					ARSystem.spellCast(player, s2target, "bload");
				}
				
			}
		} else {
			if(s3t > 0) {
				s3t = 0;
				if(s3 == 1) {
					ARSystem.giveBuff(player, new Nodamage(player), 40);
				} else if(s3 == 2 && e.getDamage() >= 2) {
					passive((LivingEntity)e.getDamager());
					ARSystem.giveBuff(player, new Nodamage(player), 2);
				} else if(s3 == 3 && e.getDamage() >= 4) {
					s4up = 1200;
					ARSystem.giveBuff(player, new Nodamage(player), 2);
				} else if(s3 == 4) {
					LivingEntity tg = (LivingEntity)e.getDamager();
					ARSystem.giveBuff(tg, new Noattack(tg), 20);
					ARSystem.giveBuff(tg, new Silence(tg), 20);
					ARSystem.giveBuff(tg, new Stun(tg), 20);
					ARSystem.giveBuff(player, new Nodamage(player), 2);
				}
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}

}