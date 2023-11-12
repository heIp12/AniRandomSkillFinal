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
import buff.Cindaella;
import buff.Fascination;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Rampage;
import buff.Silence;
import buff.Sleep;
import buff.Stun;
import buff.TimeStop;
import chars.c.c00main;
import chars.c.c05touma;
import chars.c2.c100kuroko;
import event.Skill;
import manager.AdvManager;
import types.box;
import util.AMath;
import util.GetChar;
import util.Holo;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;
import util.ULocal;

public class c6300micoto extends c00main{

	public c6300micoto(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 1063;
		load();
		text();
		c = this;
		ARSystem.playSound(player, "c63select");
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c63s1");
		ARSystem.giveBuff(player, new Stun(player), 10);
		ARSystem.giveBuff(player, new Silence(player), 10);
		delay(()->{
			ARSystem.playSoundAll("c63r1");
			skill("c1063_s1");
		},10);
		return true;
	}
	
	@Override
	public boolean skill2() {
		List<Entity> e = ARSystem.PlayerBeamBox(player, 80, 3, box.TARGET);
		ARSystem.playSound((Entity)player, "0lightning");
		if(e.size() > 0) {
			LivingEntity target = (LivingEntity)e.get(0);
			ARSystem.spellCast(player, target, "c1063_s2");
		} else {
			skill("c1063_s22");
			cooldown[1] = 0;
		}
		ARSystem.playSound((Entity)player, "c63s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		player.setVelocity(new Vector(0,-30,0));
		ARSystem.playSound((Entity)player, "c63sp2");
		ARSystem.giveBuff(player, new Nodamage(player), 80);
		for(Entity e : ARSystem.box(player, new Vector(8, 4, 8), box.TARGET)) {
			LivingEntity en  = (LivingEntity)e;
			ARSystem.giveBuff(en, new Silence(en), 20);
			ARSystem.giveBuff(en, new Stun(en), 5);
		}
		delay(()->{
			ARSystem.playSound((Entity)player, "0lightning2");
			ARSystem.giveBuff(player, new Stun(player), 80);
			Location loc = player.getLocation();
			loc.setPitch(0);
			ARSystem.spellLocCast(player, loc, "c1063_s3");
		},5);
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			
		} else {
			LivingEntity en = (LivingEntity)e.getDamager();
			ARSystem.giveBuff(en, new Noattack(en), 10);
			ARSystem.playSound(en, "0lightning");
			
			int code = Rule.c.get(e.getDamager()).number;
			if(Rule.c.get(e.getDamager()) != null && (code == 98 || code == 109)) {
				e.setDamage(e.getDamage()* 0.1);
			}
		}
		return true;
	}
	
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c05touma) {
					is = "touma";
					break;
				}
				if(Rule.c.get(e) instanceof c100kuroko) {
					is = "kuroco";
					break;
				}
			}
		}
		if(is.equals("kuroco")) {
			ARSystem.playSound((Entity)player, "c100m2");
		} else if(is.equals("touma")) {
			ARSystem.playSound((Entity)player, "c63touma");
		} else {
			ARSystem.playSound((Entity)player, "c63db");
		}
		
		return true;
	}
}
