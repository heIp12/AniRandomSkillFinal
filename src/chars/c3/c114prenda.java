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
import org.bukkit.event.entity.EntityDamageEvent;
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
import chars.c.c10bell;
import chars.c.c30siro;
import chars.c2.c60gil;
import event.Skill;
import event.WinEvent;
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
import util.Text;

public class c114prenda extends c00main{
	int tr = 0;
	int i = 0;
	boolean ister = false;
	boolean start = false;
	
	@Override
	public void setStack(float f) {
		i = (int)f;
	}
	
	public c114prenda(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 114;
		load();
		text();
		c = this;
	}

	@Override
	public boolean skill1() {
		ARSystem.playSound((Entity)player, "c114db1");
		if(player.isSneaking()){
			skill("c114_s1-2");
			cooldown[1] *= 15;
			tr+= 11;
			i += 11;
		} else {
			i++;
			tr++;
			skill("c114_s1");
		}
		if(tr >= 200) Rule.playerinfo.get(player).tropy(114, 1);
		return true;
	}
	
	@Override
	public boolean skill2() {
		ARSystem.playSound((Entity)player, "c114db3");
		if(player.isSneaking()){
			skill("c114_s2-2");
			cooldown[2] *= 5;
		} else {
			skill("c114_s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(i >= 150 && skillCooldown(0)) {
			i -= 150;
			spskillon();
			spskillen();
			ister = true;
			ARSystem.playSound((Entity)player, "c114sp");
			skill("c114_sp");
		} else {
			ARSystem.playSound((Entity)player, "c114ex");
			skill("c114_s3");
			tr+=10;
			i += 10;
		}
		if(tr >= 200) Rule.playerinfo.get(player).tropy(114, 1);
		return true;
	}
	
	@Override
	public boolean firsttick() {
		if(start) {
			if(Rule.buffmanager.OnBuffTime(player, "silence")) {
				Rule.buffmanager.selectBuffTime(player, "silence",0);
			}
		}
		return true;
	}
	@Override
	public boolean tick() {
		start = true;
		if(tk%20 == 0 && psopen) scoreBoardText.add("&c ["+Main.GetText("c114:sk0")+ "] : &f" + i +" / 150");
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			ARSystem.giveBuff(target, new Stun(target), 20);
		}
		if(n.equals("2")) {
			if(Rule.c.get(target) != null && Rule.c.get(target).getCode() == 63) {
				if(ister) {
					ister = false;
					delay(()->{
						ARSystem.playSound((Entity)player, "c114misaka");
					},20);
				}
			} else {
				target.setNoDamageTicks(0);
				target.damage(1,player);
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(2,player);
				},10);
				delay(()->{
					target.setNoDamageTicks(0);
					target.damage(3,player);
				},20);
			}
		}
	}
	
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {
			if(ARSystem.isGameMode("lobotomy")) e.setDamage(e.getDamage()*3);
			if(Rule.c.get(e.getDamager()) != null && Rule.c.get(e.getDamager()).getCode() == 63) {
				e.setDamage(e.getDamage() * 0.2);
			}
		} else {
			
		}
		return true;
	}

	@Override
	protected boolean skill9() {
		ARSystem.playSound((Entity)player, "c114db"+(AMath.random(3)+1));
		return true;
	}
}
