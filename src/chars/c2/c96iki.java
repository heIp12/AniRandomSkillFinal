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

public class c96iki extends c00main{

	int s3 = 0;
	int sp = 0;
	float d3 = 0;
	int count = 0;
	
	public c96iki(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 96;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c96s1");
		skill("c96_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c96s2");
		if(isps) {
			player.setVelocity(player.getLocation().getDirection().multiply(1.4).setY(0));
		} else {
			player.setVelocity(player.getLocation().getDirection().multiply(0.8).setY(0));
		}
		ARSystem.potion(player, 14, 4, 1);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c96s2");
		ARSystem.giveBuff(player, new Stun(player), 20);
		ARSystem.giveBuff(player, new Silence(player), 14);
		s3 = 20;
		d3 = 0;
		skill("c96_s3");
		return true;
	}
	
	@Override
	public boolean skill6() {
		if(player.isSneaking() && !isps && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time >= 30) {
			spskillon();
			spskillen();
			ARSystem.playSound((Entity)player, "c96sp");
			skill("c96_sp");
			skillmult = 11;
			Rule.buffmanager.selectBuffValue(player, "buffac",10f);
			sp = 300;
			count = s_kill;
		} else {
			super.skill6();
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(5 + (d3 *0.2),player);
		}
	}
	
	@Override
	public boolean tick() {
		if(sp > 0) {
			sp--;
			
			if(count+2 < s_kill) {
				Rule.playerinfo.get(player).tropy(96,1);
			}
			
			if(sp == 0) {
				Skill.death(player, player);
			}
		}
		if(s3 > 0) {
			s3--;
			if(s3 == 0) {
				skill("c96_s3_2");
			}
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c96:sp")+ "]&f : " + AMath.round(sp*0.05,2));
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) {
				ARSystem.heal(player, e.getDamage() * 0.2);
			}
		} else {
			if(s3 > 0) {
				d3 += e.getDamage();
				e.setDamage(0);
			}
			if(e.getDamager().getLocation().distance(player.getLocation()) <= 2) {
				e.setDamage(0);
			}
		}
		return true;
	}
}
