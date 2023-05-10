package chars.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import event.Skill;
import manager.AdvManager;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c33meliodace extends c00main{
	double damage = 0;
	int tick;
	int timer = 0;
	
	int s1= 0, s2 = 0;

	public c33meliodace(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 33;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c33_s1");
		s1 = 20;
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c33_s2");
		damage = 0;
		tick= 80;
		s2 = 60;
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c33s2");
		skill("c33_s2de");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20==0) {
			scoreBoardText.add("&c ["+Main.GetText("c33:ps")+ "]&f : "+ damage);
			if(isps) ARSystem.heal(player,3);
		}
		if(damage >= 100) {
			Rule.playerinfo.get(player).tropy(33,1);
		}
		if(tick> 0) tick--;
		if(s1 > 0) s1--;
		if(s2 > 0) {
			s2--;
			if(s2 == 1) {
				ARSystem.giveBuff(player, new Nodamage(player), 40);
			}
		}
		
		if(damage >= 50 && !isps) {
			ARSystem.giveBuff(player, new Nodamage(player), 60);
			
			spskillen();
			spskillon();
			player.setMaxHealth(16);
			player.setHealth(16);
			setcooldown[1] = setcooldown[1]/2;
			setcooldown[2] = setcooldown[2]/2;
			skill("c33_e");
			skill("c33_e2");
		}
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.OnBuffTime(player, "silence")) {
			for(int i=0; i<10;i++) if(cooldown[i] > 0) cooldown[i] -= Rule.buffmanager.GetBuffTime(player, "silence")*0.05;
			Rule.buffmanager.selectBuffTime(player, "silence",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "rampage")) {
			for(int i=0; i<10;i++) if(cooldown[i] > 0) cooldown[i] -= Rule.buffmanager.GetBuffTime(player, "rampage")*0.05;
			Rule.buffmanager.selectBuffTime(player, "rampage",0);
		}
		if(Rule.buffmanager.OnBuffTime(player, "sleep")) {
			for(int i=0; i<10;i++) if(cooldown[i] > 0) cooldown[i] -= Rule.buffmanager.GetBuffTime(player, "sleep")*0.05;
			Rule.buffmanager.selectBuffTime(player, "sleep",0);
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(damage >= 1) {
				target.setNoDamageTicks(0);
				target.damage(damage,player);
				ARSystem.heal(player, damage);
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(e.getDamage() >  0 &&e.getDamage() <= 0.01 && e.getEntity() instanceof LivingEntity) {
				return false;
			}
		} else {
			if(s1 > 0) {
				e.setCancelled(true);
				damage = e.getDamage()*2;
				if(isps) damage = e.getDamage()*5;
				delay(()->{skill("c33_s1b");},2);
				e.setDamage(0);
				return false;
			}
			if(s2 > 0) {
				damage+=e.getDamage()*2;
				if(isps) damage += e.getDamage()*3;
				if(player.getHealth() -e.getDamage() <= 1) {
					player.setHealth(1);
					e.setCancelled(true);
					e.setDamage(0);
					return false;
				}
			}
		}
		return true;
	}
}
