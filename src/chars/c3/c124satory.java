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
import ars.gui.G_Satory;
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
import chars.c.c09youmu;
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

public class c124satory extends c00main{
	int card = 0;
	public int type = 0;
	
	int s1 = 0;
	LivingEntity target;
	Location s1loc;
	
	double hp = 0;
	double damage = 0;
	
	
	public c124satory(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 124;
		load();
		text();
		c = this;
	}
	@Override
	public void setStack(float f) {
		card = (int)f;
	}

	@Override
	public boolean skill1() {
		
		List<Entity> e = ARSystem.PlayerBeamBox(player, 40, 3, box.TARGET);
		if(e.size() > 0) {
			target = (LivingEntity)e.get(0);
			s1 = 40;
			hp = player.getHealth();
			s1loc = player.getLocation();
			ARSystem.giveBuff(player, new Silence(player), 40);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> e = ARSystem.PlayerBeamBox(player, 15, 3, box.TARGET);
		if(e.size() > 0) {
			target = (LivingEntity)e.get(0);
			ARSystem.playSound(target, "c124s2");
			if(Rule.c.get(target) != null) {
				int random = AMath.random(5);
				int max = 0;
				while(Rule.c.get(target).setcooldown[random] == 0) {
					random = AMath.random(5);
					max++;
					if(max > 10000) break;
				}
				int r = random;
				((Player)target).sendTitle(random+Text.get("c124:p1"), "");
				delay(()->{
					if(Rule.c.get(target) != null && Rule.c.get(target).cooldown[r] <= 0) {
						Rule.c.get(target).cooldown[r] = 150;
						target.setNoDamageTicks(0);
						target.damage(player.getMaxHealth()*0.2,player);
					}
				},40);
			} else {
				target.setNoDamageTicks(0);
				target.damage(player.getMaxHealth()*0.2,player);
			}
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	

	@Override
	public boolean skill3() {
		List<Entity> e = ARSystem.PlayerBeamBox(player, 25, 3, box.TARGET);
		if(e.size() > 0) {
			target = (LivingEntity)e.get(0);
			ARSystem.playSound(target, "c124s3");
			if(Rule.c.get(target) != null) Rule.c.get(target).setStack(0);
			ARSystem.giveBuff(target, new Panic(target), 100);
			delay(()->{
				target.setNoDamageTicks(0);
				target.damage(player.getMaxHealth()*0.15,player);
			},100);
			
		} else {
			cooldown[3] = 0;
		}
		return true;
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0 ) scoreBoardText.add("&c ["+Main.GetText("c124:ps")+ "] : &f" + Text.get("main:tp"+type));
		if(tk%20 == 0 ) scoreBoardText.add("&c ["+Main.GetText("c124:sk0")+ "] : &f" + damage);
		if(type == 0 && Rule.c.get(player) != null && Rule.c.get(player) instanceof c124satory && !player.getOpenInventory().getTitle().equals("[GUI] Satory Select")) {
			new G_Satory(player);
		}
		if(s1 > 0 && target != null) {
			MSUtil.buffoff(player, "c124_p");
			if(player.getPassenger() != null) player.removePassenger(player.getPassenger());
			Location loc = target.getLocation();
			loc.setPitch(0);
			loc = ULocal.offset(loc, new Vector(2,0,0));
			loc = ULocal.lookAt(loc, target.getLocation());
			player.teleport(loc);
			if(s1 == 40) ARSystem.playSound(target, "c124s1");
			s1--;
			if(s1 == 0) {
				player.teleport(s1loc);
				if(hp != player.getHealth()) ARSystem.playSound((Entity)player, "c124s1heal");
				ARSystem.heal(player, (hp - player.getHealth())*2);
			}
			skill("c124_p");
		}
		if(!MSUtil.isbuff(player, "c124_p")) {
			skill("c124_p");
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(Rule.c.get(e.getEntity()) != null) {
				if(Rule.c.get(e.getEntity()).number%1000 == 123) {
					e.setDamage(e.getDamage() * 3);
					if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time > 100) {
						e.setDamage(999);
					}
				}
			}
		} else {
			damage += e.getDamage();
			if(Rule.c.get(e.getDamager()) != null) {
				int number = Rule.c.get(e.getDamager()).number;
				if(Text.get("c"+number+":type").contains("tp"+type)) {
					for(int i=0;i<10;i++)Rule.c.get(e.getDamager()).setcooldown[i] *= 1.05f;
					e.setDamage(e.getDamage() * 0.5f);
				}
			}
			if(s1 > 0 && e.getDamager() == target && player.getHealth() - e.getDamage() < 1 && skillCooldown(0)) {
				e.setDamage(0);
				e.setCancelled(true);
				
				spskillon();
				spskillen();
				ARSystem.playSound(target, "c124sp");
				if(Rule.c.get(target) != null) damage += Rule.c.get(target).s_damage;
				ARSystem.giveBuff(target, new TimeStop(target), 160);
				ARSystem.giveBuff(player, new TimeStop(player), 160);
				ARSystem.giveBuff(player, new Nodamage(player), 40);
				
				for(int i=0; i<60;i++) {
					delay(()->{
						ARSystem.spellCast(player, target, "c124_sp2");
					},i);
				}

				delay(()->{
					ARSystem.spellCast(player, target, "c124_sp4");
				},60);
				delay(()->{
					ARSystem.spellCast(player, target, "bload");
					target.setNoDamageTicks(0);
					target.damage(damage,player);
					player.setMaxHealth(player.getMaxHealth() + damage/2);
					if(player.getMaxHealth() > 200) {
						ARSystem.overheal(player, player.getMaxHealth() - 200);
						player.setMaxHealth(200);
					}
					ARSystem.heal(player, 999);
					damage = 0;
				},180);
				return false;
			}
			if(Rule.c.get(e.getDamager()) != null) {
				if(Rule.c.get(e.getDamager()).number%1000 == 123) {
					e.setDamage(0);
					e.setCancelled(true);
					return false;
				}
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
				if(Rule.c.get(e) instanceof c123rin) {
					is = "o";
					break;
				}
			}
		}
		
		if(is.equals("o")) {
			ARSystem.playSound((Entity)player, "c124orin");
		} else {
			ARSystem.playSound((Entity)player, "c124db");
		}
		
		return true;
	}
}
