package chars.c3;

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
import org.bukkit.event.entity.EntityDamageEvent;
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

public class c104rica extends c00main{
	int pd = 0;
	int ps = 0;
	boolean sp = false;
	int count = 0;
	
	boolean sk1 = false;
	
	public c104rica(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 104;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		player.setVelocity(new Vector(0,1.2,0));
		ARSystem.playSound((Entity)player, "c104s1");
		delay(()->{
			player.setVelocity(player.getLocation().getDirection().multiply(3));
			skill("c104_s1_e");
			player.setFallDistance(0);
			sk1 = true;
		},10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c104_s2");
		ARSystem.playSound((Entity)player, "c104s2");
		for(int i=0;i<4;i++) {
			delay(()->{
				for(Entity e : ARSystem.box(player, new Vector(6,6,6), box.TARGET)) {
					cooldown[3] -= 1;
					LivingEntity en =((LivingEntity)e);
					en.setNoDamageTicks(0);
					en.damage(2,player);
				}
			},i*10);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		player.setVelocity(player.getLocation().getDirection().multiply(2).setY(0.2));
		skill("c104_s3");
		ARSystem.playSound((Entity)player, "c104s3");
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			player.setVelocity(new Vector(0,0,0));
			ps = 0;
			target.setNoDamageTicks(0);
			target.damage(5,player);
		}
	}
	

	@Override
	public boolean tick() {
		if(count == 0) count = Rule.c.size();
		if(pd > 0) pd--;
		if(ps > 0) ps--;
		if(tk%20 == 0 && ps <= 0) {
			scoreBoardText.add("&c ["+Main.GetText("c104:ps")+ "]");
		}
		if(sk1 && player.isOnGround()) {
			sk1 = false;
			skill("c104_s1_boom");
			for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
				LivingEntity en =((LivingEntity)e);
				en.setNoDamageTicks(0);
				en.damage(5,player);
				ARSystem.giveBuff(en, new Stun(en), 20);
				ARSystem.giveBuff(en, new Silence(en), 40);
			}
		}
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() == 50 && !isps && Rule.c.size() == (int)(count/2)) {
			spskillon();
			spskillen();
			Rule.playerinfo.get(player).tropy(104, 1);
			ARSystem.addBuff(player, new TimeStop(player), 130);
			ARSystem.playSoundAll("c104sp1");
			player.performCommand("tm anitext all SUBTITLE true 20 c104:p1/"+player.getName());
			delay(()->{
				player.performCommand("tm anitext all SUBTITLE true 20 c104:p2/"+player.getName());
			},20);
			delay(()->{
				player.performCommand("tm anitext all SUBTITLE true 20 c104:p3/"+player.getName());
			},60);
			delay(()->{
				player.performCommand("tm anitext all SUBTITLE true 20 c104:p4/"+player.getName());
			},90);
			delay(()->{
				sp = true;
				for(Player p : Rule.c.keySet()) {
					if(p != player) {
						p.setHealth(p.getMaxHealth() * (AMath.random(70)*0.01 + 0.1));
					}
					p.teleport(Map.randomLoc());
				}
			},130);
		}
		if(sp && tk%2 == 0) {
			for(Player p : Rule.c.keySet()) {
				ARSystem.spellCast(player, p, "c104_spe");
			}
		}
		if(sp && tk%20 == 0) {
			for(Player p : Rule.c.keySet()) {
				if(p!= player) {
					if(p.getHealth() -0.4 <= 1) {
						p.setNoDamageTicks(0);
						p.damage(1,player);
						p.setNoDamageTicks(0);
						p.damage(10,player);
					} else {
						p.setHealth(p.getHealth() -0.4);
						s_damage += 0.4;
					}
				}
			}
			ARSystem.heal(player, 1);
		}
		return true;
	}
	
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getEntity() == player) {
			if(pd <= 0) {
				pd = 5;
				ARSystem.playSound((Entity)player, "c104p");
			}
		}
		return super.damage(e);
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 1 && ps <= 0) {
				ps = 100;
				skill("c104_p");
				delay(()->{
					LivingEntity en = (LivingEntity) e.getEntity();
					en.setNoDamageTicks(0);
					en.damage(2,player);
					Location loc = en.getLocation();
					loc = ULocal.lookAt(loc, player.getLocation());
					en.setVelocity(en.getVelocity().add(loc.getDirection().multiply(-3)).setY(0.2));
					delay(()->{
						en.setNoDamageTicks(0);
						en.damage(4,player);
					},10);
					if(isps) cooldown[1] = 0;
				},0);
			}
		} else {
			
		}
		return true;
	}
	
}
