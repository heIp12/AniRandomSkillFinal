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
import ars.TeamInfo;
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
import c.c000humen;
import c.c00main;
import c.c44izuna;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import manager.Holo;
import types.BuffType;
import types.box;
import types.modes;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.Local;
import util.MSUtil;
import util.Map;

public class c83sora extends c00main{
	Player zero = null;
	
	int count = 0;
	int type = 1;
	int cc = 0;
	
	public c83sora(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 83;
		load();
		text();
		c = this;
		
	}

	@Override
	public boolean skill1() {
		if(count < 5) {
			count++;
			skill("c83_s1-"+type);
			ARSystem.playSound((Entity)player, "c16get", 0.5f);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		LivingEntity target = (LivingEntity)ARSystem.boxSOne(player, new Vector(10,5,10), box.TARGET);
		if(target != null) {
			cc++;
			if(cc >= 5 ) Rule.playerinfo.get(player).tropy(83,1);
			ARSystem.playSound((Entity)player, "c83s2");
			ARSystem.giveBuff(target, new Noattack(target), 100);
			ARSystem.giveBuff(target, new Nodamage(target), 100);
			ARSystem.giveBuff(player, new Noattack(player), 100);
			ARSystem.giveBuff(player, new Nodamage(player), 100);
			return true;
		}
		cooldown[2] = 0;
		return false;
	}
	
	@Override
	public boolean skill3() {
		if(count > 0) {
			skill("c83_s3");
			ARSystem.playSound((Entity)player, "c83s3");
			count = 0;
		} else {
			cooldown[3] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill4() {
		type+=1;
		if(type > 4) {
			type = 1;
		}
		player.sendTitle(Main.GetText("c83:type"+type),Main.GetText("c83:type1") + " " + Main.GetText("c83:type2") + " " + Main.GetText("c83:type3") + " " + Main.GetText("c83:type4") + " ");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(tk%20 == 0) {
			if(player.isSprinting()) {
				player.damage(1, player);
			}
			scoreBoardText.add("&c ["+Main.GetText("c83:sk4")+"] : " + Main.GetText("c83:type"+type));
			if(Rule.c.size() <= 2 && isps && Rule.c.get(zero) != null) {
				Rule.team.getTeam("『 』").setTeamWin(true);
				ARSystem.gameMode = modes.TEAM;
				ARSystem.Stop();
			}
		}
		return true;
	}
	
	@Override
	public void WinEvent(event.WinEvent e) {
		if(Rule.c.get(e.getPlayer()) instanceof c84siro || Rule.c.get(e.getPlayer()) instanceof c83sora) return;
		for(Player pl : Rule.c.keySet()) {
			if(Rule.c.get(pl) instanceof c84siro) {
				e.setCancelled(true);
				spskillon();
				spskillen();
				((c84siro)Rule.c.get(pl)).sp(player);
				return;
			}
		}
		if(e.getPlayer() != player && skillCooldown(0)) {
			spskillon();
			spskillen();
			for(Player pl: Bukkit.getOnlinePlayers()) {
				pl.stopSound("");
			}
			Bgm.setBgm("c83");
			ARSystem.playSoundAll("c83sp2");
			for(int i=0;i<10;i++) {
				Rule.c.get(e.getPlayer()).cooldown[i] = 999;
				Rule.c.get(e.getPlayer()).skillmult = 0;
				Rule.c.get(e.getPlayer()).sskillmult = 0;
			}
			e.setCancelled(true);
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(zero != null &&p == zero) {
			zero = null;
			delay(()->{
				Skill.death(player, e);
				ARSystem.Stop();
			},10);
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) e.setDamage(e.getDamage() * 2f);
		} else {
			if(player.getLocation().getBlock().getLightLevel() <= 8) {
				e.setDamage(e.getDamage() * 0.5f);
			}
			if(isps) e.setDamage(e.getDamage() * 0.5f);
		}
		
		return true;
	}

}
