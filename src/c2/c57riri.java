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
import buff.Cindaella;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import c.c00main;
import c.c10bell;
import event.Skill;
import manager.AdvManager;
import manager.Holo;
import types.box;
import util.AMath;
import util.GetChar;
import util.InvSkill;
import util.Inventory;
import util.MSUtil;
import util.Map;

public class c57riri extends c00main{
	
	public c57riri(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 57;
		load();
		text();
		c = this;
	}
	

	@Override
	public boolean skill1() {
		if(isps) {
			skill("c57_s12");
		} else {
			skill("c57_s1");
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		skill("c57_s2");
		ARSystem.playSound((Entity)player, "c57s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(player.getMaxHealth() >= 3 && !isps) {
			hp-=2;
			player.setMaxHealth(player.getMaxHealth()-2);
			int i,j = 0;
			if(!ARSystem.gameMode2) {
				cooldown[3] = setcooldown[3]/2;
			}
			Cindaella sin = new Cindaella(player);
			sin.save(this);
			delay(()->{
				ARSystem.giveBuff(player, sin , 780);
			},20);
			while(j < 1000) {
				i = (int) (Math.random()* GetChar.getCount());
				if(Main.GetText("c"+(i+1)+":tag").contains("tg3") && i != 56) {
					Rule.c.put(player, GetChar.get(player,Rule.gamerule, ""+(i+1),this));
					Rule.c.get(player).repset();
					break;
				}
				j++;
			}
		} else if(isps){
			int i,j = 0;
			Cindaella sin = new Cindaella(player);
			sin.save(this);
			delay(()->{
				ARSystem.giveBuff(player, sin , 580);
			},20);
			while(j < 1000) {
				i = (int) (Math.random()* GetChar.getCount());
				if(Main.GetText("c"+(i+1)+":tag").contains("tg3") && i != 56) {
					Rule.c.put(player, GetChar.get(player,Rule.gamerule, ""+(i+1),this));
					Rule.c.get(player).repset();
					break;
				}
				j++;
			}
		}
		return true;
	}
	

	@Override
	public boolean tick() {
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 60 && s_damage >= 30 && !isps) {
			spskillon();
			spskillen();
			hp = 30;
			player.setMaxHealth(30);
			player.setHealth(30);
			setcooldown[2] = 2;
			setcooldown[3] = 1;
			Rule.playerinfo.get(player).tropy(57,1);
			ARSystem.playSound((Entity)player, "c57sp");
		}
		if(tk%200 == 0) {
			ARSystem.heal(player,2);
		}
		return true;
	}

	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(AMath.random(10) <= 3) {
				e.setDamage(0);
				ARSystem.playSound((Entity)player, "0miss");
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
				if(Rule.c.get(e) instanceof c10bell) {
					is = "bell";
					break;
				}
			}
		}
		
		if(is.equals("bell")) {
			ARSystem.playSound((Entity)player, "c57c2");
		} else {
			ARSystem.playSound((Entity)player, "c57db");
		}
		
		return true;
	}
}
