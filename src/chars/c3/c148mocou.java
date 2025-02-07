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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
import ars.gui.G_Saito;
import buff.Airborne;
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.Ice;
import buff.NoCC;
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Nodie;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c.c39sakuya;
import chars.c.c45momo;
import chars.c2.c60gil;
import chars.c2.c69himi;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import types.BuffType;
import types.TargetMap;
import types.box;

import util.AMath;
import util.BlockUtil;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;
import util.Text;

public class c148mocou extends c00main{
	float heal = 0.1f;
	float heals = 0;
	int damage = 0;
	
	boolean debuff = false;
	
	TargetMap<LivingEntity, Double> s1 = new TargetMap<>();
	
	@Override
	public void setStack(float f) {
		heals = f;
	}
	
	public c148mocou(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 148;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		if(isps) return false;
		ARSystem.playSound((Entity)player, "c148s1");
		skill("c148_s1");
		return true;
	}

	@Override
	public boolean skill2() {
		if(isps) return false;
		ARSystem.playSound((Entity)player, "c148s2");
		skill("c148_s2");
		heal = 0.5f;
		delay(()->{
			heal = 0.1f;
		},100);
		return true;
	}
	

	@Override
	public boolean skill3() {
		if(isps) return false;
		if(!debuff) ARSystem.playSound((Entity)player, "c148s3");
		debuff = !debuff;
		if(debuff) {
			player.setWalkSpeed(0.4f);
		} else {
			player.setWalkSpeed(0.2f);
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c148:ps")+"] : "+ AMath.round(heals,2));
			for(LivingEntity en : s1.get().keySet()) {
				scoreBoardText.add("&c ["+Main.GetText("c148:t1")+"] : " + en.getName() + " <" + AMath.round(player.getLocation().distance(en.getLocation()), 1) +"m> ("+AMath.round(s1.get(en),2)+")");
			}
		}
		if(!Rule.buffmanager.isBuff(player, "noheal")) {
			if(debuff) {
				hpCost(heal, true);
				skill("c148_pd");
				for(LivingEntity en : s1.get().keySet()) {
					ARSystem.spellLocCast(player, en.getLocation(), "c148_pd");
				}
			} else {
				if(player.getHealth() < player.getMaxHealth()) {
					ARSystem.heal(player, heal);
					heals += heal;
				}
			}
			if(heals >= 120 && !isps && skillCooldown(0)) {
				spskillon();
				spskillen();
				Rule.playerinfo.get(player).tropy(148, 1);
				ARSystem.playSound(player, "c148sp");
				delayEvent.clear();
				heal = 0.5f;
				debuff = true;
				ARSystem.giveBuff(player, new TimeStop(player), 40);
				ARSystem.giveBuff(player, new Nodamage(player), 4000);
				ARSystem.giveBuff(player, new NoCC(player), 4000);
				player.setWalkSpeed(1f);
				ARSystem.potion(player, 8, 10000, 4);
				delay(()->{
					player.setMaxHealth(200);
					player.setHealth(200);
				},40);
			}
			if(isps) {
				damage++;
			}
		}
		for(LivingEntity en : s1.get().keySet()) {
			if(en.getLocation().distance(player.getLocation()) >= 12) {
				s1.set(en, 0);
			}
		}
		
		s1.addAll(-0.05f);
		s1.removes();
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(3,player);
			s1.set(target, 10);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(heal,player);
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player && heals >= 50 && !Rule.buffmanager.isBuff(player, "noheal") && ARSystem.AniRandomSkill != null && !isps && skillCooldown(0)) {
			Location lc = player.getLocation();
			tpsdelay(()->{
				player.teleport(lc);
				Rule.c.put(player , new c148mocou(player,plugin,this));
				ARSystem.potion(p, 14, 40, 1);
				ARSystem.giveBuff(p, new Stun(p), 40);
				ARSystem.giveBuff(p, new Silence(p), 40);
				ARSystem.giveBuff(p, new Nodie(p), 40);
				ARSystem.playSound((Entity)player, "c148p",1,2);
				skill("c148_p");
			},60);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				e.setDamage(e.getDamage()* 1+(damage*0.004));
			}
		} else {
			
		}
		return true;
	}
}
