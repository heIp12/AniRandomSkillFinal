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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
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
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
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
import util.Text;

public class c1123rin extends c00main{
	boolean orin = false;
	
	public c1123rin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1123;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c1123s1");
		skill("c1123_s1-1");
		skill("c1123_s1-2");
		skill("c1123_s1-3");
		return true;
	}
	
	int s2 = 0;
	Location s2l;
	@Override
	public boolean skill2() {
		if(!player.isOnGround()) {
			cooldown[2] = 0;
			return false;
		}
		if(orin) {
			ARSystem.playSound((Entity)player, "c1123s22");
			player.setVelocity(player.getLocation().getDirection().multiply(3f));
			cooldown[2]*=0.5f;
		} else {
			ARSystem.playSound((Entity)player, "c1123s21");
			ARSystem.giveBuff(player, new Stun(player), 20);
			s2l = player.getLocation();
			s2l.setPitch(0);
			skill("c1123_s2");
			delay(()->{
				s2 = 40;
			},20);
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		orin = !orin;
		for(Buff b : Rule.buffmanager.getBuffs(player).getBuff()) b.stop();
		skill("c1123_s3");
		if(!orin) {
			ARSystem.playSound((Entity)player, "c123p3");
			skill("removemyall");
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.removePotionEffect(PotionEffectType.JUMP);
			player.setWalkSpeed(0.2f);
			player.setMaxHealth(player.getMaxHealth()*4);
			player.setHealth(player.getHealth()*4);
		} else {
			ARSystem.playSound((Entity)player, "minecraft:entity.cat.ambient");
			skill("orin");
			ARSystem.potion(player, 14, 100000, 1);
			ARSystem.potion(player, 8, 100000, 6);
			player.setWalkSpeed(0.4f);
			player.setHealth(player.getHealth()*0.25);
			player.setMaxHealth(player.getMaxHealth()*0.25);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.heal(player, 2f);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(0.5,player);
			ARSystem.giveBuff(target, new Stun(target), 4);
		}
	}

	@Override
	public boolean tick() {
		if(s2 > 0) {
			s2--;
			List<Entity> en = ARSystem.box(player, new Vector(3,3,3), box.TARGET);
			if(en.size() > 0) {
				ARSystem.giveBuff(player, new Stun(player), 3);
				skill("c1123_s2e");
			} else {
				player.setVelocity(s2l.getDirection());
			}
		}
		if(orin) {
			player.setFallDistance(0);
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player) {
			ARSystem.playSound((Entity)p, "c123s1");
			ARSystem.spellLocCast(player, p.getLocation(), "c1123_p");
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(orin && AMath.random(100) <= 75) {
				ARSystem.playSound((Entity)player, "0miss");
				e.setCancelled(true);
				e.setDamage(0);
				return false;
			}
		}
		return true;
	}
}
