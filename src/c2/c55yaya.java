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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.modes;
import util.AMath;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c55yaya extends c00main{
	int sk4 = 0;
	int sk3 = 0;
	int sp = 0;
	float damage = 0;
	boolean isstart = false;
	
	public c55yaya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 55;
		load();
		text();
	}
	

	@Override
	public boolean skill1() {
		skill("c55_s1");
		ARSystem.playSound((Entity)player, "c55s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c55_s2");
		ARSystem.playSound((Entity)player, "c55s1");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c55_s3");
		ARSystem.playSound((Entity)player, "c55s3");
		sk3 = 60;
		return true;
	}
	
	@Override
	public boolean skill4() {
		ARSystem.playSound((Entity)player, "c55s4");
		sk4 = 100;
		if(sk3 > 0 && skillCooldown(0)) {
			skill0();
		}
		return true;
	}
	

	public void skill0() {
		spskillen();
		spskillon();
		ARSystem.playSound((Entity)player,"c55sp");
		delay(()->{
			ARSystem.playSound((Entity)player,"c55sp2");
			skill("c55_sp");
			sp = 60;
		},60);
	}
	
	
	@Override
	public boolean tick() {
		if(!isstart) {
			isstart = true;
			skill("c55_p");
		}
		if(sk3>0) sk3--;
		if(sk4 > 0) sk4--;
		if(sp > 0) sp--;
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*2);
			damage+= e.getDamage();
			if(damage >= 100) Rule.playerinfo.get(player).tropy(55,1);
			if(sk4 > 0) {
				ARSystem.potion((LivingEntity) e.getEntity(), 2, 10, 1);
				delay(()->{((LivingEntity) e.getEntity()).damage(1);},60);
			}
			if(sp > 0) {
				ARSystem.spellCast(player, e.getEntity(), "c55_spe");
			}
			
		} else {
			if(ARSystem.gameMode == modes.LOBOTOMY) e.setDamage(e.getDamage()*0.6);
		}
		return true;
	}
}
