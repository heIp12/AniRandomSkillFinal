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
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c44izuna;
import event.Skill;
import event.WinEvent;
import manager.AdvManager;
import manager.Bgm;
import mode.MTeam;
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

public class c83sora extends c00main{
	Player zero = null;
	
	int type = 1;
	int stack = 0;
	int cc = 0;
	
	@Override
	public void setStack(float f) {
		stack = (int) f;
	}
	
	public c83sora(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 83;
		load();
		text();
		c = this;
		
	}

	@Override
	public boolean skill1() {
		if(player.isSneaking() && type != 1) {
			skill("c83_s1c-"+type);
			ARSystem.playSound((Entity)player, "c16get", 0.8f);
		} else {
			if(stack <= 0) return false;
			skill("c83_s1-"+type);
			ARSystem.playSound((Entity)player, "c16get", 0.5f);
			stack--;
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
			if(stack < 5) stack = 5;
			return true;
		}
		cooldown[2] = 0;
		return false;
	}
	
	@Override
	public boolean skill3() {
		skill("c83_s3");
		ARSystem.playSound((Entity)player, "c83s3");
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(player.isSneaking()) {
			type--;
			if(type < 1) type = 4;
		} else {
			type++;
			if(type > 4) type = 1;
		}
		player.sendTitle(Main.GetText("c83:type"+type),Main.GetText("c83:type1") + " " + Main.GetText("c83:type2") + " " + Main.GetText("c83:type3") + " " + Main.GetText("c83:type4") + " ");
		return true;
	}
	
	@Override
	public boolean skill5() {
		if(stack < 4) {
			cooldown[5] = 0;
			return false;
		}
		stack -=4;
		for(int i=0;i<4;i++) skill("c83_s1-1");
		ARSystem.playSound((Entity)player, "c16get", 0.2f);
		return true;
	}
	
	int tick = 0;
	@Override
	public boolean tick() {
		if(tick%Math.max(5,70-(10*(skillmult+sskillmult))) == 0) {
			if(stack < 5) stack++;
		}
		tick++;
		if(tk%20 == 0) {
			if(player.isSprinting()) {
				player.damage(1, player);
			}
			scoreBoardText.add("&c ["+Main.GetText("c83:sk4")+"] : " + Main.GetText("c83:type"+type) + "["+stack+"]");
			if(Rule.c.size() <= 2 && isps && Rule.c.get(zero) != null) {
				Rule.team.getTeam("『 』").setTeamWin(true);
				ARSystem.addGameMode(new MTeam());
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
				((c84siro)Rule.c.get(pl)).sp(player,e.getPlayer());
				return;
			}
		}
		if(e.getPlayer() != player && skillCooldown(0)) {
			spskillon();
			spskillen();
			for(TeamInfo t : Rule.team.getTeams()) t.Remove();
			
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
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.damage(3,player);
			target.setVelocity(ULocal.lookAt(player.getLocation(), target.getLocation()).getDirection().multiply(1.2f));
		}
		if(n.equals("2")) {
			target.damage(1,player);
			ARSystem.giveBuff(target, new Stun(target), 20);
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
