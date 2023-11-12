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
import chars.c.c24sinobu;
import chars.c.c30siro;
import chars.c2.c60gil;
import chars.ca.c2400sinobu;
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

public class c112hanekawa extends c00main{
	float sk2 = 0;
	int sk3 = 0;
	int sk4 = 0;
	int tr = 0;
	
	public c112hanekawa(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 112;
		load();
		text();
		c = this;
	}
	
	@Override
	public void setStack(float f) {
		sk4 = (int)f;
	}

	@Override
	public boolean skill1() {
		LivingEntity target = (LivingEntity)ARSystem.boxSOne(player, new Vector(5,3,5), box.TARGET);
		if(target != null) {
			ARSystem.playSound((Entity)player, "c112s1");
			Location lc2 = target.getLocation();
			lc2.setPitch(0);
			player.teleport(lc2);
			Location loc = ULocal.offset(target.getLocation(), new Vector(1,0,0));
			loc = ULocal.lookAt(loc, player.getLocation());
			loc.setPitch(20);
			
			target.teleport(loc.add(0,0.5,0));
			ARSystem.giveBuff(target, new Stun(target), 40);
			ARSystem.giveBuff(target, new Silence(target), 40);
			ARSystem.giveBuff(target, new Nodamage(target), 40);
			ARSystem.giveBuff(player, new Stun(player), 40);
			ARSystem.giveBuff(player, new Silence(player), 40);
			ARSystem.giveBuff(player, new Nodamage(player), 40);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c112s2");
		sk2 = 20;
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(player.isOnGround()) {
			sk4++;
			if(sk4 == 40) spskillon();
			
			if(sk3 <=0) {
				sk3 = 60;
				ARSystem.playSound((Entity)player, "c112s3");
			}
			player.setVelocity(player.getLocation().getDirection().multiply(1.2));
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(sk4 >= 40 && skillCooldown(0)) {
			tr++;
			if(tr >= 2) Rule.playerinfo.get(player).tropy(112, 1);
			spskillen();
			ARSystem.playSoundAll("c112sp");
			ARSystem.giveBuff(player, new Stun(player), 140);
			ARSystem.giveBuff(player, new Silence(player), 140);
			ARSystem.giveBuff(player, new Nodamage(player), 140);
			for(Entity e : ARSystem.box(player, new Vector(12,5,12), box.TARGET)) {
				LivingEntity target = (LivingEntity)e;
				ARSystem.giveBuff(target, new Fascination(target, player), 140,0.3);
			}
			for(int i = 1; i<12;i++) {
				int j = i;
				delay(()->{
					Holo.create(player.getLocation().clone().add(0,0.2,0), Text.get("c112:sp"+j),20,new Vector(0.5-AMath.random(0, 10)*0.1,-0.1,0.5-AMath.random(0, 10)*0.1));
				},11*i-11);
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0 && psopen) scoreBoardText.add("&c ["+Main.GetText("c112:sk0")+ "] : &f" + sk4 +" / 40");
		if(tk%2 == 0) {
			Player s = null;
			for(Player p : Rule.c.keySet()) if(Rule.c.get(p).getCode() == 24) s = p;
			
			for(Entity e : ARSystem.box(player, new Vector(3,2,3), box.TARGET)) {
				LivingEntity entity = (LivingEntity)e;
				float size = 0.1f;
				if(sk2 > 0) size = 0.5f;
				if(s != null && ((c24sinobu)Rule.c.get(s)).target == e) ((c24sinobu)Rule.c.get(s)).event(player);
				if(entity.getHealth() - size > 1) {
					if(size > 0.3) {
						ARSystem.spellCast(player, e, "c112p2");
					} else {
						ARSystem.spellCast(player, e, "c112p");	
					}
					entity.setHealth(entity.getHealth() -size);
					ARSystem.overheal(player, size);
					s_damage+=size;
				} else {
					Skill.remove(entity, player);
				}
			}
		}
		if(sk2 > 0) sk2--;
		if(sk3 > 0) sk3--;
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			
		}
		return true;
	}
	
}
