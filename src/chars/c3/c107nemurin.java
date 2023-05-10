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
import buff.Sleep;
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

public class c107nemurin extends c00main{
	int tick = 0;
	boolean on = false;
	
	public c107nemurin(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 107;
		load();
		text();
		c = this;
		player.getWorld().setTime(12000);
	}

	@Override
	public boolean skill1() {
		List<Entity> entitys = ARSystem.PlayerBeamBox(player, 30, 5, box.ALL);
		if(entitys != null && entitys.size() > 0) {
			LivingEntity en = (LivingEntity) entitys.get(0);
			if(Rule.buffmanager.GetBuffTime(en, "sleep") > 0) {
				player.teleport(en);
				ARSystem.playSound((Entity)player, "c107s1");
				en.setHealth(1);
			} else {
				cooldown[1] = 0;
			}
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c107s2");
		ARSystem.giveBuff(player, new Stun(player), 40);
		ARSystem.giveBuff(player, new Silence(player), 40);
		skill("c107_s2");
		
		return true;
	}
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(Rule.buffmanager.GetBuffTime(target, "sleep") > 0) {
				Skill.remove(target, player);
			}
		}
	}

	@Override
	public boolean firsttick() {
		if(Rule.buffmanager.GetBuffTime(player, "sleep") > 0) {
			Rule.buffmanager.selectBuffTime(player, "sleep", 0);
		}
		return super.firsttick();
	}
	
	@Override
	public boolean tick() {
		player.getWorld().setTime(player.getWorld().getTime()+5);
		if(player.getWorld().getTime() > 14000) {
			if(!on) {
				on = true;
				skill("c107_p");
				skill("c107_p2");
				skill("c107_p3");
				skill("c107_p4");
			}
			if(tick > Math.max(40,120 - (int)(10*(skillmult+sskillmult)))) {
				Player target = ARSystem.RandomPlayer();
				if(target == player && Rule.c.size() > 1) target = ARSystem.RandomPlayer();
				ARSystem.playSound((LivingEntity)target, "c107p");
				ARSystem.giveBuff(target, new Sleep(target), 20 + (Rule.c.size()*10), 2);
				player.sendTitle("", "§f§l"+target.getName() + "§a[sleep]");
				tick = 0;
			}
			
			tick++;
		}
		
		if(player.getWorld().getTime() > 23000 && !isps) {
			spskillon();
			spskillen();
			Skill.remove(player, player);
		}
		return true;
	}
	
	@Override
	public void PlayerDeath(Player p, Entity e) {
		if(p != player && Rule.c.size() <= 2) {
			Rule.playerinfo.get(player).tropy(107, 1);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(((LivingEntity)e.getEntity()).getHealth() - e.getDamage() <= 1) {
				ARSystem.playSound(e.getEntity(), "c107kill");
			}
		} else {

		}
		return true;
	}
	
}
