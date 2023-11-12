package chars.c3;

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
import org.bukkit.event.entity.EntityDamageEvent;
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
import buff.Barrier;
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
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
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
import util.Text;

public class c113shimakaze extends c00main{
	int sk1 = 30;
	int sk3tick = 0;

	public c113shimakaze(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 113;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		if(sk1 > 0) {
			sk1--;
			ARSystem.playSound((Entity)player, "0gun5",2);
			skill("c113_s1");
		} else {
			cooldown[1] = 30;
			if(isps) cooldown[1] = 20;
			sk1 = 30;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c113s2");
		skill("c113_s2");
		return true;
	}
	
	int sk3 = 1;
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c113s3");
		skill("c113_s3"+sk3);
		sk3++;
		if(sk3 == 1) sk3tick = 0;
		if(sk3 > 5) sk3 = 1; 
		return true;
	}
	
	@Override
	public boolean tick() {
		if(player.getLocation().getBlock().getTypeId() == 8 || player.getLocation().getBlock().getTypeId() == 9) {
			if(player.isSneaking()) {
				if(isps) {
					player.setVelocity(player.getLocation().getDirection().multiply(1).setY(0.02));
				} else {
					player.setVelocity(player.getLocation().getDirection().multiply(0.6).setY(0.02));
				}
				skill("c113p");
			}
		}
		if(tk%20 == 0 && psopen) scoreBoardText.add("&c ["+Main.GetText("c113:sk1")+ "] : &f" + sk1 +" / 30");
		if(sk3tick > 1600) {
			sk3tick = 0;
			sk3 = 1;
		}
		
		if(Bgm.bgmcode.equals("bc113") && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c113sp");
			skillmult += 1;
			ARSystem.giveBuff(player, new Barrier(player), 1200, 10);
		}
		sk3tick++;
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player && Rule.c.size() <= 2 && ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.time <= 120) {
			Rule.playerinfo.get(player).tropy(113, 1);
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(isps) e.setDamage(e.getDamage()*2);
		} else {
			if(player.getLocation().getBlock().getTypeId() == 8 || player.getLocation().getBlock().getTypeId() == 9) {
				if(AMath.random(100) <= 30) {
					player.setVelocity(player.getLocation().getDirection().multiply(5).setY(0.02));
					e.setDamage(0);
					e.setCancelled(true);
					return false;
				}
			}
		}
		return true;
	}
}
