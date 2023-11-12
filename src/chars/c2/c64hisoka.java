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
import types.box;

import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.ULocal;
import util.MSUtil;
import util.Map;

public class c64hisoka extends c00main{
	LivingEntity target;

	int tick = 0;
	int ty = 0;
	public c64hisoka(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 64;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c64s1");
		skill("c64_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		ty++;
		if(ty > 6) {
			Rule.playerinfo.get(player).tropy(64,1);
		}
		ARSystem.playSound((Entity)player, "c64s2");
		Location loc = player.getLocation();
		loc.setPitch(0);
		loc = ULocal.offset(loc, new Vector(2,0,0));
		ARSystem.giveBuff(target, new Noattack(target), 15);
		ARSystem.giveBuff(target, new Nodamage(target), 15);
		ARSystem.giveBuff(target, new Stun(target), 15);
		ARSystem.spellLocCast(player, loc, "c64_s2");
		Location locs = loc;
		delay(()->{
			target.teleport(locs);
			skill("c64_s2-2");
		},15);
		return true;
	}
	
	@Override
	public boolean skill3() {
		ARSystem.playSound((Entity)player, "c64s3");
		skill("c64_s3");
		return true;
	}

	@Override
	public boolean tick() {
		tick++;
		if(target == null) {
			target = player;
		}

		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c64:sk2")+ "] : "+ target.getName());
		}
		
		double lv = 1;
		for(Entity e : ARSystem.box(player, new Vector(15,15,15), box.TARGET) ) {
			if(e.getLocation().distance(player.getLocation()) <= 15) {
				if(((LivingEntity)e).getHealth()/((LivingEntity)e).getMaxHealth() < lv) {
					lv = ((LivingEntity)e).getHealth()/((LivingEntity)e).getMaxHealth();
				}
			}
		}
		if(lv != 1) {
			for(int i=0;i<10;i++) {
				if(cooldown[i] > 0) {
					cooldown[i] -= (1-lv)*0.05;
				}
			}
		}
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 111 && !isps) {
			spskillon();
			spskillen();
			ARSystem.playSoundAll("c64sp");
			for(Player p : Rule.c.keySet()) {
				ARSystem.giveBuff(p, new TimeStop(p), 100);
			}
			delay(()->{
				Rule.buffmanager.selectBuffValue(player, "buffac",4f);
				for(Player p : Rule.c.keySet()) {
					Rule.c.get(p).skillmult+=2;
				}
			},100);
		}
		return true;
	}


	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			target = (LivingEntity) e.getEntity();
		} else {
			if(isps) {
				e.setDamage(e.getDamage()*0.5);
			}
		}
		return true;
	}
	
}
