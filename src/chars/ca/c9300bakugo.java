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

public class c9300bakugo extends c00main{
	boolean sk2 = false;
	boolean ps = false;
	int pst = 0;
	int rpt = 0;
	Vector vtr;
	List<LivingEntity> tg;
	
	boolean iskill = false;
	
	@Override
	public void setStack(float f) {
		if(f == 93) {
			iskill = true;
		}
	}
	
	public c9300bakugo(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1093;
		load();
		text();
		c = this;
		ARSystem.playSound(p, "c93select");
	}
	

	@Override
	public boolean skill1() {
		Location loc = player.getLocation();
		loc.setPitch(0);
		player.setVelocity(loc.getDirection().multiply(1.1).setY(-1.4));
		ARSystem.playSound((Entity)player, "0explod", 1.4f,0.4f);
		skill("c1093_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(!sk2) {
			sk2 = true;
			player.setHealth(player.getHealth()*0.2);
			skill("c1093_s2");
			ARSystem.playSound((Entity)player, "c1093s2");
			ARSystem.giveBuff(player, new Stun(player), 10);
			ARSystem.giveBuff(player, new Nodamage(player), 10);
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(iskill && skillCooldown(0)) {
			iskill = false;
			spskillen();
			spskillon();
			ARSystem.playSound((Entity)player,"c93sp");
			ARSystem.giveBuff(player, new Stun(player), 30);
			tg = new ArrayList<>();
			delay(()->{
				pst = 30;
				vtr = player.getLocation().getDirection();
			},30);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setVelocity(player.getLocation().getDirection().multiply(5));
			ARSystem.giveBuff(target, new Silence(target), 200);
			ARSystem.giveBuff(target, new Noattack(target), 200);
			delay(()->{
				ARSystem.spellCast(player, target, "c1093_p");
				target.setNoDamageTicks(0);
				target.damage(50,player);
			},40);
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(e == player) {
			iskill = true;
			cooldown[3] = 0;
		}
	}
	
	@Override
	public boolean tick() {
		player.setFallDistance(0);
		if(rpt > 0) {
			rpt -= 1;
			vtr = player.getLocation().getDirection();
		}
		if(pst > 0) {
			pst--;
			if(rpt > 0) {
				player.setVelocity(new Vector(0,0.1,0));
			} else {
				player.setVelocity(vtr.clone().multiply(2));
			}
			skill("c1093_sp");
			ARSystem.giveBuff(player, new Silence(player), 10);
			for(Entity e : ARSystem.box(player, new Vector(5,5,5), box.TARGET)) {
				LivingEntity en = (LivingEntity)e;
				if(!tg.contains(en) && en != player) {
					rpt = 6;
					pst = 36;
					tg.add(en);
				}
			}
			for(LivingEntity en : tg) {
				ARSystem.giveBuff(en, new Noattack(en), 10);
				ARSystem.giveBuff(en, new Silence(en), 10);
				en.teleport(ULocal.offset(player.getLocation().clone(), new Vector(-1,0,0)));
			}
			if(pst <= 0) {
				skill("c93_e2");
				for(Entity e : ARSystem.box(player, new Vector(8,20,8), box.TARGET)) {
					((LivingEntity)e).setNoDamageTicks(0);
					((LivingEntity)e).damage(100,player);
				}
			}
		}
		if(iskill && tk%10 == 0) {
			ARSystem.playSound((Entity)player, "0fire", 1.5f);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() <= 1) {
				e.setDamage(5);
				LivingEntity en = (LivingEntity)e.getEntity();
				ARSystem.playSound((Entity)player, "0explod", 1,1.4f);
				ARSystem.spellCast(player,en, "c1093_p");
				delay(()->{
					en.setNoDamageTicks(0);
					ARSystem.giveBuff(en, new Stun(en), 2);
					ARSystem.giveBuff(en, new Silence(en), 2);
				},0);
				Location loc = player.getLocation();
				loc.setPitch(0);
				Vector vtr = loc.getDirection().multiply(-1);
				vtr.setY(1.4);
				player.setVelocity(vtr);
				cooldown[1] = 0.1f;
			}
		} else {
			
		}
		return true;
	}
}
