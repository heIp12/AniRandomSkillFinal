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

public class c8602iriya extends c00main{
	Install b;
	int sk2 = 0;
	public c8602iriya(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 2086;
		load();
		text();
		c = this;
	}
	
	@Override
	public boolean skill1() {
		if(b.getValue() > 70) {
			b.setValue(b.getValue()-70);
			ARSystem.playSound((Entity)player, "c2086s1");
			skill("c2086_s1");
		} else {
			cooldown[1] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(b.getValue() > 40) {
			b.setValue(b.getValue()-40);
			ARSystem.playSound((Entity)player, "c2086s2");
			skill("c2086_s2");
			sk2 = 60;
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
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c86db");
		return true;
	}
	
	@Override
	public boolean tick() {
		if(sk2>0) {
			sk2--;
			if(sk2%20 == 0) ARSystem.heal(player, 5);
			
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
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*2);
		} else {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*0.3);
		}
		return true;
	}
}
