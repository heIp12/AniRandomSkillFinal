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
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Wound;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;
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

public class c6800origami extends c00main{
	int at1 = 0;
	int at2 = 0;
	
	public c6800origami(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1068;
		load();
		text();
		c = this;
		s_score += 500;
	}

	@Override
	public boolean skill1() {

			ARSystem.playSound((Entity)player, "c1068s");
			for(int o =0; o < AMath.random(7)+7;o++) {
				int j = o;
				delay(()->{
					Location loc = player.getLocation();
					loc.setPitch(0);
					int i = j;
					if(i%2 == 0) {
						loc = ULocal.offset(loc, new Vector(-1.2+(i/2)*0.35,1.3-(i/2)*0.1,0.2+(i/2)*0.4));
					} else {
						loc = ULocal.offset(loc, new Vector(-1.2+(i/2)*0.35,1.3-(i/2)*0.1,-0.2-((i/2)*0.4)));
					}
					ARSystem.spellLocCast(player, loc, "c1068_s1");
				},o);
			}
			delay(()->{
				ARSystem.playSound((Entity)player, "c68s1");
			},20);

		return true;
	}
	
	@Override
	public boolean skill2() {

			ARSystem.playSound((Entity)player, "c1068s");
			skill("c1068_s2");
			delay(()->{ARSystem.playSound((Entity)player, "c1068s2");},20);

		return true;
	}
	
	@Override
	public boolean skill3() {

			ARSystem.playSound((Entity)player, "c1068s3");
			skill("c1068_s3");
			Location loc = player.getLocation().clone();
			for(int i =0; i<7; i++) {
				if(loc.clone().add(0,1,0).getBlock().isEmpty()) {
					loc.add(loc.getDirection().multiply(1));
				} else {
					i = 10;
				}
			}
			player.teleport(loc);
			skill("c1068_s3");

		return true;
	}
	
	
	@Override
	public boolean tick() {
		if(at1 > 0)at1--;
		if(at2 > 0)at2--;
		if(tk%20 == 0) {
			
		}
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.selectBuffType(player, BuffType.HEADCC) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.HEADCC)) {
				buff.setTime(0);
			}
		}
		if(Rule.buffmanager.selectBuffType(player, BuffType.CC) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.CC)) {
				buff.setTime(0);
			}
		}
		if(Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF) != null) {
			for(Buff buff : Rule.buffmanager.selectBuffType(player, BuffType.DEBUFF)) {
				buff.setTime(0);
			}
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*4);
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.4);
			if(at1<=0) {
				at1 = 100;
			} else if(at2<=0) {
				at2 = 100;
			} else {
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.playSound((Entity)player, "c68s3");
			}
		}
		return true;
	}
	
	@Override
	public String getBgm() {
		return "c1068";
	}
}
