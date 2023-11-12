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
import types.TargetMap;
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c7200plan extends c00main{	
	int sk2 = 0;
	boolean sk1 = false;
	TargetMap<LivingEntity, Double> target = new TargetMap<>();
	
	public c7200plan(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1072;
		load();
		text();
		c = this;
	}


	@Override
	public boolean skill1() {
		if(!isps) {
			sk1 = true;
			ARSystem.playSound((Entity)player, "c1072s1");
			player.setVelocity(player.getLocation().getDirection().multiply(1.2));
		} else {
			if(player.isSneaking()) {
				skill("c1072_s1-2");
				cooldown[1] = 5;
				ARSystem.playSound((Entity)player, "c1072s3");
				ARSystem.giveBuff(player, new Stun(player), 10);
			} else {
				skill("c1072_s1-1");
				ARSystem.playSound((Entity)player, "c72s1"+AMath.random(1,2));
				player.setVelocity(player.getLocation().getDirection().multiply(1.2));
			}
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c1072s2");
		target.clear();
		sk2 = 40;
		return true;
	}

	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1072s4");
		ARSystem.giveBuff(player, new Nodamage(player), 20);
		ARSystem.potion(player, 14, 20, 1);
		for(int i = 0;i<4;i++) {
			Location loc = player.getLocation().add(0,1,0);
			loc.setYaw(i*90);
			loc.setPitch(0);
			ARSystem.spellLocCast(player, loc, "c1072_s3");
		}
		if(isps) {
			delay(()->{
				for(int i = 0;i<4;i++) {
					Location loc = player.getLocation().add(0,1,0);
					loc.setYaw(45+i*90);
					loc.setPitch(0);
					ARSystem.spellLocCast(player, loc, "c1072_s3");
				}
			},5);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			ARSystem.addBuff(target, new Stun(target), 20);
			ARSystem.addBuff(target, new Silence(target), 20);
			target.damage(3,player);
		}
	}
	
	@Override
	public boolean tick() {
		if(sk2 > 0) {
			sk2--;
			for(LivingEntity t : target.get().keySet()) {
				t.setNoDamageTicks(0);
				t.damage(target.get().get(t)*2,player);
			}
		}
		if(!isps && Rule.buffmanager.GetBuffValue(player, "plushp") > 30) {
			spskillon();
			spskillen();
			setcooldown[1] = 1;
			ARSystem.playSound(player, "c72select");
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk1 && e.getDamage() < 2) {
				sk1 = false;
				e.setDamage(e.getDamage() + 5);
				ARSystem.playSound((Entity)player, "c72s1"+AMath.random(1,2));
				skill("c1072_s1");
			}
			ARSystem.overheal(player, e.getDamage() * 0.6);
		} else {
			if(sk2 > 0) {
				target.add((LivingEntity)e.getDamager(), e.getDamage());
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(Rule.buffmanager.GetBuffValue(player, "plushp") > e.getDamage()) {
				Rule.buffmanager.selectBuff(player, "plushp").addValue(-e.getDamage());
				e.setDamage(0);
			} else if(Rule.buffmanager.GetBuffValue(player, "plushp") > 0) {
				e.setDamage(e.getDamage() - Rule.buffmanager.GetBuffValue(player, "plushp"));
				Rule.buffmanager.selectBuff(player, "plushp").setValue(0);
			}
		}
		return true;
	}
	
}
