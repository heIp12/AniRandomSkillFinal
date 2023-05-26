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
import chars.c3.c111artorya;
import chars.c3.c126hera;
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

public class c8604iriya extends c00main{
	Install b;
	int sk1 = 0;
	public c8604iriya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 4086;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		if(b.getValue() > 10) {
			b.setValue(b.getValue()-200);
			ARSystem.playSoundAll("c4086s1");
			ARSystem.addBuff(player, new Silence(player), 60);
			ARSystem.addBuff(player, new Stun(player), 80);
			delay(()->{sk1 = 18;},50);
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(b.getValue() > 2) {
			b.setValue(b.getValue()-2);
			skill("c4086_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill5() {
		Buff b = Rule.buffmanager.selectBuff(player, "Install");
		b.setTime(2);
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk1 > 0) {
			sk1--;
			skill("c4086_s1");
		}
		if(tk%20 == 0) {
			if(b == null) b = (Install)Rule.buffmanager.selectBuff(player, "Install");
			if(b != null) {
				scoreBoardText.add("&c ["+Main.GetText("c86:t")+"] : " + AMath.round(b.getValue(),1));
			}
		}
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*5);
		} else {
			if(Rule.c.get(e.getDamager()) != null && Rule.c.get(e.getDamager()).number%1000 == 126) {
				e.setDamage(0.1);
				e.setCancelled(true);
				return false;
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
				if(Rule.c.get(e) instanceof c126hera) {
					is = "h";
					break;
				}

			}
		}

		if(is.equals("h")) {
			ARSystem.playSound((Entity)player, "c126iriya");
		} else {
			ARSystem.playSound((Entity)player, "c86db");
		}
		return true;
	}
}
