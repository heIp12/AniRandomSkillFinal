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
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
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
import buff.NoHeal;
import buff.Noattack;
import buff.Nodamage;
import buff.Panic;
import buff.PowerUp;
import buff.Silence;
import buff.Stun;
import buff.TimeStop;
import buff.Timeshock;
import buff.Wound;
import chars.c.c000humen;
import chars.c.c00main;
import chars.c.c09youmu;
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

public class c132tsuna extends c00main{
	int stack = 100;
	boolean sk1 = false;
	boolean sk1s = false;
	public c132tsuna(Player p,Plugin pl,c00main ch) {
		super(p,pl,ch);
		number = 132;
		load();
		text();
		c = this;
	}

	@Override
	public void setStack(float f) {
		stack = (int)f;
	}

	@Override
	public boolean skill1() {
		if(isps) {
			ARSystem.giveBuff(player, new Stun(player), 30);
			ARSystem.giveBuff(player, new Silence(player), 30);
			ARSystem.playSound((Entity)player, "c132s12");
			delay(()->{
				skill("c132_s10");
			},20);
			
		} else {
			if(stack > 10) {
				sk1s = player.isSneaking();
				ARSystem.playSound((Entity)player, "c132s1");
				delay(()->{
					sk1 = true;
				},20);
			}
		}
		return true;
	}

	@Override
	public boolean skill2() {
		int c = 50;
		if(isps) c = 0;
		if(stack >= c) {
			stack -= c;
			ARSystem.playSound((Entity)player, "c132s2");
			skill("c132_s2");
		} else {
			cooldown[2] = 0;
		}
		return true;
	}
	
	@Override
	public boolean skill3() {
		int c = 20;
		if(isps) c = 0;
		if(stack >= c) {
			stack -= c;
			ARSystem.playSound((Entity)player, "c132s3");
			if(AMath.random(5) <= 1)ARSystem.playSound((Entity)player, "c132s3"+AMath.random(4));
			player.setVelocity(player.getLocation().getDirection().multiply(2));
			skill("c132_s3");
		}
		return true;
	}
	
	public boolean tick() {
		if(tk%20 == 0) {
			scoreBoardText.add("&c ["+Main.GetText("c132:ps")+ "] : "+ stack);
		}
		if(sk1) {
			stack--;
			if(sk1s) stack--;
			skill("c132_s1");
			if(stack <= 0) sk1 = false;

			if(sk1s) {
				stack--;
				skill("c132_s1-2");
				ARSystem.giveBuff(player, new Stun(player), 2);
			} else {
				player.setVelocity(player.getLocation().getDirection().multiply(-3.5));
			}
		} else if(tk%5 == 0 && stack < 200) {
			stack+= sskillmult + skillmult;
		}
		if(isps && tk%20 == 0 && AMath.random(20) <= 1) {
			int i = AMath.random(4);
			if(i != 3) {
				if(i == 1) {
					ARSystem.giveBuff(player, new Nodamage(player), 50);
				} else if(i == 2) {
					ARSystem.giveBuff(player, new PowerUp(player), 80, 2);
				} else if(i == 4) {
					ARSystem.giveBuff(player, new PowerUp(player), 20, 100);
				}
				ARSystem.playSound(player, "c132rb"+i);
			}
		}
		return true;
	}
	
	@Override
	public void makerSkill(LivingEntity target, String n) {
		if(n.equals("1")) {
			target.setNoDamageTicks(0);
			target.damage(0.5,player);
		} else if(n.equals("2")) {
			if(isps) {
				for(Buff b : Rule.buffmanager.selectBuffType(target, BuffType.BUFF)) {
					b.setTime(0);
				}
			}
			ARSystem.giveBuff(target, new Silence(target), 40);
			ARSystem.giveBuff(target, new Stun(target), 100);
		}
		else if(n.equals("3")) {
			target.setNoDamageTicks(0);
			target.damage(8,player);
			Wound w = new Wound(target);
			w.setValue(1);
			w.setDelay(player,40,0);
			ARSystem.giveBuff(target, w, 200);
		}
	}
	
	@Override
	public boolean entitydamage(EntityDamageByEntityEvent e, boolean isAttack) {
		if(!isAttack) {
			if(!isps && player.getHealth() - e.getDamage() <= 1 && ((LivingEntity)e.getDamager()).getHealth() / ((LivingEntity)e.getDamager()).getMaxHealth() <= 0.3f) {
				spskillon();
				spskillen();
				e.setDamage(0);
				e.setCancelled(true);
				ARSystem.playSoundAll("c132rb3");
				ARSystem.giveBuff(player, new TimeStop(player), 100);
				delay(()->{
					ARSystem.playSoundAll("0gun5",0.2f);
					skill("c132_sp");
					ARSystem.giveBuff(player, new Nodamage(player), 300);
					cooldown[1] = 0;
					setcooldown[1] *= 0.35;
  				},100);
				return false;
			}
		} else {
			if(isps) {
				ARSystem.playSound(e.getEntity(), "0fire",1.5f);
				ARSystem.spellLocCast(player, e.getEntity().getLocation(), "c132_s1p");
			}
		}
		return true;
	}

}
