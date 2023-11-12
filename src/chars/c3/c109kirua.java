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

public class c109kirua extends c00main{
	int stack = 0;
	int p = 0;
	boolean sp = false;
	boolean delay = false;
	
	@Override
	public void setStack(float f) {
		stack = (int) f;
	}
	
	public c109kirua(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 109;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(stack > 30 || sp) {
			if(!sp) stack -= 30;
			skill("c109_s1");
			ARSystem.playSound((Entity)player, "c109s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(stack > 200 || sp) {
			if(!sp) stack -= 200;
			skill("c109_s2");
			ARSystem.playSound((Entity)player, "c109s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(stack > 20 || sp) {
			if(!sp) stack -= 20;
			ARSystem.playSound((Entity)player, "c109s3");
			if(player.isSneaking()) {
				Entity e = ARSystem.boxSOne(player, new Vector(8,4,8), box.TARGET);
				if(e != null) {
					teleport(e.getLocation());
				} else {
					player.teleport(player.getLocation().add(player.getLocation().getDirection().multiply(5)));
					skill("c109_s3e2");
					skill("c109_s3e2");
					skill("c109_s3e2");
				}
			} else {
				skill("c109_s3e2");
				skill("c109_s3e2");
				skill("c109_s3e2");
				ARSystem.potion(player, 1, 20, 10);
			}
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			ARSystem.giveBuff(target, new Stun(target), 10);
			ARSystem.giveBuff(target, new Silence(target), 10);
			for(int i=0; i<5;i++) ARSystem.spellLocCast(player, target.getLocation(), "c109_s3e2");
		}
	}
	
	void teleport(Location target) {
		Location loc = target.clone();
		loc.setPitch(0);
		loc.setYaw(AMath.random(360));
		loc = ULocal.offset(loc, new Vector(AMath.random(30)*0.1,0.2,0));
		loc.setYaw(ULocal.lookAt(loc, target).getYaw());
		ARSystem.playSound((Entity)player, "0lightning");
		player.teleport(loc);
		skill("c109_s3e2");
		skill("c109_s3e2");
		skill("c109_s3e2");
		
		if(isps) {
			p -= 5;
			for(Entity e : ARSystem.box(player, new Vector(4,4,4), box.TARGET)) {
				ARSystem.playSound(e, "0attack4" , AMath.random(20)*0.1f);
				delay(()->{ARSystem.playSound(e, "0attack4" , AMath.random(20)*0.1f);},4);
				ARSystem.spellLocCast(player, e.getLocation(), "c109_p");
				((LivingEntity)e).setNoDamageTicks(0);
				((LivingEntity)e).damage(1,player);
			}
		}
	}
	
	@Override
	public boolean tick() {
		delay = false;
		if(p > 0) p--;
		if(stack > 1000 && skillCooldown(0) && !sp) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c109sp");
			ARSystem.playSound((Entity)player, "0lightning2");
			sp = true;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c109:ps")+ "] : &f" + stack);
		}
		if(sp && stack > 0) {
			if(tk%2==0) stack -=5;
			if(stack == 0) sp = false;
			if(tk%20 == 0) {
				ARSystem.playSound((Entity)player, "0lightning", 1.4f);
				skill("c109_s3e2");
			}
		} else if(tk%10 == 0){
			stack +=10;
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(s_kill == 3) {
			Rule.playerinfo.get(player).tropy(109, 1);
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(p <= 0) {
				p = 60;
				stack +=30;
				e.setDamage(e.getDamage() + 2);
				ARSystem.playSound(e.getEntity(), "0lightning",0.5f);
				for(int i=0; i<5;i++) ARSystem.spellLocCast(player, e.getEntity().getLocation(), "c109_s3e");
			}
		} else {
			if(Rule.c.get(e.getDamager()) != null) {
				int code = Rule.c.get(e.getDamager()).number;
				if(code == 98 || code == 63) {
					stack += e.getDamage()*5;
					e.setDamage(e.getDamage() * 0.3);
				}
			}
			if(sp && e.getDamager().getLocation().distance(player.getLocation()) <= 5) {
				e.setDamage(0);
				e.setCancelled(true);
				if(!delay) {
					delay = true;
					teleport(e.getDamager().getLocation());
				}
				ARSystem.playSound((Entity)player, "0miss");
				return false;
			}
		}
		return true;
	}
	
}
