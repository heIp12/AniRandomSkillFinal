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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
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
import buff.Barrier;
import buff.Buff;
import buff.Cindaella;
import buff.Curse;
import buff.Fascination;
import buff.MindControl;
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
import types.TargetMap;
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

public class c119kayaba extends c00main{
	int card = 5;
	int sk2 = 0;
	int sk3 = 0;
	
	public c119kayaba(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 119;
		load();
		text();
		c = this;
	}
	@Override
	public void setStack(float f) {
		card = (int)f;
	}

	@Override
	public boolean skill1() {
		if(card >= 2) {
			card -= 2;
		} else {
			cooldown[1] = 0;
			return true;
		}
		skill("c119_s1");
		return true;
	}
	
	@Override
	public boolean skill2() {
		if(card >= 3) {
			card -= 3;
		} else {
			cooldown[2] = 0;
			return true;
		}
		sk2++;
		if(sk2 >= 3) {
			sk2 = 0;
			if(card >= 7) {
				card -= 7;
				skill("c119_s2-0");
				ARSystem.playSound((Entity)player, "c119s22");
			} else {
				skill("c119_s2-1");
				ARSystem.playSound((Entity)player, "c119s21");
				delay(()->{
					ARSystem.playSound((Entity)player, "c119s22");
				},40);
			}

		} else {
			skill("c119_s2");
			ARSystem.playSound((Entity)player, "c119s2");
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		if(card >= 5) {
			card -= 5;
		} else {
			cooldown[3] = 0;
			return true;
		}
		sk3 = 20;
		return true;
	}
	
	@Override
	public boolean skill4() {
		if(card < 10) card++;
		ARSystem.playSound((Entity)player, "c119s4");
		return true;
	}

	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(1,player);
			
			ARSystem.giveBuff(target, new Stun(target), 4);
		}
	}

	@Override
	public boolean tick() {
		if(tk%20 == 0) scoreBoardText.add("&c ["+Main.GetText("c119:ps")+ "] : &f" + card +" / 10");
		
		if(sk3 > 0) sk3--;
		
		return true;
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(isAttack) {

		} else {
			if(sk3 > 0) {
				sk3 = 0;
				skill("c119_s3");
				ARSystem.playSound((Entity)player, "c119s3");
				delay(()->{ARSystem.playSound((Entity)player, "c119s32");},30);
				ARSystem.giveBuff(player, new Nodamage(player), 50);
				ARSystem.giveBuff((LivingEntity) e.getDamager(), new MindControl((LivingEntity) e.getDamager(),player), 160);
				e.setDamage(0);
				e.setCancelled(true);
				return false;
			}
		}
		return true;
	}
}
