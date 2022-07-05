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

public class c71diabolo extends c00main{
	boolean ps = false;
	int sk2 = 0;
	int sk3 = 0;
	int sk3c = 0;
	HashMap<LivingEntity,Integer> attack;
	
	public c71diabolo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 71;
		load();
		text();
		c = this;
		skill("c71_p");
	}

	@Override
	public boolean skill1() {
		skill("c71_s1");
		ARSystem.playSound((Entity)player, "c71s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		sk2 = 60;
		ARSystem.giveBuff(player, new Nodamage(player), 60);
		ARSystem.playSound((Entity)player, "c71s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		sk3 = 80;
		sk3c = 0;
		attack = new HashMap<>();
		return true;
	}
	
	@Override
	public boolean skill4() {
		skill("c71_s4");
		ARSystem.playSound((Entity)player, "c71s4");
		return true;
	}
	
	@Override
	public void TargetSpell(SpellTargetEvent e, boolean mycaster) {
		if(mycaster && e.getSpell().getName().equals("c71_s12")) {
			if(sk3c >= 5 && skillCooldown(0)) {
				spskillon();
				spskillen();
				sk3c = 0;
				skill("c71_p2");
				ARSystem.spellCast(player, e.getTarget(), "look2");
				ARSystem.playSound((Entity)e.getTarget(), "c71sp1");
				ARSystem.giveBuff(e.getTarget(), new TimeStop(e.getTarget()), 180);
				ARSystem.giveBuff(player, new TimeStop(player), 200);
				delay(()->{
					ARSystem.spellCast(player, e.getTarget(), "c71_sp1");
				},60);
				delay(()->{
					ARSystem.potion(player, 14, 120, 1);
				},80);
				delay(()->{
					skill("c71_p2");
					ARSystem.playSound((Entity)e.getTarget(), "c71sp2");
					ARSystem.spellLocCast(player, e.getTarget().getLocation().clone().add(0,0.7,0), "c71_sp3");
					ARSystem.spellLocCast(player, e.getTarget().getLocation().clone().add(0,0.7,0), "c71_sp2");
				},120);
				delay(()->{
					ARSystem.playSound((Entity)e.getTarget(), "c71sp3");
					if(e.getTarget().getMaxHealth() > 26) {
						Skill.remove(e.getTarget(), player);
					} else {
						Rule.buffmanager.selectBuffTime(e.getTarget(), "timestop",0);
						e.getTarget().setNoDamageTicks(0);
						e.getTarget().damage(333,player);
						delay(()->{
							e.getTarget().setNoDamageTicks(0);
							e.getTarget().damage(333,player);
						},10);
						delay(()->{
							e.getTarget().setNoDamageTicks(0);
							e.getTarget().damage(333,player);
						},30);
					}
					ARSystem.playSound((Entity)e.getTarget(), "0bload");
					skill("c71_p");
				},180);
			} else {
				ARSystem.giveBuff(e.getTarget(), new Stun(e.getTarget()), 20);
			}
		}
	}
	
	@Override
	public boolean tick() {
		
		if(sk2>0) {
			sk2--;
			for(Entity e : ARSystem.box(player, new Vector(8,8,8), box.TARGET)) {
				ARSystem.potion((LivingEntity) e, 2, 10, 2);
				ARSystem.potion((LivingEntity) e, 15, 20, 2);
			}
			if(sk2==0) {
				Rule.buffmanager.selectBuffTime(player, "noattack",0);
			}
		}
		if(sk3>0) {
			sk3--;
			if(sk3 == 0) {
				for(LivingEntity e : attack.keySet()) {
					ARSystem.giveBuff(e, new Timeshock(e), 20*attack.get(e));
				}
			}
		}
		if(tk%20 == 0) {
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c71:sk0")+ "] : "+ sk3c);
			if(sk2>0) scoreBoardText.add("&c ["+Main.GetText("c71:sk2")+ "] : "+ AMath.round(sk2/20,1));
			if(sk3>0) scoreBoardText.add("&c ["+Main.GetText("c71:sk3")+ "] : "+ AMath.round(sk3/20,1));
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) {
			Rule.playerinfo.get(player).tropy(71,1);
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			sk2 = 0;
			double damage = (1-player.getHealth()/player.getMaxHealth())*2;
			e.setDamage(e.getDamage() + e.getDamage()*damage);
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*3);
		} else {
			if(sk3 > 0) {
				if(attack.get(e.getDamager()) == null) {
					attack.put((LivingEntity) e.getDamager(), 0);
				}
				ARSystem.playSound((Entity)player, "c71s3");
				attack.put((LivingEntity) e.getDamager(), attack.get(e.getDamager())+1);
				e.setDamage(0);
				ARSystem.spellCast(player, e.getDamager(), "c71_s3");
				sk3c++;
			}
		}
		return true;
	}
	
}
