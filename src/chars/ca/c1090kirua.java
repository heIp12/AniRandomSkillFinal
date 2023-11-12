package chars.ca;

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
import chars.c.c000humen;
import chars.c.c00main;
import event.Skill;
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

public class c1090kirua extends c00main{
	int sk1 = 0;
	int sk2 = 0;
	int sk3 = 0;
	
	int sp = 0;
	
	Location loc;
	
	
	public c1090kirua(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1109;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c109select");
	}

	@Override
	public boolean skill1() {
		if(sk1 < 5) {
			sk1++;
			skill("c1109_s1"+sk1);
			if(sk3 > 0) {
				cooldown[1] = 0.15f;
			} else {
				cooldown[1] = 0.5f;
			}
		} else {
			sk1++;
			skill("c1109_s1"+sk1);
			sk1 = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		sk2 = 20;
		if(sk3 <= 0) ARSystem.potion(player, 2, 20, 3);
		return true;
	}
	
	@Override
	public boolean skill3() {
		sk3 = 200;
		ARSystem.potion(player, 1, 200, 9);
		ARSystem.playSound((Entity)player, "c109s3");
		sp = 0;
		return true;
	}
	
	
	public boolean skill0() {
		spskillon();
		spskillen();
		ARSystem.giveBuff(player, new TimeStop(player),40);
		ARSystem.playSound((Entity)player, "c109sp");
		ARSystem.playSound((Entity)player, "c109s1", 0.2f);
		for(int i =0; i<20; i++) delay(()->{skill("c1109_s3");},i);
		loc = player.getLocation();
		
		for(Entity en : ARSystem.locEntity(loc, new Vector(12,4,12), player)) {
			LivingEntity e = (LivingEntity)en;
			ARSystem.giveBuff(e, new Stun(e), 20);
			ARSystem.giveBuff(e, new Silence(e), 40);
			ARSystem.potion(e, 2, 100, 3);
		}
		delay(()->{
			loc = player.getLocation();
			sk3 = 0;
			ARSystem.giveBuff(player, new Silence(player), 200);
			player.setGameMode(GameMode.SPECTATOR);
			ps = 200;
			for(int i =0; i< 44; i++) {
				delay(()->{
					List<Entity> entitys = ARSystem.locEntity(loc, new Vector(12,4,12), player);
					LivingEntity e = (LivingEntity)entitys.get(AMath.random(entitys.size())-1);
					ARSystem.giveBuff(e, new Stun(e), 1);
					ARSystem.giveBuff(e, new Silence(e), 1);
					ARSystem.spellCast(player,e, "c1109_sp");
					ARSystem.playSound(e, "0lightning", 1.8f);
					ARSystem.playSound(e, "0attack", 0.5f + (AMath.random(0, 10)*0.1f));
					e.setNoDamageTicks(0);
					e.damage(1,player);
				},2*i);
			}
			
			delay(()->{
				for(Entity en : ARSystem.locEntity(loc, new Vector(12,4,12), player)) {
					LivingEntity e = (LivingEntity)en;
					for(int i =0; i < 6; i++) ARSystem.spellCast(player,e, "c1109_sp");
					ARSystem.playSound(e, "c109s1", 1f);
					ARSystem.playSound(e, "0attack4", 0.5f ,2);
					e.setNoDamageTicks(0);
					e.damage(400,player);
				}
				player.teleport(loc);
				player.setGameMode(GameMode.ADVENTURE);
				ps = 10;
			},88);
		},40);
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			if(sk1 < 3) {
				target.damage(3,player);
			}
			else if(sk1 > 2) {
				target.damage(6,player);
			}
		}
		else if(n.equals("2")) {
			target.setNoDamageTicks(0);
			if(sk1 < 3) {
				target.damage(4,player);
			}
			else if(sk1 == 0) {
				target.damage(7,player);
			}
		}
		else if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(5,player);
		}
	}
	
	int ps = 0;
	@Override
	public boolean tick() {
		if(ps <= 0 && tk%10 == 0 && player.getGameMode() != GameMode.SPECTATOR) {
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				if(en.getHealth() <= 4 && en.getHealth() >= 1) {
					player.teleport(ULocal.lookAt(player.getLocation(), en.getLocation()));
					en.teleport(ULocal.lookAt(en.getLocation(), player.getLocation()));
					ARSystem.giveBuff(player, new Silence(player), 60);
					ARSystem.giveBuff(player, new TimeStop(player), 30);
					ARSystem.giveBuff(en, new TimeStop(en), 100);
					ps = 100;

					delay(()->{
						player.setGameMode(GameMode.SPECTATOR);
						
						ARSystem.spellCast(player, e, "c1109_p");
						Location loc = en.getLocation();
						loc.setPitch(0);
						en.teleport(loc);
						delay(()->{
							ARSystem.spellCast(player, e, "bload");
							Skill.death(en, player);
						},25);
						delay(()->{
							player.setGameMode(GameMode.ADVENTURE);
							player.teleport(loc);
						},30);
					},30);
				}
			}
		}
		if(ps > 0) ps--;
		if(sk3 > 0) {
			sk3--;
			if(ARSystem.box(player, new Vector(5, 2, 5), box.TARGET).size() > 0) {
				sp++;
				if(sp >= 100 && skillCooldown(0)) {
					skill0();
				}
			} else {
				sp = 0;
			}
		}
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c109:sk3")+ "] : &f" + AMath.round(sk3*0.05, 2));
			if(sk3 > 0) scoreBoardText.add("&c ["+Main.GetText("c109:sk0")+ "] : &f" + AMath.round(sp*0.05, 2) +" / 5");
		}

		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() * 1.44);
			if(sk3 > 0) {
				ARSystem.giveBuff((LivingEntity)e.getEntity(), new Stun((LivingEntity)e.getEntity()), 10);
				for(int i=0; i<3;i++) ARSystem.spellLocCast(player, e.getEntity().getLocation(), "c1109_s3");
				ARSystem.playSound((Entity)player, "c109s1");
			}
		} else {
			if(sk2 > 0) {
				e.setDamage(e.getDamage() * 0.5);
				sk2 = 0;
				player.teleport(e.getDamager());
				((LivingEntity)e.getDamager()).setNoDamageTicks(0);
				((LivingEntity)e.getDamager()).damage(11,player);
			}
			
			e.setDamage(e.getDamage() * 0.56);
			if(AMath.random(100) <= 44) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		return true;
	}
	
}
