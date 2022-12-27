package c2;

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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import c.c05touma;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c63micoto extends c00main{
	double l = 50;
	int s2= 0;
	boolean s3 = false;
	List<Entity> s1;
	
	public c63micoto(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 63;
		load();
		text();
		c = this;
		s1 = new ArrayList<Entity>();
	}
	
	@Override
	public void setStack(float f) {
		l = f;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1") && !s1.contains(target)) {
			s1.add(target);
			target.damage(10,player);
		}
		if(n.equals("2") && !s1.contains(target)) {
			s1.add(target);
			target.damage(500,player);
		}
	}

	@Override
	public boolean skill1() {
		if(l>=200 && skillCooldown(0)) {
			spskillon();
			spskillen();
			l-=100;
			s1.clear();
			ARSystem.giveBuff(player, new Stun(player), 80);
			ARSystem.giveBuff(player, new Silence(player), 80);
			for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) {
				ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 200);
				ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 20);
			}
			ARSystem.playSoundAll("c63sp");
			delay(()->{ARSystem.playSoundAll("c63sp2");},20);
			delay(()->{
				ARSystem.playSoundAll("c63r1");
				skill("c63_sp");
			},55);

		} else if(l>=10) {
			s1.clear();
			l-=10;
			ARSystem.playSound((Entity)player, "c63s1");
			ARSystem.giveBuff(player, new Stun(player), 20);
			ARSystem.giveBuff(player, new Silence(player), 20);
			delay(()->{
				ARSystem.playSoundAll("c63r1");
				skill("c63_s1");
			},20);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(l<200) l+=20;
		s2 = 60;
		ARSystem.playSound((Entity)player, "c63s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		s3 = !s3;
		if(s3) {
			ARSystem.playSound((Entity)player, "c63s3");
		} else {

		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(l == 200) Rule.playerinfo.get(player).tropy(63,1);
		
		if(s2 > 0) {
			skill("c63_s2e");
			for(Entity e : ARSystem.box(player, new Vector(6,2,6), box.TARGET)) {
				ARSystem.giveBuff((LivingEntity) e, new Silence((LivingEntity) e), 2);
				ARSystem.giveBuff((LivingEntity) e, new Stun((LivingEntity) e), 2);
			}
			s2--;
		}
		if(s3) {
			if(tk%10 == 0) {
				if(l<200) l+=1.5;
				ARSystem.playSound((Entity)player, "c63l" + AMath.random(2));
				skill("c63_s3e");
				for(Entity e : ARSystem.box(player, new Vector(6,2,6), box.TARGET)) {
					((LivingEntity)e).damage(1,player);
				}
			}
			ARSystem.giveBuff(player, new Stun(player), 2);
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c63:ps")+ "] : "+ l +"%");
		}
		return true;
	}
	

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			e.setDamage(e.getDamage() + e.getDamage()*l*0.01);
		} else {
			if(Rule.c.get(e.getDamager()) != null && Rule.c.get(e.getDamager()) instanceof c98kanna) {
				l += e.getDamage()*2;
				e.setDamage(e.getDamage()* 0.1);
			}
			if(s2 > 0) {
				e.setDamage(e.getDamage()*0.2);
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
				if(Rule.c.get(e) instanceof c05touma) {
					is = "touma";
					break;
				}
				if(Rule.c.get(e) instanceof c100kuroko) {
					is = "kuroco";
					break;
				}
			}
		}
		if(is.equals("kuroco")) {
			ARSystem.playSound((Entity)player, "c100m2");
		} else if(is.equals("touma")) {
			ARSystem.playSound((Entity)player, "c63touma");
		} else {
			ARSystem.playSound((Entity)player, "c63db");
		}
		
		return true;
	}
}
