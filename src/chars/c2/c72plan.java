package chars.c2;

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

public class c72plan extends c00main{
	int p = 0;
	int sk3 = 0;
	float sp = -1;
	double pdg = 0;
	public c72plan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 72;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		LivingEntity en = (LivingEntity)ARSystem.boxSOne(player, new Vector(6, 3, 6), box.TARGET);
		if(en != null) {
			ARSystem.playSound((Entity)player, "c72s12");
			ARSystem.spellLocCast(player, ULocal.lookAt(player.getLocation(), en.getLocation()), "c72_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 8, 3, box.TARGET);
		if(entitys != null && entitys.size() > 0) {
			ARSystem.playSound((Entity)player, "c72s11");
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(en != null) {
				ARSystem.spellCast(player, en, "c72_s2-m");
				delay(()->{
					ARSystem.playSound((Entity)player, "c72s22");
					ARSystem.spellCast(player, en, "c72_s2");
					double damage = 3 + ((en.getMaxHealth() - en.getHealth())*0.2);
					en.setNoDamageTicks(0);
					en.damage(damage,player);
					if(damage >= 6) {
						ARSystem.giveBuff(en, new Stun(en), 20);
						ARSystem.giveBuff(en, new Silence(en), 20);
					}
				},20);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c72s21");
		cooldown[1] = 0;
		sk3 = 160;
		ARSystem.potion(player, 1, 80, 2);
		return true;
	}
	
	public boolean skill0() {
		spskillon();
		spskillen();
		ARSystem.playSoundAll("c72sp");
		ARSystem.giveBuff(player, new Stun(player), 160);
		ARSystem.giveBuff(player, new Silence(player), 160);
		sp = 0;
		delay(()->{
			float damage = sp;
			sp = -1;
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			ARSystem.playSoundAll("c72sp1");
			
			Player target = null;
			double range = 0;
			if(damage >= 100) range = 999999;
			for(Player p : Rule.c.keySet()) {
				if(p != player) {
					if(damage >= 100) {
						if(Rule.c.get(p).getScore() < range) {
							target = p;
							range = Rule.c.get(p).getScore();
						}
					} else {
						if(p.getLocation().distance(player.getLocation()) > range) {
							target = p;
							range = p.getLocation().distance(player.getLocation());
						}
					}
				}
			}
			ARSystem.spellCast(player, target, "c72_sp-m");
			LivingEntity tg = target;
			ARSystem.giveBuff(target, new Silence(target), 100);
			ARSystem.giveBuff(target, new Stun(target), 100);
			delay(()->{
				for(int i=0; i<10; i++) {
					delay(()->{
						skill("c72_sp0");
					},i);
				}
				for(Entity en : ARSystem.box(player, new Vector(10,10,10), box.TARGET)) {
					LivingEntity th = (LivingEntity)en;
					th.setNoDamageTicks(0);
					th.damage(damage,player);
				}
				pdg = damage - (player.getMaxHealth() - player.getHealth());
				ARSystem.heal(player, damage);
			},40);
		},160);
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			float damage = 4;
			target.setNoDamageTicks(0);
			target.damage(damage,player);
			damage += 8*Math.min(1, 1.1 - (player.getHealth() / player.getMaxHealth()));
			ARSystem.heal(player, damage);
		}
	}
	
	@Override
	public boolean tick() {
		if(sk3 > 0) sk3--;
		if(cooldown[3] > 0 && sk3 <= 0 && tk%4 == 0) {
			ARSystem.giveBuff(player, new NoHeal(player), 5);
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&f ["+Main.GetText("c72:t1")+ "] : "+ p +" / 3 ");
			if(sk3 > 0) scoreBoardText.add("&c ["+Main.GetText("c72:sk3")+ "] : "+ AMath.round(sk3*0.05, 2));
		}
		if(sp >= 0 && !player.isSneaking()) {
			if(hpCost(0.3, false)) {
				if(tk%20 == 0) ARSystem.spellCast(player, player, "bload");
				sp+= 1.5f;
			}
			player.sendTitle("§4[Damage]","§c"+ AMath.round(sp,1),0,10,20);
		}
		if(tk%10 == 0) {
			if((score-200) >= 200 && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 30 && Rule.c.size() >= 2) {
				double range = 300;
				for(Player p : Rule.c.keySet()) {
					if(p.getGameMode() != GameMode.SPECTATOR) {
						if(p.getLocation().distance(player.getLocation()) < range && p != player) {
							range = p.getLocation().distance(player.getLocation());
						}
					}
				}
				if(tk%20 == 0) {
					scoreBoardText.add("&f ["+Main.GetText("c72:sk0")+ "] : "+ range + " / 30");
				}
				if(range >= 30 && skillCooldown(0)) {
					skill0();
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(pdg > 0) {
				e.setDamage(e.getDamage() + pdg);
				pdg = 0;
			}
			if(e.getDamage() <= 1) {
				cooldown[1] -= 0.5;
				cooldown[2] -= 1;
			}
			p++;
			if(p > 3) {
				p = 0;
				LivingEntity en = (LivingEntity)e.getEntity();
				float dg = 2;
				dg += 3*Math.min(1, 1.1 - (player.getHealth() / player.getMaxHealth()));
				en.setNoDamageTicks(0);
				en.damage(dg,player);
				ARSystem.heal(player, dg);
			}
		} else {
			if(e.getDamager().getLocation().distance(player.getLocation()) > 8) {
				e.setDamage(0);
				ARSystem.playSound((Entity)player, "0miss");
			}
			if(sk3 > 0) {
				e.setDamage(e.getDamage() * 0.6f);
			}
		}
		return true;
	}
	
}
