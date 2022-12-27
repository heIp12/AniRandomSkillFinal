package c2;

import java.util.ArrayList;
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
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import c.c000humen;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c91satoru extends c00main{
	int sk1 = 0;
	int sk2 = 0;
	int sk4 = 0;
	int count = 0;
	int cc = 0;
	int sptime = 0;
	
	List<LivingEntity> entitys;

	public c91satoru(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 91;
		load();
		text();
		c = this;
		if(ARSystem.gameMode == modes.LOBOTOMY) {
			setcooldown[1] *= 0.3;
			setcooldown[2] *= 0.3;
			setcooldown[3] *= 5;
		}
	}
	

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c91s1");
		count++;
		sk1 = 5;
		if(sptime > 0) sk1 = 10;
		skill("c91_s1");
		for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TARGET)) {
			Location loc = e.getLocation();
			loc = Local.lookAt(loc, player.getLocation());
			e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(e.getLocation().distance(player.getLocation())/2.5)));
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c91s2");
		count++;
		sk2 = 5;
		if(sptime > 0) sk2 = 10;
		skill("c91_s2");
		for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TARGET)) {
			Location loc = e.getLocation();
			loc = Local.lookAt(loc, player.getLocation());
			e.setVelocity(e.getVelocity().add(loc.getDirection().multiply((e.getLocation().distance(player.getLocation())-10)/2)));
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(18 - e.getLocation().distance(player.getLocation())*2,player);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(sk1 > 0 && sk2 > 0) {
			skill("c91_s3e");
			count++;
			ARSystem.playSoundAll("c91s3");
			ARSystem.giveBuff(player, new TimeStop(player), 60);
			delay(()->{
				skill("c91_s3");
			},60);
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		sk4 = 20;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new NoHeal(target), 400);
			for(int i = 0; i<3;i++) {
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},3*i);
			}
		}
		if(n.equals("2")) {
			ARSystem.giveBuff(target, new Stun(target), 600);
			ARSystem.giveBuff(target, new Silence(target), 600);
			ARSystem.potion(target, 9, 600, 10);
			ARSystem.potion(target, 15, 600, 10);
			entitys.add(target);
		}
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0&&psopen) {
			scoreBoardText.add("&c ["+Main.GetText("c91:sk0")+ "]&f : " +count +" / 8");
		}
		if(sk1 <= 0 && sk2 <= 0) {
			for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TARGET)) {
				if(e.getLocation().distance(player.getLocation()) < 5) {
					e.setVelocity(e.getVelocity().multiply(0.1));
					if(e.getLocation().distance(player.getLocation()) < 2) {
						e.setVelocity(e.getVelocity().multiply(0.1));
						if(Rule.buffmanager.GetBuffTime((LivingEntity) e, "silence") <= 20) {
							ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 10);
						}
						e.setVelocity(new Vector(0,-10,0));
					}
					e.setVelocity(e.getVelocity().clone().add(new Vector(0,-0.1,0)));
				}
				e.setVelocity(e.getVelocity().clone().add(new Vector(0,-0.05,0)));
			}
		}
		if(tk%5 == 0 && entitys != null && entitys.size() > 0) {
			List<LivingEntity> targets = entitys;
			for(LivingEntity target : targets) {
				String s = "c91";
				if(Rule.c.get(target) != null) {
					if(AMath.random(2) == 1 && Rule.c.get(target) != null) s = "c"+Rule.c.get(target).getCode();
					if(AMath.random(3) == 1) {
						ARSystem.playSound((Player)target, s+"sp");
					} else if(AMath.random(3) == 1){
						ARSystem.playSound((Player)target, s+"select");
					} else if(AMath.random(3) == 1){
						ARSystem.playSound((Player)target, s+"db");
					}
				}
				if(!Rule.buffmanager.isBuff(target, "stun")) {
					entitys.remove(target);
				}
			}
		}
		if(sptime > 0) sptime--;
		if(sk1 > 0) sk1--;
		if(sk2 > 0) sk2--;
		if(sk4 > 0) sk4--;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*0.7);
			if(sk4 > 0) {
				e.setDamage(e.getDamage()* 0.67);
				for(int i=0;i<10;i++) cooldown[i] -= e.getDamage();
				if(count >= 8 && sk4 >= 15 && skillCooldown(0)) {
					sk4 = 0;
					cc++;
					if(cc >= 2) Rule.playerinfo.get(player).tropy(91,1);
					sptime = 100;
					cooldown[1] = cooldown[2] = cooldown[3] = cooldown[4] = 0;
					spskillon();
					spskillen();
					ARSystem.playSound((Entity)player, "c91sp");
					player.teleport(Local.lookAt(player.getLocation(), e.getDamager().getLocation()));
					ARSystem.giveBuff(player, new TimeStop(player), 80);
					entitys = new ArrayList<>();
					Location loc = e.getDamager().getLocation();
					delay(()->{
						ARSystem.spellLocCast(player, loc, "c91_room");
					},10);
					delay(()->{
						ARSystem.spellLocCast(player, loc, "c91_sp");
					},20);
					e.setCancelled(true);
					e.setDamage(0);
					return false;
				}
				if(player.getHealth() - e.getDamage() <= 1) {
					player.setHealth(1);
					e.setCancelled(true);
					e.setDamage(0);
					return false;
				}
			}
		}
		return true;
	}
}
