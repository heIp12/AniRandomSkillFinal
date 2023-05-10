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
import chars.c2.c63micoto;
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

public class cc1000kuroko extends c00main{
	int tp = 0;
	Location loc;
	float yaw = 0;
	int sk3 = 0;
	
	public cc1000kuroko(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1100;
		load();
		text();
		c = this;
		if(p != null) loc = player.getLocation();
		ARSystem.playSound((Entity)player, "c100select");
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c100s1");
		skill("c1100_s1");

		if(yaw + 45 < player.getLocation().getYaw() || yaw - 45 > player.getLocation().getYaw()) {
			yaw = player.getLocation().getYaw();
			cooldown[1] = 1;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c100s1");
		List<Entity> target = ARSystem.box(player, new Vector(2,2,2), box.ALL);
		if(target.size() <= 0) {
			cooldown[2] = 0;
			return true;
		}
		for(Entity e : target) {
			((LivingEntity)e).setNoDamageTicks(0);
			((LivingEntity)e).damage(8,player);
			ARSystem.giveBuff(((LivingEntity)e), new Stun(((LivingEntity)e)), 10);
			ARSystem.giveBuff(((LivingEntity)e), new Silence(((LivingEntity)e)), 10);
			delay(()->{
				e.teleport(e.getLocation().add(player.getLocation().getDirection().multiply(15)).add(0,0.5,0));
				ARSystem.playSound(e, "c100s2");
			},11);
			e.setVelocity(new Vector(0,0,0));
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(player.isSneaking()) {
			player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(-10)).add(0,0.5,0));
		} else {
			player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(10)).add(0,0.5,0));
		}
		ARSystem.playSound((Entity)player, "c100s2");
		player.setVelocity(new Vector(0,0,0));
		player.setFallDistance(0);
		sk3++;
		if(sk3 > 2) {
			sk3 = 0;
			cooldown[1] -= 1;
			cooldown[2] -= 1;
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			int count = 10;
			if(Rule.c.get(target) != null) {
				count += Rule.c.get(target).s_kill*2;
			}
			for(int i =0;i<count;i++)ARSystem.spellCast(player,target, "c1100_s12");
		}
	}
	
	@Override
	public boolean tick() {
		if(tp > 0) scoreBoardText.add("&a ["+Main.GetText("c1100:ps")+ "]");
		if(loc.distance(player.getLocation()) >= 6) {
			tp=20;
		}
		if(tp > 0) tp--;
		
		if(player != null) loc = player.getLocation();
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.get(e.getEntity()) != null) {
				for(int i = 0; i < Rule.c.get(e.getEntity()).s_kill; i++) {
					e.setDamage(e.getDamage() * 1.3);
				}
			}
		} else {
			if(tp > 0) {
				tp = 0;
				e.setDamage(e.getDamage() * 0.1);
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
				if(Rule.c.get(e) instanceof c63micoto) {
					is = "micoto";
					break;
				}

			}
		}
		
		if(is.equals("micoto")) {
			ARSystem.playSound((Entity)player, "c100m");
		} else {
			ARSystem.playSound((Entity)player, "c100db");
		}
		return true;
	}
}
