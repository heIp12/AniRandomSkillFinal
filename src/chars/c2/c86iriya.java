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
import buff.Install;
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
import chars.c.c30siro;
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

public class c86iriya extends c00main{
	List<Integer> card = new ArrayList<>();
	int select = 0;
	float Mp = 100;
	
	public c86iriya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 86;
		load();
		text();
		c = this;
		card.add(1);
		if(ARSystem.AniRandomSkill == null) {
			card.add(2);
			card.add(3);
			card.add(4);
			card.add(5);
			card.add(6);
		}
	}
	
	@Override
	public void setStack(float f) {
		Mp = f;
	}

	@Override
	public boolean skill1() {
		if(Mp >= 50) {
			double hhp = player.getHealth()/player.getMaxHealth();
			skill("c86_s1");
			double hp = player.getHealth();
			if(card.get(select) == 1) {
				ARSystem.playSound((Entity)player, "c1086s3");
			} else {
				ARSystem.playSound((Entity)player, "c86s"+AMath.random(2));
			}
			Install sin = new Install(player);
			sin.save(this,Mp);
			
			Rule.c.put(player, GetChar.get(player,Rule.gamerule, "86f"+ card.get(select),this));
			Rule.c.get(player).repset();
			player.setHealth(player.getMaxHealth() * hhp);
			ARSystem.giveBuff(player, sin , 100000);
			delay(()->{
				if(Rule.c.get(player) != null && !(Rule.c.get(player) instanceof c86iriya) &&(Install)Rule.buffmanager.selectBuff(player, "Install") == null) {
					sin.save(this,Mp);
					ARSystem.giveBuff(player, sin , 100000);
				}
			},10);
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(player.isSneaking()) {
			select--;
			if(select < 0) select = card.size()-1;
			player.sendTitle(Main.GetText("c86:sk2"),Main.GetText("c86:type"+card.get(select)));
		} else {
			select++;
			if(select >= card.size()) select = 0;
			player.sendTitle(Main.GetText("c86:sk2"),Main.GetText("c86:type"+card.get(select)));
		}
		return true;
	}
	
	@Override
	public boolean tick() {
		if(Mp < 500) {
			Mp +=(0.25f * (skillmult + sskillmult));
			if(ARSystem.isGameMode("lobotomy")) Mp += 1.5f;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c86:t")+"] : " + AMath.round(Mp,1));
			if(card.size() < 6 && (ARSystem.AniRandomSkill != null)) {
				for(int i=0; i<6;i++) {
					if(card.size() < 6 && card.size() < ARSystem.AniRandomSkill.time/10+1) {
						int n = AMath.random(6);
						while(card.contains(n)) n = AMath.random(6);
						card.add(n);
					}
				}
			}
		}
		if(player.getHealth() <= 3 && Mp >= 400 && Mp <= 420) {
			ARSystem.heal(player, 5);
			ARSystem.giveBuff(player, new TimeStop(player), 200);
			ARSystem.playSound((Entity)player, "c86sp");
			skill("c86sp");
			spskillon();
			spskillen();
			delay(()->{
				Rule.c.put(player, GetChar.get(player,Rule.gamerule, "86f3",this));
				Rule.c.get(player).isps = true;
			},200);
		}
		return true;
	}
	
	@Override
	protected boolean skill9() {
		List<Entity> el = ARSystem.box(player, new Vector(10,10,10),box.ALL);
		String is = "";
		for(Entity e : el) {
			if(Rule.c.get(e) != null) {
				if(Rule.c.get(e) instanceof c30siro) {
					is = "s";
					break;
				}
			}
		}
		
		if(is.equals("s")) {
			ARSystem.playSound((Entity)player, "c86siro");
		} else {
			ARSystem.playSound((Entity)player, "c86db");
		}
		
		return true;
	}
}
