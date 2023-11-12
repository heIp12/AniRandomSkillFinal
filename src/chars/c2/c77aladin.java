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

public class c77aladin extends c00main{

	int ugo = 0;
	
	public c77aladin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 77;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		skill("c77_s1");
		if(AMath.random(11) < 2) {
			ARSystem.playSound((Entity)player, "c77s2");
			ugo++;
			if(!ARSystem.gameMode2) ugo++;
		} else {
			ARSystem.playSound((Entity)player, "c77s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c77_s2");
		if(AMath.random(11) < 2) {
			ARSystem.playSound((Entity)player, "c77s2");
			ugo++;
			if(!ARSystem.gameMode2) ugo++;
		} else {
			ARSystem.playSound((Entity)player, "c77s1");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c77s3");
		skill("c77_s3");
		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(AMath.random(400) == 1) {
			cooldown[1] = cooldown[2] = 0;
			if(!ARSystem.gameMode2) {
				if(AMath.random(10) < 2) {
					ARSystem.playSound((Entity)player, "c77p"+(1+AMath.random(2)));
					ugo++;
					if(!ARSystem.gameMode2) ugo++;
				} else {
					ARSystem.playSound((Entity)player, "c77p1");
				}
			} else {
				if(AMath.random(15) < 2) {
					ARSystem.playSound((Entity)player, "c77p"+(1+AMath.random(2)));
					ugo++;
					if(!ARSystem.gameMode2) ugo++;
				} else {
					ARSystem.playSound((Entity)player, "c77p1");
				}
			}
		}
		if(!isps && ugo >= 10) {
			spskillon();
			spskillen();
			Rule.playerinfo.get(player).tropy(77,1);
			ARSystem.playSound((Entity)player, "c77sp");
			
		}
		if(tk%20 == 0) {
			if(psopen) scoreBoardText.add("&c ["+Main.GetText("c77:sk0")+ "] : "+ ugo +" / 10");
			if(AMath.random(10) == 3 && isps) {
				ARSystem.playSoundAll("c77sp"+(1+AMath.random(2)));
				for(Entity e : ARSystem.box(player, new Vector(999,999,999), box.TARGET)) ARSystem.spellLocCast(player, e.getLocation(), "c77_sp");
			}
		}
		if(tk%5 == 1 && isps) {
			ARSystem.spellLocCast(player, Map.randomLoc(), "c77_sp");
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {

		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		int i = AMath.random(14);
		if(!ARSystem.gameMode2) i = AMath.random(4);
		if(i == 1) {
			ARSystem.playSound((Entity)player, "c77db3");
			ugo++;
			if(!ARSystem.gameMode2) ugo++;
		} else if(i==2) {
			ARSystem.playSound((Entity)player, "c77db2");
			ugo++;
			if(!ARSystem.gameMode2) ugo++;
		} else {
			ARSystem.playSound((Entity)player, "c77db");
		}
		return true;
	}
	
}
