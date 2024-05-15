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
import ars.ARSystem;
import ars.Rule;
import buff.Nodamage;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.BlockUtil;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c4200touno extends c00main{
	int cr = 3;
	int p_tick = 0;
	
	boolean ison = false;
	
	@Override
	public void setStack(float f) {
		if(((int)f) == 42) {
			spskillen("17분할 제로투");
			spskillon();
			ison = true;
		}
	}
	
	public c4200touno(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1042;
		load();
		text();
		
	}
	

	@Override
	public boolean skill1() {
		skill("c1042_s1");
		return true;
	}
	@Override
	public boolean skill2() {
		skill("c1042_s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c1042_s3");
		return true;
	}
	
	LivingEntity target;
	int sk4 = 0;
	@Override
	public boolean skill4() {
		List<Entity> e = ARSystem.PlayerBeamBox(player, 10, 3, box.TARGET);
		if(e.size() > 0) {
			target = (LivingEntity)e.get(0);
			if(!ison) {
				ARSystem.playSound((Entity)player, "c1042s41");
				ARSystem.spellCast(player, target, "c1042_s4");
			} else {
				zero = 0;
				yaw = 1000;
				sk4 = 140;
				ARSystem.giveBuff(player, new Silence(player), 40);
				ARSystem.giveBuff(target, new Stun(target), 40);
				ARSystem.giveBuff(target, new Silence(target), 40);
			}
		} else {
			cooldown[4] = 0;
		}
		return false;
	}

	double yaw = 0;
	int zero = 0;
	int snk = 0;
	boolean sn = false;
	@Override
	public boolean tick() {
		if(p_tick >40) {
			 p_tick--;
		}
		if(player.isSneaking() && !sn) {
			sn = true;
			snk++;
		}
		if(!player.isSneaking() && sn){
			sn = false;
		}
		if(snk >= 150 && !isps) {
			spskillen("17분할 제로투");
			spskillon();
			ison = true;
		}
		float yw = player.getLocation().getYaw();
		if(sk4 > 0) sk4--;
		if(ison && player.isSneaking() && sk4 > 0 && target != null) {
			if(Math.abs(Math.abs(yw) - Math.abs(yaw)) > 45) {
				ARSystem.giveBuff(player, new Nodamage(player), 20);
				ARSystem.giveBuff(player, new Silence(player), 20);
				ARSystem.giveBuff(target, new Silence(target), 10);
				ARSystem.giveBuff(target, new Stun(target), 10);
				target.teleport(ULocal.lookAt(target.getLocation().clone(), player.getLocation()));
				player.setSneaking(false);
				yaw = yw;
				zero++;
				if(zero > 7) zero = 0;
				ARSystem.spellCast(player, target, "c1042_s4_4");
				remove(target);
				ARSystem.playSound((Entity)player, "0zero"+zero);
				ARSystem.playSound(target, "0zero"+zero);
			}
		}
		return true;
	}

	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			remove(target);
		}
		if(n.equals("2")) {
			remove(target);
			target.setNoDamageTicks(0);
			target.damage(0.5,player);
		}
	}

	
	void remove(LivingEntity e) {
		if(p_tick <=0 && AMath.random(100) <= cr) {
			cooldown[4] = 0;
			ARSystem.giveBuff(e, new TimeStop(e), 40);
			delay(()->{
				ARSystem.playSound(e, "c1042p");
			},20);
			delay(()->{
				ARSystem.playSound(e, "0bload");
				Skill.remove(e, player);
			},40);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			remove(e.getEntity());
		} else {

		}
		return true;
	}
}
