package chars.c2;

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

public class c82maple extends c00main{
	float mp = 60;
	int s2 = 0;
	int cc = 0;
	
	public c82maple(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 82;
		load();
		text();
		c = this;
		
	}

	@Override
	public boolean skill1() {
		if(mp >= 10) {
			mp -= 10;
		} else {
			return false;
		}
		skill("c82_s1");
		ARSystem.playSound((Entity)player, "c82s1");
		for(Entity e : ARSystem.box(player, new Vector(6,4,6), box.TARGET)) {
			ARSystem.giveBuff((LivingEntity)e, new Stun((LivingEntity)e), 100);
			ARSystem.giveBuff((LivingEntity)e, new Silence((LivingEntity)e), 140);
			cc++;
			if(cc >= 20) {
				Rule.playerinfo.get(player).tropy(82,1);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean skill2() {
		s2 = 40;
		ARSystem.playSound((Entity)player, "c82s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(mp >= 15) {
			mp -= 15;
		} else {
			return false;
		}
		skill("c82_s3");
		ARSystem.playSound((Entity)player, "c82s3");
		for(Entity e : ARSystem.box(player, new Vector(10,4,10), box.TARGET)) {
			Wound w = new Wound((LivingEntity)e);
			w.setValue(1);
			if(ARSystem.isGameMode("lobotomy")) w.setValue(10);
			w.setDelay(player,40,0);
			ARSystem.giveBuff((LivingEntity)e, w, 600);
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(s2 > 0) s2--;
		mp += 0.005;
		
		if(tk%20 == 0) {
			scoreBoardText.add("&c [Mp] : "+ mp);
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			if(s2 > 0) {
				mp += (e.getDamage()*2);
				e.setDamage(0);
				return false;
			}
			e.setDamage(e.getDamage()*0.1);
		}
		
		return true;
	}
}
