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

public class c95aya extends c00main{
	int s2 = 0;
	Location start;
	Location save;
	int count = 0;

	public c95aya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 95;
		load();
		text();
		c = this;
		if(p != null) start = save = player.getLocation();
	}
	
	public void Teleport(LivingEntity e) {
		if(isps && e != player) {
			ARSystem.overheal(player, e.getMaxHealth()*0.1);
			e.setNoDamageTicks(0);
			e.damage(e.getMaxHealth()*0.1,player);
			if(Rule.c.get(e) != null) {
				for(int i =0;i<10;i++) {
					Rule.c.get(e).cooldown[i] +=1;
					cooldown[1] -=1;
				}
			}
		}
		if(s2 == 0) {
			e.teleport(start);
		}
		if(s2 == 1) {
			e.teleport(save);
		}
		if(s2 == 2) {
			e.teleport(Map.randomLoc());
		}
		if(s2 == 3) {
			Player p = ARSystem.RandomPlayer();
			for(int i =0; i<100;i++) if(p == e) p = ARSystem.RandomPlayer();
			Location l = e.getLocation();
			e.teleport(p);
			p.teleport(l);
		}
	}

	@Override
	public boolean skill1() {
		hpCost(0.5, true);
		ARSystem.playSound((Entity)player, "c95s1");
		if(player.isSneaking()) {
			Teleport(player);
			skill("c95_s1_1");
		} else {
			skill("c95_s1");
		}
		count++;
		return true;
	}
	
	@Override
	public boolean skill2() {
		s2++;
		if(s2 > 3) s2 = 0;
		player.sendTitle(""+Main.GetText("c95:p"+(s2+1)), "");
		count++;
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c95s2");
		save = player.getLocation();
		count++;
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			Teleport(target);
		}
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(player != p && p.getLocation().distance(player.getLocation()) <= 6 && !isps) {
			spskillen();
			spskillon();
			ARSystem.playSound((Entity)player, "c95sp");
		}
		if(player == p && count == 0) {
			Rule.playerinfo.get(player).tropy(95,1);
		}
	}
	
	@Override
	public boolean tick() {
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {

		}
		return true;
	}
}
