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

public class c111artorya extends c00main{
	int stack = 0;
	int sk3 = 0;
	int damage = stack;
	
	@Override
	public void setStack(float f) {
		stack = (int)f;
	}
	
	public c111artorya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 111;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c111s3");
		sk3 = 10;
		ARSystem.giveBuff(player, new Stun(player), 10);
		ARSystem.giveBuff(player, new Silence(player), 10);
		skill("c111_s3-1");
		damage = stack;
		stack = 0;
		return true;
	}
	
	@Override
	public boolean skill2() {
		stack += 20;
		skill("c111_s2");
		return true;
	}
	

	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c111s1");
		skill("c111_s1");
		damage = stack;
		stack = 0;
		for(Entity e : ARSystem.box(player, new Vector(8,5,8), box.TARGET)) {
			for(int i =0; i<5;i++) {
				delay(()->{
					Location loc = e.getLocation();
					loc = ULocal.lookAt(loc, player.getLocation());
					e.setVelocity(e.getVelocity().add(loc.getDirection().multiply(Math.min(0,e.getLocation().distance(player.getLocation())-8)/4)));
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(damage*0.02,player);
				},i*2);
			}
		}
		return true;
	}

	public void skillsp() {
		Rule.playerinfo.get(player).tropy(111, 1);
		for(Player p : Rule.c.keySet()) {
			ARSystem.giveBuff(p, new TimeStop(p), 200);
			if(p != player) {
				Location loc = player.getLocation();
				loc.setPitch(0);
				loc = ULocal.offset(loc, new Vector(3,0,0));
				loc = ULocal.lookAt(loc, player.getLocation());
				p.teleport(loc);
			}
		}
		ARSystem.playSoundAll("c111sp2");
		skill("c111_sp");
		delay(()->{
			ARSystem.playSoundAll("c111sp");
			skill("c111_spl");
			delay(()->{
				Skill.win(player);
			},60);
		},100);
	}
	
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
	}
	
	@Override
	public boolean tick() {
		if(tk%4 == 0 && sk3 <= 0 && stack > 0) stack--;
		if(sk3 > 0) sk3--;
		if(tk%20 == 0 && stack > 0) ARSystem.heal(player, 1);
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c111:p")+ "] : &f" + stack +"");
		}
		return true;
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
		} else {
			if(sk3 > 0) {
				ARSystem.giveBuff(player, new Nodamage(player), 15+sk3);
				ARSystem.giveBuff(player, new Silence(player), 15+sk3);
				ARSystem.giveBuff(player, new Stun(player), 15+sk3);
				Location loc = player.getLocation();
				loc.setPitch(0);
				player.teleport(loc);
				stack = (int) (damage*1.5);
				cooldown[3] *= 0.5;
				delay(()->{
					ARSystem.giveBuff(player, new Nodamage(player), 60);
					ARSystem.giveBuff(player, new Silence(player), 20);
					int count = 0;
					List<Entity> targets = ARSystem.PlayerBeamBox(player, 13, 5, box.TARGET);
					for(Entity t : ARSystem.box(player, new Vector(8,8,8), box.TARGET)) {
						if(!targets.contains(t)) targets.add(t);
					}
					
					for(Entity entity : targets){
						if(Rule.c.get(entity) != null) count++;
					}
					
					if(Rule.c.size() > 1 && Rule.c.size() -1 == count && damage >= 200) {
						spskillen();
						spskillon();
						WinEvent event = new WinEvent(player);
						Bukkit.getPluginManager().callEvent(event);
						if(!event.isCancelled()) {
							skillsp();
						}
					} else {
						skill("c111_s3-2");
						ARSystem.playSound((Entity)player, "c111s32");
						for(Entity entity : targets){
							((LivingEntity)entity).setNoDamageTicks(0);
							((LivingEntity)entity).damage(5+(damage * 0.2),player);
						}
					}
				},10+sk3);
				sk3 = 0;
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
			if(e.getDamage() >= player.getMaxHealth()) {
				e.setDamage(player.getMaxHealth() - 1);
			}
		}
		return true;
	}
	@Override
	public boolean damage(EntityDamageEvent e) {
		if(e.getDamage() >= player.getMaxHealth()) {
			e.setDamage(player.getMaxHealth() - 1);
		}
		return super.damage(e);
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
				if(Rule.c.get(e) instanceof c60gil) {
					is = "gil";
					break;
				}
			}
		}
		
		if(is.equals("siro")) {
			ARSystem.playSound((Entity)player, "c111siro");
		} else if(is.equals("gil")) {
			ARSystem.playSound((Entity)player, "c111gil");
		} else {
			ARSystem.playSound((Entity)player, "c111db");
		}
		
		return true;
	}
}
