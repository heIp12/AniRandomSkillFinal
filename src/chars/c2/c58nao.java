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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import event.Skill;
import manager.AdvManager;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c58nao extends c00main{
	Player target;
	
	public c58nao(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 58;
		load();
		text();
		c = this;
		target = player;
	}
	

	@Override
	public boolean skill1() {
		skill("c58_s1");
		ARSystem.playSound((Entity)player, "c58s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c58_s2");
		ARSystem.playSound((Entity)player, "c58s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		skill("c58_s3");
		ARSystem.playSound((Entity)player, "c58s3");
		return true;
	}
	

	@Override
	public boolean tick() {
		if(tk%2 == 0) {
			Player pl = null;
			double l = 1000;
			for(Player p : Rule.c.keySet()) {
				if(p != player && p.getLocation().distance(player.getLocation()) <= l) {
					pl = p;
					l = p.getLocation().distance(player.getLocation());
				}
			}
			if(pl != null && target != pl && target != null) {
				pl.hidePlayer(player);
				target.showPlayer(player);
				target = pl;
			}
		}
		if(tk%20 == 0 && target != null) {
			scoreBoardText.add("&c ["+Main.GetText("c58:ps")+ "] : "+ target.getName());
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p == player) {
			target.showPlayer(player);
		}
		if(Rule.c.size() <= 2 && e == player) {
			Rule.playerinfo.get(player).tropy(58,1);
		}
	}
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			if(Rule.c.size() <=2) e.setDamage(e.getDamage() * 10);
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.05);
		}
		return true;
	}
}
