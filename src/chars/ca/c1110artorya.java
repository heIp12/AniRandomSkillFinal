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
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
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

public class c1110artorya extends c00main{
	float stack = 0;
	int sk1 = 0;
	int sk3 = 0;
	boolean sk2 = false;
	boolean start = false;
	
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c1110artorya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1111;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound(player, "c1111s1");
		sk1 = 20;
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 20);
		skill("c1111_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		player.setVelocity(new Vector(0,1.2,0));
		ARSystem.playSound((Entity)player, "c1111s2");
		delay(()->{
			Vector vt = player.getLocation().getDirection().multiply(3);
			if(vt.getY() > 0) vt.setY(vt.getY()*-0.5);
			player.setVelocity(vt);
			player.setFallDistance(0);
			sk2 = true;
		},10);
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c1111s3");
		sk3 = 20;
		stack = 0;
		return true;
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(4+stack, player);
		}
	}
	
	@Override
	public boolean firsttick() {
		if(start && sk1 <= 0) {
			if(Rule.buffmanager.selectBuffType(player, BuffType.STOP) != null) {
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF)) {
					if(buff.getTime() > 0) {
						buff.setTime(0);
						ARSystem.heal(player, 5);
					}
				}
				for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.BUFF)) {
					if(buff.getTime() > 0) {
						buff.setTime(0);
						ARSystem.heal(player, 5);
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean tick() {
		if(!start) start = true;
		if(sk1 > 0) sk1--;
		if(sk3 > 0) sk3--;
		if(sk2 && player.isOnGround()) {
			sk2 = false;
			skill("c1111_s2");
			skill("c1111_s2e");
			player.teleport(player.getLocation());
			ARSystem.giveBuff(player, new Stun(player), 4);
			ARSystem.giveBuff(player, new Silence(player), 4);
			for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
				LivingEntity en =((LivingEntity)e);
				en.setNoDamageTicks(0);
				en.damage(stack,player);
				Location loc = e.getLocation();
				loc = ULocal.lookAt(loc, player.getLocation());
				Vector vt = e.getVelocity().add(loc.getDirection()).multiply(-1);
				if(stack > 10) vt = vt.multiply(2);
				if(stack > 50) vt = vt.multiply(2);
				if(stack > 100) vt = vt.multiply(2);
				if(stack > 500) vt = vt.multiply(2);
				e.setVelocity(vt);
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c1111:p")+ "] : &f" + stack +"");
		}
		return true;
	}

	
	@Override
	public boolean remove(Entity caster) {
		if(sk3 > 0) {
			stack = 999;
			return false;
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			LivingEntity en = (LivingEntity)e.getEntity();
			if(en.getHealth()*10 <= player.getHealth()) {
				Skill.quit(en);
				if(en instanceof Player) ARSystem.playSound((Player)en, "c1111p");
			}
			else if(en.getHealth() < player.getHealth()) {
				e.setDamage(e.getDamage()*2);
			}
		} else {
			if(sk3 > 0) {
				stack += (float) e.getDamage();
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c30siro) {
					is = "siro";
					break;
				}
			}
		}
		
		if(is.equals("siro")) {
			ARSystem.playSound((Entity)player, "c1111siro");
		} else {
			ARSystem.playSound((Entity)player, "c1111db");
		}
		
		return true;
	}
}
