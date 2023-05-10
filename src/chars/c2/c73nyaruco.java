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

public class c73nyaruco extends c00main{
	float p = 0;
	float s1 = 1;
	int sk2 = 60;

	public c73nyaruco(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 73;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		skill("c73_s1");
		ARSystem.playSound((Entity)player, "0sword");
		return true;
	}
	
	@Override
	public boolean skill2() {
		sk2 = 60;
		ARSystem.potion(player, 1, 60, 2);
		ARSystem.playSound((Entity)player, "c73s2");
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(isps) {
			skill("c73_s32");
		} else {
			skill("c73_s3");
		}
		ARSystem.playSound((Entity)player, "c73s3");
		return true;
	}

	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			if(AMath.random(10) == 1) {
				if(ARSystem.isGameMode("lobotomy")) {
					if(s1 < 10) {
						s1+=0.1;
					} else {
						s1+=0.02;
					}
				} else {
					s1+=0.5;
				}
				target.damage(s1*5,player);
				ARSystem.playSound((Entity)player, "c71s11");
			} else {
				if(ARSystem.isGameMode("lobotomy")) {
					if(s1 < 10) {
						s1+=0.1;
					} else {
						s1+=0.02;
					}
				} else {
					s1+=0.5;
				}
				target.damage(s1,player);
			}
		}
		if(s1 >= 12) Rule.playerinfo.get(player).tropy(73,1);
	}
	
	
	@Override
	public boolean tick() {
		if(ARSystem.AniRandomSkill != null && ARSystem.AniRandomSkill.getTime() >= 60 && !isps) {
			spskillen();
			spskillon();
			ARSystem.playSound((Entity)player, "c73sp");
			setcooldown[1] = 0.7f;
		}
		if(tk%20 == 0) {
			scoreBoardText.add("&c [Damage] : "+ AMath.round(s1,1));
		}
		if(sk2 > 0) sk2--;
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(sk2 > 0) {
				sk2 = 0;
				ARSystem.addBuff((LivingEntity) e.getEntity(),new Stun((LivingEntity) e.getEntity()), 60);
				ARSystem.addBuff((LivingEntity) e.getEntity(),new Silence((LivingEntity) e.getEntity()), 60);
			}
			p+= e.getDamage();
			for(int i = 0; i<100;i++) {
				if(p > 6) {
					p-=6;
					ARSystem.heal(player, 4);
				} else {
					i = 100;
				}
			}
		} else {
			if(isps) {
				e.setDamage(e.getDamage()*0.7);
			}
		}
		return true;
	}
	
}
