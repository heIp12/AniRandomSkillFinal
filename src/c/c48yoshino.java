package c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
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

import com.nisovin.magicspells.MagicSpells;

import Main.Main;
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Stun;
import event.Skill;
import manager.AdvManager;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c48yoshino extends c00main{
	float damage = 0.2f;
	int tick = 0;
	HashMap<LivingEntity,Integer> hit = new HashMap<LivingEntity, Integer>();
	int tropy = 0;
	
	public c48yoshino(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 48;
		load();
		text();
		damage = 0.2f;
	}
	

	@Override
	public boolean skill1() {
		skill("c48_s1");
		ARSystem.playSound((Entity)player, "c48s1",(float) 1);
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c48_s2");
		ARSystem.playSound((Entity)player, "c48s2",(float) 1);
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(!isps) {
			skill("c48_s3");
			hit = new HashMap<LivingEntity, Integer>();
			ARSystem.playSound((Entity)player, "c48s3",(float) 1);
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		tick++;
		scoreBoardText.add("&c ["+Main.GetText("c48:ps")+ "] : "+ AMath.round(((double)1.0-damage)*100,0));
		if(tick%160==0 && damage < 1) {
			damage+=0.05;
		}
		if(tick%80==0 && isps) {
			skill("c48_s3");
			hit = new HashMap<LivingEntity, Integer>();
		}
		
		return true;
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(target.getMaxHealth()/10,player);
		}
		if(n.equals("2")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			
			if(hit.get(target) == null) {
				hit.put(target, 1);
			} else {
				hit.put(target, 1 + hit.get(target));
				if(hit.get(target) == 3) {
					ARSystem.spellCast(player, target, "c48_s3_i5");
					ARSystem.giveBuff(target, new Stun(target), 40);
				}
			}
		}
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			ARSystem.potion((LivingEntity) e.getEntity(), 2, 100, 2);
		} else {
			tropy++;
			if(tropy >= 50) {
				Rule.playerinfo.get(player).tropy(48,1);
			}
			e.setDamage(e.getDamage()*damage);
			if(!isps && AMath.random(33)==3) {
				ARSystem.playSound((Entity)player, "c48sp",(float) 1);
				setcooldown[1] *= 0.5;
				setcooldown[2] *= 0.5;
				spskillon();
				spskillen();
				damage = 0.2f;
				ARSystem.heal(player, 14);
			}
		}
		return true;
	}
}
